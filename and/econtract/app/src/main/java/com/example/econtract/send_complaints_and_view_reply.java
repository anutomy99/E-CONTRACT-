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

public class send_complaints_and_view_reply extends AppCompatActivity {

    EditText e1;
    Button b1;
    ListView lst;
    SharedPreferences sh;
    String complaint,url;
    ArrayList<String> complaints,date,reply;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_complaints_and_view_reply);

        e1 = findViewById(R.id.editTextTextPersonName13);
        b1 = findViewById(R.id.button18);
        lst = findViewById(R.id.lst10);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        url ="http://"+sh.getString("ip", "") + ":5000/view_reply";
        RequestQueue queue = Volley.newRequestQueue(send_complaints_and_view_reply.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    complaints= new ArrayList<>();
                    date = new ArrayList<>();
                    reply = new ArrayList<>();
//                    place= new ArrayList<>();
//                    post=new ArrayList<>();
//                    pin= new ArrayList<>();
//                    phone= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        complaints.add(jo.getString("complaint"));
                        date.add(jo.getString("date"));
                        reply.add(jo.getString("reply"));
//                        place.add(jo.getString("place"));
//                        post.add(jo.getString("post"));
//                        pin.add(jo.getString("pin"));
//                        phone.add(jo.getString("phonenum"));





                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lst.setAdapter(new custom_view_reply(send_complaints_and_view_reply.this,complaints,date,reply));
//                    l1.setOnItemClickListener(send_complaints_and_view_reply.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(send_complaints_and_view_reply.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",sh.getString("lid",""));


                return params;
            }
        };
        queue.add(stringRequest);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               complaint = e1.getText().toString();
               if (complaint.equalsIgnoreCase(""))
               {
                   e1.setError("Please enter your complaint");
                   e1.requestFocus();
               }
               else {
                   url = "http://" + sh.getString("ip", "") + ":5000/add_complaint";
                   RequestQueue queue = Volley.newRequestQueue(send_complaints_and_view_reply.this);
                   StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           try {
                               JSONObject json = new JSONObject(response);
                               String res = json.getString("task");
                               if (res.equalsIgnoreCase("valid")) {
                                   Toast.makeText(getApplicationContext(), "Successfully send Complaint", Toast.LENGTH_SHORT).show();
                                   Intent i = new Intent(getApplicationContext(), userhome.class);
                                   startActivity(i);
                               } else {
                                   Toast.makeText(getApplicationContext(), "failed to send Complaint", Toast.LENGTH_SHORT).show();
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
                           params.put("complaint", complaint);
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