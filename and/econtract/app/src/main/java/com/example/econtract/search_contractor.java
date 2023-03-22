package com.example.econtract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
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

public class search_contractor extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    Spinner sp;
    ListView lst;
    String url;
    String a[]={"plumber","electrician","painter","carpenter","roofer"};
    ArrayList<String> lid,fname,lname,place,post,pin,phone;
    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contractor);
        sp = findViewById(R.id.spinner2);
        sp.setOnItemSelectedListener(this);

        ArrayAdapter ad = new ArrayAdapter(search_contractor.this, android.R.layout.simple_list_item_1,a);
        sp.setAdapter(ad);
        lst = findViewById(R.id.lst1);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String type=a[i];


        url ="http://"+sh.getString("ip", "") + ":5000/search_contractor";
        RequestQueue queue = Volley.newRequestQueue(search_contractor.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    lid= new ArrayList<>();
                    fname = new ArrayList<>();
                    lname = new ArrayList<>();
                    place= new ArrayList<>();
                    post=new ArrayList<>();
                    pin= new ArrayList<>();
                    phone= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        lid.add(jo.getString("lid"));
                        fname.add(jo.getString("firstname"));
                        lname.add(jo.getString("lastname"));
                        place.add(jo.getString("place"));
                        post.add(jo.getString("post"));
                        pin.add(jo.getString("pin"));
                        phone.add(jo.getString("phonenum"));





                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lst.setAdapter(new custom_view_contractors(search_contractor.this,fname,place,post,pin,phone));
                    lst.setOnItemClickListener(search_contractor.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(search_contractor.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("service",type);
                params.put("latitude",LocationService.lati);
                params.put("longitude",LocationService.logi);


                return params;
            }
        };
        queue.add(stringRequest);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


        Intent ii=new Intent(getApplicationContext(),contractor_details.class);

        ii.putExtra("cid",lid.get(i));

        startActivity(ii);



    }
}