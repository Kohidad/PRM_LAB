package com.example.lab13_googlemap;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;

    FrameLayout map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        map = findViewById(R.id.map);

        if (savedInstanceState == null){
            SupportMapFragment mapFragment = new SupportMapFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.map, mapFragment)
                    .commit();
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng mapNVH = new LatLng(10.8751312, 106.7595246);
        this.googleMap.addMarker(new MarkerOptions().position(mapNVH).title("Nhà văn hóa sinh viên"));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(mapNVH));
    }

}