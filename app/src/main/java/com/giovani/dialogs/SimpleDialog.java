package com.giovani.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.giovani.greenhat.R;

/**
 * Created by darkgeat on 4/1/16.
 */
public class SimpleDialog extends Dialog {

    public SimpleDialog(final Context context, String Title, String message, final okListener listener) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_simple);

        TextView titleLabel = (TextView) findViewById(R.id.textView7);
        TextView messageLabel = (TextView) findViewById(R.id.messageDialog);

        titleLabel.setText(Title);
        messageLabel.setText(message);
        Button okButton = (Button) findViewById(R.id.okForgot);
        Button cancelButton = (Button) findViewById(R.id.cancelForgot);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnOkSelected();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnCancelSelected();
                dismiss();
            }
        });
    }

    public interface okListener{
        void OnOkSelected();
        void OnCancelSelected();
    }
}
