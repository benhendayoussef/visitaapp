package com.attt.vazitaapp.modelView;

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.attt.vazitaapp.data.model.Chapitre
import com.attt.vazitaapp.data.model.Dossier
import com.attt.vazitaapp.data.model.PointDefault
import com.attt.vazitaapp.data.repository.DossierRepository
import com.attt.vazitaapp.data.requestModel.SubmitDossierResponse
import javax.inject.Inject;
import kotlin.Int

public class DossierViewModel @Inject constructor() :ViewModel() {

    private val dossierRepository = DossierRepository()

    private val _pisteId = MutableLiveData<String?>()
    val pisteId: LiveData<String?> get() = _pisteId

    private val _dossier = MutableLiveData<List<Dossier>>()
    val dossier: LiveData<List<Dossier>> get() = _dossier

    private val _selectedDossier = MutableLiveData<Dossier?>()
    val selectedDossier: LiveData<Dossier?> get() = _selectedDossier

    private val _posteId = MutableLiveData<String?>()
    val posteId: LiveData<String?> get() = _posteId

    private val _chapitres = MutableLiveData<List<Chapitre>>()
    val chapitres: LiveData<List<Chapitre>> get() = _chapitres
    private val _selectedChapitre = MutableLiveData<Chapitre?>()
    val selectedChapitre: LiveData<Chapitre?> get() = _selectedChapitre
    private val _selectedPoint = MutableLiveData<PointDefault?>()
    val selectedPoint: LiveData<PointDefault?> get() = _selectedPoint

    fun setSelectedDossier(dossier: Dossier) {
        _selectedDossier.value = dossier
    }
    fun getSelectedDossier(): Dossier? {
        return _selectedDossier.value
    }
    fun setPisteId(id: String) {
        _pisteId.value = id
    }
    fun getPisteId(): String? {
        return _posteId.value
    }


    fun setSelectedPoint(point: PointDefault?) {
        _selectedPoint.value = point
        _chapitres.value = _chapitres.value?.map { chap ->
            if(chap.codeChapitre==point?.codeChapitre)
            chap.copy(
                pointDefautResponses = chap.pointDefautResponses.map { pointDefault ->
                    if (point?.codePoint == pointDefault.codePoint) {
                        pointDefault.copy(
                            isViewed = true
                        )
                    } else {
                        pointDefault
                    }
                }
            )
            else {
                chap
            }
        }
    }
    fun getSelectedPoint(): PointDefault? {
        return _selectedPoint.value
    }

    fun setSelectedChapitre(chapitre: Chapitre?) {
        Log.d("selected Chapitre",chapitre?.codeChapitre.toString())
        _selectedChapitre.value = chapitre
        _chapitres.value = _chapitres.value?.map { chap ->
            if (chapitre?.codeChapitre == chap.codeChapitre) {
                chap.copy(
                    isViewed = true
                )
            } else {
                chap
            }
        }
    }
    fun getSelectedChapitre(): Chapitre? {
        return _selectedChapitre.value
    }


    fun setChapitres(chapitres: List<Chapitre>) {
        _chapitres.value = chapitres
    }

    fun getChapitres(): List<Chapitre>? {
        return _chapitres.value
    }

    fun getChapitreById(id: Int): Chapitre? {
        return _chapitres.value?.find { it.codeChapitre == id }
    }

    fun resetChapitres() {
        _chapitres.value = _chapitres.value?.map { chapitre ->
            chapitre.copy(
                pointDefautResponses = chapitre.pointDefautResponses.map { point ->
                    point.copy(
                        isViewed = false,
                        alterationResponses = point.alterationResponses.map {
                            it.copy(isSelected = false)
                        }
                    )
                },
                isViewed = false
            )
        }
        _selectedChapitre.value=null
        _selectedPoint.value=null


    }

    fun setChapitreViewed(id: Int) {
        _chapitres.value = _chapitres.value?.map { chapitre ->
            if (chapitre.codeChapitre == id) {
                chapitre.copy(isViewed = true)
            } else {
                chapitre
            }
        }

    }

    fun setPointDefaultViewed(chapitreId: Int, pointId: Int) {
        _chapitres.value = _chapitres.value?.map { chapitre ->
            if (chapitre.codeChapitre == chapitreId) {
                chapitre.copy(pointDefautResponses = chapitre.pointDefautResponses.map { point ->
                    if (point.codePoint == pointId) {
                        point.copy(isViewed = true)
                    } else {
                        point
                    }
                })
            } else {
                chapitre
            }
        }
    }

    fun changeAlterationSelected(chapitreId: Int?, pointId: Int?, alterationId: Int?) {
        _chapitres.value = _chapitres.value?.map { chapitre ->
            if (chapitre.codeChapitre == chapitreId) {
                chapitre.copy(pointDefautResponses = chapitre.pointDefautResponses.map { point ->
                    if (point.codePoint == pointId) {
                        point.copy(alterationResponses = point.alterationResponses.map { alteration ->
                            if (alteration.codeAlteration == alterationId) {
                                alteration.copy(isSelected = !alteration.isSelected)

                            } else {
                                alteration.copy(isSelected = false)
                            }
                        })
                    } else {
                        point
                    }
                })
            } else {
                chapitre
            }
        }
        if(_selectedPoint.value?.codePoint==pointId){
            _selectedPoint.value=_selectedPoint.value?.copy(
                alterationResponses = _selectedPoint.value?.alterationResponses?.map { alteration ->
                    if (alteration.codeAlteration == alterationId) {
                        alteration.copy(isSelected = !alteration.isSelected)
                    } else {
                        alteration.copy(isSelected = false)
                    }
                }?: emptyList()
            )
        }
        if(_selectedChapitre.value?.codeChapitre==chapitreId){
            _selectedChapitre.value=_selectedChapitre.value?.copy(
                pointDefautResponses = _selectedChapitre.value?.pointDefautResponses?.map { point ->
                    if (point.codePoint == pointId) {
                        point.copy(alterationResponses = point.alterationResponses.map { alteration ->
                            if (alteration.codeAlteration == alterationId) {
                                alteration.copy(isSelected = !alteration.isSelected)

                            } else {
                                alteration.copy(isSelected = false)
                            }
                        })
                    } else {
                        point
                    }
                }?:emptyList()
            )
        }
    }

