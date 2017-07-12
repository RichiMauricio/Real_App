package com.example.mauricio.real;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mauricio.real.mensajes.MensajeriaReal;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.prefs.Preferences;

//Por el momento esta Actividad es el Login
public class PrincipalActivity extends AppCompatActivity {

    private EditText etUsuario;
    private EditText etContrasena;
    private Button btnIngresar;
    private static final String IP= "http://realappdemo.pe.hu/ArchivosPHP/Login_GETID.php?id=";
    private static final String IP_TOKEN = "http://realappdemo.pe.hu/ArchivosPHP/Token_INSERT_and_UPDATE.php";

    private RequestQueue mRequest;
    private VolleyRP volley;

    private String USER="";
    private String PASS="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        etUsuario  = (EditText)findViewById(R.id.etUsuario);
        etContrasena = (EditText)findViewById(R.id.etContrasena);
        btnIngresar  = (Button)findViewById(R.id.btnIngresar);

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerificarLogin(etUsuario.getText().toString().toLowerCase(),etContrasena.getText().toString().toLowerCase());
            }
        });
    }

    public void VerificarLogin(String user, String contrasena){
        USER = user;
        PASS = contrasena;
        SolicitudJSON(IP+user);
    }

    public void SolicitudJSON(String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            //Ejecuta esto cuando la solicitud es correctamente enviada
            @Override
            public void onResponse(JSONObject datos) {
                VerificarPassword(datos);
            }
        }, new Response.ErrorListener() {
            //Ejecuta esto cuando hay algún error e.j: cae el servidor
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PrincipalActivity.this, "Error ocurrido " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,mRequest,this,volley);
    }

    public void VerificarPassword(JSONObject datos){
        try{
            String estado= datos.getString("resultado");
            if(estado.equals("consulta correcta")){
                JSONObject JsonDatos = new JSONObject(datos.getString("datos"));
                String usr_Id = JsonDatos.getString("Usr_Id");
                String usr_nombre = JsonDatos.getString("Usr_Nombre");
                String usr_contrasena = JsonDatos.getString("Usr_Contrasena");
                if (usr_nombre.equals(USER) && usr_contrasena.equals(PASS)){
                    String  token= FirebaseInstanceId.getInstance().getToken();
                    //Posible corrección...!!!
                    if (token != null){
                        //if((""+token.charAt(0)).equalsIgnoreCase("{")) {
                        //JSONObject js = new JSONObject(token);//SOLO SI APARECE {"token":"...."} o "asdasdas"
                        //String tokenRecortado = js.getString("token");
                        subirToken(token);
                        /*}else{
                            subirToken(token);
                        }*/
                    }else{
                        Toast.makeText(PrincipalActivity.this, "El token es nulo...!!!", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(PrincipalActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(PrincipalActivity.this, estado, Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(PrincipalActivity.this, "Error ocurrido: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void subirToken(String token){
        HashMap<String,String> hashMapToken = new HashMap<>();
        hashMapToken.put("tkn_user",USER);
        hashMapToken.put("tkn_token",token);
        JSONObject params = new JSONObject(hashMapToken);

        //Con esto decimos q nuestra solicitud va a ser un método post
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_TOKEN, params, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    Toast.makeText(PrincipalActivity.this,datos.getString("Token subido correctamente"),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(PrincipalActivity.this,MensajeriaReal.class);
                    startActivity(i);
                } catch (JSONException e){
                    Toast.makeText(PrincipalActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(PrincipalActivity.this,"El token no se pudo subir a la base de datos " + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,mRequest,this,volley);
    }
}
