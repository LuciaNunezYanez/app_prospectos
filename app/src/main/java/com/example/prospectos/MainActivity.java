package com.example.prospectos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.prospectos.Fragments.AgregarFragment;
import com.example.prospectos.Fragments.EvaluacionFragment;
import com.example.prospectos.Fragments.ProspectosFragment;
import com.example.prospectos.Fragments.InicioFragment;
import com.example.prospectos.Models.Usuario;
import com.example.prospectos.Preferences.LoginPreferences;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        InicioFragment.DataListener,
        AgregarFragment.DataListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Comprobar credenciales antes de permitir el acceso
        comprobarCredenciales();

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        getSupportFragmentManager().beginTransaction().add(R.id.content, new InicioFragment()).commit();
        setTitle(R.string.nav_registro);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);

        drawerLayout.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                sustituirFragment(R.id.btnAdelante);
            }
        });
    }

    private void comprobarCredenciales(){
        Usuario u = LoginPreferences.leerLogin(this.getApplicationContext());
        if(u.getId_usuario() == 0){
            startActivity(new Intent(this.getApplicationContext(), LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        sustituirFragment(item.getItemId());
        return true;
    }

    private void sustituirFragment(int r){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (r){
            case R.id.nav_inicio:
                ft.replace(R.id.content, new InicioFragment()).commit();
                setTitle(R.string.nav_registro);
                drawerLayout.closeDrawers();
                break;

            case R.id.nav_prospectos:
                ft.replace(R.id.content, new ProspectosFragment()).commit();
                setTitle(R.string.nav_prospectos);
                drawerLayout.closeDrawers();
                break;

            case R.id.nav_evaluacion:
                ft.replace(R.id.content, new EvaluacionFragment()).commit();
                setTitle(R.string.nav_evaluacion);
                drawerLayout.closeDrawers();
                break;

            case R.id.nav_cerrar:
                LoginPreferences.cerrarSesion(MainActivity.this);
                comprobarCredenciales();
                break;
            case R.id.btnAdelante:
                ft.replace(R.id.content, new AgregarFragment()).commit();
                setTitle("Agregar prospecto");
                break;

            case R.id.btnVolver:
                ft.replace(R.id.content, new InicioFragment()).commit();
                setTitle(R.string.nav_registro);
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openRegistro() {
        sustituirFragment(R.id.btnAdelante);
    }

    @Override
    public void closeRegistro() {
        sustituirFragment(R.id.btnVolver);
    }
}