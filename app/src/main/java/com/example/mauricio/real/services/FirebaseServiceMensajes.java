package com.example.mauricio.real.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.content.LocalBroadcastManager;

import com.example.mauricio.real.Preferencias;
import com.example.mauricio.real.R;
import com.example.mauricio.real.mensajes.MensajeriaReal;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Created by Mauricio on 31/05/2017.
 */

public class FirebaseServiceMensajes extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String tipo = remoteMessage.getData().get("tipo");
        String cabecera = remoteMessage.getData().get("cabecera");
        String cuerpo_mensaje = remoteMessage.getData().get("cuerpo_mensaje");
        switch (tipo){
            case "mensaje":
                String mensaje = remoteMessage.getData().get("mensaje");
                String hora = remoteMessage.getData().get("hora");
                String receptor = remoteMessage.getData().get("receptor");
                String emisorPHP = remoteMessage.getData().get("emisor");
                String emisor = Preferencias.obtenerPreferenceString(this,Preferencias.PREFERENCE_USUARIO_LOGIN);
                if (emisor.equals(receptor)){
                    mensaje(mensaje,hora ,emisorPHP);
                    showNotification(cabecera,cuerpo_mensaje);
                }
                break;
            case "solicitud":
                String usuarioEnvioSolicitud = remoteMessage.getData().get("user_envio_solicitud");
                showNotification(cabecera,cuerpo_mensaje);
                break;
        }

    }

    private void mensaje(String mensaje, String hora, String emisor){
        Intent i = new Intent(MensajeriaReal.MENSAJE);
        i.putExtra("key_mensaje",mensaje);
        i.putExtra("key_hora",hora);
        i.putExtra("key_emisor_PHP",emisor);
        //Obtiene el contexto actual de donde nos encontremos xq no estamos en nuestra aplicación
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
    }

    private void showNotification(String cabecera, String cuerpo){
        Intent i = new Intent(this,MensajeriaReal.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);  //El último parámetro es para cerrar la notificación al dar clic y no q siga visible
        //Uri del sonido de la notif
        Uri soundNotif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Inicializar la notificación
        Builder builder = new Builder(this);
        //Acción que queremos q haga la notif al momento de darle clic osea cancelarla
        builder.setAutoCancel(true);
        //titulo de la notif
        builder.setContentTitle(cabecera);
        //Cuerpo de la notif
        builder.setContentText(cuerpo);
        //Sonido de la notificación
        builder.setSound(soundNotif);
        //Ícono de la notif
        builder.setSmallIcon(R.drawable.iconnotif);

        builder.setContentIntent(pendingIntent);
        //Obtenemos el servicio de nuestro celular que viene en el sistema incorporado
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Identificador delas notificaciones
        Random random = new Random();

        notificationManager.notify(random.nextInt(),builder.build());
    }
}
