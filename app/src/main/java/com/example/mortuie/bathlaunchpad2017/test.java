package com.example.mortuie.bathlaunchpad2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


// this is the API key: AIzaSyCuot73LGetaKOTpAyO0r4tZOQQAg2vu_Q


public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        String text = "";
        BufferedReader in = null;

        try {

            URL query = new URL("https://maps.googleapis.com/maps/api/place/radarsearch/json?location=51.503186,-0.126446&radius=5000&type=museum&key=AIzaSyCuot73LGetaKOTpAyO0r4tZOQQAg2vu_Q");
            //URLConnection q2 = query.openConnection();
            in = new BufferedReader(new InputStreamReader(query.openConnection().getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                text += inputLine + "\n";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView tv2 = (TextView) findViewById(R.id.textView2);
        tv2.setText(text);

    }




}
