package com.example.moviesflix.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.moviesflix.Fragments.HomeFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Utils {
    private static final String PREF_NAME = "ApiUrlsData";
    public static String setData(Context context, String key, String value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
        return value;
    }

    public static String getData(Context context, String key, String defaultValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    public static class JsonUtils {
        private static final String TAG = HomeFragment.JsonUtils.class.getSimpleName();

        public static String makeHttpRequest(String urlString) {
            String jsonResponse = "";

            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                // Set connection timeout and read timeout
                urlConnection.setConnectTimeout(10000); // 10 seconds
                urlConnection.setReadTimeout(15000); // 15 seconds

                // Connect to the server
                urlConnection.connect();

                // Check if the request was successful (response code 200)
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    // Get the input stream
                    InputStream inputStream = urlConnection.getInputStream();

                    // Read the stream
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(TAG, "Problem making the HTTP request.", e);
            }

            return jsonResponse;
        }

        private static String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }
    }

}
