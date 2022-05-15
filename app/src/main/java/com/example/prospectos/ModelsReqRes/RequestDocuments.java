package com.example.prospectos.ModelsReqRes;

import java.util.List;

import okhttp3.MultipartBody;

public class RequestDocuments {
    int id_prospecto;
    List<String> nombre_documento;
    MultipartBody.Part[] archivos;

    public int getId_prospecto() {
        return id_prospecto;
    }

    public void setId_prospecto(int id_prospecto) {
        this.id_prospecto = id_prospecto;
    }

    public List<String> getNombre_documento() {
        return nombre_documento;
    }

    public void setNombre_documento(List<String> nombre_documento) {
        this.nombre_documento = nombre_documento;
    }

    public MultipartBody.Part[] getArchivos() {
        return archivos;
    }

    public void setArchivos(MultipartBody.Part[] archivos) {
        this.archivos = archivos;
    }
}