    suspend fun updateDossierStructure(){
        /*_chapitres.value = List(3) { it ->
            when (it) {
                0 -> Chapitre(
                    CODE_CHAPITRE = it,
                    LIBELLE_CHAPITRE = "Chapitre $it",
                    pointsDefault = List(3) { it2 ->
                        PointDefault(
                            CODE_POINT = it * 10 + it2,
                            LIBELLE_POINT = "Pointa ${it * 10 + it2}",
                            CODE_CHAPITRE = it,
                            alterations = List(3) { it3 ->
                                Alteration(
                                    CODE_ALTERATION = it * 100 + it2 * 10 + it3,
                                    LIBELLE_ALTERATION = "Alteration $it",
                                    CODE_CHAPITRE = it,
                                    CODE_POINT = it2
                                )
                            }
                        )
                    }
                )
                1 -> Chapitre(
                    CODE_CHAPITRE = it,
                    LIBELLE_CHAPITRE = "Chapitre $it",
                    pointsDefault = List(2) { it2 ->
                        PointDefault(
                            CODE_POINT = it * 10 + it2,
                            LIBELLE_POINT = "Point ${it * 10 + it2}",
                            CODE_CHAPITRE = it,
                            alterations = List(3) { it3 ->
                                Alteration(
                                    CODE_ALTERATION = it * 100 + it2 * 10 + it3,
                                    LIBELLE_ALTERATION = "Alteration $it",
                                    CODE_CHAPITRE = it,
                                    CODE_POINT = it2
                                )
                            }
                        )
                    }
                )
                else -> Chapitre(
                    CODE_CHAPITRE = it,
                    LIBELLE_CHAPITRE = "Chapitre $it",
                    pointsDefault = List(4) { it2 ->
                        PointDefault(
                            CODE_POINT = it * 10 + it2,
                            LIBELLE_POINT = "Point ${it * 10 + it2}",
                            CODE_CHAPITRE = it,
                            alterations = List(3) { it3 ->
                                Alteration(
                                    CODE_ALTERATION = it * 100 + it2 * 10 + it3,
                                    LIBELLE_ALTERATION = "Alteration $it",
                                    CODE_CHAPITRE = it,
                                    CODE_POINT = it2
                                )
                            }
                        )
                    }
                )
            }
        }*/
        _chapitres.value = dossierRepository.getChapitres()
    }

    suspend fun updateDossier(dossiers: List<Dossier>) {
        _dossier.value = dossiers
    }

    suspend fun loadDossier() {
        /*_dossier.value = List(8) { it ->
            Dossier(
                numDossier = it,
                numChassis = "Chassis $it",
                immatriculation = "Immatriculation $it",
                cPiste = it,
                dateHeureEnregistrement = "Date $it"
            )
            Dossier(
                numDossier = it,
                numChassis = "Chassis $it",
                immatriculation = "Immatriculation $it",
                cPiste = it,
                dateHeureEnregistrement = "Date $it"
            )
            Dossier(
                numDossier = it,
                numChassis = "Chassis $it",
                immatriculation = "Immatriculation $it",
                cPiste = it,
                dateHeureEnregistrement = "Date $it"
            )
            Dossier(
                numDossier = it,
                numChassis = "Chassis $it",
                immatriculation = "Immatriculation $it",
                cPiste = it,
                dateHeureEnregistrement = "Date $it"
            )

            Dossier(
                numDossier = it,
                numChassis = "Chassis $it",
                immatriculation = "Immatriculation $it",
                cPiste = it,
                dateHeureEnregistrement = "Date $it"
            )

            Dossier(
                numDossier = it,
                numChassis = "Chassis $it",
                immatriculation = "Immatriculation $it",
                cPiste = it,
                dateHeureEnregistrement = "Date $it"
            )

            Dossier(
                numDossier = it,
                numChassis = "Chassis $it",
                immatriculation = "Immatriculation $it",
                cPiste = it,
                dateHeureEnregistrement = "Date $it"
            )

            Dossier(
                numDossier = it,
                numChassis = "Chassis $it",
                immatriculation = "Immatriculation $it",
                cPiste = it,
                dateHeureEnregistrement = "Date $it"
            )

        }*/

        _dossier.value=dossierRepository.getDossierByPisteId(pisteid = pisteId.value)
    }

    fun removeDossier(dossierId:String){
        _dossier.value = _dossier.value?.filter { it.numDossier.toString() != dossierId }
    }

    suspend fun submitDossier(dossierId:String,alterationList:List<Int>,onFinish:(SubmitDossierResponse) -> Unit) {
        val response= dossierRepository.submitDossier(dossierId,alterationList)
        if(response.code==200){
            removeDossier(dossierId)
        }
        onFinish(response)


    }

}
