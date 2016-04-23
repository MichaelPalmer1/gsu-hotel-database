package com.kittymcfluffums.hotel.API;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class API extends AsyncTask<String, Void, String> {

    HttpURLConnection connection;

    protected API()
    {
        this.connection = this.connect("http://michaeldpalmer.com/");
    }

    protected API(String url)
    {
        this.connection = this.connect(url);
    }

    protected HttpURLConnection connect(String url) {
        try {
            return (HttpURLConnection) ((new URL("http", url, "/").openConnection()));
        } catch (IOException e) {
            Log.e("API", "Error encountered while connecting to: " + url);
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPreExecute() {
        // format request to send off
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        // receive json and ensure proper type
        if ( isCancelled() )
        result = null;

        // Check result
        if ( result != null ) {
            System.out.println(result);
        }
    }

}


//    public static abstract class Get extends AsyncTask<String, Void, String> {
//
//        protected abstract void processData(String data);
//
//        @Override
//        protected String doInBackground(String... urls) {
//            String s, response = "";
//            try {
//                // Create connection
//                HttpURLConnection conn = (HttpURLConnection) new URL(urls[0]).openConnection();
//                conn.setRequestMethod("GET");
//
//                // Check response code
//                if(conn.getResponseCode() != 200) {
//                    throw new Exception(
//                            String.format(
//                                    "Could not get data from remote source. HTTP response: %s (%d)",
//                                    conn.getResponseMessage(), conn.getResponseCode()
//                            )
//                    );
//                }
//
//                // Save response to a string
//                InputStream in = new BufferedInputStream(conn.getInputStream());
//                BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
//                while ((s = buffer.readLine()) != null)
//                    response += s;
//
//                Log.d("API_Data", response);
//
//                return response;
//
//            } catch (Exception e) {
//                Log.e("API", "Error encountered while downloading JSON");
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            // Check for cancellations
//            if ( isCancelled() )
//                result = null;
//
//            // Check result
//            if ( result != null ) {
//                processData(result);
//            }
//        }
//    }
//
//    public static abstract class Post extends AsyncTask<String, Void, String> {
//
//        protected abstract void processData(String data);
//
//        @Override
//        protected String doInBackground(String... data) {
//            String result = null;
//            try{
//                //Connect
//                HttpURLConnection conn = (HttpURLConnection) ((new URL (data[0]).openConnection()));
//                conn.setDoOutput(true);
//                conn.setRequestProperty("Content-Type", "application/json");
//                conn.setRequestMethod("POST");
//                conn.connect();
//
//                //Write
//                OutputStream os = conn.getOutputStream();
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//                writer.write(data[1]);
//                writer.close();
//                os.close();
//
//                //Read
//                BufferedReader br = new BufferedReader(
//                        new InputStreamReader(conn.getInputStream(),"UTF-8"));
//
//                String line;
//                StringBuilder sb = new StringBuilder();
//
//                while ((line = br.readLine()) != null) {
//                    sb.append(line);
//                }
//
//                br.close();
//                result = sb.toString();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            // Check for cancellations
//            if ( isCancelled() )
//                result = null;
//
//            // Check result
//            if ( result != null ) {
//                processData(result);
//            }
//        }
//    }

