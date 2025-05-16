package com.attt.vazitaapp.data.source.remote;

import com.attt.vazitaapp.data.model.Chapitre;
import com.attt.vazitaapp.data.model.Dossier;
import com.attt.vazitaapp.data.requestModel.SubmitDossierRequest;
import com.attt.vazitaapp.data.requestModel.SubmitDossierResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DossierService {


    @GET("api/v1/dossiers/{id}")
    Call<List<Dossier>> getDossierByPisteId(@Path("id") String id);

    @GET("api/v1/chapitres")
    Call<List<Chapitre>> getChapitres();

    @POST("api/v1/dossiers/{dossierId}")
    Call<SubmitDossierResponse> submitDossier(@Path("dossierId") String dossierId, @Body SubmitDossierRequest submitDossierRequest);

}
