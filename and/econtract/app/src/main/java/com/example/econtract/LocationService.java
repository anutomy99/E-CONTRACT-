package com.example.econtract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class LocationService extends Service{

    private LocationManager locationManager;
    private Boolean locationChanged;
    private Handler handler = new Handler();

    public static Location curLocation;
    public static String place="";

     public static String latitude,longitude,lati="",logi="";

    SharedPreferences sh;

    String imei = "",url="";
   
    LocationListener locationListener = new LocationListener()
    {
        public void onLocationChanged(Location location)
        {
            if (curLocation == null)
            {
                curLocation = location;
                locationChanged = true;
            }
            else if (curLocation.getLatitude() == location.getLatitude() && curLocation.getLongitude() == location.getLongitude())
            {
                locationChanged = false;
                return;
            }
            else
                locationChanged = true;

            curLocation = location;
            latitude=String.valueOf(curLocation.getLatitude());
            longitude=String.valueOf(curLocation.getLongitude());

            updatelocation(latitude,longitude);

            if (locationChanged)
                locationManager.removeUpdates(locationListener);
        }
        public void onProviderDisabled(String provider)
        {
        }
        public void onProviderEnabled(String provider)
        {
        }
        public void onStatusChanged(String provider, int status,Bundle extras) {
        }
    };

    @Override
    public void onCreate()
    {
        super.onCreate();
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        try
        {
            if(Build.VERSION.SDK_INT>9)
            {
                StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
        }
        catch(Exception e)
        {

        }

        curLocation = getBestLocation();
        if (curLocation == null)
        {
            Toast.makeText(this, "GPS problem..........", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStart(Intent i, int startId)
    {
        Toast.makeText(this, "Start services", Toast.LENGTH_SHORT).show();
        handler.post(GpsFinder);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onDestroy()
    {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(provider.contains("gps"))
        { //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
        handler.removeCallbacks(GpsFinder);
        handler = null;
        Toast.makeText(this, "Service Stopped..!!", Toast.LENGTH_SHORT).show();
    }

    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    public Runnable GpsFinder = new Runnable(){

        @SuppressWarnings("deprecation")
        public void run(){
            try
            {

                String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if(!provider.contains("gps")){ //if gps is disabled
                    final Intent poke = new Intent();
                    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    poke.setData(Uri.parse("3"));
                    sendBroadcast(poke);
                }
            }
            catch(Exception e)
            {
                Toast.makeText(getApplicationContext(), "Error in gps on:"+e.toString(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            Location tempLoc = getBestLocation();
            if(tempLoc!=null)
            {
                curLocation = tempLoc;
                lati=Double.toString(curLocation.getLatitude());
                logi=Double.toString(curLocation.getLongitude());
                Toast.makeText(getApplicationContext(), lati + " ----- " + logi, Toast.LENGTH_SHORT).show();

                
            	String loc="";
    	    	String address = "";
    	        Geocoder geoCoder = new Geocoder( getBaseContext(), Locale.getDefault());      
    	          try
    	          {    	
    	            List<Address> addresses = geoCoder.getFromLocation(curLocation.getLatitude(), curLocation.getLongitude(), 1);
    	            if (addresses.size() > 0)
    	            {        	  
    	            	for (int index = 0;index < addresses.get(0).getMaxAddressLineIndex(); index++)
    	            		address += addresses.get(0).getAddressLine(index) + " ";
    	            	//Log.d("get loc...", address);
    	            	
    	            	 place=addresses.get(0).getFeatureName().toString();
    	            	 
    	            	
    	            //	 loc= addresses.get(0).getLocality().toString();
    	            //	Toast.makeText(getBaseContext(),address , Toast.LENGTH_SHORT).show();
    	            //	Toast.makeText(getBaseContext(),ff , Toast.LENGTH_SHORT).show();
    	            }
    	            else
    	            {
    	          	  //Toast.makeText(getBaseContext(), "noooooooo", Toast.LENGTH_SHORT).show();
    	            }      	
    	          }
    	          catch (IOException e) 
    	          {        
    	            e.printStackTrace();
    	          }
    	          
//    	    Toast.makeText(getBaseContext(), "locality-"+place, Toast.LENGTH_SHORT).show();
    	     

                updatelocation(lati,logi);

            }
            else
            {
                Toast.makeText(getBaseContext(), "Temploc null", Toast.LENGTH_SHORT).show();
            }
            handler.postDelayed(GpsFinder,2000000000);// register again to start after 20 seconds...
        }
    };

    @SuppressLint("MissingPermission")
    private Location getBestLocation()
    {
        Location gpslocation = null;
        Location networkLocation = null;

        if(locationManager==null){
            locationManager = (LocationManager) getApplicationContext() .getSystemService(Context.LOCATION_SERVICE);
        }
        try
        {
            if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000, 0, locationListener);// here you can set the 2nd argument time interval also that after how much time it will get the gps location
                gpslocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
            {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000, 0, locationListener);
                networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        catch (IllegalArgumentException e)
        {
            Log.e("error", e.toString());
        }
        if(gpslocation==null && networkLocation==null)
            return null;

        if(gpslocation!=null && networkLocation!=null)
        {
            if(gpslocation.getTime() < networkLocation.getTime())
            {
                gpslocation = null;
                return networkLocation;
            }
            else
            {
                networkLocation = null;
                return gpslocation;
            }
        }
        if (gpslocation == null)
        {
            return networkLocation;
        }
        if (networkLocation == null)
        {
            return gpslocation;
        }
        return null;
    }


    protected void updatelocation(final String lat,final String lng)
    {
        sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       
//        RequestQueue queue = Volley.newRequestQueue(LocationService.this);
//         url = "http://" + sh.getString("ip","") + ":5000/getlocation";
//
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                // Display the response string.
//                Log.d("+++++++++++++++++", response);
//                try {
//                    JSONObject json = new JSONObject(response);
//                    String res = json.getString("task");
//
//                    if (res.equalsIgnoreCase("success")) {
//
//                        Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
//                    } else {
//
//                    	Toast.makeText(getApplicationContext(), "invalid", Toast.LENGTH_LONG).show();
//                            }
//
//                    }
//                 catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//                Toast.makeText(getApplicationContext(), "Error"+error, Toast.LENGTH_LONG).show();
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("imei", sh.getString("imei",""));
//                params.put("lati", lati);
//                params.put("longi", logi);
//                params.put("place", place);
//
//
//                return params;
//            }
//        };
//queue.add(stringRequest);

    }




}




