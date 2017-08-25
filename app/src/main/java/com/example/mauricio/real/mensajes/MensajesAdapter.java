package com.example.mauricio.real.mensajes;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mauricio.real.R;

import java.util.List;

/**
 * Created by Mauricio on 25/05/2017.
 */

public class MensajesAdapter extends RecyclerView.Adapter<MensajesAdapter.MensajesViewHolder> {

    private List<MensajesDeTexto> mensajesDeTexto;
    private Context context;

    public MensajesAdapter(List<MensajesDeTexto> mensajesDeTexto, Context context) {     //El parámetro mensaje de texto será enviado desde la clase mensajería Real
        this.mensajesDeTexto = mensajesDeTexto;
        this.context = context;
    }

    static class MensajesViewHolder extends RecyclerView.ViewHolder{        //En esta clase se crean las variables que van en la tarjeta. Es como una miniclase del layout activity_card_view_mensaje

        CardView cardview;
        TextView tvMensaje, tvHoraMensaje;
        LinearLayout mensajeBg;

        MensajesViewHolder(View itemView){      //Están las 3 variales instanciadas en la clase estática
            super(itemView);
            cardview = (CardView)itemView.findViewById(R.id.cardViewMensaje);
            tvMensaje = (TextView)itemView.findViewById(R.id.tvMensajeTexto);
            tvHoraMensaje = (TextView)itemView.findViewById(R.id.tvHora);
            mensajeBg = (LinearLayout)itemView.findViewById(R.id.mensajeBG);
        }
    }

    @Override
    public MensajesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {      //Con este método se infla tarjeta del layout card_view para q  al momenot d uplicar aparezca en nuestra vista
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view_mensaje,parent, false);
        return new MensajesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MensajesViewHolder holder, int position) {     //Donde se modifica cada tarjeta de mensaje

        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams)holder.cardview.getLayoutParams();
        FrameLayout.LayoutParams fl = (FrameLayout.LayoutParams)holder.mensajeBg.getLayoutParams();     //Un cardview es contenido x defecto en un frame layout, para que no se desconfigure al ejecutar la aplicación el cardview, configuramos el padre del cardview q es un fram
        LinearLayout.LayoutParams llMensaje = (LinearLayout.LayoutParams)holder.tvMensaje.getLayoutParams();
        LinearLayout.LayoutParams llHora = (LinearLayout.LayoutParams)holder.tvHoraMensaje.getLayoutParams();

        if (mensajesDeTexto.get(position).getTipoMensaje() == 1){
            holder.mensajeBg.setBackgroundResource(R.drawable.chatout);     //Indica la imagen que va de fondo para el mensaje
            rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT,0);     //Indica desde donde empieza el card
            rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            llMensaje.gravity = Gravity.RIGHT;  //Posición del mensaje a la derecha
            llHora.gravity = Gravity.RIGHT;     //Posición de la hora a la derecha
            fl.gravity = Gravity.RIGHT;     //Para que los mensajes no se regresen a la posición original del cardview, sino que se mantengan al lado derechos como mensajes de recptor
            holder.tvMensaje.setGravity(Gravity.RIGHT);
        }else if (mensajesDeTexto.get(position).getTipoMensaje() == 2){
            holder.mensajeBg.setBackgroundResource(R.drawable.chatin);
            rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,0);
            rl.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            llMensaje.gravity = Gravity.LEFT;   //Posición del mensaje a la izquierda
            llHora.gravity = Gravity.LEFT;      //Posición de la hora a la izquierda
            fl.gravity = Gravity.LEFT;
            holder.tvMensaje.setGravity(Gravity.LEFT);
        }
        holder.cardview.setLayoutParams(rl);          //Guarda las configuraciones del cardview según el tipo de mensaje
        holder.mensajeBg.setLayoutParams(fl);        //Mantiene las Configuraciones igual q en el cardview pero para que los cards no se muevan de su posición
        holder.tvHoraMensaje.setLayoutParams(llHora);
        holder.tvMensaje.setLayoutParams(llMensaje);

        holder.tvMensaje.setText(mensajesDeTexto.get(position).getMensaje());
        holder.tvHoraMensaje.setText(mensajesDeTexto.get(position).getHoraMensaje());
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
            holder.cardview.getBackground().setAlpha(0);    //Transparencia del cardview a 0 hasta api 21
        }else{
            holder.cardview.setBackgroundColor(ContextCompat.getColor(context,android.R.color.transparent));
        }
    }

    @Override
    public int getItemCount() {     //Los elementos que va a contener mi recyclerView.(Sitengo 10 mensajes este me retornaría 10 )
        return mensajesDeTexto.size();
    }
}
