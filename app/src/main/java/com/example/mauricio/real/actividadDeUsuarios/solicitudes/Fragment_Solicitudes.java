package com.example.mauricio.real.actividadDeUsuarios.solicitudes;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mauricio.real.Preferencias;
import com.example.mauricio.real.R;
import com.example.mauricio.real.VolleyRP;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Solicitudes extends Fragment {

    private SolicitudesAdapter solicitudesAdapter;
    private List<SolicitudesAtributos> listSolicitudes;
    private RecyclerView rv;
    private LinearLayout llNoSolicitudes;

    private EventBus bus = EventBus.getDefault();

    //private final String URL_GET_ALL_USUARIOS = "http://realappdemo.pe.hu/ArchivosPHP/Amigos_GETALL.php?id=";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment__solicitudes,container,false);

        listSolicitudes = new ArrayList<SolicitudesAtributos>();
        rv = (RecyclerView)v.findViewById(R.id.rvSolicitudes);
        llNoSolicitudes = (LinearLayout)v.findViewById(R.id.llNoSolicitudes);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        solicitudesAdapter = new SolicitudesAdapter(listSolicitudes,getContext());
        rv.setAdapter(solicitudesAdapter);

        //String usuario = Preferencias.obtenerPreferenceString(getContext(),Preferencias.PREFERENCE_USUARIO_LOGIN);
        //solicitudJSON(URL_GET_ALL_USUARIOS + usuario);
        verificarSolicitudes();
        return v;
    }

    public void verificarSolicitudes(){
        if (listSolicitudes.isEmpty()){
            llNoSolicitudes.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else{
            llNoSolicitudes.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }

    public void actualizarTarjetas(){
        solicitudesAdapter.notifyDataSetChanged();
        verificarSolicitudes();
    }

    public void agregarTarjetasDeSolicitud(int fotoPerfil,String id, String nombre, String hora){
        SolicitudesAtributos solicitudesAtributos = new SolicitudesAtributos();
        solicitudesAtributos.setFotoPerfil(fotoPerfil);
        solicitudesAtributos.setId(id);
        solicitudesAtributos.setNombre(nombre);
        solicitudesAtributos.setHora(hora);
        listSolicitudes.add(solicitudesAtributos);
        actualizarTarjetas();
    }

    public void agregarTarjetasDeSolicitud(SolicitudesAtributos solicitudesAtributos){
        listSolicitudes.add(solicitudesAtributos);
        actualizarTarjetas();
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);//Registrar el bus para q comience a funcionar
    }

    @Subscribe
    public void ejecutarLlamada(SolicitudesAtributos b){ //Si en el fragment emisor enviamos un objeto d cualquier tipo debe ser paámetro ese objeto
        agregarTarjetasDeSolicitud(b);
    }

    public void solicitudJSON(String URL){
        /*JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            //Ejecuta esto cuando la solicitud es correctamente enviada
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String datosAmigos = datos.getString("resultado");
                    JSONArray jsonArrayAmigos = new JSONArray(datosAmigos);
                    for (int i = 0;i<jsonArrayAmigos.length();i++){
                        JSONObject jsonObject = new JSONObject(jsonArrayAmigos.getString(i));
                        agregarTarjetasDeSolicitud(R.drawable.ic_account_circle, "null", jsonObject.getString("nombre"),jsonObject.getString("amg_fechaAmistad"));
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error ocurrido en Fragment_ListaSolicitudes al descomponer el json" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            //Ejecuta esto cuando hay algún error e.j: cae el servidor
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error ocurrido en Fragment_ListaSolicitudes" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,mRequest,getContext(),volley);*/
    }
}
