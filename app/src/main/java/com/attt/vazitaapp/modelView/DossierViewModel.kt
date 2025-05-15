package com.attt.vazitaapp.modelView;

import android.util.Log
import androidx.compose.animation.core.exponentialDecay
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.attt.vazitaapp.data.model.Alteration
import com.attt.vazitaapp.data.model.Chapitre
import com.attt.vazitaapp.data.model.Dossier
import com.attt.vazitaapp.data.model.PointDefault
import javax.inject.Inject;

public class DossierViewModel @Inject constructor() :ViewModel() {

    private val _pisteId = MutableLiveData<Int?>()
    val pisteId: LiveData<Int?> get() = _pisteId

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
        _posteId.value = id
    }
    fun getPisteId(): String? {
        return _posteId.value
    }


    fun setSelectedPoint(point: PointDefault?) {
        _selectedPoint.value = point
    }
    fun getSelectedPoint(): PointDefault? {
        return _selectedPoint.value
    }

    fun setSelectedChapitre(chapitre: Chapitre?) {
        Log.d("selected Chapitre",chapitre?.CODE_CHAPITRE.toString())
        _selectedChapitre.value = chapitre
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
        return _chapitres.value?.find { it.CODE_CHAPITRE == id }
    }

    fun resetChapitres() {
        _chapitres.value = _chapitres.value?.map { chapitre ->
            chapitre.copy(
                pointsDefault = chapitre.pointsDefault.map { point ->
                    point.copy(
                        isViewed = false,
                        alterations = point.alterations.map {
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
            if (chapitre.CODE_CHAPITRE == id) {
                chapitre.copy(isViewed = true)
            } else {
                chapitre
            }
        }

    }

    fun setPointDefaultViewed(chapitreId: Int, pointId: Int) {
        _chapitres.value = _chapitres.value?.map { chapitre ->
            if (chapitre.CODE_CHAPITRE == chapitreId) {
                chapitre.copy(pointsDefault = chapitre.pointsDefault.map { point ->
                    if (point.CODE_POINT == pointId) {
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

    fun changeAlterationSelected(chapitreId: Int, pointId: Int, alterationId: Int) {
        _chapitres.value = _chapitres.value?.map { chapitre ->
            if (chapitre.CODE_CHAPITRE == chapitreId) {
                chapitre.copy(pointsDefault = chapitre.pointsDefault.map { point ->
                    if (point.CODE_POINT == pointId) {
                        point.copy(alterations = point.alterations.map { alteration ->
                            if (alteration.CODE_ALTERATION == alterationId) {
                                alteration.copy(isSelected = !alteration.isSelected)

                            } else {
                                alteration
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
        if(_selectedPoint.value?.CODE_POINT==pointId){
            _selectedPoint.value=_selectedPoint.value?.copy(
                alterations = _selectedPoint.value?.alterations?.map { alteration ->
                    if (alteration.CODE_ALTERATION == alterationId) {
                        alteration.copy(isSelected = !alteration.isSelected)
                    } else {
                        alteration
                    }
                }?: emptyList()
            )
        }
    }

    suspend fun updateDossierStructure(){
        _chapitres.value=List<Chapitre>(3){
            Chapitre(
                CODE_CHAPITRE = it,
                LIBELLE_CHAPITRE = "Chapitre $it",
                pointsDefault = List(3) {it2->
                    PointDefault(
                        CODE_POINT = it*10+it2,
                        LIBELLE_POINT = "Point $it",
                        CODE_CHAPITRE = it,
                        alterations = List(3) {it3->
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                        }
                    )
                    PointDefault(
                        CODE_POINT = it*10+it2,
                        LIBELLE_POINT = "Point $it",
                        CODE_CHAPITRE = it,
                        alterations = List(3) {it3->
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                        }
                    )
                    PointDefault(
                        CODE_POINT = it*10+it2,
                        LIBELLE_POINT = "Point $it",
                        CODE_CHAPITRE = it,
                        alterations = List(3) {it3->
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                        }
                    )
                }
            )
            Chapitre(
                CODE_CHAPITRE = it,
                LIBELLE_CHAPITRE = "Chapitre $it",
                pointsDefault = List(2) {it2->
                    PointDefault(
                        CODE_POINT = it*10+it2,
                        LIBELLE_POINT = "Point $it",
                        CODE_CHAPITRE = it,
                        alterations = List(3) {it3->
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                        }
                    )
                    PointDefault(
                        CODE_POINT = it*10+it2,
                        LIBELLE_POINT = "Point $it",
                        CODE_CHAPITRE = it,
                        alterations = List(3) {it3->
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                        }
                    )
                }
            )
            Chapitre(
                CODE_CHAPITRE = it,
                LIBELLE_CHAPITRE = "Chapitre $it",
                pointsDefault = List(4) {it2->
                    PointDefault(
                        CODE_POINT = it*10+it2,
                        LIBELLE_POINT = "Point $it",
                        CODE_CHAPITRE = it,
                        alterations = List(3) {it3->
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                        }
                    )
                    PointDefault(
                        CODE_POINT = it*10+it2,
                        LIBELLE_POINT = "Point $it",
                        CODE_CHAPITRE = it,
                        alterations = List(3) {it3->
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                        }
                    )
                    PointDefault(
                        CODE_POINT = it*10+it2,
                        LIBELLE_POINT = "Point $it",
                        CODE_CHAPITRE = it,
                        alterations = List(3) {it3->
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                        }

                    )
                    PointDefault(
                        CODE_POINT = it*10+it2,
                        LIBELLE_POINT = "Point $it",
                        CODE_CHAPITRE = it,
                        alterations = List(3) {it3->
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                            Alteration(
                                CODE_ALTERATION = it*100+it2*10+it3,
                                LIBELLE_ALTERATION = "Alteration $it",
                                CODE_CHAPITRE = it,
                                CODE_POINT = it2
                            )
                        }
                    )
                }
            )
        }
    }

    suspend fun updateDossier(dossiers: List<Dossier>) {
        _dossier.value = dossiers
    }

    suspend fun loadDossier() {
        _dossier.value = List(8) { it ->
            Dossier(
                N_DOSSIER = it,
                NUM_CHASSIS = "Chassis $it",
                IMMATRICULATION = "Immatriculation $it",
                C_PISTE = "Piste $it",
                DATE_HEURE_ENREGISTREMENT = "Date $it"
            )
            Dossier(
                N_DOSSIER = it,
                NUM_CHASSIS = "Chassis $it",
                IMMATRICULATION = "Immatriculation $it",
                C_PISTE = "Piste $it",
                DATE_HEURE_ENREGISTREMENT = "Date $it"
            )
            Dossier(
                N_DOSSIER = it,
                NUM_CHASSIS = "Chassis $it",
                IMMATRICULATION = "Immatriculation $it",
                C_PISTE = "Piste $it",
                DATE_HEURE_ENREGISTREMENT = "Date $it"
            )
            Dossier(
                N_DOSSIER = it,
                NUM_CHASSIS = "Chassis $it",
                IMMATRICULATION = "Immatriculation $it",
                C_PISTE = "Piste $it",
                DATE_HEURE_ENREGISTREMENT = "Date $it"
            )

            Dossier(
                N_DOSSIER = it,
                NUM_CHASSIS = "Chassis $it",
                IMMATRICULATION = "Immatriculation $it",
                C_PISTE = "Piste $it",
                DATE_HEURE_ENREGISTREMENT = "Date $it"
            )

            Dossier(
                N_DOSSIER = it,
                NUM_CHASSIS = "Chassis $it",
                IMMATRICULATION = "Immatriculation $it",
                C_PISTE = "Piste $it",
                DATE_HEURE_ENREGISTREMENT = "Date $it"
            )

            Dossier(
                N_DOSSIER = it,
                NUM_CHASSIS = "Chassis $it",
                IMMATRICULATION = "Immatriculation $it",
                C_PISTE = "Piste $it",
                DATE_HEURE_ENREGISTREMENT = "Date $it"
            )

            Dossier(
                N_DOSSIER = it,
                NUM_CHASSIS = "Chassis $it",
                IMMATRICULATION = "Immatriculation $it",
                C_PISTE = "Piste $it",
                DATE_HEURE_ENREGISTREMENT = "Date $it"
            )

        }
    }

}
