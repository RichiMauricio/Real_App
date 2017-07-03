package com.example.mauricio.real.mensajes;

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

import java.util.ArrayList;
import java.util.List;

public class MensajeriaReal extends AppCompatActivity {

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

        for (int i=0;i<10;i++){
            MensajesDeTexto mensajeDeTextoAuxiliar = new MensajesDeTexto();
            mensajeDeTextoAuxiliar.setId("" + i);
            mensajeDeTextoAuxiliar.setMensaje("Emisor " + i);
            mensajeDeTextoAuxiliar.setHoraMensaje("20:4" + i);
            mensajeDeTextoAuxiliar.setTipoMensaje(1);
            mensajeTexto.add(mensajeDeTextoAuxiliar);
        }

        for (int i=0;i<10;i++){
            MensajesDeTexto mensajeDeTextoAuxiliar = new MensajesDeTexto();
            mensajeDeTextoAuxiliar.setId("" + i);
            mensajeDeTextoAuxiliar.setMensaje("Receptor " + i);
            mensajeDeTextoAuxiliar.setHoraMensaje("20:4" + i);
            mensajeDeTextoAuxiliar.setTipoMensaje(2);
            mensajeTexto.add(mensajeDeTextoAuxiliar);
        }

        adapter = new MensajesAdapter(mensajeTexto,this);

        rv.setAdapter(adapter);

        //Cada q haya un salto de línea no tapar el recycler view, sino q vaya desplegando al final el scroll

        btnEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearMensaje(etEscribirMensaje.getText().toString());   //Se llama al método escribir mensaje tomando como parámetro el dontenido del editText
            }
        });

        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setScrollBarChat();     //enviar el scroll al final para ver el último mensaje
    }

    public void crearMensaje(String mensaje){
        MensajesDeTexto mensajeDeTextoAuxiliar = new MensajesDeTexto();
        mensajeDeTextoAuxiliar.setId("0");
        mensajeDeTextoAuxiliar.setMensaje(mensaje);
        mensajeDeTextoAuxiliar.setHoraMensaje("21:30");
        mensajeDeTextoAuxiliar.setTipoMensaje(1);
        mensajeTexto.add(mensajeDeTextoAuxiliar);
        adapter.notifyDataSetChanged();     //Método que indica que ha ocurrido algún cambio en el adapter
        etEscribirMensaje.setText("");
        setScrollBarChat();     //Llamamos al método para q cada vez q haya un mensaje vaya al final de la pantalla
    }

    public void setScrollBarChat(){
        rv.scrollToPosition(adapter.getItemCount()-1);  //Ubicar la pantalla al final para leer el último mensaje
    }
}
