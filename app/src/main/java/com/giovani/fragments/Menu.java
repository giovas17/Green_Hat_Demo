package com.giovani.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.giovani.greenhat.R;

/**
 * Created by DarkGeat on 3/29/2016.
 */
public class Menu extends Fragment implements View.OnClickListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menu,container,false);

        ImageView catologo = (ImageView)v.findViewById(R.id.libreriaButton);
        catologo.setOnClickListener(this);

        ImageView catalogoIcon = (ImageView)v.findViewById(R.id.buttonCatalogo);
        catalogoIcon.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.libreriaButton || v.getId() == R.id.buttonCatalogo){
            intent = new Intent(getActivity(), com.giovani.greenhat.Librero.class);
            startActivity(intent);
        }
    }
}
