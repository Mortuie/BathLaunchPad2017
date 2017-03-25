package com.example.mortuie.bathlaunchpad2017;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class RoutePage extends AppCompatActivity {
    InputStream is;
    BufferedReader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_page);
        Bundle i = getIntent().getExtras();
        String ib = i.get("routeID").toString();
        TextView id = (TextView) findViewById(R.id.textView);
        id.setText(ib);

        is = this.getResources().openRawResource(R.raw.route1);
        reader = new BufferedReader(new InputStreamReader(is));
        addLocations();
    }

    private void addLocations(){
        String line;
        try {
            while((line = reader.readLine()) != null){
                line.split("/*");
                System.out.println(Arrays.toString(line.split(",")));
                TextView id = (TextView) findViewById(R.id.textView);
                String text = id.getText().toString();
                text += line.split(",")[0];
                text += "\n\r";
                id.setText(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
