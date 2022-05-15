package com.example.prospectos.ModelsReqRes;

import com.example.prospectos.Models.Evaluacion;
import com.example.prospectos.Models.Prospecto;

import java.util.ArrayList;

public class ResponseListProspectos {
    Boolean ok;
    ArrayList<Prospecto> prospectos;
    ArrayList<Evaluacion> evaluaciones;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public ArrayList<Prospecto> getProspectos() {
        return prospectos;
    }

    public void setProspectos(ArrayList<Prospecto> prospectos) {
        this.prospectos = prospectos;
    }

    public ArrayList<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(ArrayList<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }
}
