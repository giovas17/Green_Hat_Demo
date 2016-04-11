package com.giovani.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.giovani.greenhat.R;
import com.giovani.listeners.OnTextListener;

/**
 * Created by DarkGeat on 4/10/2016.
 */
public class TextDialog extends Dialog {

    private EditText ingresado;
    private String textoEtiqueta;

    public TextDialog(Context context, final OnTextListener listener) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_text_entry);

        ingresado = (EditText)findViewById(R.id.textoEtiqueta);
        Button ok = (Button)findViewById(R.id.okTextButton);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textoEtiqueta = ingresado.getText().toString();
                listener.OnOkPressed(textoEtiqueta);
                dismiss();
            }
        });

        Button cancel = (Button)findViewById(R.id.cancelTextButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnCancelPressed();
                dismiss();
            }
        });
    }
}
