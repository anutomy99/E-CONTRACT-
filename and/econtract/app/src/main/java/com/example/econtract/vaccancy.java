package com.example.econtract;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class vaccancy extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lst;
    SharedPreferences sh;
    Button b1;
    String url;
    ArrayList<String> job,details,vaccancy_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccancy);
        lst = findViewById(R.id.lst8);
        b1 = findViewById(R.id.button17);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url ="http://"+sh.getString("ip", "") + ":5000/view_vaccancy";
        RequestQueue queue = Volley.newRequestQueue(vaccancy.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    job= new ArrayList<>();
                    details = new ArrayList<>();
                    vaccancy_id = new ArrayList<>();
//                    price = new ArrayList<>();
//                    place= new ArrayList<>();
//                    post=new ArrayList<>();
//                    pin= new ArrayList<>();
//                    phone= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        job.add(jo.getString("job"));
                        details.add(jo.getString("details"));
                        vaccancy_id.add(jo.getString("vaccancy_id"));
//                        price.add(jo.getString("price"));
//                        place.add(jo.getString("place"));
//                        post.add(jo.getString("post"));
//                        pin.add(jo.getString("pin"));
//                        phone.add(jo.getString("phonenum"));





                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lst.setAdapter(new custom_view_vaccancy(vaccancy.this,job,details));
                    lst.setOnItemClickListener(vaccancy.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(vaccancy.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();


                return params;
            }
        };
        queue.add(stringRequest);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),view_job_application_status.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String vid=vaccancy_id.get(i);


        AlertDialog.Builder ald=new AlertDialog.Builder(vaccancy.this);
        ald.setTitle("Apply Job")
                .setPositiveButton("Apply", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {




                        url = "http://" + sh.getString("ip", "") + ":5000/applyjob";


                        RequestQueue queue = Volley.newRequestQueue(vaccancy.this);

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

                                        Toast.makeText(vaccancy.this, "Applied", Toast.LENGTH_SHORT).show();
                                        Intent ii = new Intent(getApplicationContext(), vaccancy.class);
                                        startActivity(ii);

                                    } else {

                                        Toast.makeText(vaccancy.this, "Already Applied", Toast.LENGTH_SHORT).show();

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

                                params.put("vid",vid);
                                params.put("lid",sh.getString("lid",""));

                                return params;
                            }
                        };
                        queue.add(stringRequest);











                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent i=new Intent(getApplicationContext(),vaccancy.class);

                        startActivity(i);
                    }
                });

        AlertDialog al=ald.create();
        al.show();


//    #################################################

    }





}