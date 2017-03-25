package com.example.mortuie.bathlaunchpad2017;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class RoutePage extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    InputStream is;
    BufferedReader reader;
    ArrayList<String[]> mapPoints = new ArrayList<>();
    int mapPoint = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_page);
        Bundle i = getIntent().getExtras();
        String ib = i.get("routeID").toString();

        is = this.getResources().openRawResource(R.raw.route1);
        reader = new BufferedReader(new InputStreamReader(is));

        String line;
        try {
            while((line = reader.readLine()) != null){
                String[] splitString = line.split(",");
                mapPoints.add(splitString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapRoute);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        PolylineOptions polylineOptions = new PolylineOptions();
        if(mapPoint < mapPoints.size()) {
            LatLng place = new LatLng(Double.valueOf(mapPoints.get(mapPoint)[1]), Double.valueOf(mapPoints.get(mapPoint)[2]));
            mMap.addMarker(new MarkerOptions().position(place).title(mapPoints.get(mapPoint)[0]).snippet("THos os a comment"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
            polylineOptions.add(place);

            LatLng placeBefore = new LatLng(Double.valueOf(mapPoints.get(mapPoint-1)[1]), Double.valueOf(mapPoints.get(mapPoint-1)[2]));
            mMap.addMarker(new MarkerOptions().position(placeBefore).title(mapPoints.get(mapPoint)[0]).snippet("THos os a comment"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(placeBefore));
            polylineOptions.add(placeBefore);

            mMap.addPolyline(polylineOptions);
            mMap.setMinZoomPreference(14);

            TextView routeDetails = (TextView) findViewById(R.id.routeDetails);
            routeDetails.setText(mapPoints.get(mapPoint-1)[0] + " to " + mapPoints.get(mapPoint)[0] + " - You are " + (int)(mapPoint*100/mapPoints.size()) + "% completed");
        }
    }

    public void generateNextPath(){
        mMap.clear();
        PolylineOptions polylineOptions = new PolylineOptions();
        // Changes the map points
        if(mapPoint < mapPoints.size()) {
            LatLng place = new LatLng(Double.valueOf(mapPoints.get(mapPoint)[1]), Double.valueOf(mapPoints.get(mapPoint)[2]));
            mMap.addMarker(new MarkerOptions().position(place).title(mapPoints.get(mapPoint)[0]).snippet("THos os a comment"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
            polylineOptions.add(place);

            LatLng placeBefore = new LatLng(Double.valueOf(mapPoints.get(mapPoint-1)[1]), Double.valueOf(mapPoints.get(mapPoint-1)[2]));
            mMap.addMarker(new MarkerOptions().position(placeBefore).title(mapPoints.get(mapPoint)[0]).snippet("THos os a comment"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(placeBefore));
            polylineOptions.add(placeBefore);

            // Add grey line of where youve been
            PolylineOptions whereYouveBeen = new PolylineOptions();
            for(int i = 0; i < mapPoint; i++){
                LatLng placeBeen = new LatLng(Double.valueOf(mapPoints.get(i)[1]), Double.valueOf(mapPoints.get(i)[2]));
                whereYouveBeen.add(placeBeen);
            }
            whereYouveBeen.color(Color.argb(255, 0, 51, 204));
            mMap.addPolyline(whereYouveBeen);
            mMap.addPolyline(polylineOptions);
            mMap.setMinZoomPreference(14);

            TextView routeDetails = (TextView) findViewById(R.id.routeDetails);
            routeDetails.setText(mapPoints.get(mapPoint-1)[0] + " to " + mapPoints.get(mapPoint)[0] + " - You are " + (int)(mapPoint*100/mapPoints.size()) + "% completed");
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void getNextPath(View view){
        mapPoint ++;
        generateNextPath();
    }
}
