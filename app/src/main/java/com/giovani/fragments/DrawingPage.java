package com.giovani.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.giovani.greenhat.R;

/**
 * Created by DarkGeat on 3/31/2016.
 */
public class DrawingPage extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawing_page,container,false);
        int image = getActivity().getIntent().getExtras().getInt("image");
        ImageView page = (ImageView)v.findViewById(R.id.imagePaginaDrawing);
        Glide.with(this).load(image).into(page);
        return v;
    }
}
