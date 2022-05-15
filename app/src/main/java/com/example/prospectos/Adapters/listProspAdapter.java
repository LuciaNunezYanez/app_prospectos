package com.example.prospectos.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prospectos.InformacionActivity;
import com.example.prospectos.Models.Prospecto;
import com.example.prospectos.Models.ProspectoCompleto;
import com.example.prospectos.R;
import com.example.prospectos.Utilidades.Constantes;

import java.util.ArrayList;

public class listProspAdapter extends RecyclerView.Adapter<listProspAdapter.MyViewHolder>  {

    ArrayList<ProspectoCompleto> prospectosList;
    Context context;
    String tipo;

    public listProspAdapter(ArrayList<ProspectoCompleto> prospectosList, Context context, String tipo) {
        this.prospectosList = prospectosList;
        this.context = context;
        this.tipo = tipo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_prosp_adapter, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull listProspAdapter.MyViewHolder holder, int position) {
        holder.txtNombre.setText(
                this.prospectosList.get(position).getProspecto().getNombre_prospecto() + " " +
                this.prospectosList.get(position).getProspecto().getApellido_p_prospecto() + " " +
                this.prospectosList.get(position).getProspecto().getApellido_m_prospecto());
        holder.txtFecha.setText(this.prospectosList.get(position).getEvaluacion().getFecha_registro_evaluacion());

        String estatus = this.prospectosList.get(position).getEvaluacion().getEstatus_evaluacion();
        holder.txtEstatus.setText(estatus.toUpperCase());

        switch (estatus){
            case "aceptado":
                holder.txtEstatus.setTextColor(context.getResources().getColor(R.color.verdePrimary));
                break;
            case "rechazado":
                holder.txtEstatus.setTextColor(context.getResources().getColor(R.color.rojo));
                break;
            case "enviado":
                holder.txtEstatus.setTextColor(context.getResources().getColor(R.color.azul));
                break;
        }

        holder.cardProspView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InformacionActivity.class);
                intent.putExtra("id_prospecto", prospectosList.get(position).getProspecto().getId_prospecto());
                intent.putExtra("tipo", tipo);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.prospectosList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtFecha, txtEstatus;
        CardView cardProspView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombreView);
            txtFecha = itemView.findViewById(R.id.txtFechaView);
            txtEstatus = itemView.findViewById(R.id.txtEstatusView);
            cardProspView = itemView.findViewById(R.id.cardProspView);
        }
    }
}
