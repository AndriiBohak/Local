package com.example.local.activity;

import java.io.*;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.local.R;
import com.example.local.other.Session;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private Session session;

    Button login_btn;
    EditText input_card_number;
    EditText input_password;
    TextView link_forgot;

    @Override
//////////////////////
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new Session(this);
        login_btn = (Button) findViewById(R.id.btn_login);
        input_card_number = (EditText) findViewById(R.id.input_card_number);
        input_password = (EditText) findViewById(R.id.input_password);
        link_forgot = (TextView) findViewById(R.id.link_forgot);
        if (session.loggedin()) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
        }
        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    login();
                } catch (IOException e) {
                    Log.i("ERROR1", "" + e.toString());
                    finish();
                }
            }
        });

        link_forgot.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }

    /////////////////////////////////////
    public void login() throws IOException {
        input_card_number.setText("1700000095226");
        input_password.setText("andriybohak52634413");
        if (!validate()) {
            onLoginFailed();
            return;
        }
        session.setLoggedin(true);
        login_btn.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = input_card_number.getText().toString();
        String password = input_password.getText().toString();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        onLoginSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);


        Log.i("BUTTON", "click login");
        LoginLocal temp = new LoginLocal(input_card_number.getText().toString(), input_password.getText().toString());
        temp.execute();
        Toast.makeText(getBaseContext(), "Login ok", Toast.LENGTH_LONG).show();
    }

    //////////////////////////////////
    public boolean validate() {
        boolean valid = true;

        String card_number = input_card_number.getText().toString();
        String password = input_password.getText().toString();

        if (card_number.isEmpty() || card_number.length() != 13) {
            input_card_number.setError("enter a valid card number");
            valid = false;
        } else {
            input_card_number.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 25) {
            input_password.setError("between 4 and 25 alphanumeric characters");
            valid = false;
        } else {
            input_password.setError(null);
        }

        return valid;
    }

    //////////////////////////
    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        login_btn.setEnabled(true);
    }

    //////////////////////////
    public void onLoginSuccess() {
        login_btn.setEnabled(true);
        finish();
    }

    //////////////
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }
    //////


}


