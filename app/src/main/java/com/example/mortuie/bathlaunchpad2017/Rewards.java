package com.example.mortuie.bathlaunchpad2017;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

public class Rewards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);
        Intent i = getIntent();
        for(int j = 1; j <= 3; j++){
            String layoutName = "a" + j;
            String id = layoutName + "Points";
            int textView = getResources().getIdentifier(id, "id", getApplicationContext().getPackageName());
            int linearLayout = getResources().getIdentifier(layoutName, "id", getApplicationContext().getPackageName());
            TextView pointsString = (TextView)findViewById(textView);
            if(pointsString.getText().toString().equals("Used")) {
                ((LinearLayout)findViewById(linearLayout)).setBackgroundColor(Color.GREEN);
            }

        }
    }

    public void goBack(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void UseReward(View view){
        String layoutName = view.getResources().getResourceName(view.getId());
        String id = layoutName.substring(41) + "Points";
        int i = getResources().getIdentifier(id, "id", getApplicationContext().getPackageName());
        TextView pointsString = (TextView)findViewById(i);
        if(!pointsString.getText().toString().equals("Used")) {
            String points = pointsString.getText().toString();
            int pointsToBeRemoved = Integer.parseInt(points.split(" ")[0]);
            if(pointsToBeRemoved <= getPoints()) {
                removePoints(pointsToBeRemoved);
                view.setBackgroundColor(Color.GREEN);
                pointsString.setText("Used");
            } else {
                Toast.makeText(this, "You dont have enough points", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void removePoints(int pointsToRemove){
        try {
            int points = getPoints() - pointsToRemove;
            FileOutputStream outputStream = openFileOutput(("points.txt"), Context.MODE_PRIVATE); // TODO: Make sure this can be read
            outputStream.write((String.valueOf(points)).getBytes());
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    private int getPoints(){
        try {
            FileInputStream fis = openFileInput("points.txt");
            byte[] buffer = new byte[1024];
            int n = 0;
            String string = "";
            while ((n = fis.read(buffer)) != -1) {
                string = new String(buffer, 0, n);
            }
            return Integer.parseInt(string);
        } catch (IOException e) {
            return 0;
        }
    }



}
