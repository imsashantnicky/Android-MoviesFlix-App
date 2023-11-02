package com.example.moviesflix.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.moviesflix.Adapter.SearchResultAdapter;
import com.example.moviesflix.ModelClass.DownloadModelClass;
import com.example.moviesflix.ModelClass.SearchResultRecvModelClass;
import com.example.moviesflix.R;
import com.example.moviesflix.Utils.Endpoints;
import com.example.moviesflix.Utils.Utils;

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

public class MoviesCollectionActivity extends AppCompatActivity {
    RecyclerView collectionRecv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_collection);

        collectionRecv = findViewById(R.id.collection_recv);

        Intent intent = getIntent();
        String collectionId = intent.getStringExtra("collectionId");

        String apiUrl = Endpoints.MOVIES_COLLECTION+"?api_key="+ Utils.getData(getApplicationContext(), "apiKey", "")+"&language=en-US&page=1&with_companies=" + collectionId;

        GetData getData = new GetData(apiUrl, new ArrayList<>(), collectionRecv);
        getData.execute();

    }

    public class GetData extends AsyncTask<String, String, String> {
        private String apiUrl;
        private List<SearchResultRecvModelClass> searchResultData;
        private RecyclerView recyclerView;

        public GetData(String apiUrl, List<SearchResultRecvModelClass> searchResultData, RecyclerView recyclerView) {
            this.apiUrl = apiUrl;
            this.searchResultData = searchResultData;
            this.recyclerView = recyclerView;
        }


        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(apiUrl);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();
                    while (data != -1) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    //First RecyclerView Data Setting
                    SearchResultRecvModelClass model = new SearchResultRecvModelClass();
                    model.setChildImageView("https://image.tmdb.org/t/p/w500" + jsonObject1.getString("poster_path"));
                    model.setTitle(jsonObject1.getString("title"));
                    model.setSubTitle(jsonObject1.getString("release_date"));
                    String movieId = jsonObject1.getString("id");
                    model.setMovieId(movieId);

                    searchResultData.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            putDataIntoRecyclerView(searchResultData, recyclerView);

        }
    }

    private void putDataIntoRecyclerView(List<SearchResultRecvModelClass> recvData, RecyclerView recv) {
        SearchResultAdapter searchResultAdapter = new SearchResultAdapter(recvData, getApplicationContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recv.setLayoutManager(gridLayoutManager);
        recv.setAdapter(searchResultAdapter);

        searchResultAdapter.setOnItemClickListener(new SearchResultAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String movieId) {
                Intent intent = new Intent(getApplicationContext(), MoviesDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
    
}