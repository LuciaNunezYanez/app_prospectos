package com.example.prospectos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prospectos.Models.Documento;
import com.example.prospectos.Models.DocumentoMultipart;
import com.example.prospectos.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ListDocAdapter extends RecyclerView.Adapter<ListDocAdapter.MyViewHolder> {

    ArrayList<DocumentoMultipart> arrayDocumentoMultiparts;
    Context context;

    public ListDocAdapter(Context context, ArrayList<DocumentoMultipart> arrayDocumentoMultiparts) {
        this.arrayDocumentoMultiparts = arrayDocumentoMultiparts;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_docum, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.lblNombre.setText(this.arrayDocumentoMultiparts.get(position).getNombre());
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayDocumentoMultiparts.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, arrayDocumentoMultiparts.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.arrayDocumentoMultiparts.size();
    }

    public ArrayList<DocumentoMultipart> getDocumentosView(){
        return this.arrayDocumentoMultiparts;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardDocumento;
        TextView lblNombre;
        Button btnRemove;


        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            lblNombre = itemView.findViewById(R.id.lblNombreDocumento);
            btnRemove = itemView.findViewById(R.id.btnRemoveDocumento);
            cardDocumento = itemView.findViewById(R.id.cardDocumento);
        }
    }
}
