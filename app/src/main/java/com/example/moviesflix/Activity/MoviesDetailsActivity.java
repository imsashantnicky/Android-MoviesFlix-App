package com.example.moviesflix.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.moviesflix.Adapter.ChildAdapter;
import com.example.moviesflix.Adapter.GenresAdapter;
import com.example.moviesflix.Adapter.TopCastAdapter;
import com.example.moviesflix.Adapter.TvShowsAdapter;
import com.example.moviesflix.Adapter.YouTubeAdapter;
import com.example.moviesflix.Fragments.HomeFragment;
import com.example.moviesflix.ModelClass.ChildRecvModelClass;
import com.example.moviesflix.ModelClass.GenresModelClass;
import com.example.moviesflix.ModelClass.TopCastModelClass;
import com.example.moviesflix.ModelClass.YoutubeModelClass;
import com.example.moviesflix.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.moviesflix.Utils.Endpoints;
import com.example.moviesflix.Utils.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;

public class MoviesDetailsActivity extends AppCompatActivity {
    ImageView backImg, frontImg, searchBtn;
    TextView topCastTitle, videoTitle, similarTitle, topratedTitle;
    TextView movieTitle, movieTagline, overviewDesc, releaseStatus, releaseDate, runtime, directorName, writerName;
    String movieId, tvshowId, Id;
    LinearLayout simmerView, topCastSimmerView, videoSimmerView, similerMoviesSimmerView, topRatedSimmerView, directorNameSimmerView, directorNameLayout;
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView genresRecv, topCastRecv, youtubeRecv, recv1, recv2;
    List<GenresModelClass> genresListData = new ArrayList<>();
    List<TopCastModelClass> topCastListData = new ArrayList<>();
    List<YoutubeModelClass> videoIdListData = new ArrayList<>();
    List<ChildRecvModelClass> childRecvModelClassList = new ArrayList<>();

    String TrendingMoviesJSON_URL = "https://api.themoviedb.org/3/trending/movie/day?api_key=6a215d3a3b85b4319eaa45038a6f11c0";
    String TrendingTvShowsJSON_URL = "https://api.themoviedb.org/3/trending/tv/day?api_key=6a215d3a3b85b4319eaa45038a6f11c0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);

        backImg = findViewById(R.id.background_banner_imv);
        frontImg = findViewById(R.id.front_banner_imv);
        movieTitle = findViewById(R.id.movie_title_txt);
        movieTagline = findViewById(R.id.tag_line_txt);
        overviewDesc = findViewById(R.id.overview_desc_txt);
        releaseStatus = findViewById(R.id.release_status_txt);
        releaseDate = findViewById(R.id.release_date_txt);
        runtime = findViewById(R.id.runtime_txt);
        directorName = findViewById(R.id.director_name_txt);
        writerName = findViewById(R.id.writer_name_txt);
        simmerView = findViewById(R.id.simmer_view_layout);
        searchBtn = findViewById(R.id.htb_search_imv);

        topCastTitle = findViewById(R.id.topcastTitle_txt);
        videoTitle = findViewById(R.id.officialVideoTitle_txt);
        similarTitle = findViewById(R.id.similarMoviesTitle_txt);
        topratedTitle = findViewById(R.id.topMoviesTitle_txt);

        topCastRecv = findViewById(R.id.top_cast_recv);
        genresRecv = findViewById(R.id.recv_genres);
        youtubeRecv = findViewById(R.id.trailer_recv);
        recv1 = findViewById(R.id.recv1);
        recv2 = findViewById(R.id.recv2);

        shimmerFrameLayout = findViewById(R.id.recv2_shimmer_view_container);
        topCastSimmerView = findViewById(R.id.topCastSimmerView_layout);
        videoSimmerView = findViewById(R.id.videoSimmerView_layout);
        similerMoviesSimmerView = findViewById(R.id.similerMoviesSimmerView_layout);
        topRatedSimmerView = findViewById(R.id.topRatedSimmerView_layout);
        directorNameSimmerView = findViewById(R.id.directorNameSimmer_layout);
        directorNameLayout = findViewById(R.id.directorName_layout);

