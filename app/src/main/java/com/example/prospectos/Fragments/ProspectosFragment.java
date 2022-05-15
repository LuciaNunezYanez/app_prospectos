package com.example.prospectos.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.prospectos.Adapters.listProspAdapter;
import com.example.prospectos.Interfaces.ServiceProspectos;
import com.example.prospectos.Models.Evaluacion;
import com.example.prospectos.Models.Prospecto;
import com.example.prospectos.Models.ProspectoCompleto;
import com.example.prospectos.Models.Usuario;
import com.example.prospectos.ModelsReqRes.ResponseListProspectos;
import com.example.prospectos.Preferences.LoginPreferences;
import com.example.prospectos.R;
import com.example.prospectos.Utilidades.Constantes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProspectosFragment extends Fragment {

    RecyclerView recyclerView;
    ImageView imageNoAccess;

    public ProspectosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prospectos, container, false);
        imageNoAccess = view.findViewById(R.id.imgNoAcces);
        recyclerView = view.findViewById(R.id.recyclerProspectosVer);
        recyclerView.setLayoutManager( new LinearLayoutManager(this.getContext()));

        if(!LoginPreferences.leerLogin(getContext()).getDepartamento().equals(Constantes.DEPARTAMENTO_PROMOCION)){
            // Mostrar imagen de no acceso
            imageNoAccess.setVisibility(View.VISIBLE);
        } else {
            imageNoAccess.setVisibility(View.GONE);
            cargarListaPromotor();
        }
        return view;
    }

    private ArrayList<ProspectoCompleto> cargarListaPromotor(){
        ArrayList<ProspectoCompleto> prospectos = new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceProspectos service  = retrofit.create(ServiceProspectos.class);

        Call<ResponseListProspectos> response = service.getListProspectos(LoginPreferences.leerLogin(getContext()).getId_usuario());
        response.enqueue(new Callback<ResponseListProspectos>() {
            @Override
            public void onResponse(Call<ResponseListProspectos> call, Response<ResponseListProspectos> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Prospecto> misProspectos = response.body().getProspectos();
                ArrayList<Evaluacion> misEvaluaciones = response.body().getEvaluaciones();

                for (Prospecto p : misProspectos){
                    // Ponerle su prospecto y evaluacion correspondiente
                    for (Evaluacion e: misEvaluaciones) {
                        if(p.getId_prospecto() == e.getId_prospecto_evaluacion()){
                            ProspectoCompleto pro = new ProspectoCompleto();
                            pro.setProspecto(p);
                            pro.setEvaluacion(e);
                            prospectos.add(pro);
                        }
                    }
                }
                recyclerView.setAdapter(new listProspAdapter(prospectos, getContext(), Constantes.TIPO_VER));
            }

            @Override
            public void onFailure(Call<ResponseListProspectos> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return prospectos;
    }
}