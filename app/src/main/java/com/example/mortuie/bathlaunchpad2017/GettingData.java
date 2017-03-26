package com.example.mortuie.bathlaunchpad2017;

import android.os.AsyncTask;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by mortuie on 26/03/17.
 */

public class GettingData extends Thread{

    private String value = "";
    private boolean valueEntered = false;

    public void run() {
        valueEntered = false;
        URL url;
        InputStream is = null;
        BufferedReader br;
        String lines = "";

        try {
            url = new URL("https://citysherpablog.wordpress.com/about/");
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("<p>") && line.endsWith("</p>")) {
                    line = line.replaceAll("<p>", "");
                    line = line.replaceAll("</p>", "");
                    lines = line;
                    valueEntered = true;
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        value = lines;
    }

    public String getValue(){
        if(valueEntered)
            return value;
        return null;
    }


}
