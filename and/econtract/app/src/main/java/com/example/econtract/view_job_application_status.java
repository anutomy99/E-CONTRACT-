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

public class view_job_application_status extends AppCompatActivity {
    SharedPreferences sh;
    ListView lst;
    String url;
    ArrayList job,details,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job_application_status);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lst = findViewById(R.id.lst9);

        url ="http://"+sh.getString("ip", "") + ":5000/view_application_status";
        RequestQueue queue = Volley.newRequestQueue(view_job_application_status.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    job= new ArrayList<>();
                    details = new ArrayList<>();
                    status = new ArrayList<>();
//                    place= new ArrayList<>();
//                    post=new ArrayList<>();
//                    pin= new ArrayList<>();
//                    phone= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        job.add(jo.getString("job"));
                        details.add(jo.getString("details"));
                        status.add(jo.getString("status"));
//                        place.add(jo.getString("place"));
//                        post.add(jo.getString("post"));
//                        pin.add(jo.getString("pin"));
//                        phone.add(jo.getString("phonenum"));





                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lst.setAdapter(new custom_application_status(view_job_application_status.this,job,details,status));
//                    l1.setOnItemClickListener(viewmechanics.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(view_job_application_status.this, "err"+error, Toast.LENGTH_SHORT).show();
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