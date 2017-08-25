package com.example.mauricio.real.actividadDeUsuarios;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mauricio.real.Login;
import com.example.mauricio.real.Preferencias;
import com.example.mauricio.real.R;
import com.example.mauricio.real.actividadDeUsuarios.amigos.AmigosAtributos;
import com.example.mauricio.real.actividadDeUsuarios.clasesComunicacion.Usuario;
import com.example.mauricio.real.actividadDeUsuarios.solicitudes.SolicitudesAtributos;
import com.example.mauricio.real.actividadDeUsuarios.usuarios.UsuarioBuscadorAtributos;
import com.example.mauricio.real.internet.SolicitudesJSON;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Activity_principal extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EventBus bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Mensajes");
        setContentView(R.layout.activity_principal);
        bus = EventBus.getDefault();
        tabLayout = (TabLayout)findViewById(R.id.tabLayoutUsuarios);
        viewPager = (ViewPager)findViewById(R.id.viewPagerUsuarios);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new AdapterUsuarios(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setTitle("Amigos");
                        break;
                    case 1:
                        setTitle("Solicitudes");
                        break;
                    case 2:
                        setTitle("Buscador");
                        break;
                    case 3:
                        setTitle("Mapa");
                        break;
                    default:
                        setTitle("Sin título");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(4);

        SolicitudesJSON sj = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    JSONArray ja = j.getJSONArray("resultado");
                    for (int i = 0; i< ja.length();i++){
                        JSONObject json = ja.getJSONObject(i);
                        String id = json.getString("id");
                        String nombre = json.getString("nombre");
                        String estado = json.getString("amg_estado");
                        UsuarioBuscadorAtributos usuario = new UsuarioBuscadorAtributos();
                        usuario.setId(id);
                        usuario.setFotoPerfil(R.drawable.ic_account_circle);
                        usuario.setNombre(nombre);
                        usuario.setEstado(1);
                        SolicitudesAtributos s;
                        switch (estado){
                            case "2"://solicitudes = solicitudes
                                usuario.setEstado(2);
                                s = new SolicitudesAtributos();
                                s.setId(id);
                                s.setNombre(nombre);
                                s.setFotoPerfil(R.drawable.ic_account_circle);
                                s.setHora(json.getString("amg_fechaAmistad"));
                                s.setEstado(2);
                                //bus.post(s);
                                break;
                            case "3"://solicitudes = solicitudes
                                usuario.setEstado(3);
                                s = new SolicitudesAtributos();
                                s.setId(id);
                                s.setNombre(nombre);
                                s.setFotoPerfil(R.drawable.ic_account_circle);
                                s.setHora(json.getString("amg_fechaAmistad"));
                                s.setEstado(3);
                                //bus.post(s);
                                break;
                            case "4"://amigos
                                usuario.setEstado(4);
                                AmigosAtributos a = new AmigosAtributos();
                                a.setId(id);
                                a.setNombre(nombre);
                                a.setFotoPerfil(R.drawable.ic_account_circle);
                                a.setMensaje(json.getString("msj_mensaje"));
                                String hora_mensaje = json.getString("msj_hora_mensaje");
                                String hora_vector[] = hora_mensaje.split("\\,");
                                if (!hora_vector[0].equals(null)){
                                    a.setHora(json.getString(hora_vector[0]));
                                    //bus.post(a);
                                }else{
                                    a.setHora("hora no válida");
                                    //bus.post(a);
                                }
                                break;
                            default:
                                break;
                        }
                        bus.post(usuario);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void solicitudNoCompletada() {
                Toast.makeText(Activity_principal.this,"Error Activity_principal método abstracto", Toast.LENGTH_SHORT).show();
            }
        };
        String usuario =  Preferencias.obtenerPreferenceString(this,Preferencias.PREFERENCE_USUARIO_LOGIN);
        sj.solicitudJSONGET(this, SolicitudesJSON.URL_GET_ALL_DATA + usuario);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actiivity_amigos,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.itemCerrarSesion){
            Preferencias.guardarPreferenciasBoolean(Activity_principal.this,false,Preferencias.PREFERENCE_ESTADO_SESION);
            Intent i = new Intent(Activity_principal.this,Login.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
