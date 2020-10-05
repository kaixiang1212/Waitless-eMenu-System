package com.example.waiterapp.ApiRequests;

import android.os.AsyncTask;

import com.example.waiterapp.Models.AssistList;
import com.example.waiterapp.Models.AssistListItem;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GetAssistanceRequests - API to submit a GET Request to the Backend API
 * Specifically used to fetch customer assistance requests details in this Project
 */
public class GetAssistanceRequests extends AsyncTask<String, Void, AssistList> {

    /**
     * doInBackground - Overridden method to perform a GET REST API request
     * to the backend API.
     * @param strings (Contains API urlString and jsonData)
     * @return AssistList containing list of assistance requests
     */
    @Override
    protected AssistList doInBackground(String... strings) {
        String urlString = strings[0];
        AssistList list = new AssistList();

        try {
            URL url = new URL(urlString);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream((con.getInputStream()));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            String[] split = line.split("\\s");
            // regex pattern
            String pattern = "(\\d+)";
            Pattern r = Pattern.compile(pattern);
            for (String s: split) {
                // regex to get digits only
                Matcher m = r.matcher(s);
                if (m.find()) {
                    AssistListItem li = new AssistListItem(m.group());
                    list.addAssistListItem(li);
                }
            }
            con.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
