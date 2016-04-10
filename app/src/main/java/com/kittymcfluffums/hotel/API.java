package com.kittymcfluffums.hotel;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class API extends AsyncTask<String, Void, String> {

    protected abstract void processData(String data);

    @Override
    protected String doInBackground(String... urls) {
        String s, response = "";
        try {
            // Create connection
            HttpURLConnection conn = (HttpURLConnection) new URL(urls[0]).openConnection();
            conn.setRequestMethod("GET");

            // Check response code
            if(conn.getResponseCode() != 200) {
                throw new Exception(
                        String.format(
                                "Could not get data from remote source. HTTP response: %s (%d)",
                                conn.getResponseMessage(), conn.getResponseCode()
                        )
                );
            }

            // Save response to a string
            InputStream in = new BufferedInputStream(conn.getInputStream());
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

    @Override
    protected void onPostExecute(String result) {
        // Check for cancellations
        if ( isCancelled() )
            result = null;

        // Check result
        if ( result != null ) {
            processData(result);
        }
    }
}

// TODO: Not done yet with this
abstract class Query extends AsyncTask<String, Void, String> {

    protected abstract void processData(String data);

    @Override
    protected String doInBackground(String... data) {
        String response = "";

        HttpURLConnection httpcon;
        String url = null;
        String d = null;
        String result = null;
        try{
            //Connect
            httpcon = (HttpURLConnection) ((new URL (url).openConnection()));
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Accept", "application/json");
            httpcon.setRequestMethod("POST");
            httpcon.connect();

            //Write
            OutputStream os = httpcon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(d);
            writer.close();
            os.close();

            //Read
            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(),"UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // Check for cancellations
        if ( isCancelled() )
            result = null;

        // Check result
        if ( result != null ) {
            processData(result);
        }
    }
}