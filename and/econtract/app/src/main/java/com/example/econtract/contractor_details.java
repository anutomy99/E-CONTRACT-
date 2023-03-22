package com.example.econtract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class contractor_details extends AppCompatActivity {
    TextView t1,t2;
    Button b1;
    SharedPreferences sh;
    String cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_details);
        t1 = findViewById(R.id.textView16);
        t2 = findViewById(R.id.textView17);
        b1 = findViewById(R.id.button13);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        cid=getIntent().getStringExtra("cid");


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),request_contractor.class);
                i.putExtra("cid",cid);
                startActivity(i);

            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),contractor_works.class);
                i.putExtra("cid",cid);
                startActivity(i);
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),contractor_skills.class);
                i.putExtra("cid",cid);
                startActivity(i);
            }
        });
    }
}