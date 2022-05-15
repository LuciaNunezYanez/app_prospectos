package com.example.prospectos.ModelsReqRes;

public class File {
    String nombre;
    String url;
    String extension;

    public File() {
    }

    public File(String nombre, String url, String extension) {
        this.nombre = nombre;
        this.url = url;
        this.extension = extension;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
