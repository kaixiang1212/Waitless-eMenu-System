package com.example.customerapp.ApiRequests;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * This class creates the HTTP POST requests for the API calls
 */
public class PostApiRequest extends AsyncTask<String, String, String> {

    /**
     * Code to do in the background of the app
     * @param strings : android system parameters
     * @return response : json response from server
     */
    @Override
    protected String doInBackground(String... strings) {
        String urlString = strings[0];
        String jsonData = strings[1];
        String response = "";
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()){
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuilder sb = new StringBuilder();
                String line = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = br.readLine()) != null){
                    sb.append(line);
                }
                response = sb.toString();
            }
            // Close
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
