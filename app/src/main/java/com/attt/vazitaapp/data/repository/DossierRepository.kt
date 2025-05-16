package com.attt.vazitaapp.data.repository

import android.content.Context
import android.util.Log
import com.attt.vazitaapp.data.manager.TokenManager
import com.attt.vazitaapp.data.model.Chapitre
import com.attt.vazitaapp.data.model.Dossier
import com.attt.vazitaapp.data.model.User
import com.attt.vazitaapp.data.requestModel.GetUserInfoResponse
import com.attt.vazitaapp.data.requestModel.LogoutResponse
import com.attt.vazitaapp.data.source.remote.Services
import com.attt.vazitaapp.data.requestModel.SignInRequest
import com.attt.vazitaapp.data.requestModel.SignInResponse
import com.attt.vazitaapp.data.requestModel.SubmitDossierRequest
import com.attt.vazitaapp.data.requestModel.SubmitDossierResponse
import com.attt.vazitaapp.data.source.local.UserLocally
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.sign

class DossierRepository (
    context: Context?= null
){


    suspend fun getDossierByPisteId(pisteid:String?): List<Dossier>? {
        return withContext(Dispatchers.IO) {
            try {
                val call =Services.getDossierService().getDossierByPisteId(pisteid.toString())
                val response = withContext(Dispatchers.IO) { call.execute() }
                val signinResponse = response.body()
                println("response code: ${response.code()}")
                println("body: " + signinResponse)
                if (signinResponse == null) {
                    null
                } else {

                    signinResponse




                }
            } catch (e: Exception) {
                null
            }

        }
    }

    suspend fun getChapitres(): List<Chapitre>? {
        return withContext(Dispatchers.IO) {
            try {
                val call =Services.getDossierService().getChapitres()
                val response = withContext(Dispatchers.IO) { call.execute() }
                val signinResponse = response.body()
                println("response code: ${response.code()}")
                println("body: " + signinResponse)
                if (signinResponse == null) {
                    null
                } else {

                    signinResponse




                }
            } catch (e: Exception) {
                null
            }

        }
    }




    suspend fun submitDossier(dossierId:String,alterationList:List<Int>): SubmitDossierResponse{
        return withContext(Dispatchers.IO) {
            try {
                val call = Services
                    .getDossierService()
                    .submitDossier(dossierId,
                        SubmitDossierRequest(alterationList)
                )
                val response = withContext(Dispatchers.IO) { call.execute() }
                val submitDossierResponse = response.body()
                println("response code: ${response.code()}")
                println("body: " + submitDossierResponse)
                if (submitDossierResponse == null) {
                    SubmitDossierResponse(response.code(), "erreur lors de l\'enregistrement de dossier", null)
                } else {

                    SubmitDossierResponse(response.code(), submitDossierResponse.message, submitDossierResponse.data)

                }
            } catch (e: Exception) {
                SubmitDossierResponse(500, e.message, null)

            }
        }
    }


}