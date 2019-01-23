package com.example.encription;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.encription.email.SendingMail;
import com.example.encription.encripts.Decryption;
import com.example.encription.message.SendingSms;
import com.example.encription.password.EnterPassword;
import com.spark.submitbutton.SubmitButton;

public class welcome extends AppCompatActivity {

    String password;
    SubmitButton messageButton,mailButtonForWelcome, decryptButtonForWelcome ,FileButtonForWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Context context = getApplicationContext();
        SharedPreferences settings = context.getSharedPreferences(getString(R.string.account),Context.MODE_PRIVATE);
        password = settings.getString(getString(R.string.name),"null");

        messageButton =(SubmitButton)findViewById(R.id.messageButton2);
        mailButtonForWelcome =(SubmitButton)findViewById(R.id.EmailButton);
        decryptButtonForWelcome = (SubmitButton) findViewById(R.id.CallButton);
        FileButtonForWelcome =(SubmitButton)findViewById(R.id.FileButton);

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getApplicationContext(), SendingSms.class);
                            startActivity(i);
                            finish();
                        }
                    },1000);
            }
        });

        mailButtonForWelcome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent =new Intent(getApplicationContext(), SendingMail.class);
                        startActivity(intent);
                        finish();
                    }
                },1000);
            }
        });
        decryptButtonForWelcome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent =new Intent(getApplicationContext(), Decryption.class);
                        startActivity(intent);
                        finish();
                    }
                },1000);
            }
        });
       // Toast.makeText(getApplicationContext(),password,Toast.LENGTH_LONG).show();
    }
}
