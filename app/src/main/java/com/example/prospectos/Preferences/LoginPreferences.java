package com.example.prospectos.Preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.prospectos.Models.Usuario;

public class LoginPreferences {

    private static String LOGIN = "login";
    private static String TAG = "LoginPreferences";

    public static Boolean guardarLogin(Context context, Usuario usuario){
        try {
            SharedPreferences preferences = context.getSharedPreferences(LOGIN,Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putInt("id_usuario", usuario.getId_usuario());
            editor.putString("nombres_usuario", usuario.getNombres_usuario());
            editor.putString("apellido_p_usuario", usuario.getApellido_p_usuario());
            editor.putString("apellido_m_usuario", usuario.getApellido_m_usuario());
            editor.putString("departamento", usuario.getDepartamento());

            editor.commit();
            return true;
        }catch(Exception e){
            Log.e(TAG, e.getMessage());
            return false;
        }
    }

    public static Usuario leerLogin(Context context) {
        Usuario usuario = new Usuario();
        SharedPreferences preferences = context.getSharedPreferences(LOGIN, Context.MODE_PRIVATE);
        if (!preferences.contains("id_usuario")) return usuario;

        usuario.setId_usuario(preferences.getInt("id_usuario",0));
        usuario.setNombres_usuario(preferences.getString("nombres_usuario",""));
        usuario.setApellido_p_usuario(preferences.getString("apellido_p_usuario", ""));
        usuario.setApellido_m_usuario(preferences.getString("apellido_m_usuario", ""));
        usuario.setDepartamento(preferences.getString("departamento",""));
        return usuario;
    }

    public static void cerrarSesion(Context context){
        SharedPreferences preferences = context.getSharedPreferences(LOGIN,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
    }

}
