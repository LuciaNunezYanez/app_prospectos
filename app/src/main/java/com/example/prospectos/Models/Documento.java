package com.example.prospectos.Models;

public class Documento {

    int id_documento;
    int id_prospecto_documento;
    String fecha_documento;
    String nombre_documento;
    String ruta_documento;

    public Documento() {
    }

    public Documento(String nombre_documento, String ruta_documento) {
        this.nombre_documento = nombre_documento;
        this.ruta_documento = ruta_documento;
    }

    public Documento(int id_documento, int id_prospecto_documento, String fecha_documento, String nombre_documento, String ruta_documento) {
        this.id_documento = id_documento;
        this.id_prospecto_documento = id_prospecto_documento;
        this.fecha_documento = fecha_documento;
        this.nombre_documento = nombre_documento;
        this.ruta_documento = ruta_documento;
    }

    public int getId_documento() {
        return id_documento;
    }

    public void setId_documento(int id_documento) {
        this.id_documento = id_documento;
    }

    public int getId_prospecto_documento() {
        return id_prospecto_documento;
    }

    public void setId_prospecto_documento(int id_prospecto_documento) {
        this.id_prospecto_documento = id_prospecto_documento;
    }

    public String getFecha_documento() {
        return fecha_documento;
    }

    public void setFecha_documento(String fecha_documento) {
        this.fecha_documento = fecha_documento;
    }

    public String getNombre_documento() {
        return nombre_documento;
    }

    public void setNombre_documento(String nombre_documento) {
        this.nombre_documento = nombre_documento;
    }

    public String getRuta_documento() {
        return ruta_documento;
    }

    public void setRuta_documento(String ruta_documento) {
        this.ruta_documento = ruta_documento;
    }
}
