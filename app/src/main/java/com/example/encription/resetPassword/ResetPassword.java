package com.example.encription.resetPassword;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.encription.R;
import com.example.encription.encripts.AesEncription;
import com.example.encription.password.EnterPassword;
import com.example.encription.welcome;
import com.spark.submitbutton.SubmitButton;

public class ResetPassword extends AppCompatActivity {

    EditText firstPass,secondPass;
    SubmitButton confirmPass;
    String keyPassword= "1Hbfh667bcaDEJ78";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstPass =(EditText)findViewById(R.id.firstPassword);
        secondPass =(EditText)findViewById(R.id.SecondPassword);
        confirmPass =(SubmitButton)findViewById(R.id.ConfirmPasswordButton);

        Context context = getApplicationContext();
        SharedPreferences settings = context.getSharedPreferences(getString(R.string.account),Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor= settings.edit();



        confirmPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String firstPassword = firstPass.getText().toString();
                final String secondPassword = secondPass.getText().toString();
                if(firstPassword.equals("") || secondPassword.equals("")){
                    Toast.makeText(getApplicationContext(),"Password Field is Empty",Toast.LENGTH_SHORT).show();
                }
                else if(firstPassword.equals(secondPassword)){
                    try{
                        String incriptedPassword = AesEncription.encript(firstPassword,keyPassword);
                        editor.putString(getString(R.string.password),incriptedPassword);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Password Changed ", Toast.LENGTH_SHORT).show();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    // Enter the new main Activity
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getApplicationContext(), EnterPassword.class);
                            startActivity(i);
                            finish();
                        }
                    },1200);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Password Do not Match",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), CheckCredentials.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
