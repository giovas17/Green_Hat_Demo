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

import com.giovani.greenhat.*;
import com.giovani.greenhat.Paginas;

/**
 * Created by DarkGeat on 3/28/2016.
 */
public class Librero extends Fragment implements View.OnClickListener{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_librero,container,false);

        ImageView book = (ImageView) v.findViewById(R.id.book1);
        book.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.book1){
            intent = new Intent(getActivity(), com.giovani.greenhat.Paginas.class);
            startActivity(intent);
        }
    }
}
