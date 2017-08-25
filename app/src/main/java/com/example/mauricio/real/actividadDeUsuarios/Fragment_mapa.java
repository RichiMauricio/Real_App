package com.example.mauricio.real.actividadDeUsuarios;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mauricio.real.R;

/**
 * Created by Mauricio on 09/08/2017.
 */

public class Fragment_mapa extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_amigos,container,false);
        return view;
    }
}
