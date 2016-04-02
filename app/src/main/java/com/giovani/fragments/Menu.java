package com.giovani.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.giovani.greenhat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DarkGeat on 3/29/2016.
 */
public class Menu extends Fragment implements View.OnClickListener{

    public static List<String> imagesEdited = new ArrayList<>();

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
        if (v.getId() == R.id.buttonCatalogo){
            intent = new Intent(getActivity(), com.giovani.greenhat.Librero.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.libreriaButton){
            imagesEdited = getImagesEdited(getContext());
            if (imagesEdited.size() > 0) {
                intent = new Intent(getActivity(), com.giovani.greenhat.Paginas.class);
                intent.putExtra("origin", "librero");
                startActivity(intent);
            }
        }
    }

    public static List<String> getImagesEdited(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String images = preferences.getString(context.getString(R.string.editadas), "");
        String path = null;
        List<String> reg = new ArrayList<>();
        int index = images.indexOf("|");
        while(index > 0){
            path = images.substring(0,index);
            images = images.substring(index+1);
            reg.add(path);
            index = images.indexOf("|");
            if (index < 0 && images.length() > 0){
                reg.add(images);
                images = "";
            }
        }
        if (index < 0 && images.length() > 0){
            reg.add(images);
        }
        return reg;
    }

    public static void setImagesEdited(Context context, String newImage){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        String allImages = "";
        for (String imagePath : imagesEdited){
            allImages += imagePath + "|";
        }
        allImages += newImage;
        imagesEdited.add(newImage);
        editor.putString(context.getString(R.string.editadas),allImages);
        editor.apply();
    }

    public static void updateImagesEdited(Context context, List<String> newImages){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        String allImages = "";
        for (String imagePath : newImages){
            allImages += imagePath + "|";
        }
        if (allImages.length() > 0) {
            allImages = allImages.substring(0, allImages.length() - 1);
        }
        editor.putString(context.getString(R.string.editadas),allImages);
        editor.apply();
    }
}
