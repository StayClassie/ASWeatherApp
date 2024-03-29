package com.example.homework1secondtry;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap gmap;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";    //api key

    //Chris Driving
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mapView = findViewById(R.id.mapView2);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }
    //end of chris driving David driving

    //COPIED from https://www.zoftino.com/android-mapview-tutorial/////
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));
    }
//end of David Driving
    ////////////

    //Chris and David
    public void getLocation(View view) {

        final TextView textView = (TextView) findViewById(R.id.text);
        final EditText editAddress = (EditText) findViewById(R.id.inputTextAddress);
        final String address = editAddress.getText().toString();

        final Toast locationCheck = Toast.makeText(this, "Test", Toast.LENGTH_SHORT);

        RequestQueue locationCheckQueue = Volley.newRequestQueue(this);

        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key=AIzaSyCHXOMmaHN8359i89ydVlay4dH4r7ec8Nc";


        //String url = "https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyCHXOMmaHN8359i89ydVlay4dH4r7ec8Nc";
        System.out.println(url);
        StringRequest locationStringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            JSONObject results = jsonArray.getJSONObject(0);
                            JSONObject geometry = results.getJSONObject("geometry");
                            JSONObject location = geometry.getJSONObject("location");
                            String latitude = location.getString("lat");
                            String longitude = location.getString("lng");

                            System.out.println(latitude.toString()+", "+longitude.toString());

                            double latD = Double.parseDouble(latitude);
                            double lngD = Double.parseDouble(longitude);

                            System.out.println(latD+", "+lngD);

                            gmap.setMinZoomPreference(12);
                            LatLng ny = new LatLng(latD, lngD);
                            gmap.addMarker(new MarkerOptions().position(new LatLng(latD, lngD)));
                            gmap.moveCamera(CameraUpdateFactory.newLatLng(ny));

                            getWeather(latD, lngD);

                        } catch (JSONException e) {
                           System.out.println("EXCEPTION CAUGHT");
                            Toast toast=Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT);
                            toast.setMargin(50,50);
                            toast.show();
                           //e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        locationCheckQueue.add(locationStringRequest);

    }


    //Chris Driving
    public void getWeather(Double lat, Double lng){
        RequestQueue weatherRequestQueue = Volley.newRequestQueue(this);

        String url = "https://api.darksky.net/forecast/3637ef1e41bca98d932636e877e04ea9/"+lat.toString()+","+lng.toString()+"?&exclude=minutely,hourly,daily,alerts,flags";

        StringRequest weatherStringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject current = jsonObject.getJSONObject("currently");
                            Double temp = current.getDouble("temperature");
                            temp = Math.round(temp*100.0)/100.0;
                            Double humid = current.getDouble("humidity");
                            humid = Math.round(humid*100.0)/100.0;
                            Double wind = current.getDouble("windSpeed");
                            wind = Math.round(wind*100.0)/100.0;
                            Double precip = current.getDouble("precipProbability");
                            precip = Math.round(precip*100.0)/100.0;

                            TextView tempText =(TextView)findViewById(R.id.textViewTemp);
                            TextView humidText =(TextView)findViewById(R.id.textViewHumid);
                            TextView windText =(TextView)findViewById(R.id.textViewWind);
                            TextView precipText =(TextView)findViewById(R.id.textViewPrecip);

                            tempText.setText(temp.toString()+"°F");
                            humidText.setText(humid.toString()+"%");
                            windText.setText(wind.toString()+"mph");
                            precipText.setText(precip.toString()+"%");

                            System.out.println(temp+humid+wind+precip);


                        } catch (JSONException e) {
                            System.out.println("EXCEPTION CAUGHT WEATHER");
                            //e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        weatherRequestQueue.add(weatherStringRequest);

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
