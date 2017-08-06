package com.example.mauricio.real;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Mauricio on 30/07/2017.
 */

public class Preferencias {

    public static final String STRING_PREFERENCES = "com.example.mauricio.real";
    public static final String PREFERENCE_ESTADO_SESION = "estado.button.sesion";
    public static final String PREFERENCE_USUARIO_LOGIN = "usuario.login";


    public static void guardarPreferenciasBoolean(Context c, boolean b, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES,c.MODE_PRIVATE);
        preferences.edit().putBoolean(key,b).apply();
    }

    public static void guardarPreferenciasString(Context c, String b, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES,c.MODE_PRIVATE);
        preferences.edit().putString(key,b).apply();
    }

    public static boolean obtenerPreferenceBoolbean(Context c, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES,c.MODE_PRIVATE);
        return preferences.getBoolean(key,false);//Si esq nunca se ha guardardado nada el la key retiornará false
    }

    public static String obtenerPreferenceString(Context c, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES,c.MODE_PRIVATE);
        return preferences.getString( key,"");//Si esq nunca se ha guardardado nada el la key retiornará false
    }

}
