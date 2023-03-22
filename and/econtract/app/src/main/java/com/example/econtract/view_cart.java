package com.example.econtract;

import androidx.appcompat.app.AppCompatActivity;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class view_cart extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lst;
    SharedPreferences sh;
    String url;
    Button b1;
    ArrayList<String> product,price,qty,img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lst = findViewById(R.id.lst6);
        b1 = findViewById(R.id.button22);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url ="http://"+sh.getString("ip", "") + ":5000/view_productss";
        RequestQueue queue = Volley.newRequestQueue(view_cart.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    product= new ArrayList<>();

                    price = new ArrayList<>();
                    qty= new ArrayList<>();
                    img= new ArrayList<>();
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        product.add(jo.getString("product_name"));
                        price.add(jo.getString("price"));
                        qty.add(jo.getString("quantity"));
                        img.add(jo.getString("image"));
                    }


                    lst.setAdapter(new customviewproduct(view_cart.this,product,img,qty,price));
                    lst.setOnItemClickListener(view_cart.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(view_cart.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id",sh.getString("ordid",""));


                return params;
            }
        };
        queue.add(stringRequest);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getApplicationContext(),cart.class));




            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}