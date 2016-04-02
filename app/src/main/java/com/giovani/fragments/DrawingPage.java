package com.giovani.fragments;

import android.app.Dialog;
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
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.giovani.dialogs.SimpleDialog;
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
    private ImageView trazo;
    private ImageView erase;
    private ImageView line;
    private ImageView circle;
    private ImageView square;
    private FrameLayout completeImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawing_page, container, false);
        int image = getActivity().getIntent().getExtras().getInt("image");
        ImageView page = (ImageView)v.findViewById(R.id.imagePaginaDrawing);
        Glide.with(this).load(image).into(page);

        completeImage = (FrameLayout)v.findViewById(R.id.containerDraw);

        final Lienzo lienzo = (Lienzo)v.findViewById(R.id.drawImage);
        lienzo.setBackgroundColor(Color.TRANSPARENT);

        trazo = (ImageView)v.findViewById(R.id.trazoLibre);
        trazo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDraw == TypeDraw.PENCIL) {
                    typeDraw = TypeDraw.NONE;
                } else {
                    typeDraw = TypeDraw.PENCIL;
                }
                lienzo.setTypeDraw(typeDraw);
                updateViews(typeDraw);
            }
        });

        erase = (ImageView)v.findViewById(R.id.goma);
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDraw == TypeDraw.ERASE){
                    typeDraw = TypeDraw.NONE;
                }else {
                    typeDraw = TypeDraw.ERASE;
                }
                lienzo.setTypeDraw(typeDraw);
                updateViews(typeDraw);
            }
        });

        line = (ImageView)v.findViewById(R.id.lineDrawing);
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDraw == TypeDraw.LINE) {
                    typeDraw = TypeDraw.NONE;
                } else {
                    typeDraw = TypeDraw.LINE;
                }
                lienzo.setTypeDraw(typeDraw);
                updateViews(typeDraw);
            }
        });

        circle = (ImageView)v.findViewById(R.id.circleDrawing);
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDraw == TypeDraw.CIRCLE) {
                    typeDraw = TypeDraw.NONE;
                } else {
                    typeDraw = TypeDraw.CIRCLE;
                }
                lienzo.setTypeDraw(typeDraw);
                updateViews(typeDraw);
            }
        });

        square = (ImageView)v.findViewById(R.id.squareDrawing);
        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeDraw == TypeDraw.SQUARE) {
                    typeDraw = TypeDraw.NONE;
                } else {
                    typeDraw = TypeDraw.SQUARE;
                }
                lienzo.setTypeDraw(typeDraw);
                updateViews(typeDraw);
            }
        });

        ImageView clearAll = (ImageView) v.findViewById(R.id.imageClearAll);
        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lienzo.clear();
            }
        });

        ImageView back = (ImageView) v.findViewById(R.id.imageBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDialog confirmacion = new SimpleDialog(getContext(), "Guardado Automático", "¿Desea guardar los cambios que hizo en la página?", new SimpleDialog.okListener() {
                    @Override
                    public void OnOkSelected() {
                        lienzo.save(completeImage);
                        getActivity().onBackPressed();
                    }

                    @Override
                    public void OnCancelSelected() {
                        getActivity().onBackPressed();
                    }
                });
                confirmacion.show();
            }
        });

        scroll = (CustomScroll)v.findViewById(R.id.scroll);
        return v;
    }

    private void updateViews(TypeDraw type){
        Log.v("UpdatesViews",type.name());
        switch (type){
            case NONE:{
                scroll.setmScrollable(true);
                trazo.setBackgroundColor(Color.TRANSPARENT);
                erase.setBackgroundColor(Color.TRANSPARENT);
                line.setBackgroundColor(Color.TRANSPARENT);
                circle.setBackgroundColor(Color.TRANSPARENT);
                square.setBackgroundColor(Color.TRANSPARENT);
                break;
            }
            case PENCIL:{
                scroll.setmScrollable(false);
                trazo.setBackgroundColor(Color.YELLOW);
                erase.setBackgroundColor(Color.TRANSPARENT);
                line.setBackgroundColor(Color.TRANSPARENT);
                circle.setBackgroundColor(Color.TRANSPARENT);
                square.setBackgroundColor(Color.TRANSPARENT);
                break;
            }
            case ERASE:{
                scroll.setmScrollable(false);
                erase.setBackgroundColor(Color.YELLOW);
                trazo.setBackgroundColor(Color.TRANSPARENT);
                line.setBackgroundColor(Color.TRANSPARENT);
                circle.setBackgroundColor(Color.TRANSPARENT);
                square.setBackgroundColor(Color.TRANSPARENT);
                break;
            }
            case LINE:{
                scroll.setmScrollable(false);
                line.setBackgroundColor(Color.YELLOW);
                trazo.setBackgroundColor(Color.TRANSPARENT);
                erase.setBackgroundColor(Color.TRANSPARENT);
                circle.setBackgroundColor(Color.TRANSPARENT);
                square.setBackgroundColor(Color.TRANSPARENT);
                break;
            }
            case CIRCLE:{
                scroll.setmScrollable(false);
                circle.setBackgroundColor(Color.YELLOW);
                line.setBackgroundColor(Color.TRANSPARENT);
                trazo.setBackgroundColor(Color.TRANSPARENT);
                erase.setBackgroundColor(Color.TRANSPARENT);
                square.setBackgroundColor(Color.TRANSPARENT);
                break;
            }
            case SQUARE:{
                scroll.setmScrollable(false);
                square.setBackgroundColor(Color.YELLOW);
                circle.setBackgroundColor(Color.TRANSPARENT);
                line.setBackgroundColor(Color.TRANSPARENT);
                trazo.setBackgroundColor(Color.TRANSPARENT);
                erase.setBackgroundColor(Color.TRANSPARENT);
                break;
            }
            default:{
                scroll.setmScrollable(false);
                break;
            }
        }
    }

}
