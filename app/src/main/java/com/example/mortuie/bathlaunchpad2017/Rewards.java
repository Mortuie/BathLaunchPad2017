package com.example.mortuie.bathlaunchpad2017;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
    }

    public void goBack(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

    public void UseReward(View view){
        view.setBackgroundColor(Color.GREEN);
        String layoutName = view.getResources().getResourceName(view.getId());
        String id = layoutName + "Points";
        int i = getResId(id, Rewards.class);
        System.out.println("qwertyuiop"+layoutName.substring(41));
        System.out.println(i);
        String points = ((TextView)findViewById(i)).getText().toString();
        removePoints(Integer.parseInt(points.split(" ")[0]));
    }

    public int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
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
