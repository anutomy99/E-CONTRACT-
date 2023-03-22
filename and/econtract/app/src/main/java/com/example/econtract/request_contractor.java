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

public class request_contractor extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    String wrkdtls,cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_contractor);
        e1 = findViewById(R.id.editTextTextPersonName7);
        b1 = findViewById(R.id.button14);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        cid=getIntent().getStringExtra("cid");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                wrkdtls = e1.getText().toString();

             String   url = "http://" + sh.getString("ip", "") + ":5000/request_contractor";
                RequestQueue queue = Volley.newRequestQueue(request_contractor.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String res = json.getString("task");
                            if (res.equalsIgnoreCase("success")) {
                                Toast.makeText(getApplicationContext(), "Successfully send request", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), userhome.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(getApplicationContext(), "fail....", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("work", wrkdtls);
                        params.put("id", sh.getString("lid", ""));
                        params.put("cid", cid);

                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}