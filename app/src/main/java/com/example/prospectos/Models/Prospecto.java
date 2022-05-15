package com.example.prospectos.Models;

public class Prospecto {

    int id_prospecto;
    String nombre_prospecto;
    String apellido_p_prospecto;
    String apellido_m_prospecto;
    String telefono_prospecto;
    String rfc_prospecto;
    int id_direccion_prospecto;

    public Prospecto() {
    }

    public Prospecto(int id_prospecto, String nombre_prospecto, String apellido_p_prospecto, String apellido_m_prospecto, String telefono_prospecto, String rfc_prospecto, int id_direccion_prospecto) {
        this.id_prospecto = id_prospecto;
        this.nombre_prospecto = nombre_prospecto;
        this.apellido_p_prospecto = apellido_p_prospecto;
        this.apellido_m_prospecto = apellido_m_prospecto;
        this.telefono_prospecto = telefono_prospecto;
        this.rfc_prospecto = rfc_prospecto;
        this.id_direccion_prospecto = id_direccion_prospecto;
    }

    public int getId_prospecto() {
        return id_prospecto;
    }

    public void setId_prospecto(int id_prospecto) {
        this.id_prospecto = id_prospecto;
    }

    public String getNombre_prospecto() {
        return nombre_prospecto;
    }

    public void setNombre_prospecto(String nombre_prospecto) {
        this.nombre_prospecto = nombre_prospecto;
    }

    public String getApellido_p_prospecto() {
        return apellido_p_prospecto;
    }

    public void setApellido_p_prospecto(String apellido_p_prospecto) {
        this.apellido_p_prospecto = apellido_p_prospecto;
    }

    public String getApellido_m_prospecto() {
        return apellido_m_prospecto;
    }

    public void setApellido_m_prospecto(String apellido_m_prospecto) {
        this.apellido_m_prospecto = apellido_m_prospecto;
    }

    public String getTelefono_prospecto() {
        return telefono_prospecto;
    }

    public void setTelefono_prospecto(String telefono_prospecto) {
        this.telefono_prospecto = telefono_prospecto;
    }

    public String getRfc_prospecto() {
        return rfc_prospecto;
    }

    public void setRfc_prospecto(String rfc_prospecto) {
        this.rfc_prospecto = rfc_prospecto;
    }

    public int getId_direccion_prospecto() {
        return id_direccion_prospecto;
    }

    public void setId_direccion_prospecto(int id_direccion_prospecto) {
        this.id_direccion_prospecto = id_direccion_prospecto;
    }
}
