package com.example.mauricio.real.actividadDeUsuarios.usuarios;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class Fragment_buscar_usuarios extends Fragment {

    private List<UsuarioBuscadorAtributos> atributosList;//conectada con el adaptador
    private List<UsuarioBuscadorAtributos> listAuxiliar;
    private RecyclerView rv;
    private BuscadorAdapter adapter;
    private EditText etBuscar;

    //private static final String URL_GET_ALL_USUARIOS = "http://realappdemo.pe.hu/ArchivosPHP/Usuarios_GETALL.php";

    private EventBus bus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_buscar_usuarios,container,false);

        bus = EventBus.getDefault();
        atributosList = new ArrayList<>();
        listAuxiliar = new ArrayList<>();
        rv = (RecyclerView)v.findViewById(R.id.rvUsuariosBuscador);
        etBuscar = (EditText)v.findViewById(R.id.etBuscarUsuario);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);
        adapter = new BuscadorAdapter(atributosList,getContext());
        rv.setAdapter(adapter);

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtroBuscador("" + s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    public void insertarUsuario(int fotoPerfil, String nombre, int estado,String id){
        UsuarioBuscadorAtributos buscadorAtributos = new UsuarioBuscadorAtributos();
        buscadorAtributos.setFotoPerfil(fotoPerfil);
        buscadorAtributos.setId(id);
        buscadorAtributos.setNombre(nombre);
        buscadorAtributos.setEstado(estado);
        atributosList.add(buscadorAtributos);
        listAuxiliar.add(buscadorAtributos);
        adapter.notifyDataSetChanged();
    }

    public void insertarUsuario(UsuarioBuscadorAtributos user){
        atributosList.add(user);
        listAuxiliar.add(user);
        adapter.notifyDataSetChanged();
    }

    public void filtroBuscador(String texto){
        atributosList.clear();
        for (int i=0;i<listAuxiliar.size();i++){
            if (listAuxiliar.get(i).getNombre().toLowerCase().contains(texto.toLowerCase())){
                atributosList.add(listAuxiliar.get(i));
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void solicitudJSON(){
        /*JsonObjectRequest solicitud = new JsonObjectRequest(URL_GET_ALL_USUARIOS, null, new Response.Listener<JSONObject>() {
            //Ejecuta esto cuando la solicitud es correctamente enviada
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String datosAmigos = datos.getString("resultado");
                    JSONArray jsonArrayAmigos = new JSONArray(datosAmigos);
                    String miUsuario = Preferencias.obtenerPreferenceString(getContext(),Preferencias.PREFERENCE_USUARIO_LOGIN);
                    for (int i = 0; i<jsonArrayAmigos.length();i++){
                        JSONObject jsAmigo = jsonArrayAmigos.getJSONObject(i);
                        if (!jsAmigo.getString("id").equals(miUsuario)){
                            insertarUsuario(R.drawable.ic_action_user, jsAmigo.getString("nombre"),1, jsAmigo.getString("id"));
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error ocurrido en Fragment_ListaAmigos al descomponer el json" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            //Ejecuta esto cuando hay algún error e.j: cae el servidor
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error ocurrido en Fragment_ListaAmigos" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,mRequest,getContext(),volley);*/
    }

    @Subscribe
    public void ejecutarLlamada(UsuarioBuscadorAtributos user) { //Si en el fragment emisor enviamos un objeto d cualquier tipo debe ser paámetro ese objeto
        insertarUsuario(user);
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }



}
