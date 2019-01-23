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
import android.widget.Toast;

import com.example.encription.R;
import com.example.encription.encripts.AesEncription;
import com.example.encription.welcome;
import com.spark.submitbutton.SubmitButton;

public class CreatePassword extends AppCompatActivity {

    EditText editText2,editText,editName,editEmail,editNumber;
    SubmitButton createbutton;
    String Password= "1Hbfh667bcaDEJ78";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        editText =(EditText)findViewById(R.id.editText);
        editText2 =(EditText)findViewById(R.id.editText2);
        createbutton =(SubmitButton)findViewById(R.id.CreateButton);
        editName =(EditText)findViewById(R.id.nameText);
        editEmail =(EditText)findViewById(R.id.editEmail);
        editNumber =(EditText)findViewById(R.id.editnumber);

        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String test1 =editText.getText().toString();
                String test2 =editText2.getText().toString();
                String email =editEmail.getText().toString();
                String name =editName.getText().toString();
                String number = editNumber.getText().toString();

                if(test1.equals("") || test2.equals("")){
                    Toast.makeText(CreatePassword.this,"No Password Enter",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(test1.equals(test2)){
                        Context context = getApplicationContext();
                        SharedPreferences settings = context.getSharedPreferences(getString(R.string.account),Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor= settings.edit();
                        try{
                            String encriptPassword = AesEncription.encript(test1,Password);
                            String encriptName = AesEncription.encript(name,Password);
                            String encriptEmail =AesEncription.encript(email,Password);
                            String encriptNumber =AesEncription.encript(number,Password);

                            editor.putString(getString(R.string.password),encriptPassword);
                            editor.putString(getString(R.string.email),encriptEmail);
                            editor.putString(getString(R.string.name),encriptName);
                            editor.putString(getString(R.string.mobile),encriptNumber);
                            editor.commit();
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                            // Enter the new main Activity
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(getApplicationContext(), welcome.class);
                                startActivity(i);
                                finish();
                            }
                        },1200);
                    }
                    else{
                        Toast.makeText(CreatePassword.this,"Password Doesn't Match",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
