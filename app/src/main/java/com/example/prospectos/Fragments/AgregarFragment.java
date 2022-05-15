package com.example.prospectos.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.prospectos.Adapters.ListDocAdapter;
import com.example.prospectos.Clases.Decodificaciones;
import com.example.prospectos.Interfaces.ServiceDocumentos;
import com.example.prospectos.Interfaces.ServiceProspectos;
import com.example.prospectos.Models.Direccion;
import com.example.prospectos.Models.Documento;
import com.example.prospectos.Models.DocumentoMultipart;
import com.example.prospectos.Models.Prospecto;
import com.example.prospectos.ModelsReqRes.ResponseGeneral;
import com.example.prospectos.ModelsReqRes.ResponseProspecto;
import com.example.prospectos.Models.Usuario;
import com.example.prospectos.Preferences.LoginPreferences;
import com.example.prospectos.R;
import com.example.prospectos.Utilidades.Constantes;
import com.example.prospectos.Utilidades.Permisos;
import com.example.prospectos.Utilidades.UriUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AgregarFragment extends Fragment implements View.OnClickListener {

    private static String TAG = "AGREGAR_PROSPECTO";
    private AwesomeValidation validations;

    // Data
    MultipartBody.Part[] matrizParts;
    DataListener callback;

    // UI
    // Datos personales
    EditText txtNombre;
    EditText txtApellidoP;
    EditText txtApellidoM;

    // Dirección
    EditText txtCalle;
    EditText txtNumero;
    EditText txtColonia;
    EditText txtCP;
    EditText txtTelefono;
    EditText txtRFC;
    Button btnCancelar, btnRegistrar, btnAddDocument;
    RecyclerView recyclerView;
    ListDocAdapter listDocadapter;

    // UI Alert Documents
    TextView lblRutaDocView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_agregar, container, false);
        btnCancelar = view.findViewById(R.id.btnVolver);
        btnCancelar.setOnClickListener(this);
        btnRegistrar = view.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);
        btnAddDocument = view.findViewById(R.id.btnAddDocument);
        btnAddDocument.setOnClickListener(this);

        txtNombre = view.findViewById(R.id.txtNombreAgregar);
        txtApellidoP = view.findViewById(R.id.txtApellidoPaternoAgregar);
        txtApellidoM = view.findViewById(R.id.txtApellidoMaternoAgregar);
        txtCalle = view.findViewById(R.id.txtCalleAgregar);
        txtNumero = view.findViewById(R.id.txtNumeroAgregar);
        txtColonia = view.findViewById(R.id.txtColoniaAgregar);
        txtCP = view.findViewById(R.id.txtCPAgregar);
        txtTelefono = view.findViewById(R.id.txtTelefonoAgregar);
        txtRFC = view.findViewById(R.id.txtRFCAgregar);

        validations = new AwesomeValidation(ValidationStyle.COLORATION);
        validations.addValidation(txtNombre, RegexTemplate.NOT_EMPTY, "Campo obligatorio");
        validations.addValidation(txtApellidoP, RegexTemplate.NOT_EMPTY, "Campo obligatorio");
        validations.addValidation(txtCalle, RegexTemplate.NOT_EMPTY, "Campo obligatorio");
        validations.addValidation(txtNumero, RegexTemplate.NOT_EMPTY, "Campo obligatorio");
        validations.addValidation(txtColonia, RegexTemplate.NOT_EMPTY, "Campo obligatorio");
        validations.addValidation(txtCP, RegexTemplate.NOT_EMPTY, "Campo obligatorio");
        validations.addValidation(txtTelefono, Patterns.PHONE, "Teléfono incorrecto");
        validations.addValidation(txtRFC, RegexTemplate.NOT_EMPTY, "Campo obligatorio");


        recyclerView = view.findViewById(R.id.recyclerAgregar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRegistrar:

                if (!validations.validate()) {
                    Toast.makeText(getContext(), "Valide los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Prospecto prospecto = new Prospecto();
                prospecto.setNombre_prospecto(txtNombre.getText().toString().trim());
                prospecto.setApellido_p_prospecto(txtApellidoP.getText().toString().trim());
                prospecto.setApellido_m_prospecto(txtApellidoM.getText().toString().trim());
                prospecto.setTelefono_prospecto(txtTelefono.getText().toString().trim());
                prospecto.setRfc_prospecto(txtRFC.getText().toString().trim());

                Direccion direccion = new Direccion();
                direccion.setCalle(txtCalle.getText().toString().trim());
                direccion.setNumero(txtNumero.getText().toString().trim());
                direccion.setColonia(txtColonia.getText().toString().trim());
                direccion.setCp(Integer.parseInt(txtCP.getText().toString()));

                registrarProspecto(prospecto, direccion);
                break;
            case R.id.btnVolver:
                cancelarRegistro();
                break;
            case R.id.btnAddDocument:
                abrirDialogAddDocument();
                break;
        }
    }

    private void cancelarRegistro(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Si continúa perderá toda la información");
        dialog.setMessage("¿Desea continuar?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Perder información", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                callback.closeRegistro();
            }
        });
        dialog.setNegativeButton("Continuar registro", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            }
        });
        dialog.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (DataListener) context;
        } catch (Exception e) {
            Toast.makeText(context, "ERROR ON ATTACH " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public interface DataListener {
        void closeRegistro();
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellidoP.setText("");
        txtApellidoM.setText("");
        txtCalle.setText("");
        txtNumero.setText("");
        txtColonia.setText("");
        txtCP.setText("");
        txtTelefono.setText("");
        txtRFC.setText("");
    }

    private void abrirDialogAddDocument() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        final View documentView = getLayoutInflater().inflate(R.layout.fragment_dialog_add_document, null);
        EditText txtNombre = documentView.findViewById(R.id.txtNombreArchivoAlert);
        lblRutaDocView = documentView.findViewById(R.id.lblRutaArchivoAlert);
        Button btnCerrar = documentView.findViewById(R.id.btnCancelarArchivoAlert);
        Button btnAceptar = documentView.findViewById(R.id.btnAceptarArchivoAlert);
        Button btnSelect = documentView.findViewById(R.id.btnSelectArchivoAlert);

        dialogBuilder.setView(documentView);
        dialogBuilder.setCancelable(false);
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtNombre.getText().toString();
                String ruta = lblRutaDocView.getText().toString();

                if (nombre.length() <= 0 || ruta.length() <= 0) {
                    Toast.makeText(getContext(), "Por favor llene ambos campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Codificar ruta y agregarlo al array
                Decodificaciones decodificaciones = new Decodificaciones(getContext());
                MultipartBody.Part archivoSubir = decodificaciones.prepararArchivo("archivos", ruta);

                // Agregarlo a la lista de archivos por subir
                DocumentoMultipart documentoMultipart = new DocumentoMultipart();
                documentoMultipart.setNombre(nombre);
                documentoMultipart.setParte(archivoSubir);

                dialog.dismiss();
                ArrayList<DocumentoMultipart> listActualizada;
                if (listDocadapter != null) {
                    listActualizada = listDocadapter.getDocumentosView();
                } else {
                    listActualizada = new ArrayList<>();
                }
                listActualizada.add(documentoMultipart);

                listDocadapter = new ListDocAdapter(getContext(), listActualizada);
                // Mandar la lista actualizada
                recyclerView.setAdapter(listDocadapter);

            }
        });
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Permisos permisos = new Permisos();
                if (permisos.verificarPermisoStorage(getContext(), getActivity())) {
                    // Abrir el explorador de archivos
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");

                    // TODO: Código comentado ya que solo limita a documentos de tipo doc, docx, ppt, pptx, pdf
                    /*intent.setType("application/msword|application/vnd.openxmlformats-officedocument.wordprocessingml.document" +
                            "|application/vnd.ms-powerpoint|application/vnd.openxmlformats-officedocument.presentationml.presentation|application/pdf");
                    // Configuración para versiones posteriores
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        intent.putExtra(Intent.EXTRA_MIME_TYPES,
                                new String[]{Constantes.DOC, Constantes.DOCX, Constantes.PPT, Constantes.PPTX, Constantes.PDF});
                    }*/

                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                    resultLauncher.launch(intent);
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
            }
        });
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String uri = UriUtils.getPathFromUri(getContext(), data.getData());

                        lblRutaDocView.setText(uri);
                    }
                }
            });

    private void enviarDocumentos(int id_prospecto) {
        RequestBody nombreArchivo = RequestBody.create(MediaType.parse("text/plain"),
                String.valueOf(Calendar.getInstance().getTimeInMillis()));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceDocumentos service = retrofit.create(ServiceDocumentos.class);

        // Llenar matriz con mis archivos para pasarla a la interface
        ArrayList<DocumentoMultipart> listaActualizadaAdapter = listDocadapter.getDocumentosView();
        int size = listaActualizadaAdapter.size();
        Map<String, String> mapNombres = new HashMap<>();
        matrizParts = new MultipartBody.Part[size];
        for (int i = 0; i < size; i++) {
            mapNombres.put("nombre" + i, listaActualizadaAdapter.get(i).getNombre());
            matrizParts[i] = listaActualizadaAdapter.get(i).getParte();
        }

        // Consumir API REST
        Call<ResponseGeneral> response = service.agregarDocumentos(id_prospecto, matrizParts, nombreArchivo, mapNombres);
        response.enqueue(new Callback<ResponseGeneral>() {
            @Override
            public void onResponse(Call<ResponseGeneral> call, Response<ResponseGeneral> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().getOk()) {
                    Toast.makeText(getContext(), "SE AGREGÓ EL PROSPECTO CON SUS DOCUMENTOS", Toast.LENGTH_LONG).show();
                    limpiarCampos();
                    listDocadapter = new ListDocAdapter(getContext(), new ArrayList<>());
                    recyclerView.setAdapter(listDocadapter);
                } else
                    Toast.makeText(getContext(), "SE AGREGÓ EL PROSPECTO, PERO NO SUS DOCUMENTOS", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseGeneral> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void registrarProspecto(Prospecto prospecto, Direccion direccion) {
        Usuario promotor = LoginPreferences.leerLogin(getContext());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceProspectos service = retrofit.create(ServiceProspectos.class);

        HashMap<String, Object> body = new HashMap<>();
        // Prospecto
        body.put("nombre_prospecto", prospecto.getNombre_prospecto());
        body.put("apellido_p_prospecto", prospecto.getApellido_p_prospecto());
        body.put("apellido_m_prospecto", prospecto.getApellido_m_prospecto());
        body.put("telefono_prospecto", prospecto.getTelefono_prospecto());
        body.put("rfc_prospecto", prospecto.getRfc_prospecto());
        // Dirección
        body.put("calle", direccion.getCalle());
        body.put("numero", direccion.getNumero());
        body.put("colonia", direccion.getColonia());
        body.put("cp", direccion.getCp());
        // Promotor
        body.put("id_usuario", promotor.getId_usuario());

        Call<ResponseProspecto> response = service.agregarProspecto(body);
        response.enqueue(new Callback<ResponseProspecto>() {
            @Override
            public void onResponse(Call<ResponseProspecto> call, Response<ResponseProspecto> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Error code: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                ResponseProspecto res = response.body();
                if (listDocadapter != null && listDocadapter.getDocumentosView().size() > 0)
                    enviarDocumentos(res.getProspecto().getId_prospecto());
                else
                    Toast.makeText(getContext(), "Se agregó prospecto sin documentos", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseProspecto> call, Throwable t) {
                Toast.makeText(getContext(), "Error al registrar prospecto: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}