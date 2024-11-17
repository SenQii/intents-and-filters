package com.example.cc_intents;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

public class webBrowser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_browser);


        String msg = "This was (only) a demonstration for how to use Intent filters " +
                "to make an app component respond to the user, so the user can choose " +
                "which app to use";
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}