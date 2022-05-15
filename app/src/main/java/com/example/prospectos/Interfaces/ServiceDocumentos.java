package com.example.prospectos.Interfaces;

import com.example.prospectos.ModelsReqRes.ResponseGeneral;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;

public interface ServiceDocumentos {

    @Multipart
    @POST("documentos/add/{id}")
    Call<ResponseGeneral> agregarDocumentos(
            @Path("id") int id,
            @Part MultipartBody.Part[] archivos,
            @Part("nombreArchivo") RequestBody nombre,
            @QueryMap Map<String, String> options
            );

    @Streaming
    @GET("documentos/file/download/file/{id}")
    Call<ResponseGeneral> descargarArchivoFile(@Path("id") int id);

    @GET("documentos/file/download/url/{id}")
    Call<ResponseGeneral> descargarArchivoUrl(@Path("id") int id);
}