        Intent intent = getIntent();
        if (intent != null) {
            String idKey = "";
            String apiUrl = "";

            if (intent.hasExtra("movie_id")) {
                idKey = "movie_id";
                apiUrl = "https://api.themoviedb.org/3/movie/";
                Id = intent.getStringExtra(idKey);

                GetMovieData getMovieData = new GetMovieData(getApplicationContext(),Endpoints.BASE_URL+"movie/", Id);
                getMovieData.execute();

                String similarMoviesJSON_URL = Endpoints.BASE_URL+"movie/" + Id + "/similar?api_key="+ Utils.getData(getApplicationContext(), "apiKey", "");
                GetData similarMoviesData = new GetData(similarMoviesJSON_URL, new ArrayList<>(), recv1);
                similarMoviesData.execute();

                GetData trendingMoviesData = new GetData(TrendingMoviesJSON_URL, new ArrayList<>(), recv2);
                trendingMoviesData.execute();

            } else if (intent.hasExtra("tv_shows_id")) {
                idKey = "tv_shows_id";
                apiUrl = "https://api.themoviedb.org/3/tv/";
                Id = intent.getStringExtra(idKey);

                GetTvShowData getTvShowData = new GetTvShowData(apiUrl, Id);
                getTvShowData.execute();

                String similarTvShowsJSON_URL = apiUrl + Id + "/similar?api_key=6a215d3a3b85b4319eaa45038a6f11c0";
                GetTvData similarTvShowsData = new GetTvData(similarTvShowsJSON_URL, new ArrayList<>(), recv1);
                similarTvShowsData.execute();

                GetTvData trendingTvShowsData = new GetTvData(TrendingTvShowsJSON_URL, new ArrayList<>(), recv2);
                trendingTvShowsData.execute();

            }

            // Check if mediaId is valid before making API calls
            if (Id != null && !Id.isEmpty()) {

                GetCrewData getCrewData = new GetCrewData(apiUrl, Id);
                getCrewData.execute();

                GetYoutubeVideoData getYoutubeVideoData = new GetYoutubeVideoData(apiUrl, Id);
                getYoutubeVideoData.execute();

                shimmerFrameLayout.startShimmer();

            } else {
                finish();
            }
        } else {
            finish();
        }

