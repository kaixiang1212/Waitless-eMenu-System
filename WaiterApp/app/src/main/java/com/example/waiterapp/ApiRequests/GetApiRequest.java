package com.example.waiterapp.ApiRequests;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * GetApiRequest - Generic API to submit a GET Request to the Backend API
 * Specifically used to fetch Order Item details in this Project
 */
public class GetApiRequest extends AsyncTask<String, String, AsyncTaskResult<String>> {
    /**
     * doInBackground - Overridden method to perform a GET REST API request
     * to the backend API.
     * @param strings (Contains API urlString and jsonData)
     * @return JSONResponse of the API encapsulated in AsyncTaskResult
     */
    @Override
    protected AsyncTaskResult<String> doInBackground(String... strings) {
        String urlString = strings[0];

        String response = "";
        try {
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null){
                sb.append(line);
            }
            response = sb.toString();

            /* Clean up */
            connection.disconnect();
            in.close();
            br.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return new AsyncTaskResult<>(response);
    }
}
