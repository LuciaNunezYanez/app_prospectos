package com.example.prospectos.ModelsReqRes;

import com.example.prospectos.Models.Prospecto;

public class ResponseProspecto {
    Boolean ok;
    Prospecto prospecto;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Prospecto getProspecto() {
        return prospecto;
    }

    public void setProspecto(Prospecto prospecto) {
        this.prospecto = prospecto;
    }
}
