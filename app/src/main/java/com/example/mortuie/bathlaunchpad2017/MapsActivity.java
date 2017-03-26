package com.example.mortuie.bathlaunchpad2017;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    InputStream is;
    BufferedReader reader;
    String routeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Bundle i = getIntent().getExtras();
        String bundleString = i.get("routeID").toString();
        if(bundleString.equals("1")) {
            is = this.getResources().openRawResource(R.raw.route1);
            reader = new BufferedReader(new InputStreamReader(is));
            routeName = bundleString;
        } else {
            routeName = bundleString;
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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
        String line;
        if(is != null) {
            try {
                while ((line = reader.readLine()) != null) {
                    String[] map = line.split(",");
                    LatLng place = new LatLng(Double.valueOf(map[1]), Double.valueOf(map[2]));
                    mMap.addMarker(new MarkerOptions().position(place).title(map[0]).snippet("This is a comment"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
                    polylineOptions.add(place);
                }
                mMap.addPolyline(polylineOptions);
                mMap.setMinZoomPreference(14);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PolylineOptions polylineOptionsCustom = new PolylineOptions();
                FileInputStream fis = openFileInput("data.txt");
                byte[] buffer = new byte[1024];
                int n = 0;
                while ((n = fis.read(buffer)) != -1) {
                    String string = new String(buffer, 0, n);
                    String[] split = string.split("\n");
                    for (int i = 0; i < split.length; i++) {
                        String[] splitAgain = split[i].split(",");
                        boolean thisRoute = false;
                        if (splitAgain[0].equals("route")) {
                            thisRoute = true;
                        } else if(splitAgain[0].equals("route")){
                            thisRoute = false;
                            break;
                        } else if(thisRoute){
                            LatLng place = new LatLng(Double.valueOf(splitAgain[1]), Double.valueOf(splitAgain[2]));
                            mMap.addMarker(new MarkerOptions().position(place).title(splitAgain[0]).snippet("This is a comment"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
                            polylineOptions.add(place);
                        }
                    }
                    mMap.addPolyline(polylineOptions);
                    mMap.setMinZoomPreference(14);
                }
                fis.close();
            } catch(IOException e){

            }

        }
    }

    public void goToTheRoute(View view){
        Intent intent = new Intent(this, RoutePage.class);
        intent.putExtra("routeID", routeName);
        startActivity(intent);

    }
}
