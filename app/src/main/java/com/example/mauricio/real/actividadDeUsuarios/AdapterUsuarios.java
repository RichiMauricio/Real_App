package com.example.mauricio.real.actividadDeUsuarios;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mauricio.real.actividadDeUsuarios.amigos.Fragment_ListaAmigos;
import com.example.mauricio.real.actividadDeUsuarios.solicitudes.Fragment_Solicitudes;
import com.example.mauricio.real.actividadDeUsuarios.usuarios.Fragment_buscar_usuarios;

/**
 * Created by Mauricio on 09/08/2017.
 */

public class AdapterUsuarios extends FragmentPagerAdapter {

    public AdapterUsuarios(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new Fragment_ListaAmigos();
            case 1: return new Fragment_Solicitudes();
            case 2: return new Fragment_buscar_usuarios();
            case 3: return new Fragment_mapa();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Amigos";
            case 1: return "Solicitudes";
            case 2: return "Buscar Usuarios";
            case 3: return "Mapa";
            default: return null;
        }
    }
}
