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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;


import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap gmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void getLocation(View view) {

        final TextView textView = (TextView) findViewById(R.id.text);
        final EditText editAddress = (EditText) findViewById(R.id.inputTextAddress);
        final String address = editAddress.getText().toString();

        final Toast locationCheck = Toast.makeText(this, "Test", Toast.LENGTH_SHORT);

        RequestQueue locationCheckQueue = Volley.newRequestQueue(this);

        //String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key=AIzaSyCHXOMmaHN8359i89ydVlay4dH4r7ec8Nc";


        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=AIzaSyCHXOMmaHN8359i89ydVlay4dH4r7ec8Nc";
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


                        } catch (JSONException e) {
                           System.out.println("EXCEPTION CAUGHT");
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
