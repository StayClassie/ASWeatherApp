package com.example.homework1secondtry;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mMapView;

    private static final String MAPVIEW_BUNDLE_KEY = "AIzaSyCHXOMmaHN8359i89ydVlay4dH4r7ec8Nc"; //API Key


    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("TESTLOCATION"));
    }



}
