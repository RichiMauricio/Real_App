package com.example.mauricio.real.actividadDeUsuarios.clasesComunicacion;

/**
 * Created by Mauricio on 23/08/2017.
 */

/*
Datos que se necesitan para el fragment de buscar usuarios
id
nombre
estado
fecha_amigos
mensaje
hora mensaje
 */
public class Usuario {

    String id;
    String nombre;
    int estado;
    String mensaje;
    String hora;
    int fotoPerfil;

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(int fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
