package com.example.mauricio.real.internet;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mauricio.real.Preferencias;
import com.example.mauricio.real.R;
import com.example.mauricio.real.VolleyRP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mauricio on 23/08/2017.
 */

public abstract class SolicitudesJSON {

    public static String URL_GET_ALL_DATA = "http://realappdemo.pe.hu/ArchivosPHP/Datos_GETALL.php?id=";

    public abstract void solicitudCompletada(JSONObject j);
    public abstract void solicitudNoCompletada();

    public SolicitudesJSON(){}

    public void solicitudJSONGET(Context context, String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            //Ejecuta esto cuando la solicitud es correctamente enviada
            @Override
            public void onResponse(JSONObject datos) {
                solicitudCompletada(datos);
            }
        }, new Response.ErrorListener() {
            //Ejecuta esto cuando hay algún error e.j: cae el servidor
            @Override
            public void onErrorResponse(VolleyError error) {
                solicitudNoCompletada();
            }
        });
        VolleyRP.addToQueue(solicitud,VolleyRP.getInstance(context).getRequestQueue(),context, VolleyRP.getInstance(context));
    }

    public static void solicitudJSONPOST(){

    }

}
