package com.example.prospectos.Clases;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class Decodificaciones {

    Context context;
    HashMap<String, String> lista;

    public Decodificaciones(Context context) {
        this.context = context;
    }
    public Decodificaciones(Context context, HashMap<String, String> lista) {
        this.context = context;
        this.lista = lista;
    }

    /*
    * Método que prepara el archivo para despues enviarlo con Retrofit
    * */
    public MultipartBody.Part prepararArchivo(String partName, String uriString){
        Uri uri = Uri.parse(uriString);
        File file = new File(uri.getPath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        return MultipartBody.Part.createFormData(partName, file.getName(), requestBody);
    }
}
