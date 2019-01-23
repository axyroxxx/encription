package com.example.encription.password;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.encription.R;
import com.example.encription.encripts.AesEncription;
import com.example.encription.resetPassword.CheckCredentials;
import com.example.encription.welcome;
import com.spark.submitbutton.SubmitButton;

public class EnterPassword extends AppCompatActivity {

    SubmitButton enterbutton;
    EditText editText3;
    String password,decriptpassowrd;
    String keyPassword= "1Hbfh667bcaDEJ78";
    TextView resertPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        editText3 =(EditText)findViewById(R.id.editText3);
        enterbutton =(SubmitButton)findViewById(R.id.EnterButton);
        resertPass =(TextView)findViewById(R.id.textView4);

        Context context =getApplicationContext();
        SharedPreferences settings = context.getSharedPreferences(getString(R.string.account),Context.MODE_PRIVATE);
        password = settings.getString(getString(R.string.password),"");
        try {
            decriptpassowrd = AesEncription.decript(password, keyPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        enterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText3.getText().toString();

                if(text.equals(decriptpassowrd)){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getApplicationContext(), welcome.class);
                            startActivity(i);
                            finish();
                        }
                    },1000);
                }
                else{
                    Toast.makeText(EnterPassword.this,"Enter correct Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
        resertPass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CheckCredentials.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
