package com.example.soulbook;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<String> latitude;
    private ArrayList<String> longtitude;
    private ArrayList<String> emotion;
    private ArrayList<String> nicknames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        nicknames = intent.getStringArrayListExtra("nickname");
        latitude = intent.getStringArrayListExtra("latitude");
        longtitude = intent.getStringArrayListExtra("longtitude");
        emotion = intent.getStringArrayListExtra("emotion");
        setContentView(R.layout.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String emoji = "";
        String[] emojis = {"\uD83D\uDE03", "\uD83D\uDE31", "\uD83D\uDE22" ,"\uD83D\uDC7F", "\uD83D\uDE2F", "\uD83E\uDD2E", "\uD83E\uDD29"};
        for(int i = 0; i < nicknames.size(); i++){
            if(latitude.get(i) != "null") {
                LatLng place = new LatLng(Double.parseDouble(latitude.get(i)), Double.parseDouble(longtitude.get(i)));
                switch (emotion.get(i)) {
                    case "Happiness":
                        emoji = emojis[0];
                        break;
                    case "Fear":
                        emoji = emojis[1];
                        break;
                    case "Sadness":
                        emoji = emojis[2];
                        break;
                    case "Anger":
                        emoji = emojis[3];
                        break;
                    case "Surprise":
                        emoji = emojis[4];
                        break;
                    case "Disgust":
                        emoji = emojis[5];
                        break;
                    case "Excitement":
                        emoji = emojis[6];
                        break;
                }

                mMap.addMarker(new MarkerOptions().position(place).title(emoji + nicknames.get(i)));
            }
        }
        final LocationManager locationManager;
        final Geocoder geocoder = new Geocoder(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 10);
        }

        final Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        List<Address> addresses = new ArrayList<Address>();
        double lon = location.getLatitude();
        double lat = location.getLongitude();
        LatLng camera = new LatLng(lat,lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(camera));
    }
}


