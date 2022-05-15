package com.example.prospectos.ModelsReqRes;

import com.example.prospectos.Models.Usuario;

public class ResponseLogin {
    Boolean ok;
    Usuario usuario;

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
