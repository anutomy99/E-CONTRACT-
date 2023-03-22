package com.example.econtract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class ordered_products extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String url;
    ArrayList<String> order_id,total,date,status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_products);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=findViewById(R.id.lst11);
        url ="http://"+sh.getString("ip", "") + ":5000/view_ordered_products";
        RequestQueue queue = Volley.newRequestQueue(ordered_products.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    order_id= new ArrayList<>();
                    status= new ArrayList<>();
                    total = new ArrayList<>();
                    date= new ArrayList<>();
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        order_id.add(jo.getString("order_id"));
                        total.add(jo.getString("total"));
                        status.add(jo.getString("stauts"));
                        date.add(jo.getString("date"));
                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom_ordered_products(ordered_products.this,order_id,total,date, status));
                    l1.setOnItemClickListener(ordered_products.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ordered_products.this, "err"+error, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        SharedPreferences.Editor  ed=sh.edit();
        ed.putString("ordid",order_id.get(position));
        ed.putString("amount",total.get(position));
        ed.commit();
        startActivity(new Intent(getApplicationContext(),view_cart.class));


    }
}