package com.giovani.fragments;

import android.content.Context;
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
import com.giovani.greenhat.R;

/**
 * Created by DarkGeat on 3/28/2016.
 */
public class Pagina_Libro extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_item_pagina,container,false);
        int image = getArguments().getInt("page");
        ImageView pagina = (ImageView)v.findViewById(R.id.imagePagina);
        Glide.with(this).load(image).into(pagina);
        return v;
    }

}
