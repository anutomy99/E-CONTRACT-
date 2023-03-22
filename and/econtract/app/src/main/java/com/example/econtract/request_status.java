package com.example.econtract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class request_status extends AppCompatActivity {
    ListView lst;
    SharedPreferences sh;
    String url;
    ArrayList id,work_details,date,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_status);
        lst = findViewById(R.id.req);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url ="http://"+sh.getString("ip", "") + ":5000/view_request_status";
        RequestQueue queue = Volley.newRequestQueue(request_status.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    id= new ArrayList<>();
                    work_details = new ArrayList<>();
                    date = new ArrayList<>();
                    status = new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
//                        id.add(jo.getString("lid"));
                        work_details.add(jo.getString("work"));
                        date.add(jo.getString("date"));
                        status.add(jo.getString("status"));

                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lst.setAdapter(new custom_request_status(request_status.this,work_details,date,status));
//                    l1.setOnItemClickListener(viewmechanics.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(request_status.this, "err"+error, Toast.LENGTH_SHORT).show();
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

    }
}