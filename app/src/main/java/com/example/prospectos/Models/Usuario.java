package com.example.prospectos.Models;

public class Usuario {

    int id_usuario;
    String nombres_usuario;
    String apellido_p_usuario;
    String apellido_m_usuario;
    String departamento;
    String usuario;
    String contrasena;
    int estatus;

    public Usuario() {
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getNombres_usuario() {
        return nombres_usuario;
    }

    public void setNombres_usuario(String nombres_usuario) {
        this.nombres_usuario = nombres_usuario;
    }

    public String getApellido_p_usuario() {
        return apellido_p_usuario;
    }

    public void setApellido_p_usuario(String apellido_p_usuario) {
        this.apellido_p_usuario = apellido_p_usuario;
    }

    public String getApellido_m_usuario() {
        return apellido_m_usuario;
    }

    public void setApellido_m_usuario(String apellido_m_usuario) {
        this.apellido_m_usuario = apellido_m_usuario;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
}
