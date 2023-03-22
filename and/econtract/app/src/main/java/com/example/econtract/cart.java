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

public class cart extends AppCompatActivity {
    TextView t1;
    EditText e1,e2,e3;
    Button b1;
    SharedPreferences sh;
    String acc,ifsc,key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1 = findViewById(R.id.editTextTextPersonName15);
        e2 = findViewById(R.id.editTextTextPersonName11);
        e3 = findViewById(R.id.editTextTextPersonName22);
        t1 = findViewById(R.id.textView24);
        t1.setText(sh.getString("amount",""));
        b1 = findViewById(R.id.button16);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acc=e1.getText().toString();
                ifsc=e2.getText().toString();
                key=e3.getText().toString();

                if(acc.equalsIgnoreCase(""))
                {
                    e1.setError("enter account number");
                    e1.requestFocus();
                }
                else if(ifsc.equalsIgnoreCase(""))
                {
                    e2.setError("enter ifsc code");

                    e2.requestFocus();
                }
                else if(key.equalsIgnoreCase(""))
                {
                    e3.setError("enter keynumber");
                    e3.requestFocus();
                }
                else {
                  String  url = "http://" + sh.getString("ip", "") + ":5000/pay";


                    RequestQueue queue = Volley.newRequestQueue(cart.this);

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

                                if (res.equalsIgnoreCase("success")) {

                                    Toast.makeText(cart.this, "Payment Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),ordered_products.class));

                                }
                                else    if (res.equalsIgnoreCase("invalid"))
                                {

                                    Toast.makeText(cart.this, "invalid account", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),cart.class));
                                }
                                else    if (res.equalsIgnoreCase("noamount"))
                                {
                                    startActivity(new Intent(getApplicationContext(),ordered_products.class));

                                    Toast.makeText(cart.this, "Insufficient amount", Toast.LENGTH_SHORT).show();

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
                            params.put("acc",acc);
                            params.put("uid", sh.getString("lid",""));
                            params.put("oid",sh.getString("ordid",""));
                            params.put("key",key);
                            params.put("ifsc",ifsc);

                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }


            }
        });


    }
}