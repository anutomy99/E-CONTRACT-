package com.example.econtract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = findViewById(R.id.editTextTextPersonName);
        b1 = findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String ip = e1.getText().toString();

               if (ip.equalsIgnoreCase(""))
               {
                   e1.setError("Please enter the ip address");
                   e1.requestFocus();
               }

               else {

                   sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                   SharedPreferences.Editor ed = sh.edit();
                   ed.putString("ip", ip);
                   ed.commit();
                   Toast toast = Toast.makeText(getApplicationContext(), "ip set successfully as " + ip, Toast.LENGTH_LONG);
                   toast.show();
                   Intent i = new Intent(getApplicationContext(), login.class);
                   startActivity(i);
               }

            }
        });
    }
}