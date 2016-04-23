package com.kittymcfluffums.hotel.API;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

/**
 * Created by charlesarvey on 4/23/16.
 */
public class APIGet extends API
{

    @Override
    protected HttpURLConnection connect(String url) {
        HttpURLConnection connection = super.connect(url);
        try {
            connection.setRequestMethod("GET");
            connection.connect();
            return connection;

        } catch (Exception e) {
            Log.e("API", "Error encountered while connecting to: " + url);
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected String doInBackground(String... params){
        String s, response = "";
        try {
            // Check response code
            if(this.connection.getResponseCode() != 200) {
                throw new Exception(
                        String.format(
                                "Could not get data from remote source. HTTP response: %s (%d)",
                                this.connection.getResponseMessage(), this.connection.getResponseCode()
                        )
                );
            }

            // Save response to a string
            InputStream in = new BufferedInputStream(this.connection.getInputStream());
            BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
            while ((s = buffer.readLine()) != null)
                response += s;

            Log.d("API_Data", response);

            return response;

        } catch (Exception e) {
            Log.e("API", "Error encountered while downloading JSON");
            e.printStackTrace();
        }
        return null;
    }
}
