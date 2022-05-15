package com.example.prospectos.Interfaces;

import com.example.prospectos.ModelsReqRes.ResponseLogin;
import com.example.prospectos.Models.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceLogin {

    @POST("login")
    Call<ResponseLogin> login(@Body Usuario usuario);

}