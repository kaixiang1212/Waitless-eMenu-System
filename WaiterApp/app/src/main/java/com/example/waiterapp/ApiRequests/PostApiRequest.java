package com.example.waiterapp.ApiRequests;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * PostApiRequest - Generic API to submit a POST Request to the Backend API
 * Specifically used to update Order Item Status in this Project
 */
public class PostApiRequest extends AsyncTask<String, String, String> {

    /**
     * doInBackground - Overridden method to perform a POST REST API request
     * to the backend API.
     * @param strings (Contains API urlString and jsonData)
     * @return JSONResponse of the API
     */
    @Override
    protected String doInBackground(String... strings) {
        String urlString = strings[0];
        String jsonData = strings[1];

        DataOutputStream out = null;
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
                br.close();
                response = sb.toString();
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
