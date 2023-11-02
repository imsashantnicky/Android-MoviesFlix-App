package com.example.moviesflix.Fragments;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moviesflix.Activity.SearchActivity;
import com.example.moviesflix.Adapter.DownloadAdapter;
import com.example.moviesflix.Adapter.NewMoviesAdapter;
import com.example.moviesflix.ModelClass.ChildRecvModelClass;
import com.example.moviesflix.ModelClass.DownloadModelClass;
import com.example.moviesflix.ModelClass.NewMoviesModelClass;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class NewmoviesFragment extends Fragment {

    RecyclerView recyclerView;
    ImageView searchimg;
    List<NewMoviesModelClass> newMoviesModelClassArrayList = new ArrayList<>();
    List<String> movieIds = new ArrayList<>();
    private static String JSON_URL = "https://api.themoviedb.org/3/movie/upcoming?api_key=6a215d3a3b85b4319eaa45038a6f11c0";


    public NewmoviesFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_newmovies, container, false);
        recyclerView = rootView.findViewById(R.id.newmovies_recv);
        searchimg = rootView.findViewById(R.id.htb_search_imv);

        Getdata getdata = new Getdata();
        getdata.execute();

        searchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

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

                    NewMoviesModelClass model = new NewMoviesModelClass();
                    model.setImage("https://image.tmdb.org/t/p/w500" + jsonObject1.getString("backdrop_path"));

                    // Get first 3 letters of the month name
                    String month = getMonthName(jsonObject1.getString("release_date"));
                    String firstThreeLetters = getFirstThreeLetters(month);
                    model.setMonth(firstThreeLetters);

                    // Get Date of the month
                    model.setDate(getDateOfMonth(jsonObject1.getString("release_date")));
                    model.setComingDate(jsonObject1.getString("title"));
                    model.setDesc(jsonObject1.getString("overview"));

                    // Get the Languages
                    String movieLanguage = "Language :- ";
                    model.setCategory(movieLanguage + jsonObject1.getString("original_language"));

                    String movieId = jsonObject1.getString("id");
                    model.setVideoId(movieId);
                    movieIds.add(movieId);

                    newMoviesModelClassArrayList.add(model);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            putDataIntoRecyclerView(newMoviesModelClassArrayList);
        }
    }

    private void putDataIntoRecyclerView(List<NewMoviesModelClass> newMoviesModelClassArrayList) {
        NewMoviesAdapter newMoviesAdapter = new NewMoviesAdapter(newMoviesModelClassArrayList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(newMoviesAdapter);
    }

    public static String getMonthName(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = inputFormat.parse(inputDate);

            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFirstThreeLetters(String monthName) {
        if (monthName != null && monthName.length() >= 3) {
            return monthName.substring(0, 3);
        }
        return null;
    }

    public static String getDateOfMonth(String inputDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = inputFormat.parse(inputDate);

            SimpleDateFormat outputFormat = new SimpleDateFormat("dd", Locale.getDefault());
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    public void getVideoKeyFromMovieIds(List<String> movieIds) {
        for (String movieId : movieIds) {
            String singleMovieJSON_URL = "https://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=6a215d3a3b85b4319eaa45038a6f11c0";
            GetVideodata getVideoData = new GetVideodata(singleMovieJSON_URL);
            getVideoData.execute();
        }
    }

    private class GetVideodata extends AsyncTask<String, String, String> {
        private String apiUrl;
        public GetVideodata(String apiUrl) {
            this.apiUrl = apiUrl;
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
                JSONObject jsonObject1 = jsonArray.getJSONObject(1);
                String videoKey = jsonObject1.getString("key");
                NewMoviesModelClass model = new NewMoviesModelClass();
                model.setVideoId(videoKey);
                newMoviesModelClassArrayList.add(model);

                putDataIntoRecyclerView(newMoviesModelClassArrayList);

            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

     */

}

