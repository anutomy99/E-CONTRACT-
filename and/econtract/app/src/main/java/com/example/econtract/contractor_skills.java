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

public class contractor_skills extends AppCompatActivity {
    ListView lst;
    SharedPreferences sh;
    ArrayList<String> skill,exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contractor_skills);
        lst = findViewById(R.id.lst3);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String  url ="http://"+sh.getString("ip", "") + ":5000/skills";
        RequestQueue queue = Volley.newRequestQueue(contractor_skills.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    skill= new ArrayList<>();
                    exp = new ArrayList<>();



                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        skill.add(jo.getString("skill"));
                        exp.add(jo.getString("experience"));


                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lst.setAdapter(new custom_view_vaccancy(contractor_skills.this,skill,exp));


                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(contractor_skills.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",getIntent().getStringExtra("cid"));


                return params;
            }
        };
        queue.add(stringRequest);


    }
}