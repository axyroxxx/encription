package com.example.encription.message;

import android.Manifest;
import android.app.ActionBar;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.encription.MainActivity;
import com.example.encription.R;
import com.example.encription.encripts.AesEncription;
import com.example.encription.welcome;
import com.spark.submitbutton.SubmitButton;

import javax.microedition.khronos.egl.EGLDisplay;

public class SendingSms extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    EditText destinationNumber,destinationMessage,keyEncription;
    SubmitButton subencript,subDecript,subsend;
    Boolean isEncript=false;
    String number,incriptedNumber;
    String keyPassword= "1Hbfh667bcaDEJ78";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending_sms);

        // for backward button
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        destinationNumber =(EditText)findViewById(R.id.destNumber);
        destinationMessage =(EditText)findViewById(R.id.SendMessage);
        subencript =(SubmitButton)findViewById(R.id.EncriptSmsButton);
        subDecript =(SubmitButton)findViewById(R.id.DecryptSmsButton);
        subsend =(SubmitButton)findViewById(R.id.SendSmsButton);
        keyEncription=(EditText)findViewById(R.id.keyText);

        Context context =getApplicationContext();
        SharedPreferences settings = context.getSharedPreferences(getString(R.string.account),Context.MODE_PRIVATE);
        incriptedNumber = settings.getString(getString(R.string.mobile),"");
        try {
            number = AesEncription.decript(incriptedNumber, keyPassword);
        }catch (Exception e){
            e.printStackTrace();
        }

        checkForSmsPermission();

        // for backward buttom
        // disabling Decript buttton
        subDecript.setEnabled(false);
        //encripting Message
        subencript.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (keyEncription.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter key", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        if (isEncript == false) {
                            isEncript = true;
                            String EncriptedMessage = AesEncription.encript(destinationMessage.getText().toString(), keyEncription.getText().toString());
                            destinationMessage.setText(EncriptedMessage);
                            subDecript.setEnabled(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Decrypting Message
        subDecript.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(keyEncription.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please Enter key",Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        if (isEncript == true) {
                            isEncript = false;
                            String DecriptedMessage = AesEncription.decript(destinationMessage.getText().toString(), keyEncription.getText().toString());
                            destinationMessage.setText(DecriptedMessage);
                            //subDecript.setEnabled(false);
                            //Toast.makeText(getApplicationContext(),number,Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //sending message
        subsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if( destinationNumber.getText().toString().equals("") || destinationNumber.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),"Enter valid Mobile Number",Toast.LENGTH_SHORT).show();
                }else {
                     String destNumber = destinationNumber.getText().toString();
                     String destMessage = destinationMessage.getText().toString();
                     String sourceNumber = number;
                     PendingIntent sendIntent = null, deliveryIntent = null;

                     SmsManager smsManager = SmsManager.getDefault();
                     smsManager.sendTextMessage(destNumber, sourceNumber, destMessage, sendIntent, deliveryIntent);
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
    private void checkForSmsPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, getString(R.string.permission_not_granted));
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            // Permission already granted. Enable the SMS button.
            enableSmsButton();
        }
    }
    /**
     * Processes permission request codes.
     *
     * @param requestCode  The request code passed in requestPermissions()
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        // For the requestCode, check if permission was granted or not.
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (permissions[0].equalsIgnoreCase(Manifest.permission.SEND_SMS)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted. Enable sms button.
                    enableSmsButton();

                } else {
                    // Permission denied.
                    Log.d(TAG, getString(R.string.failure_permission));
                    Toast.makeText(this, getString(R.string.failure_permission),
                            Toast.LENGTH_LONG).show();
                    // Disable the sms button.
                    disableSmsButton();
                }
            }
        }
    }

    private void disableSmsButton() {
        subsend.setEnabled(false);
    }

    private void enableSmsButton() {
        subsend.setEnabled(true);
    }
}
