package com.example.prospectos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prospectos.Adapters.LisDocAbrirAdapter;
import com.example.prospectos.Adapters.ListDocAdapter;
import com.example.prospectos.Adapters.listProspAdapter;
import com.example.prospectos.Interfaces.ServiceEvaluacion;
import com.example.prospectos.Interfaces.ServiceProspectos;
import com.example.prospectos.Models.Evaluacion;
import com.example.prospectos.Models.ProspectoCompleto;
import com.example.prospectos.Models.Usuario;
import com.example.prospectos.ModelsReqRes.ResponseGeneral;
import com.example.prospectos.Preferences.LoginPreferences;
import com.example.prospectos.Utilidades.Constantes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InformacionActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "INFORMACION_ACTIVITY";
    // UI
    TextView txtNombre;
    TextView txtApellidoP;
    TextView txtApellidoM;
    TextView txtTelefono;
    TextView txtRFC;
    TextView txtCalle;
    TextView txtNumero;
    TextView txtColonia;
    TextView txtCP;
    LinearLayout linearEvaluacion;
    TextView txtFechaEval;
    TextView txtEstatus;
    TextView txtObservaciones;
    RecyclerView recyclerView;
    Button btnVolverInf, btnEvaluarInf;
    AlertDialog dialog;

    // Data
    private int id_prospecto = 0;
    private String tipo = Constantes.TIPO_VER;
    private ProspectoCompleto prospectoCompleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getExtras() != null){
            id_prospecto = getIntent().getExtras().getInt("id_prospecto");
            tipo = getIntent().getExtras().getString("tipo", Constantes.TIPO_VER);
        }

        setContentView(R.layout.activity_informacion);
        btnVolverInf = findViewById(R.id.btnVolverInf);
        btnEvaluarInf = findViewById(R.id.btnEvaluarInf);
        btnVolverInf.setOnClickListener(this);
        btnEvaluarInf.setOnClickListener(this);

        txtNombre = findViewById(R.id.txtNombreInfo);
        txtApellidoP = findViewById(R.id.txtApellidoPaternoInfo);
        txtApellidoM = findViewById(R.id.txtApellidoMaternoInfo);
        txtTelefono = findViewById(R.id.txtTelefonoInfo);
        txtRFC = findViewById(R.id.txtRFCInfo);
        txtCalle = findViewById(R.id.txtCalleInfo);
        txtNumero = findViewById(R.id.txtNumeroInfo);
        txtColonia = findViewById(R.id.txtColoniaInfo);
        txtCP = findViewById(R.id.txtCPInfo);
        linearEvaluacion = findViewById(R.id.layoutEvaluacion);
        txtEstatus = findViewById(R.id.txtEstatus);
        txtFechaEval = findViewById(R.id.txtFechaEval);
        txtObservaciones = findViewById(R.id.txtObservaciones);
        recyclerView = findViewById(R.id.recyclerDocAbrir);
        recyclerView.setLayoutManager( new LinearLayoutManager(InformacionActivity.this));

        cargarInformacion(id_prospecto);
        // Depende el usuario es la interface que muestra
        ui(tipo);
    }

    private void abrirDialogEvaluacion(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(InformacionActivity.this);
        final View evaluacionView = getLayoutInflater().inflate(R.layout.fragment_dialog_evaluar, null);
        EditText txtNombre = evaluacionView.findViewById(R.id.txtObservacionesView);
        Spinner spinner = evaluacionView.findViewById(R.id.spinnerEvaluarView);
        Button btnCerrar = evaluacionView.findViewById(R.id.btnCerrarEvalView);
        Button btnFinalizarEval = evaluacionView.findViewById(R.id.btnEvaluarView);

        dialogBuilder.setView(evaluacionView);
        dialogBuilder.setCancelable(false);
        dialog = dialogBuilder.create();
        dialog.show();

        // Llenar spinner
        List<String> estatus = new ArrayList<>();
        estatus.add("- Seleccionar -");
        estatus.add("Rechazar");
        estatus.add("Aceptar");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(InformacionActivity.this, android.R.layout.select_dialog_item, estatus);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnFinalizarEval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Comprobar los campos de evaluación de prospecto
                String nuevoEstatus = "aceptado";
                if(spinner.getSelectedItem().toString().equals("- Seleccionar -")){
                    Toast.makeText(getApplicationContext(), "Por favor evalue al prospecto", Toast.LENGTH_LONG).show();
                    return;
                }
                if(spinner.getSelectedItem().toString().equals("Rechazar") && txtNombre.getText().toString().length()<=0){
                    Toast.makeText(getApplicationContext(), "Por favor llene el campo de observaciones", Toast.LENGTH_LONG).show();
                    nuevoEstatus = "rechazado";
                    return;
                }if(spinner.getSelectedItem().toString().equals("Rechazar")){
                    nuevoEstatus = "rechazado";
                }
                // Leer usuario (Para saber quien evaluó)
                Usuario evaluador = LoginPreferences.leerLogin(InformacionActivity.this);
                Evaluacion evaluacion = new Evaluacion();
                evaluacion.setId_prospecto_evaluacion(id_prospecto);
                evaluacion.setId_evaluador_evaluacion(evaluador.getId_usuario());
                evaluacion.setEstatus_evaluacion(nuevoEstatus);
                evaluacion.setObservaciones_evaluacion(txtNombre.getText().toString().trim());

                // Consumir API
                evaluarProspecto(evaluacion);
            }
        });
    }

    private void cargarInformacion(int id_prospecto){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceProspectos service  = retrofit.create(ServiceProspectos.class);
        Call<ProspectoCompleto> response = service.getProspectoCompleto(id_prospecto);
        response.enqueue(new Callback<ProspectoCompleto>() {
            @Override
            public void onResponse(Call<ProspectoCompleto> call, Response<ProspectoCompleto> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mostrar información
                prospectoCompleto = response.body();
                txtNombre.setText(prospectoCompleto.getProspecto().getNombre_prospecto());
                txtApellidoP.setText(prospectoCompleto.getProspecto().getApellido_p_prospecto());
                txtApellidoM.setText(prospectoCompleto.getProspecto().getApellido_m_prospecto());
                txtTelefono.setText(prospectoCompleto.getProspecto().getTelefono_prospecto());
                txtRFC.setText(prospectoCompleto.getProspecto().getRfc_prospecto());

                txtCalle.setText(prospectoCompleto.getDireccion().getCalle());
                txtNumero.setText(prospectoCompleto.getDireccion().getNumero());
                txtColonia.setText(prospectoCompleto.getDireccion().getColonia());
                txtCP.setText(prospectoCompleto.getDireccion().getCp()+"");

                recyclerView.setAdapter(new LisDocAbrirAdapter(InformacionActivity.this, prospectoCompleto.getDocumentos(), InformacionActivity.this));

                if(!prospectoCompleto.getEvaluacion().getEstatus_evaluacion().equals("enviado")){
                    txtFechaEval.setText(prospectoCompleto.getEvaluacion().getFecha_evaluacion_evaluacion());
                    txtEstatus.setText(prospectoCompleto.getEvaluacion().getEstatus_evaluacion().toUpperCase());
                    txtObservaciones.setText(prospectoCompleto.getEvaluacion().getObservaciones_evaluacion());
                    btnEvaluarInf.setVisibility(View.GONE);
                    linearEvaluacion.setVisibility(View.VISIBLE);

                    switch (prospectoCompleto.getEvaluacion().getEstatus_evaluacion()){
                        case "aceptado":
                            txtEstatus.setTextColor(getResources().getColor(R.color.verdePrimary));
                            break;
                        case "rechazado":
                            txtEstatus.setTextColor(getResources().getColor(R.color.rojo));
                            break;
                        case "enviado":
                            txtEstatus.setTextColor(getResources().getColor(R.color.azul));
                            break;
                    }
                } else {
                    linearEvaluacion.setVisibility(View.GONE);
                    txtFechaEval.setVisibility(View.GONE);
                    txtEstatus.setVisibility(View.GONE);
                    txtObservaciones.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<ProspectoCompleto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void evaluarProspecto(Evaluacion evaluacion){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceEvaluacion service = retrofit.create(ServiceEvaluacion.class);
        Call<ResponseGeneral> response = service.evaluarProspecto(id_prospecto, evaluacion);
        response.enqueue(new Callback<ResponseGeneral>() {
            @Override
            public void onResponse(Call<ResponseGeneral> call, Response<ResponseGeneral> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(InformacionActivity.this, "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body().getOk()) {
                    Toast.makeText(InformacionActivity.this, response.body().getMensaje(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                    //Actualizar vista
                    txtFechaEval.setText("Hace un momento");
                    txtFechaEval.setVisibility(View.VISIBLE);
                    txtEstatus.setText(evaluacion.getEstatus_evaluacion());
                    txtEstatus.setVisibility(View.VISIBLE);
                    txtObservaciones.setText(evaluacion.getObservaciones_evaluacion());
                    txtObservaciones.setVisibility(View.VISIBLE);
                    linearEvaluacion.setVisibility(View.VISIBLE);
                    btnEvaluarInf.setVisibility(View.GONE);
                } else
                    Toast.makeText(InformacionActivity.this, response.body().getError(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<ResponseGeneral> call, Throwable t) {
                Toast.makeText(InformacionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnVolverInf:
                finish();
                break;
            case R.id.btnEvaluarInf:
                abrirDialogEvaluacion();
                break;
        }
    }

    private void ui(String tipo){
        switch (tipo){
            case Constantes.TIPO_VER:
                btnEvaluarInf.setVisibility(View.GONE);
                break;
            case Constantes.TIPO_EVALUAR:
                btnEvaluarInf.setVisibility(View.VISIBLE);
                break;
        }
    }
}