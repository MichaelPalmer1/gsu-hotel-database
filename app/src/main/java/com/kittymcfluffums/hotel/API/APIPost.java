package com.kittymcfluffums.hotel.API;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class APIPost extends API
{

    @Override
    protected HttpURLConnection connect(String url) {
        HttpURLConnection connection = super.connect(url);

        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        try {
            connection.setRequestMethod("POST");
            connection.connect();
            return connection;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected String doInBackground(String... data) {
        String result = null;
        try{

            //Write
            OutputStream os = this.connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data[1]);
            writer.close();
            os.close();

            //Read
            BufferedReader br = new BufferedReader(
                new InputStreamReader(this.connection.getInputStream(),"UTF-8"));

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


}
