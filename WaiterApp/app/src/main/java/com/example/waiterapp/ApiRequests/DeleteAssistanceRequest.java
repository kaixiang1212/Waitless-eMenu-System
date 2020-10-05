package com.example.waiterapp.ApiRequests;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * DeleteAssistanceRequest - to send POST API Request call to
 * update the Customer Assistance Request
 */
public class DeleteAssistanceRequest extends AsyncTask<String, String, Void> {
    /**
     * doInBackground - Overridden method to make POST API call to
     * update customer assistance request.
     * @param strings (API urlString and JSON request data)
     * @return
     */
    @Override
    protected Void doInBackground(String... strings) {
        String urlString = strings[0];
        String table_no = strings[1];
        String jsonData = "{\"table_no\": " + table_no + "}";

        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = con.getResponseCode();

            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
