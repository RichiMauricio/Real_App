package com.example.mauricio.real.mensajes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mauricio.real.Preferencias;
import com.example.mauricio.real.Login;
import com.example.mauricio.real.R;
import com.example.mauricio.real.VolleyRP;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MensajeriaReal extends AppCompatActivity {

    public static final String MENSAJE = "MENSAJE";
    private BroadcastReceiver br;
    private RecyclerView rv;
    private List<MensajesDeTexto> mensajeTexto;
    private MensajesAdapter adapter;
    private ImageButton btnEnviarMensaje;
    private EditText etEscribirMensaje;
    private int LINEAS_TEXTO=1;

    private RequestQueue mRequest;
    private VolleyRP volley;

    private String MENSAJE_ENVIAR = "";
    private String EMISOR = "";
    private String RECEPTOR = "";

    private static final String IP_MENSAJE = "http://realappdemo.pe.hu/ArchivosPHP/EnviarMensajes.php";

    public static final String STRING_PREFERENCES = "com.example.mauricio.real";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeria_real);
        mensajeTexto = new ArrayList<>();

        EMISOR = Preferencias.obtenerPreferenceString(this,Preferencias.PREFERENCE_USUARIO_LOGIN);
        Intent i = getIntent();     //Intent para recibir el id del q será receptor del mensaje
        Bundle bundle = i.getExtras();
        if (bundle!=null){
            RECEPTOR = bundle.getString("key_receptor");
        }

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        Toolbar toolBar= (Toolbar)findViewById(R.id.barraTareas);

        rv = (RecyclerView)findViewById(R.id.rvMensajes);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);//Mensajería
        rv.setLayoutManager(llm);   //Sirve para tener los datos en forma vertical

        btnEnviarMensaje = (ImageButton)findViewById(R.id.btnEnviarMensaje);
        etEscribirMensaje = (EditText)findViewById(R.id.etEscribirMensaje);
        adapter = new MensajesAdapter(mensajeTexto,this);

        rv.setAdapter(adapter);


        //Cada q haya un salto de línea no tapar el recycler view, sino q vaya desplegando al final el scroll

        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = (etEscribirMensaje.getText().toString()).trim();   //Convierte espacios al inicio y al final en solo la cadena de texto
                if  (!mensaje.isEmpty() && !RECEPTOR.isEmpty()){
                    MENSAJE_ENVIAR = mensaje;
                    enviarMensaje();
                    crearMensaje(mensaje,"00:00",1);   //Se llama al método escribir mensaje tomando como parámetro el dontenido del editText
                    etEscribirMensaje.setText("");
                }
            }
        });

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Broadcast reciber me permite actualizar los datos en segundo plano
        setScrollBarChat();     //enviar el scroll al final para ver el último mensaje

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String mensaje = intent.getStringExtra("key_mensaje");
                String hora = intent.getStringExtra("key_hora");
                String emisor = intent.getStringExtra("key_emisor_PHP");
                String horaParametros[] = hora.split("\\,");
                if (emisor.equals(RECEPTOR)){
                    crearMensaje(mensaje,horaParametros[0],2);
                }
            }
        };
    }


    private void enviarMensaje(){
        HashMap<String,String> hashMapToken = new HashMap<>();
        hashMapToken.put("emisor",EMISOR);
        hashMapToken.put("receptor",RECEPTOR);
        hashMapToken.put("mensaje",MENSAJE_ENVIAR);
        JSONObject params = new JSONObject(hashMapToken);

        //Con esto decimos q nuestra solicitud va a ser un método post
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_MENSAJE, params, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    Toast.makeText(MensajeriaReal.this,datos.getString("resultado"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e){
                    Toast.makeText(MensajeriaReal.this,"Ocurrió un error " + e.getMessage() ,Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MensajeriaReal.this,"Ocurrió un error en Mensajería real " + error.getMessage() ,Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,mRequest,this,volley);
    }

    public void crearMensaje(String mensaje,String hora, int tipoMensaje){
        MensajesDeTexto mensajeDeTextoAuxiliar = new MensajesDeTexto();
        mensajeDeTextoAuxiliar.setId("0");
        mensajeDeTextoAuxiliar.setMensaje(mensaje);
        mensajeDeTextoAuxiliar.setHoraMensaje(hora);
        mensajeDeTextoAuxiliar.setTipoMensaje(tipoMensaje);
        mensajeTexto.add(mensajeDeTextoAuxiliar);
        adapter.notifyDataSetChanged();     //Método que indica que ha ocurrido algún cambio en el adapter
        setScrollBarChat();     //Llamamos al método para q cada vez q haya un mensaje vaya al final de la pantalla
    }

    @Override
    protected void onPause(){
        super.onPause();
        //Permite parar el broadcastreciver por el moemnto y q no se actualicen los mensajes
        LocalBroadcastManager.getInstance(this).unregisterReceiver(br);
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Vovler a nicializar el br si esq se lo detuvo en el método onPause
        LocalBroadcastManager.getInstance(this).registerReceiver(br, new IntentFilter(MENSAJE));
    }
    public void setScrollBarChat(){
        rv.scrollToPosition(adapter.getItemCount()-1);  //Ubicar la pantalla al final para leer el último mensaje
    }

}
