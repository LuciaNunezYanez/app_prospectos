package com.example.prospectos.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prospectos.Models.Usuario;
import com.example.prospectos.Preferences.LoginPreferences;
import com.example.prospectos.R;
import com.example.prospectos.Utilidades.Constantes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class InicioFragment extends Fragment {

    FloatingActionButton btn;
    DataListener callback;
    TextView lblTipoSesion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        btn = view.findViewById(R.id.btnAdelante);
        lblTipoSesion = view.findViewById(R.id.lblTipoSesion);

        Usuario u = LoginPreferences.leerLogin(view.getContext());
        lblTipoSesion.setText((u.getDepartamento().equals("promocion")?"PROMOTOR":"EVALUADOR"));
        if(u.getDepartamento().equals(Constantes.DEPARTAMENTO_PROMOCION)){
            btn.setVisibility(View.VISIBLE);
        } else {
            btn.setVisibility(View.GONE);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.openRegistro();
            }
        });
        return view;
    }

    public interface DataListener{
        void openRegistro();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (DataListener) context;
        } catch(Exception e){
            Toast.makeText(context, "ERROR ON ATTACH " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}