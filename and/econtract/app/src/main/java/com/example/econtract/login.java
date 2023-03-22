package com.example.econtract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class login extends AppCompatActivity {
    EditText e1,e2;
    TextView t1;
    Button b1;
    String uname,pswd,url;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1 = findViewById(R.id.editTextTextPersonName2);
        e2 = findViewById(R.id.editTextTextPersonName3);
        t1 = findViewById(R.id.textView3);
        b1 = findViewById(R.id.button);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = e1.getText().toString();
                pswd = e2.getText().toString();

                if(uname.equalsIgnoreCase(""))
                {
                    e1.setError("Please enter your username");
                    e1.requestFocus();
                }
                else if (pswd.equalsIgnoreCase(""))
                {
                    e2.setError("Please enter your password");
                }
                else {

                    url = "http://" + sh.getString("ip", "") + ":5000/login";


                    RequestQueue queue = Volley.newRequestQueue(login.this);

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");
//                            Toast toast = Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG);
//                            toast.show();

                                if (res.equalsIgnoreCase("valid")) {
                                    String lid = json.getString("id");
                                    SharedPreferences.Editor edp = sh.edit();
                                    edp.putString("lid", lid);
                                    edp.commit();
                                    Intent ik = new Intent(getApplicationContext(), userhome.class);
                                    startActivity(ik);
                                    Intent ii = new Intent(getApplicationContext(), LocationService.class);
                                    startService(ii);


                                } else {

                                    Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("user_name", uname);
                            params.put("password", pswd);

                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }
//                Intent i = new Intent(getApplicationContext(),userhome.class);
//                startActivity(i);


            }
        });
       t1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i = new Intent(getApplicationContext(),registration.class);
               startActivity(i);
           }
       });

    }
}