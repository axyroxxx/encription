package com.example.encription;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Handler;

import com.example.encription.password.CreatePassword;
import com.example.encription.password.EnterPassword;

public class MainActivity extends AppCompatActivity {

    public static int SPLASH_TIME =1000;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context =getApplicationContext();
        SharedPreferences settings = context.getSharedPreferences(getString(R.string.account), Context.MODE_PRIVATE);
        password = settings.getString(getString(R.string.password),"");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(password.equals("")){
                    Intent i = new Intent(getApplicationContext(), CreatePassword.class);
                    startActivity(i);
                    finish();
                }else {
                    Intent i = new Intent(getApplicationContext(), EnterPassword.class);
                    startActivity(i);
                    finish();
                }
            }
        }, SPLASH_TIME);
    }
}
