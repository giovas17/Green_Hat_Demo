package com.giovani.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.giovani.enums.TypeDraw;
import com.giovani.greenhat.R;
import com.giovani.views.CustomScroll;
import com.giovani.views.Lienzo;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by DarkGeat on 3/31/2016.
 */
public class DrawingPage extends Fragment {

    private CustomScroll scroll;
    private TypeDraw typeDraw = TypeDraw.NONE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawing_page, container, false);
        int image = getActivity().getIntent().getExtras().getInt("image");
        ImageView page = (ImageView)v.findViewById(R.id.imagePaginaDrawing);
        Glide.with(this).load(image).into(page);

        final Lienzo lienzo = (Lienzo)v.findViewById(R.id.drawImage);
        lienzo.setBackgroundColor(Color.TRANSPARENT);

        final ImageView trazo = (ImageView)v.findViewById(R.id.trazoLibre);
        trazo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected;
                if (trazo.getTag() == null) {
                    selected = false;
                    trazo.setTag(selected);
                }
                selected = (boolean) trazo.getTag();
                scroll.setmScrollable(selected);
                trazo.setBackgroundColor(!selected ? Color.YELLOW : Color.TRANSPARENT);
                lienzo.setTypeDraw(!selected ? TypeDraw.PENCIL : TypeDraw.NONE);
                trazo.setTag(!selected);
            }
        });

        final ImageView erase = (ImageView)v.findViewById(R.id.goma);
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected;
                if (erase.getTag() == null){
                    selected = false;
                    erase.setTag(selected);
                }
                selected = (boolean)erase.getTag();
                scroll.setmScrollable(selected);
                erase.setBackgroundColor(!selected ? Color.YELLOW : Color.TRANSPARENT);
                lienzo.setTypeDraw(!selected ? TypeDraw.ERASE : TypeDraw.NONE);
                erase.setTag(!selected);
            }
        });

        scroll = (CustomScroll)v.findViewById(R.id.scroll);
        return v;
    }

}
