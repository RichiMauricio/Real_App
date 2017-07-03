package com.example.mauricio.real.services;

import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Mauricio on 31/05/2017.
 * Clase para los tokens
 */

public class FirebaseId extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
    }
}
