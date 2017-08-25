package com.example.mauricio.real;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Activity_registro extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private EditText nombre;
    private EditText telefono;
    private Button btnRegistrar;
    private Spinner spnPais;

    private RequestQueue mRequest;
    private VolleyRP volley;

    private static final String IP_REGISTRAR = "http://realappdemo.pe.hu/ArchivosPHP/Registro_INSERT.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        user = (EditText) findViewById(R.id.userRegistro);
        password = (EditText) findViewById(R.id.passwordRegistro);
        nombre = (EditText) findViewById(R.id.nombreRegistro);
        telefono = (EditText) findViewById(R.id.telefonoRegistro);
        btnRegistrar = (Button) findViewById(R.id.btnRegistro);
        spnPais = (Spinner)findViewById(R.id.spnPais);

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarWebService(getStringET(user).trim(),getStringET(password).trim(),getStringET(nombre).trim(),getStringET(telefono).trim());
            }
        });
    }

    private void registrarWebService(String user, String contrasena, String nombre, String telefono){
        if  (!user.isEmpty() && !contrasena.isEmpty() && !nombre.isEmpty() && !telefono.isEmpty()){
            HashMap<String,String> hashMapToken = new HashMap<>();
            hashMapToken.put("id",user);
            hashMapToken.put("nombre",nombre);
            hashMapToken.put("telefono",telefono);
            hashMapToken.put("usr_nombre",user);
            hashMapToken.put("usr_contrasena",contrasena);
            JSONObject params = new JSONObject(hashMapToken);

            //Con esto decimos q nuestra solicitud va a ser un método post
            JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_REGISTRAR, params, new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject datos) {
                    try {
                        String estado = datos.getString("resultado");
                        if(estado.equalsIgnoreCase("El usuario se registró correctamente")){
                            Toast.makeText(Activity_registro.this,estado,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Activity_registro.this,estado,Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e){
                        Toast.makeText(Activity_registro.this,"Ocurrió un error " + e.getMessage() ,Toast.LENGTH_SHORT).show();
                    }
                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(Activity_registro.this,"Ocurrió un error " + error.getMessage() ,Toast.LENGTH_SHORT).show();
                }
            });
            VolleyRP.addToQueue(solicitud,mRequest,this,volley);
        }else{
            Toast.makeText(Activity_registro.this,"Complete todos los campos",Toast.LENGTH_SHORT).show();
        }

    }

    private String getStringET(EditText et){
        return et.getText().toString();
    }
}