        //Going to search activity
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivityIntent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(searchActivityIntent);
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
                frontImg.startAnimation(animation);
            }
        }, 2000);

    }

    public class GetMovieData extends AsyncTask<String, String, String> {
        String apiUrl, Id;
        Context context;
        public GetMovieData(Context context,String apiUrl, String id) {
            this.apiUrl = apiUrl;
            this.Id = id;
            this.context = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            String JSON_URL = apiUrl + Id +"?api_key="+Utils.getData(context, "apiKey", "");
            return Utils.JsonUtils.makeHttpRequest(JSON_URL);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                // Assuming the image URL is in the "poster_path" field
                String imageUrl = "https://image.tmdb.org/t/p/w500" + jsonObject.getString("poster_path");

                // Load the image into frontImg using Glide
                Glide.with(MoviesDetailsActivity.this).load(imageUrl).placeholder(R.drawable.placeholder_image).into(frontImg);
                Glide.with(MoviesDetailsActivity.this).load(imageUrl).placeholder(R.drawable.placeholder_image).into(backImg);

                movieTitle.setText(jsonObject.getString("title"));
                movieTagline.setText(jsonObject.getString("tagline"));
                overviewDesc.setText(jsonObject.getString("overview"));
                releaseStatus.setText(jsonObject.getString("status"));
                releaseDate.setText(jsonObject.getString("release_date"));

                int totalMinutes = Integer.parseInt(jsonObject.getString("runtime"));
                int hours = totalMinutes / 60;
                int minutes = totalMinutes % 60;
                runtime.setText(hours + " hr " + minutes + " min");

                genresListData.clear();

                // Parse genres information
                JSONArray genresArray = jsonObject.getJSONArray("genres");
                for (int i = 0; i < genresArray.length(); i++) {
                    JSONObject genreObject = genresArray.getJSONObject(i);
                    String genreName = genreObject.getString("name");

                    GenresModelClass genresModel = new GenresModelClass();
                    genresModel.setGenresTxt(genreName);

                    genresListData.add(genresModel);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            putDataInGenresRecv(genresListData);

        }
    }

    public class GetTvShowData extends AsyncTask<String, String, String> {
        String apiUrl, Id;
        public GetTvShowData(String apiUrl, String id) {
            this.apiUrl = apiUrl;
            this.Id = id;
        }

        @Override
        protected String doInBackground(String... strings) {
            String JSON_URL = apiUrl + Id + "?api_key=6a215d3a3b85b4319eaa45038a6f11c0";
            return Utils.JsonUtils.makeHttpRequest(JSON_URL);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                // Assuming the image URL is in the "poster_path" field
                String imageUrl = "https://image.tmdb.org/t/p/w500" + jsonObject.getString("poster_path");

                // Load the image into frontImg using Glide
                Glide.with(MoviesDetailsActivity.this).load(imageUrl).placeholder(R.drawable.placeholder_image).into(frontImg);
                Glide.with(MoviesDetailsActivity.this).load(imageUrl).placeholder(R.drawable.placeholder_image).into(backImg);

                movieTitle.setText(jsonObject.getString("name"));
                movieTagline.setText(jsonObject.getString("tagline"));
                overviewDesc.setText(jsonObject.getString("overview"));
                releaseStatus.setText(jsonObject.getString("status"));
                releaseDate.setText(jsonObject.getString("release_date"));

                int totalMinutes = Integer.parseInt(jsonObject.getString("runtime"));
                runtime.setText(totalMinutes + "min");

                genresListData.clear();

                // Parse genres information
                JSONArray genresArray = jsonObject.getJSONArray("genres");
                for (int i = 0; i < genresArray.length(); i++) {
                    JSONObject genreObject = genresArray.getJSONObject(i);
                    String genreName = genreObject.getString("name");

                    GenresModelClass genresModel = new GenresModelClass();
                    genresModel.setGenresTxt(genreName);

                    genresListData.add(genresModel);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            putDataInGenresRecv(genresListData);

        }
    }

    public class GetCrewData extends AsyncTask<String, String, String> {
        String apiUrl, Id;

        public GetCrewData(String apiUrl, String id) {
            this.apiUrl = apiUrl;
            Id = id;
        }

        @Override
        protected String doInBackground(String... strings) {
            String JSON_URL = apiUrl + Id + "/credits?api_key=6a215d3a3b85b4319eaa45038a6f11c0";
            return Utils.JsonUtils.makeHttpRequest(JSON_URL);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                String directors = "";
                String writers = "";
                // Parse genres information
                JSONArray crewDetailArray = jsonObject.getJSONArray("crew");
                for (int i = 0; i < crewDetailArray.length(); i++) {
                    JSONObject crewObject = crewDetailArray.getJSONObject(i);

                    String jobRole = crewObject.getString("job");
                    String name = crewObject.getString("name");
                    if ("Director".equals(jobRole)) {
                        directors += name + ", ";
                    } else if ("Writer".equals(jobRole)) {
                        writers += name + ", ";
                    }

                }
                directors = removeTrailingComma(directors);
                writers = removeTrailingComma(writers);
                directorName.setText(directors);
                writerName.setText(writers);

                //Geting Cast Data From API
                JSONArray castDetailArray = jsonObject.getJSONArray("cast");
                for (int i = 0; i < castDetailArray.length(); i++) {
                    JSONObject castObject = castDetailArray.getJSONObject(i);

                    //setting data in Top Cast RecyclerView
                    TopCastModelClass topCastModel = new TopCastModelClass();
                    topCastModel.setCastImg("https://image.tmdb.org/t/p/w500" + castObject.getString("profile_path"));
                    topCastModel.setCastName(castObject.getString("original_name"));
                    topCastModel.setCastProfile(castObject.getString("character"));

                    topCastListData.add(topCastModel);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            directorNameSimmerView.setVisibility(View.GONE);
            topCastSimmerView.setVisibility(View.GONE);
            directorNameLayout.setVisibility(View.VISIBLE);
            topCastTitle.setVisibility(View.VISIBLE);
            putDataInTopCastRecv(topCastListData);

        }

        private String removeTrailingComma(String str) {
            if (str.endsWith(", ")) {
                str = str.substring(0, str.length() - 2);
            }
            return str;
        }

    }

    public class GetTvShowCrewData extends AsyncTask<String, String, String> {
        String MovieDetailsJSON_URL = "https://api.themoviedb.org/3/tv/" + Id + "/credits?api_key=6a215d3a3b85b4319eaa45038a6f11c0";

        @Override
        protected String doInBackground(String... strings) {
            return Utils.JsonUtils.makeHttpRequest(MovieDetailsJSON_URL);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                String directors = "";
                String writers = "";
                // Parse genres information
                JSONArray crewDetailArray = jsonObject.getJSONArray("crew");
                for (int i = 0; i < crewDetailArray.length(); i++) {
                    JSONObject crewObject = crewDetailArray.getJSONObject(i);

                    String jobRole = crewObject.getString("job");
                    String name = crewObject.getString("name");
                    if ("Director".equals(jobRole)) {
                        directors += name + ", ";
                    } else if ("Writer".equals(jobRole)) {
                        writers += name + ", ";
                    }

                }
                directors = removeTrailingComma(directors);
                writers = removeTrailingComma(writers);
                directorName.setText(directors);
                writerName.setText(writers);

                //Geting Cast Data From API
                JSONArray castDetailArray = jsonObject.getJSONArray("cast");
                for (int i = 0; i < castDetailArray.length(); i++) {
                    JSONObject castObject = castDetailArray.getJSONObject(i);

                    //setting data in Top Cast RecyclerView
                    TopCastModelClass topCastModel = new TopCastModelClass();
                    topCastModel.setCastImg("https://image.tmdb.org/t/p/w500" + castObject.getString("profile_path"));
                    topCastModel.setCastName(castObject.getString("original_name"));
                    topCastModel.setCastProfile(castObject.getString("character"));

                    topCastListData.add(topCastModel);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            directorNameSimmerView.setVisibility(View.GONE);
            topCastSimmerView.setVisibility(View.GONE);
            directorNameLayout.setVisibility(View.VISIBLE);
            topCastTitle.setVisibility(View.VISIBLE);
            putDataInTopCastRecv(topCastListData);

        }

        private String removeTrailingComma(String str) {
            if (str.endsWith(", ")) {
                str = str.substring(0, str.length() - 2);
            }
            return str;
        }

    }

    public class GetYoutubeVideoData extends AsyncTask<String, String, String> {
        String apiUrl, Id;

        public GetYoutubeVideoData(String apiUrl, String id) {
            this.apiUrl = apiUrl;
            Id = id;
        }

        @Override
        protected String doInBackground(String... strings) {
            String JSON_URL = apiUrl + Id + "/videos?api_key=6a215d3a3b85b4319eaa45038a6f11c0";
            return Utils.JsonUtils.makeHttpRequest(JSON_URL);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray videoDataArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < videoDataArray.length(); i++) {
                    JSONObject videoObject = videoDataArray.getJSONObject(i);

                    YoutubeModelClass videoModel = new YoutubeModelClass();
                    videoModel.setVideoId(videoObject.getString("key"));

                    videoIdListData.add(videoModel);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            videoSimmerView.setVisibility(View.GONE);
            videoTitle.setVisibility(View.VISIBLE);
            putDataInYoutubeRecv(videoIdListData);
        }
    }

    public class GetTvShowYoutubeVideoData extends AsyncTask<String, String, String> {
        String YoutubeVideoJSON_URL = "https://api.themoviedb.org/3/tv/" + Id + "/videos?api_key=6a215d3a3b85b4319eaa45038a6f11c0";

        @Override
        protected String doInBackground(String... strings) {
            return Utils.JsonUtils.makeHttpRequest(YoutubeVideoJSON_URL);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray videoDataArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < videoDataArray.length(); i++) {
                    JSONObject videoObject = videoDataArray.getJSONObject(i);

                    YoutubeModelClass videoModel = new YoutubeModelClass();
                    videoModel.setVideoId(videoObject.getString("key"));

                    videoIdListData.add(videoModel);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            videoSimmerView.setVisibility(View.GONE);
            videoTitle.setVisibility(View.VISIBLE);
            putDataInYoutubeRecv(videoIdListData);
        }
    }

    public class GetData extends AsyncTask<String, String, String> {
        private String apiUrl;
        private List<ChildRecvModelClass> childData;
        private RecyclerView recyclerView;

        public GetData(String apiUrl, List<ChildRecvModelClass> childData, RecyclerView recyclerView) {
            this.apiUrl = apiUrl;
            this.childData = childData;
            this.recyclerView = recyclerView;
        }


        @Override
        protected String doInBackground(String... strings) {
            return Utils.JsonUtils.makeHttpRequest(apiUrl);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    //First RecyclerView Data Setting
                    ChildRecvModelClass model = new ChildRecvModelClass();
                    model.setChildImageView("https://image.tmdb.org/t/p/w500" + jsonObject1.getString("poster_path"));
                    model.setTitle(jsonObject1.getString("title"));
                    model.setSubTitle(jsonObject1.getString("release_date"));
                    model.setRatingText(jsonObject1.getInt("vote_average"));
                    int rating = jsonObject1.getInt("vote_average") * 10;
                    model.setProgress(rating);
                    String movieId = jsonObject1.getString("id");
                    model.setMovieId(movieId);

                    childData.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            similerMoviesSimmerView.setVisibility(View.GONE);
            topRatedSimmerView.setVisibility(View.GONE);
            simmerView.setVisibility(View.GONE);

            similarTitle.setVisibility(View.VISIBLE);
            topratedTitle.setVisibility(View.VISIBLE);
            putDataIntoRecyclerView(childData, recyclerView);

        }
    }

    public class GetTvData extends AsyncTask<String, String, String> {
        private String apiUrl;
        private List<ChildRecvModelClass> childData;
        private RecyclerView recyclerView;

        public GetTvData(String apiUrl, List<ChildRecvModelClass> childData, RecyclerView recyclerView) {
            this.apiUrl = apiUrl;
            this.childData = childData;
            this.recyclerView = recyclerView;
        }


        @Override
        protected String doInBackground(String... strings) {
            return Utils.JsonUtils.makeHttpRequest(apiUrl);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    //First RecyclerView Data Setting
                    ChildRecvModelClass model = new ChildRecvModelClass();
                    model.setChildImageView("https://image.tmdb.org/t/p/w500" + jsonObject1.getString("poster_path"));
                    model.setTitle(jsonObject1.getString("name"));
                    model.setSubTitle(jsonObject1.getString("first_air_date"));
                    model.setRatingText(jsonObject1.getInt("vote_average"));
                    int rating = jsonObject1.getInt("vote_average") * 10;
                    model.setProgress(rating);
                    String movieId = jsonObject1.getString("id");
                    model.setMovieId(movieId);

                    childData.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            similerMoviesSimmerView.setVisibility(View.GONE);
            topRatedSimmerView.setVisibility(View.GONE);
            simmerView.setVisibility(View.GONE);

            similarTitle.setVisibility(View.VISIBLE);
            topratedTitle.setVisibility(View.VISIBLE);
            putDataIntoRecyclerView(childData, recyclerView);

        }
    }

    private void putDataInGenresRecv(List<GenresModelClass> genresData) {
        GenresAdapter genresAdapter = new GenresAdapter(genresData, getApplicationContext());
        genresRecv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        genresRecv.setAdapter(genresAdapter);
    }

    private void putDataInTopCastRecv(List<TopCastModelClass> topCastListData) {
        TopCastAdapter topCastAdapter = new TopCastAdapter(topCastListData, getApplicationContext());
        topCastRecv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        topCastRecv.setAdapter(topCastAdapter);
    }

    private void putDataInYoutubeRecv(List<YoutubeModelClass> youtubeModelClasse) {
        YouTubeAdapter youTubeAdapter = new YouTubeAdapter(youtubeModelClasse, getApplicationContext());
        youtubeRecv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        youtubeRecv.setAdapter(youTubeAdapter);
    }

    private void putDataIntoRecyclerView(List<ChildRecvModelClass> recvData1, RecyclerView recv) {
        ChildAdapter childAdapter = new ChildAdapter(recvData1, getApplicationContext());
        recv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recv.setAdapter(childAdapter);

        childAdapter.setOnItemClickListener(new ChildAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String movieId) {
                Intent intent = new Intent(getApplicationContext(), MoviesDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void putDataIntoTvShowsRecyclerView(List<ChildRecvModelClass> recvData, RecyclerView recv) {
        TvShowsAdapter tvShowsAdapter = new TvShowsAdapter(recvData, getApplicationContext());
        recv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recv.setAdapter(tvShowsAdapter);

        tvShowsAdapter.setOnItemClickListener(new TvShowsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String movieId) {
                Intent intent = new Intent(getApplicationContext(), MoviesDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }


}


