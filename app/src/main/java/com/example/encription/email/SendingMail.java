package com.example.encription.email;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.encription.R;
import com.example.encription.encripts.AesEncription;
import com.example.encription.welcome;
import com.spark.submitbutton.SubmitButton;

public class SendingMail extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private EditText editTextKey;
    private boolean isEncryptMail=false;
    public static String CURRENT_EMAIL,CURRENT_PASSWORD;
    String decriptpas,deciptedema;
    String keyPassword= "1Hbfh667bcaDEJ78";
    private SubmitButton encriptButton,decryptButton,buttonSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_mail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        editTextKey = (EditText) findViewById(R.id.encriptionMailKeyEdit);

        encriptButton =(SubmitButton)findViewById(R.id.EncriptMailButton);
        decryptButton =(SubmitButton)findViewById(R.id.DecryptMailButton);
        buttonSend =(SubmitButton)findViewById(R.id.SendMailButton);
/**
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
**/
        decryptButton.setEnabled(false);

        encriptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(isEncryptMail==false) {
                        isEncryptMail = true;
                        String encriptMessage = AesEncription.encript(editTextMessage.getText().toString(), editTextKey.getText().toString());
                        editTextMessage.setText(encriptMessage);
                        decryptButton.setEnabled(true);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    if (isEncryptMail == true) {
                        isEncryptMail = false;
                        String decrptMesage = AesEncription.decript(editTextMessage.getText().toString(),editTextKey.getText().toString());
                        editTextMessage.setText(decrptMesage);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });


    }
    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, subject, message);
        //Executing sendmail to send email
        sm.execute();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case android.R.id.home: {
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }*/
        Intent myIntent = new Intent(getApplicationContext(), welcome.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
