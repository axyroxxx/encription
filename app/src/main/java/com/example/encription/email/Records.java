package com.example.encription.email;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.example.encription.R;
import com.example.encription.encripts.AesEncription;

public class Records extends AppCompatActivity {

    public static String CURRENT_EMAIL,CURRENT_PASSWORD;
    String decriptpas,deciptedema;
    String keyPassword= "1Hbfh667bcaDEJ78";

    public void Record(int x){
        Context context =getApplicationContext();
        SharedPreferences settings = context.getSharedPreferences(getString(R.string.account),Context.MODE_PRIVATE);
        decriptpas = settings.getString(getString(R.string.email),"");
        deciptedema =settings.getString(getString(R.string.password),"");
        try {
            CURRENT_EMAIL= AesEncription.decript(deciptedema, keyPassword);
            CURRENT_PASSWORD=AesEncription.decript(decriptpas,keyPassword);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public String getMail(){
        return CURRENT_EMAIL;
    }
    public String getPass(){
        return CURRENT_PASSWORD;
    }
}
