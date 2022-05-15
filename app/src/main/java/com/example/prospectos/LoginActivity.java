package com.example.prospectos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prospectos.Interfaces.ServiceLogin;
import com.example.prospectos.ModelsReqRes.ResponseLogin;
import com.example.prospectos.Models.Usuario;
import com.example.prospectos.Preferences.LoginPreferences;
import com.example.prospectos.Utilidades.Constantes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TextView txtUsuario;
    TextView txtContrasena;

    private static String TAG = "LOGINACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsuario = findViewById(R.id.txtUsuarioLogin);
        txtContrasena = findViewById(R.id.txtContrasenaLogin);
    }

    public void login(View view){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServiceLogin service  = retrofit.create(ServiceLogin.class);

        // Obtener los datos del login
        Usuario usuario = new Usuario();
        usuario.setUsuario(txtUsuario.getText().toString().trim());
        usuario.setContrasena(txtContrasena.getText().toString().trim());

        Call<ResponseLogin> response = service.login(usuario);
        response.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(view.getContext(), "Error code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ResponseLogin responseLogin = response.body();
                if(responseLogin.getUsuario() == null){
                    Toast.makeText(view.getContext(), "El usuario no existe, valide sus datos", Toast.LENGTH_SHORT).show();
                    return;
                }
                Usuario user = responseLogin.getUsuario();
                Boolean guardo = LoginPreferences.guardarLogin(view.getContext(), user);
                if(!guardo) {
                    Toast.makeText(view.getContext(), "Error al guardar los datos del login.", Toast.LENGTH_SHORT).show();
                } else {
                    // Permitir acceso a la aplicación si se guardaron correctamente los datos.
                    Usuario u = LoginPreferences.leerLogin(view.getContext());
                    if(u.getId_usuario() != 0){
                        view.getContext().startActivity(new Intent(view.getContext(), MainActivity.class));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(view.getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}