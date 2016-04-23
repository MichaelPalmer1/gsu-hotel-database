package com.kittymcfluffums.hotel;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class API {
    public static abstract class Get extends AsyncTask<String, Void, String> {
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
                            String.format(Locale.US,
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

    public static abstract class Post extends AsyncTask<String, Void, String> {

        protected abstract void processData(String data);

        @Override
        protected String doInBackground(String... data) {
            String result = null;
            try{
                //Connect
                HttpURLConnection conn = (HttpURLConnection) ((new URL (data[0]).openConnection()));
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("POST");
                conn.connect();

                //Write
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(data[1]);
                writer.close();
                os.close();

                //Read
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(),"UTF-8"));

                String line;
                StringBuilder sb = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                br.close();
                result = sb.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
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

}