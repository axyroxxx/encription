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

public class CheckCredentials extends AppCompatActivity {

    private EditText emailEdit,numberEdit;
    SubmitButton verifybutton;
    String emailCre,numberCre,decreptedEmail,decrepedNumber;
    String keyPassword= "1Hbfh667bcaDEJ78";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_credentials);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emailEdit =(EditText)findViewById(R.id.emailCredential);
        numberEdit =(EditText)findViewById(R.id.mobilecredeentials);
        verifybutton =(SubmitButton)findViewById(R.id.verifyButton);

        Context context =getApplicationContext();
        SharedPreferences setting =context.getSharedPreferences(getString(R.string.account),Context.MODE_PRIVATE);
        emailCre = setting.getString(getString(R.string.email),"");
        numberCre = setting.getString(getString(R.string.mobile),"");


        try{
            decreptedEmail = AesEncription.decript(emailCre,keyPassword);
            decrepedNumber = AesEncription.decript(numberCre,keyPassword);
        }catch (Exception e){
            e.printStackTrace();
        }

        verifybutton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String emailget = emailEdit.getText().toString();
                String numberget = numberEdit.getText().toString();
                if(emailget.equals("") || numberget.equals("")){
                    Toast.makeText(getApplicationContext(),"Please fill the boxes",Toast.LENGTH_SHORT).show();
                }
                else if(emailget.equals(decreptedEmail) && numberget.equals(decrepedNumber)){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent =new Intent(getApplicationContext(),ResetPassword.class);
                            startActivity(intent);
                            finish();
                            //Toast.makeText(getApplicationContext(),"yes information is right",Toast.LENGTH_SHORT).show();
                        }
                    },1000);
                }
                else{
                    //Toast.makeText(getApplicationContext(),emailget+" "+numberget,Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"Please Enter valid Details",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), EnterPassword.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
