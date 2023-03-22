package com.example.econtract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class registration extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    RadioButton r1,r2;
    Spinner sp1;
    Button b1;
    SharedPreferences sh;
    String fname,lname,email,gender,place,post,pin,district,phone,url,uname,pswd;
    String a[]={"Kasaragod","Kannur","Kozhikode","Wayanad","Kochi","Kottayam","Thrissur","Idukki","Thiruvananthapuram","Alappuzha","Palakkad","Malappuram","Kollam","Pathanamthitta"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1 = findViewById(R.id.editTextTextPersonName5);
        e2 = findViewById(R.id.editTextTextPersonName4);
        e3 = findViewById(R.id.editTextTextPersonName6);
        e4 = findViewById(R.id.editTextTextPersonName8);
        e5 = findViewById(R.id.editTextTextPersonName9);
        e6 = findViewById(R.id.editTextTextPersonName10);
        e7 = findViewById(R.id.editTextTextPersonName12);
        e8 = findViewById(R.id.editTextTextPersonName16);
        e9 = findViewById(R.id.editTextTextPersonName17);
        r1 = findViewById(R.id.radioButton2);
        r2 = findViewById(R.id.radioButton);
        sp1 = findViewById(R.id.spinner);
        ArrayAdapter ad = new ArrayAdapter(registration.this, android.R.layout.simple_list_item_1,a);
        sp1.setAdapter(ad);
        b1 = findViewById(R.id.button3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname = e1.getText().toString();
                lname = e2.getText().toString();
                email = e3.getText().toString();
                if (r1.isChecked())
                {
                    gender = r1.getText().toString();
                }
                else
                {
                    gender = r2.getText().toString();
                }
                place = e4.getText().toString();
                post = e5.getText().toString();
                pin = e6.getText().toString();
                district = sp1.getSelectedItem().toString();
                phone = e7.getText().toString();
                uname = e8.getText().toString();
                pswd = e9.getText().toString();


                if(fname.equalsIgnoreCase(""))
                {
                    e1.setError("Please enter your first name");
                    e1.requestFocus();
                }
                else if(!fname.matches("^[a-z A-Z]*$"))
                {
                    e1.setError("Only characters are allowed");
                    e1.requestFocus();
                }
                else if(lname.equalsIgnoreCase(""))
                {
                    e2.setError("Please enter your lastname");
                    e2.requestFocus();
                }
                else if (!lname.matches("^[a-z A-z]*$"))
                {
                    e2.setError("Only characters are allowed");
                    e2.requestFocus();
                }
                else if(email.equalsIgnoreCase(""))
                {
                    e3.setError("Please enter your email address");
                    e3.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    e3.setError("Invalid Email format");
                    e3.requestFocus();
                }
                else if(place.equalsIgnoreCase(""))
                {
                    e4.setError("Please enter your place");
                    e4.requestFocus();
                }
                else if(!place.matches("^[a-z A-Z]*$"))
                {
                    e4.setError("Only characters are allowed");
                    e4.requestFocus();
                }
                else if(post.equalsIgnoreCase(""))
                {
                    e5.setError("PLease enter your post");
                    e5.requestFocus();
                }
                else if(pin.equalsIgnoreCase(""))
                {
                    e6.setError("Please enter your pin");
                    e6.requestFocus();
                }
                else if(pin.length()!=6)
                {
                    e6.setError("Pin length must be six");
                    e6.requestFocus();
                }
                else if(phone.equalsIgnoreCase(""))
                {
                    e7.setError("Please enter your phone number");
                    e7.requestFocus();
                }
                else if(phone.length()<10)
                {
                    e7.setError("Phone number length must be ten numbers");
                    e7.requestFocus();
                }
                else if(phone.length()>10)
                {
                    e7.setError("Password length do not exceed ten numbers");
                    e7.requestFocus();
                }
                else if(uname.equalsIgnoreCase(""))
                {
                    e8.setError("Please enter your username");
                    e8.requestFocus();
                }
                else if(pswd.equalsIgnoreCase(""))
                {
                    e9.setError("enter password");
                }
                else {


                    url = "http://" + sh.getString("ip", "") + ":5000/registration";


                    RequestQueue queue = Volley.newRequestQueue(registration.this);

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
//                                String lid = json.getString("id");
//                                SharedPreferences.Editor edp = sh.edit();
//                                edp.putString("lid", lid);
//                                edp.commit();
                                    Toast.makeText(getApplicationContext(), "successfully registered", Toast.LENGTH_SHORT).show();
                                    Intent ik = new Intent(getApplicationContext(), login.class);
                                    startActivity(ik);

                                } else {

                                    Toast.makeText(registration.this, "failed to register", Toast.LENGTH_SHORT).show();

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
                            params.put("firstname", fname);
                            params.put("lastname", lname);
                            params.put("email", email);
                            params.put("gender", gender);
                            params.put("place", place);
                            params.put("post", post);
                            params.put("pin", pin);
                            params.put("district", district);
                            params.put("phone", phone);
                            params.put("username", uname);
                            params.put("password", pswd);

                            return params;
                        }
                    };
                    queue.add(stringRequest);
                }


            }
        });
    }
}