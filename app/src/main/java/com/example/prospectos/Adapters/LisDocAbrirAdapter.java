package com.example.prospectos.Adapters;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prospectos.Interfaces.ServiceDocumentos;
import com.example.prospectos.Models.Documento;
import com.example.prospectos.Models.DocumentoMultipart;
import com.example.prospectos.ModelsReqRes.ResponseGeneral;
import com.example.prospectos.R;
import com.example.prospectos.Utilidades.Constantes;
import com.example.prospectos.Utilidades.Permisos;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LisDocAbrirAdapter extends RecyclerView.Adapter<LisDocAbrirAdapter.MyViewHolder> {

    private String TAG = "DESCARGAR";
    Activity activity;
    ArrayList<Documento> arrayDocumento;
    Context context;

    /*
    * Esta es la clase encargada de abrir y descargar los archivos de los prospectos.
    * */

    public LisDocAbrirAdapter(Context context, ArrayList<Documento> arrayDocumento, Activity activity) {
        this.arrayDocumento = arrayDocumento;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @NotNull
    @Override
    public LisDocAbrirAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new LisDocAbrirAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_docum_abrir, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LisDocAbrirAdapter.MyViewHolder holder, int position) {
        holder.lblNombreAbrir.setText(this.arrayDocumento.get(position).getNombre_documento());
        holder.cardDocumentoAbrir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id_documento = arrayDocumento.get(position).getId_documento();

                // Consumir API Documento
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constantes.URL_BASE)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                ServiceDocumentos service = retrofit.create(ServiceDocumentos.class);


                Call<ResponseGeneral> request = service.descargarArchivoUrl(id_documento);
                request.enqueue(new Callback<ResponseGeneral>() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onResponse(Call<ResponseGeneral> call, Response<ResponseGeneral> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(context, "CODE ERROR: " + response.code(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        try {
                            String urlDown = response.body().getFile().getUrl();
                            Permisos permisos = new Permisos();
                            if (permisos.verificarPermisoStorage(context, activity)) {

                                Toast.makeText(context, "Descargando documento..", Toast.LENGTH_LONG).show();

                                // Se descarga el documento en "descargas"
                                DownloadManager.Request downloadRequest = new DownloadManager.Request(Uri.parse(response.body().getFile().getUrl()));
                                String title = URLUtil.guessFileName(urlDown, null, null);
                                downloadRequest.setTitle(title);
                                downloadRequest.setDescription("Descargando documento..");
                                String cookie = CookieManager.getInstance().getCookie(urlDown);
                                downloadRequest.addRequestHeader("cookie", cookie);
                                downloadRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                downloadRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title);

                                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                downloadManager.enqueue(downloadRequest);


                            } else {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                            }
                            return;
                        }catch (Exception e){
                            Toast.makeText(context, "Error al descargar el archvo: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseGeneral> call, Throwable t) {
                        Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.arrayDocumento.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardDocumentoAbrir;
        TextView lblNombreAbrir;
        ImageView imgDownload;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            lblNombreAbrir = itemView.findViewById(R.id.lblNombreDocView);
            cardDocumentoAbrir = itemView.findViewById(R.id.cardDocumentAbrir);
            imgDownload = itemView.findViewById(R.id.imgDownload);
        }
    }
}
