package com.example.cc_intents;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static  android.R.attr.duration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

    }


    public void openWebSite(View V) {
        EditText ed = findViewById(R.id.httpLinkInp);
        String input = ed.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a valid URL", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://" + input;
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // Verify if there is an activity to handle the intent
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivity(i);
        } else {
            Toast.makeText(this, "No application can handle this request", Toast.LENGTH_SHORT).show();
        }
    }



    public void sendSMS(View V) {
        EditText ed = findViewById(R.id.phoneNumInp);
        String phoneNumber = ed.getText().toString().trim();

        if (phoneNumber.length() != 10) {
            Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

//        sms intent
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No application can send SMS", Toast.LENGTH_SHORT).show();
        }
    }


    public void call(View V) {
        EditText ed = findViewById(R.id.phoneNumInp);
        String phoneNumber = ed.getText().toString().trim();

        if (phoneNumber.length() != 10) {
            Toast.makeText(this, "Please enter a valid phone number " + phoneNumber.length(), Toast.LENGTH_SHORT).show();
            return;
        }

//        need permission
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
//          its not granted, request it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        } else {
//           granted
            start_call(phoneNumber);
        }
    }

    public void  start_call(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));

        // Verify if there is an activity to handle the intent
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No application can make a phone call", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                EditText ed = findViewById(R.id.phoneNumInp);
                String phoneNumber = ed.getText().toString().trim();
                if (!phoneNumber.isEmpty()) {
                    start_call(phoneNumber);
                }
            } else {
                Toast.makeText(this, "Permission denied to make phone calls", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void showContactList(View V) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("content://contacts/people"));
        startActivity(intent);
    }



    public void srchTxt(View V) {
        EditText ed = findViewById(R.id.srchInp);
        String input = ed.getText().toString().trim();

        if(input.isEmpty()) {
            Toast.makeText(this, "Please enter a valid word", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=" + Uri.encode(input)));
        startActivity(i);


//        any apps to handle
        startActivity(Intent.createChooser(i, "Choose a browser"));

    }

    public void showLoc(View V) {
//        start - dist
        String startLocation = "King Khalid Street, Ar-Rass, Qassim";
        String endLocation = "Al Matar Street, Ar-Rass, Qassim";

        Uri GMIntentUri = Uri.parse("https://maps.google.com/maps/dir/?api=1&origin=" + Uri.encode(endLocation) + "&destination=" + Uri.encode(startLocation));
//        resault uri : https://www.google.com/maps/dir/King+Khalid+Street,+Ar-Rass,+Qassim/Al+Matar+Street,+Ar-Rass,+Qassim
//        resault uri : https://www.google.com/maps/dir/?api=1&origin=King+Khalid+Street,+Ar-Rass,+Qassim&destination=Al+Matar+Street,+Ar-Rass,+Qassim
        Intent mapI = new Intent(Intent.ACTION_VIEW, GMIntentUri);

          startActivity(Intent.createChooser(mapI, "Choose a map app"));
    }

}