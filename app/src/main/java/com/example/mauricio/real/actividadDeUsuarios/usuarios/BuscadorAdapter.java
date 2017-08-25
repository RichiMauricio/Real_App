package com.example.mauricio.real.actividadDeUsuarios.usuarios;

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
 * Created by Mauricio on 15/08/2017.
 */

public class BuscadorAdapter extends RecyclerView.Adapter<BuscadorAdapter.BuscadorHolder> {

    private List<UsuarioBuscadorAtributos> atributosList;
    private Context context;

    public BuscadorAdapter(List<UsuarioBuscadorAtributos> atributosList, Context context) {
        this.atributosList = atributosList;
        this.context = context;
    }

    @Override
    public BuscadorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view_buscar_usuarios,parent,false);
        return new BuscadorHolder(v);
    }

    @Override
    public void onBindViewHolder(BuscadorHolder holder, int position) {
        holder.fotoPerfil.setImageResource(atributosList.get(position).getFotoPerfil());
        holder.nombre.setText(atributosList.get(position).getNombre());
        holder.estadoUsuario.setText(atributosList.get(position).getEstado());
    }

    @Override
    public int getItemCount() {
        return atributosList.size();
    }

    static class BuscadorHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView fotoPerfil;
        TextView nombre;
        TextView estadoUsuario;
        Button enviar;


        public BuscadorHolder(View itemView) {
            super(itemView);
            fotoPerfil = (ImageView) itemView.findViewById(R.id.imgUserEnvioSolicitud);
            nombre = (TextView)itemView.findViewById(R.id.tvNombreSolicitudUsuario);
            estadoUsuario= (TextView)itemView.findViewById(R.id.tvEstadoSolicitud);
            enviar = (Button)itemView.findViewById(R.id.btnEnviarSolicitud);
        }
    }
}
