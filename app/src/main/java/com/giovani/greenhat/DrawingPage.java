package com.giovani.greenhat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by DarkGeat on 3/30/2016.
 */
public class DrawingPage extends AppCompatActivity {

    public onBackListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_page);

        listener = (onBackListener) getSupportFragmentManager().findFragmentById(R.id.fragment_image_page);
    }

    @Override
    public void onBackPressed() {
        listener.onBackPressed();
    }

    public interface onBackListener{
        void onBackPressed();
    }
}
