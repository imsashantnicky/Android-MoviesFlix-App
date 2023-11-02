package com.example.moviesflix.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.moviesflix.Activity.MoviesDetailsActivity;
import com.example.moviesflix.Adapter.ChildAdapter;
import com.example.moviesflix.Adapter.SearchResultAdapter;
import com.example.moviesflix.ModelClass.ChildRecvModelClass;
import com.example.moviesflix.ModelClass.SearchResultRecvModelClass;
import com.example.moviesflix.R;
import com.google.android.material.textfield.TextInputEditText;

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

public class SearchActivity extends AppCompatActivity {
    TextInputEditText searchBarEdt;
    String searchInputTxt;
    RecyclerView searchMoviResultRecv, searchTvShowResultRecv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        TextView searchBtn = findViewById(R.id.search_btn_txt);
        searchBarEdt = findViewById(R.id.searchbar_edit_txt);
        searchMoviResultRecv = findViewById(R.id.searched_movie_item_recv);
        searchTvShowResultRecv = findViewById(R.id.searched_tvshows_item_recv);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchInputTxt = searchBarEdt.getText().toString();

                String SearchResultJSON_URL = "https://api.themoviedb.org/3/search/movie?api_key=6a215d3a3b85b4319eaa45038a6f11c0&query=" + searchInputTxt;
                GetData searchResultData = new GetData(SearchResultJSON_URL, new ArrayList<>(), searchMoviResultRecv);
                searchResultData.execute();

            }
        });

        searchBarEdt.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        searchBarEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String SearchResultJSON_URL = "https://api.themoviedb.org/3/search/movie?api_key=6a215d3a3b85b4319eaa45038a6f11c0&query=" + s;
                GetData searchResultData = new GetData(SearchResultJSON_URL, new ArrayList<>(), searchMoviResultRecv);
                searchResultData.execute();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


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