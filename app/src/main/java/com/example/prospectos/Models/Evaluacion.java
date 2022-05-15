package com.example.prospectos.Models;

public class Evaluacion {

    int id_prospecto_evaluacion;
    int id_promotor_evaluacion;
    int id_evaluador_evaluacion;
    String fecha_registro_evaluacion;
    String fecha_evaluacion_evaluacion;
    String estatus_evaluacion;
    String observaciones_evaluacion;

    public int getId_prospecto_evaluacion() {
        return id_prospecto_evaluacion;
    }

    public void setId_prospecto_evaluacion(int id_prospecto_evaluacion) {
        this.id_prospecto_evaluacion = id_prospecto_evaluacion;
    }

    public int getId_promotor_evaluacion() {
        return id_promotor_evaluacion;
    }

    public void setId_promotor_evaluacion(int id_promotor_evaluacion) {
        this.id_promotor_evaluacion = id_promotor_evaluacion;
    }

    public int getId_evaluador_evaluacion() {
        return id_evaluador_evaluacion;
    }

    public void setId_evaluador_evaluacion(int id_evaluador_evaluacion) {
        this.id_evaluador_evaluacion = id_evaluador_evaluacion;
    }

    public String getFecha_registro_evaluacion() {
        return fecha_registro_evaluacion;
    }

    public void setFecha_registro_evaluacion(String fecha_registro_evaluacion) {
        this.fecha_registro_evaluacion = fecha_registro_evaluacion;
    }

    public String getFecha_evaluacion_evaluacion() {
        return fecha_evaluacion_evaluacion;
    }

    public void setFecha_evaluacion_evaluacion(String fecha_evaluacion_evaluacion) {
        this.fecha_evaluacion_evaluacion = fecha_evaluacion_evaluacion;
    }

    public String getEstatus_evaluacion() {
        return estatus_evaluacion;
    }

    public void setEstatus_evaluacion(String estatus_evaluacion) {
        this.estatus_evaluacion = estatus_evaluacion;
    }

    public String getObservaciones_evaluacion() {
        return observaciones_evaluacion;
    }

    public void setObservaciones_evaluacion(String observaciones_evaluacion) {
        this.observaciones_evaluacion = observaciones_evaluacion;
    }
}
