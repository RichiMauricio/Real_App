package com.example.mauricio.real.services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.mauricio.real.mensajes.MensajeriaReal;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Mauricio on 31/05/2017.
 */

public class FirebaseServiceMensajes extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String datos = remoteMessage.getData().get("Objetos");
        Mensaje();
    }

    private void Mensaje(){
        Intent i = new Intent(MensajeriaReal.MENSAJE);
        //Obtiene el contexto actual de donde nos encontremos xq no estamos en nuestra aplicación
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
    }
}
