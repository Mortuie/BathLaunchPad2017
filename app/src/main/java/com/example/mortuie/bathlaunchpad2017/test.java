package com.example.mortuie.bathlaunchpad2017;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

// this is the API key: AIzaSyCuot73LGetaKOTpAyO0r4tZOQQAg2vu_Q


public class test extends AppCompatActivity implements OnMapReadyCallback {

    int PLACE_PICKER_REQUEST = 1;
    GoogleMap mMap;
    ArrayList<String> places = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place selectedPlace = PlacePicker.getPlace(this, data);
                mMap.addMarker(new MarkerOptions().position(selectedPlace.getLatLng()).title(selectedPlace.getName().toString()));
                places.add(selectedPlace.getName() + "," + selectedPlace.getLatLng().latitude + "," + selectedPlace.getLatLng().longitude);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedPlace.getLatLng()));
                mMap.setMinZoomPreference(15);
            }
        }
    }

    public void addPoint(View view){
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    public void submitRoute(View view){
        //Leon writes to a file
        if(writeToFile(places)) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private boolean writeToFile(ArrayList<String> arrayList) {
        EditText rN = (EditText) findViewById(R.id.routeName);
        String routeName = rN.getText().toString();
        if((!routeName.trim().isEmpty()) && (places.size()!=1)) {
            try {
                FileOutputStream outputStream = openFileOutput(("data.txt"), Context.MODE_APPEND); // TODO: Make sure this can be read

                outputStream.write(("route," + routeName + "\n").getBytes());
                for (String stringInArrayList: arrayList) {
                    outputStream.write((stringInArrayList + "\n").getBytes());
                }

                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }





    }
