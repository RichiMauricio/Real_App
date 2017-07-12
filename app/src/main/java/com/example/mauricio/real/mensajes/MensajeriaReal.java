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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mauricio.real.R;
import com.example.mauricio.real.services.FirebaseId;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajeria_real);
        mensajeTexto = new ArrayList<>();

        Toolbar toolBar= (Toolbar)findViewById(R.id.barraTareas);

        rv = (RecyclerView)findViewById(R.id.rvMensajes);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rv.setLayoutManager(llm);   //Sirve para tener los datos en forma vertical

        btnEnviarMensaje = (ImageButton)findViewById(R.id.btnEnviarMensaje);
        etEscribirMensaje = (EditText)findViewById(R.id.etEscribirMensaje);
        adapter = new MensajesAdapter(mensajeTexto,this);

        rv.setAdapter(adapter);

        //Cada q haya un salto de línea no tapar el recycler view, sino q vaya desplegando al final el scroll

        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = etEscribirMensaje.getText().toString();
                if  (!mensaje.isEmpty()){
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
                crearMensaje(mensaje,hora,2);
            }
        };
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
