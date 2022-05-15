package com.example.prospectos.Models;

import okhttp3.MultipartBody;

public class DocumentoMultipart {
    String nombre;
    MultipartBody.Part parte;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MultipartBody.Part getParte() {
        return parte;
    }

    public void setParte(MultipartBody.Part parte) {
        this.parte = parte;
    }
}
