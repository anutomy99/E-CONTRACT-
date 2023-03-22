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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class buy_products extends AppCompatActivity implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    Spinner sp;
//    Button b1;
    ListView lst;
    SharedPreferences sh;
    String url,cont,pid,prc;
    Button b1;
    ArrayList<String> product,quantity,price,contractor,id,pidd,img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_products);
        sp = findViewById(R.id.spinner3);
        b1=findViewById(R.id.button15);
        lst = findViewById(R.id.lst5);
        b1.setVisibility(View.INVISIBLE);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        getoid();
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    url ="http://"+sh.getString("ip", "") + ":5000/finish";


                    RequestQueue queue = Volley.newRequestQueue(buy_products.this);

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");
                                if (res.equalsIgnoreCase("ok"))
                                {
                                    Toast.makeText(getApplicationContext(),"ORDER PLACED SUCCESSFULLY",Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(getApplicationContext(),userhome.class));
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
                            params.put("oid",sh.getString("oid",""));


                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }


            });
        url ="http://"+sh.getString("ip", "") + ":5000/view_contractors";
        RequestQueue queue = Volley.newRequestQueue(buy_products.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
//                    Toast.makeText(getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
                    contractor = new ArrayList<>();
                    id = new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        contractor.add(jo.getString("firstname")+" "+jo.getString("lastname"));
                        id.add(jo.getString("lid"));

                    }
                    ArrayAdapter<String> ad=new ArrayAdapter<>(buy_products.this,android.R.layout.simple_spinner_item,contractor);
                    sp.setAdapter(ad);
                    sp.setOnItemSelectedListener(buy_products.this);


                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(buy_products.this, "err"+error, Toast.LENGTH_SHORT).show();
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

    private void viewpdt(String cont) {
        String url1 ="http://"+sh.getString("ip", "") + ":5000/view_products";
        RequestQueue queue1 = Volley.newRequestQueue(buy_products.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//view service code
                        JSONArray ar = jsonObj.getJSONArray("data");//from python
//
//                        JSONArray ar = new JSONArray(response);
                        product = new ArrayList<>();
                        quantity = new ArrayList<>();
                        price = new ArrayList<>();
                        pidd = new ArrayList<>();
                        img = new ArrayList<>();

                        b1.setVisibility(View.VISIBLE);
                        lst.setVisibility(View.VISIBLE);

                        for (int i = 0; i < ar.length(); i++) {
                            JSONObject jo = ar.getJSONObject(i);

                            product.add(jo.getString("product_name"));
                            quantity.add(jo.getString("quantity"));
                            price.add(jo.getString("price"));
                            pidd.add(jo.getString("product_id"));
                            img.add(jo.getString("image"));


                        }


                        lst.setAdapter(new customviewproduct(buy_products.this, product, img, quantity, price));
                        lst.setOnItemClickListener(buy_products.this);
                    }
                    else {
                        lst.setVisibility(View.INVISIBLE);
                        b1.setVisibility(View.INVISIBLE);
                    }
                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(buy_products.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("id",cont);


                return params;
            }
        };
        queue1.add(stringRequest);
    }

    private void getoid() {
        url ="http://"+sh.getString("ip", "") + ":5000/getoid";

        RequestQueue queue = Volley.newRequestQueue(buy_products.this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {
                    JSONObject json = new JSONObject(response);
                    String res = json.getString("task");
                    SharedPreferences.Editor ed=sh.edit();
                    ed.putString("oid",res);
                    ed.commit();


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
                params.put("id",sh.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        cont=id.get(i);
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("cntrid",cont);
        ed.commit();
        viewpdt(cont);








    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        pid=pidd.get(position);
        prc=price.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(buy_products.this);
        builder.setTitle("ORDER PRODUCT");
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_layout, null);
        builder.setView(customLayout);
        // add a button
        builder
                .setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int which)
                            {

                                // send data from the
                                // AlertDialog to the Activity

                                Spinner  s1 = customLayout.findViewById(R.id.spinner);





                                sendDialogDataToActivity(s1.getSelectedItem().toString(),pid,prc);
                            }
                        });

        // create and show
        // the alert dialog
        AlertDialog dialog
                = builder.create();
        dialog.show();






    }

    private void sendDialogDataToActivity(String data,String pdid,String pecc)
    {

        url="http://"+sh.getString("ip","")+":5000/inserorder";





        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(buy_products.this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.

                try {
                    JSONObject jo = new JSONObject(response);
                    String status = jo.getString("task");

                    if (status.equalsIgnoreCase("ok")) {
                        Toast.makeText(buy_products.this, "success", Toast.LENGTH_SHORT).show();
                            viewpdt(sh.getString("cntrid",""));
                    } else {
                        Toast.makeText(buy_products.this, "error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {

                    Toast.makeText(buy_products.this, "" + e, Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(buy_products.this, error + "errrrrrrr", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("qty", data);
                params.put("pid", pdid);
                params.put("uid",sh.getString("lid",""));
                params.put("oid",sh.getString("oid",""));
                params.put("prc", prc);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);







    }
}