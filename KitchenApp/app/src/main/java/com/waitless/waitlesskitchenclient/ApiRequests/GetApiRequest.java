package com.waitless.waitlesskitchenclient.ApiRequests;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetApiRequest extends AsyncTask<String, String, AsyncTaskResult<String>> {
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
            return new AsyncTaskResult<>(response);
        } catch (IOException e){
            System.out.println(e.toString());
            return new AsyncTaskResult<>(e);
        }
    }
}
