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

public class communication extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lst;
    SharedPreferences sh;
    String url,ttid;
    ArrayList<String> fname,lname,cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lst = findViewById(R.id.lst7);

        url ="http://"+sh.getString("ip", "") + ":5000/view_contractors";
        RequestQueue queue = Volley.newRequestQueue(communication.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    fname= new ArrayList<>();
                    lname = new ArrayList<>();
                    cid = new ArrayList<String>();
//                    place= new ArrayList<>();
//                    post=new ArrayList<>();
//                    pin= new ArrayList<>();
//                    phone= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        fname.add(jo.getString("firstname")+" "+jo.getString("lastname"));

                        cid.add(jo.getString("lid"));
//                        place.add(jo.getString("place"));
//                        post.add(jo.getString("post"));
//                        pin.add(jo.getString("pin"));
//                        phone.add(jo.getString("phonenum"));





                    }
                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    lst.setAdapter(new custom_communication(communication.this,fname));
                    lst.setOnItemClickListener(communication.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(communication.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();


                return params;
            }
        };
        queue.add(stringRequest);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        ttid=cid.get(position);
        SharedPreferences.Editor edp = sh.edit();
        edp.putString("uid", ttid);
        edp.commit();
        Intent i=new Intent(getApplicationContext(), Test.class);
        i.putExtra("name",fname.get(position));

        i.putExtra("type","type");
        startActivity(i);


    }
}