package com.example.prospectos.Interfaces;

import com.example.prospectos.Models.Evaluacion;
import com.example.prospectos.ModelsReqRes.ResponseGeneral;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceEvaluacion {

    @PUT("evaluacion/{id}")
    Call<ResponseGeneral> evaluarProspecto(@Path("id") int id, @Body Evaluacion evaluacion);

}
