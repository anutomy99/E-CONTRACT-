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

public class send_feedback_and_rating extends AppCompatActivity {
    EditText e1,e2;
    Button b1;
    SharedPreferences sh;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_feedback_and_rating);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1 = findViewById(R.id.editTextTextPersonName14);
//        e2 = findViewById(R.id.editTextTextPersonName15);
        b1 = findViewById(R.id.button19);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String feedback = e1.getText().toString();

                if(feedback.equalsIgnoreCase(""))
                {
                    e1.setError("Please enter your feedback");
                    e1.requestFocus();
                }

                else {

                    url = "http://" + sh.getString("ip", "") + ":5000/send_feedback";
                    RequestQueue queue = Volley.newRequestQueue(send_feedback_and_rating.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");
                                if (res.equalsIgnoreCase("valid")) {
                                    Toast.makeText(getApplicationContext(), "Successfully send feedback", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), userhome.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "failed to send feedback", Toast.LENGTH_SHORT).show();
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
                            params.put("feedback", feedback);
                            params.put("id", sh.getString("lid", ""));

                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }

        }
        });
    }
}