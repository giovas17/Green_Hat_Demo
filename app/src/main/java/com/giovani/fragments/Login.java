package com.giovani.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.giovani.greenhat.*;

/**
 * Created by DarkGeat on 3/28/2016.
 */
public class Login extends Fragment {

    private Button login;
    private EditText passwordInput;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login,container,false);

        passwordInput = (EditText)v.findViewById(R.id.editTextPassword);
        login = (Button)v.findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatePassword()){
                    if (passwordInput.getText().toString().equalsIgnoreCase(getString(R.string.valid_password))){
                        Intent intent = new Intent(getActivity(), com.giovani.greenhat.Librero.class);
                        startActivity(intent);
                    }
                }
            }
        });

        return v;
    }

    private boolean validatePassword() {
        boolean valid = true;
        if (passwordInput.getText().toString().length() == 0){
            passwordInput.setError(getString(R.string.error_required_field));
            passwordInput.requestFocus();
            valid = false;
        }
        if (passwordInput.getText().toString().length() > 4){
            passwordInput.setError(getString(R.string.error_wrong_length));
            passwordInput.requestFocus();
            valid = false;
        }
        return valid;
    }
}
