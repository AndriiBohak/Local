package com.example.local.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.local.R;

public class ForgotPassword extends AppCompatActivity {
       private static final int REQUEST_SIGNUP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
    }
    public void onBackPressed() {
        // Disable going back to the MainActivity
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivityForResult(intent,REQUEST_SIGNUP);
        finish();
       overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
}
