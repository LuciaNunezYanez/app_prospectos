package com.example.prospectos.Interfaces;

import com.example.prospectos.Models.ProspectoCompleto;
import com.example.prospectos.ModelsReqRes.ResponseListProspectos;
import com.example.prospectos.ModelsReqRes.ResponseProspecto;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceProspectos {

    @POST("prospectos/add")
    Call<ResponseProspecto> agregarProspecto(@Body HashMap<String, Object> body);

    @GET("prospectos/mios/{id}")
    Call<ResponseListProspectos> getListProspectos(@Path("id") int id);

    @GET("prospectos/{estatus}")
    Call<ResponseListProspectos> getListProspectosEstatus(@Path("estatus") String estatus);

    @GET("prospectos/unique/{id}")
    Call<ProspectoCompleto> getProspectoCompleto(@Path("id") int id);
}
