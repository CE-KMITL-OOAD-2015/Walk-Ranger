package com.example.nattachai.walkingranger;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Admin1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin1);
        final Button B1 = (Button) findViewById(R.id.B1);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Admin.class);
                startActivity(i);
            }
        });
        final Button B2 = (Button) findViewById(R.id.B2);
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Admin2.class);
                startActivity(i);
            }
        });
        final Button B3 = (Button) findViewById(R.id.B3);
        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Admin3.class);
                startActivity(i);
            }
        });
        final Button B4 = (Button) findViewById(R.id.B4);
        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Admin4.class);
                startActivity(i);
            }
        });
    }

}
