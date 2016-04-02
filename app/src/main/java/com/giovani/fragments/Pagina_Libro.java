package com.giovani.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;
import com.giovani.dialogs.SimpleDialog;
import com.giovani.greenhat.*;

/**
 * Created by DarkGeat on 3/28/2016.
 */
public class Pagina_Libro extends Fragment {

    private int image;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_item_pagina, container, false);
        final ImageView pagina = (ImageView)view.findViewById(R.id.imagePagina);
        final boolean images = getArguments().getBoolean("selector");
        if (images) {
            String image = getArguments().getString("page");
            view.setTag(image);
            Glide.with(this).load(image).into(pagina);
        }else {
            image = getArguments().getInt("page");
            Glide.with(this).load(image).into(pagina);
        }
        pagina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!images) {
                    Intent intent = new Intent(getActivity(), com.giovani.greenhat.DrawingPage.class);
                    intent.putExtra("image", image);
                    startActivity(intent);
                }else {
                    SimpleDialog borrado = new SimpleDialog(getContext(), "Borrado", "¿Quiere borrar este elemento?", new SimpleDialog.okListener() {
                        @Override
                        public void OnOkSelected() {
                            Menu.imagesEdited.remove((String)view.getTag());
                            Menu.updateImagesEdited(getActivity(), Menu.imagesEdited);
                            getActivity().finish();
                        }

                        @Override
                        public void OnCancelSelected() {

                        }
                    });
                    borrado.show();
                }
            }
        });
        return view;
    }

}
