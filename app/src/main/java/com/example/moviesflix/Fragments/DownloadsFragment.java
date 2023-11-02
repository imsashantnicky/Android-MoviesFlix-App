package com.example.moviesflix.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviesflix.Adapter.DownloadAdapter;
import com.example.moviesflix.ModelClass.DownloadModelClass;
import com.example.moviesflix.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadsFragment extends Fragment {
    List<DownloadModelClass> downloadModelClassList = new ArrayList<>();
    RecyclerView recyclerView;
    int[] img = {R.drawable.marvel_logo, R.drawable.dc_logo, R.drawable.lucas_logo, R.drawable.warner_bro_logo};
    int[] id = {420, 9993, 1, 174};
    private static String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=6a215d3a3b85b4319eaa45038a6f11c0";

    public DownloadsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_downloads, container, false);

        recyclerView = rootView.findViewById(R.id.downloade_recv);

        for (int i = 0; i < img.length && i < id.length; i++) {
            int currentImg = img[i];
            int currentId = id[i];

            DownloadModelClass downloadModelClass = new DownloadModelClass();
            downloadModelClass.setImage(currentImg);
            downloadModelClass.setTitle(Integer.toString(currentId));

            downloadModelClassList.add(downloadModelClass);
        }
        putDataIntoRecyclerView(downloadModelClassList);

        return rootView;
    }

/*
    public class Getdata extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(JSON_URL);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1){
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i=0; i<jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    DownloadModelClass model = new DownloadModelClass();
                    model.setImage("https://image.tmdb.org/t/p/w500" + jsonObject1.getString("backdrop_path"));
                    model.setTitle(jsonObject1.getString("title"));
                    downloadModelClassList.add(model);
                }

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            putDataIntoRecyclerView(downloadModelClassList);
        }
    }
*/

    private void putDataIntoRecyclerView(List<DownloadModelClass> downloadModelClassList){
        DownloadAdapter downloadAdapter = new DownloadAdapter(downloadModelClassList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(downloadAdapter);
    }




}