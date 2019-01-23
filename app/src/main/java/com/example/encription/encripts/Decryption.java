package com.example.encription.encripts;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.encription.R;
import com.example.encription.welcome;
import com.spark.submitbutton.SubmitButton;

public class Decryption extends AppCompatActivity {

    EditText message ,keymessage;
    SubmitButton decripti,pasteButton,clearButton;
    private Boolean isDecript=true;
    ClipboardManager cm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decryption);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        message =(EditText)findViewById(R.id.textMessageUnencripted);
        keymessage=(EditText)findViewById(R.id.decriptionKey);
        decripti =(SubmitButton)findViewById(R.id.decryptDecryptionButton);
        pasteButton =(SubmitButton)findViewById(R.id.PasteDncriptButton);
        clearButton =(SubmitButton)findViewById(R.id.ClearDecryptButton);


        pasteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                cm = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip =cm.getPrimaryClip();
                ClipData.Item item = clip.getItemAt(0);
                String text = item.getText().toString();
                message.setText(text);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message.setText("");
            }
        });
        decripti.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                String mess= message.getText().toString();
                String key =keymessage.getText().toString();
                if(isDecript==true){
                    if(mess.equals("")){
                        Toast.makeText(getApplicationContext(),"Please Enter the message",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        try {
                            String decrptedMessage = AesEncription.decript(mess, key);
                            message.setText(decrptedMessage);
                            isDecript =false;
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);*/
        Intent myIntent = new Intent(getApplicationContext(), welcome.class);
        startActivityForResult(myIntent, 0);
        return true;
    }
}
