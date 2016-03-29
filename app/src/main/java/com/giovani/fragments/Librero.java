package com.giovani.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.giovani.greenhat.*;

/**
 * Created by DarkGeat on 3/28/2016.
 */
public class Librero extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_librero,container,false);

        Button book = (Button)v.findViewById(R.id.button);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.giovani.greenhat.Paginas.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
