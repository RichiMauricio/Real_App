package com.example.mauricio.real.mensajes;

/**
 * Created by Mauricio on 25/05/2017.
 */

public class MensajesDeTexto {

    private String id;
    private String mensaje;
    private String horaMensaje;
    private int tipoMensaje;        //mensaje enviado o recibido    1=emisor    2=receptor

    public MensajesDeTexto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHoraMensaje() {
        return horaMensaje;
    }

    public void setHoraMensaje(String horaMensaje) {
        this.horaMensaje = horaMensaje;
    }

    public int getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(int tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }
}
