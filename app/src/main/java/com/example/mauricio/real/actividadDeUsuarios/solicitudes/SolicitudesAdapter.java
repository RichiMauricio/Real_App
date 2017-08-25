package com.example.mauricio.real.actividadDeUsuarios.solicitudes;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mauricio.real.R;

import java.util.List;

/**
 * Created by Mauricio on 10/08/2017.
 */

public class SolicitudesAdapter extends RecyclerView.Adapter<SolicitudesAdapter.SolicitudesHolder> {

    private List<SolicitudesAtributos> listSolicitudes;
    private Context context;

    public SolicitudesAdapter(List<SolicitudesAtributos> listSolicitudes, Context context){
        this.listSolicitudes = listSolicitudes;
        this.context = context;
    }
    @Override
    public SolicitudesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view__solicitudes,parent,false);
        return new SolicitudesAdapter.SolicitudesHolder(v);
    }

    @Override
    public void onBindViewHolder(SolicitudesHolder holder, int position) {
        holder.fotoPerfil.setImageResource(listSolicitudes.get(position).getFotoPerfil());
        holder.nombre.setText(listSolicitudes.get(position).getNombre());
        holder.hora.setText(listSolicitudes.get(position).getHora());
    }

    @Override
    public int getItemCount() {
        return listSolicitudes.size();
    }

    static class SolicitudesHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView fotoPerfil;
        TextView nombre;
        TextView hora;
        Button aceptar;
        Button cancelar;

        public SolicitudesHolder(View itemView) {
            super(itemView);
            fotoPerfil = (ImageView) itemView.findViewById(R.id.imgUserSolicitud);
            nombre = (TextView)itemView.findViewById(R.id.tvNombreSolicitud);
            hora= (TextView)itemView.findViewById(R.id.tvHoraSolicitud);
            aceptar = (Button)itemView.findViewById(R.id.btnAceptarSolicitud);
            cancelar = (Button)itemView.findViewById(R.id.btnCancelarSolicitud);
        }
    }
}
