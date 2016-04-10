package com.giovani.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.giovani.greenhat.R;
import com.giovani.listeners.OnColorListener;
import com.larswerkman.lobsterpicker.LobsterPicker;

/**
 * Created by DarkGeat on 4/9/2016.
 */
public class ColorPickerDialog extends Dialog {

    private OnColorListener listener;

    public ColorPickerDialog(Context context, int lastSelectedColor, OnColorListener onColorListener, int position) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_color_picker);

        listener = onColorListener;

        TextView title = (TextView)findViewById(R.id.textTitleColorPicker);
        title.setText(context.getString(R.string.title_color_picker));

        final LobsterPicker picker = (LobsterPicker)findViewById(R.id.colorPicker);
        picker.setHistory(lastSelectedColor);
        picker.setColorPosition(position);

        Button possitive = (Button)findViewById(R.id.okButton);
        possitive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnColorSelected(picker.getColor(), picker.getColorPosition());
                dismiss();
            }
        });

        Button cancel = (Button)findViewById(R.id.cancelButton);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
