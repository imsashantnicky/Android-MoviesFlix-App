package com.example.moviesflix.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.moviesflix.Activity.MoviesDetailsActivity;
import com.example.moviesflix.Adapter.ChildAdapter;
import com.example.moviesflix.Adapter.FeaturedAdapter;
import com.example.moviesflix.Adapter.TvShowsAdapter;
import com.example.moviesflix.ModelClass.ChildRecvModelClass;
import com.example.moviesflix.ModelClass.FeaturedModelClass;
import com.example.moviesflix.R;
import com.example.moviesflix.Activity.SearchActivity;
import com.example.moviesflix.Utils.Endpoints;
import com.example.moviesflix.Utils.Utils;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recv1, recv2, recv3, featuredRecyclerView;
    TextView title1, title2, title3, featuredMoviesBtn, featuredTvShowsBtn;
    ImageView topBanner, searchBtn;
    CardView c1, c2;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout toolbarSimmerLayout, fisrtSimmerLayout, secondSimmerLayout, thirdSimmerLayout, fourthSimmerLayout, fifthSimmerLayout;
    RelativeLayout home_layout;

    private static String TrendingTvShowsJSON_URL;
    private static String TrendingMoviesJSON_URL;
    private static String TopRatedJSON_URL;
    private static String PopularJSON_URL;

    public HomeFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        shimmerFrameLayout = rootView.findViewById(R.id.home_fragment_shimmer_view_container);
        shimmerFrameLayout.startShimmer();

        toolbarSimmerLayout = rootView.findViewById(R.id.toolbar_simmer_layout);
        fisrtSimmerLayout = rootView.findViewById(R.id.first_simmer_layout);
        secondSimmerLayout = rootView.findViewById(R.id.second_simmer_layout);
        thirdSimmerLayout = rootView.findViewById(R.id.third_simmer_layout);
        fourthSimmerLayout = rootView.findViewById(R.id.fouth_simmer_layout);
        fifthSimmerLayout = rootView.findViewById(R.id.fifth_simmer_layout);
        home_layout = rootView.findViewById(R.id.home_main_layout);

        recv1 = rootView.findViewById(R.id.child_recv);
        recv2 = rootView.findViewById(R.id.second_recv);
        recv3 = rootView.findViewById(R.id.third_recv);
        featuredRecyclerView = rootView.findViewById(R.id.featured_item_recv);

        topBanner = rootView.findViewById(R.id.hfrg_top_banner_imv);

        title1 = rootView.findViewById(R.id.first_title);
        title2 = rootView.findViewById(R.id.second_title);
        title3 = rootView.findViewById(R.id.third_title);
        searchBtn = rootView.findViewById(R.id.htb_search_imv);
        featuredMoviesBtn = rootView.findViewById(R.id.featured_movies_filter_btn);
        featuredTvShowsBtn = rootView.findViewById(R.id.featured_tvshow_filter_btn);
        c1 = rootView.findViewById(R.id.c1);
        c2 = rootView.findViewById(R.id.c2);

        title1.setText("Top Rated");
        title2.setText("Populer");
        title3.setText("Only On MoviesFlix");


        featuredMoviesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movietxt = "movie";
                TrendingMoviesJSON_URL = Endpoints.TRENDING_MOVIES+"?api_key="+ Utils.getData(getContext(), "apiKey", "");
                TopRatedJSON_URL = Endpoints.TOP_RATED_MOVIES+"?api_key="+ Utils.getData(getContext(), "apiKey", "");
                PopularJSON_URL = Endpoints.POPULAR_MOVIES+"?api_key="+ Utils.getData(getContext(), "apiKey", "");

                getLocalData();
                shimmerFrameLayout.stopShimmer();

                GetData trendingData = new GetData(TrendingMoviesJSON_URL, new ArrayList<>(), recv3, getContext(),3);
                trendingData.execute();

                GetData popularData = new GetData(PopularJSON_URL, new ArrayList<>(), recv2, getContext(),2);
                popularData.execute();

                GetData topRatedData = new GetData(TopRatedJSON_URL, new ArrayList<>(), recv1, getContext(),1);
                topRatedData.execute();


                GetTrendingMoviesData trendingMoviesData = new GetTrendingMoviesData(TrendingMoviesJSON_URL, new ArrayList<>(), featuredRecyclerView, getContext());
                trendingMoviesData.execute();

                c1.setCardBackgroundColor(getContext().getColor(R.color.white));
                c1.setAlpha(1.0f);
                c2.setAlpha(0.5f);
            }
        });

        featuredMoviesBtn.performClick();

        featuredTvShowsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tvshowtxt = "tv";
                TrendingTvShowsJSON_URL = Endpoints.TRENDING_TVSHOWS+"?api_key="+ Utils.getData(getContext(), "apiKey", "");
                TopRatedJSON_URL = Endpoints.TOP_RATED_TVSHOWS+"?api_key="+ Utils.getData(getContext(), "apiKey", "");
                PopularJSON_URL = Endpoints.POPULAR_TVSHOWS+"?api_key="+ Utils.getData(getContext(), "apiKey", "");

                GetTvShowData trendingtvData = new GetTvShowData(TrendingTvShowsJSON_URL, new ArrayList<>(), recv3);
                trendingtvData.execute();

                GetTvShowData populartvData = new GetTvShowData(PopularJSON_URL, new ArrayList<>(), recv2);
                populartvData.execute();

                GetTvShowData topRatedtvData = new GetTvShowData(TopRatedJSON_URL, new ArrayList<>(), recv1);
                topRatedtvData.execute();

                GetTvData topTvShowsData = new GetTvData(TrendingTvShowsJSON_URL, new ArrayList<>(), featuredRecyclerView, getContext());
                topTvShowsData.execute();

                c1.setAlpha(0.5f);
                c2.setCardBackgroundColor(getContext().getColor(R.color.white));
                c2.setAlpha(1.0f);

            }
        });

        //Going to search activity
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchActivityIntent = new Intent(getContext(), SearchActivity.class);
                startActivity(searchActivityIntent);
            }
        });

        return rootView;
    }

    private void getLocalData() {
        try {
            JSONObject jsonObject1 = new JSONObject(Utils.getData(getContext(),"home_date_1", ""));
            setValues(jsonObject1,recv1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        try {
            JSONObject jsonObject2 = new JSONObject(Utils.getData(getContext(),"home_date_2", ""));
            setValues(jsonObject2,recv2);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        try {
            JSONObject jsonObject3 = new JSONObject(Utils.getData(getContext(),"home_date_3", ""));
            setValues(jsonObject3,recv3);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }


    public class GetData extends AsyncTask<String, String, String> {
        private String apiUrl;
        private List<ChildRecvModelClass> childData;
        private RecyclerView recyclerView;
        Context context;
        int parent = 0;

        public GetData(String apiUrl, List<ChildRecvModelClass> childData, RecyclerView recyclerView, Context context, int i) {
            this.apiUrl = apiUrl;
            this.childData = childData;
            this.recyclerView = recyclerView;
            this.context = context;
            this.parent = i;
        }

        @Override
        protected String doInBackground(String... strings) {
            return JsonUtils.makeHttpRequest(apiUrl);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                Utils.setData(getContext(), "home_date_"+parent, jsonObject.toString());
                setValues(jsonObject,recyclerView);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void setValues(JSONObject onj, RecyclerView recyclerView) throws JSONException {
        JSONArray jsonArray = onj.getJSONArray("results");
        List<ChildRecvModelClass> childData=new ArrayList<>();
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

        putDataIntoRecyclerView(childData, recyclerView);
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
        home_layout.setVisibility(View.VISIBLE);

    }

    public class GetTvShowData extends AsyncTask<String, String, String> {
        private String apiUrl;
        private List<ChildRecvModelClass> childData;
        private RecyclerView recyclerView;

        public GetTvShowData(String apiUrl, List<ChildRecvModelClass> childData, RecyclerView recyclerView) {
            this.apiUrl = apiUrl;
            this.childData = childData;
            this.recyclerView = recyclerView;
        }


        @Override
        protected String doInBackground(String... strings) {
            return JsonUtils.makeHttpRequest(apiUrl);
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

            putDataIntoTvShowsRecyclerView(childData, recyclerView);
            shimmerFrameLayout.setVisibility(View.GONE);
            shimmerFrameLayout.stopShimmer();
            home_layout.setVisibility(View.VISIBLE);
        }
    }

    public class GetTvData extends AsyncTask<String, String, String> {
        private String apiUrl;
        private List<FeaturedModelClass> featuredData;
        private RecyclerView recyclerView;
        private Context context;

        public GetTvData(String apiUrl, List<FeaturedModelClass> featuredData, RecyclerView recyclerView, Context context) {
            this.apiUrl = apiUrl;
            this.featuredData = featuredData;
            this.recyclerView = recyclerView;
            this.context = context;
        }


        @Override
        protected String doInBackground(String... strings) {
            return JsonUtils.makeHttpRequest(apiUrl);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    FeaturedModelClass featuredModel = new FeaturedModelClass();
                    featuredModel.setImage("https://image.tmdb.org/t/p/w500" + jsonObject1.getString("backdrop_path"));
                    featuredModel.setDesc(jsonObject1.getString("overview"));
                    featuredModel.setTvShowTitle(jsonObject1.getString("name"));
                    String imdbRating = "IMDB Rating : " + jsonObject1.getString("vote_average");
                    featuredModel.setImdbRating(imdbRating);

                    featuredData.add(featuredModel);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (isAdded()) {
                putDataIntoFeatureRecv(featuredData, recyclerView);
            }

        }
    }

    public class GetTrendingMoviesData extends AsyncTask<String, String, String> {
        private String apiUrl;
        private List<FeaturedModelClass> featuredData;
        private RecyclerView recyclerView;
        private Context context;
        public GetTrendingMoviesData(String apiUrl, List<FeaturedModelClass> featuredData, RecyclerView recyclerView, Context context) {
            this.apiUrl = apiUrl;
            this.featuredData = featuredData;
            this.recyclerView = recyclerView;
            this.context = context;
        }


        @Override
        protected String doInBackground(String... strings) {
            return JsonUtils.makeHttpRequest(apiUrl);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    FeaturedModelClass featuredModel = new FeaturedModelClass();
                    featuredModel.setImage("https://image.tmdb.org/t/p/w500" + jsonObject1.getString("backdrop_path"));
                    featuredModel.setDesc(jsonObject1.getString("overview"));
                    featuredModel.setTvShowTitle(jsonObject1.getString("title"));
                    String imdbRating = "IMDB Rating : " + jsonObject1.getString("vote_average");
                    featuredModel.setImdbRating(imdbRating);

                    featuredData.add(featuredModel);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (isAdded()) {
                putDataIntoFeatureRecv(featuredData, recyclerView);
            }

        }
    }

    private void putDataIntoRecyclerView(List<ChildRecvModelClass> recvData1, RecyclerView recv) {
        ChildAdapter childAdapter = new ChildAdapter(recvData1, getContext());
        recv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recv.setAdapter(childAdapter);

        childAdapter.setOnItemClickListener(new ChildAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String movieId) {
                Intent intent = new Intent(getContext(), MoviesDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void putDataIntoTvShowsRecyclerView(List<ChildRecvModelClass> recvData, RecyclerView recv) {
        TvShowsAdapter tvShowsAdapter = new TvShowsAdapter(recvData, getContext());
        recv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recv.setAdapter(tvShowsAdapter);

        tvShowsAdapter.setOnItemClickListener(new TvShowsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String movieId) {
                Intent intent = new Intent(getContext(), MoviesDetailsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void putDataIntoFeatureRecv(List<FeaturedModelClass> featuredData, RecyclerView recv) {
        FeaturedAdapter featuredAdapter = new FeaturedAdapter(featuredData, getContext());
        recv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recv.setAdapter(featuredAdapter);
    }

    public static class JsonUtils {
        private static final String TAG = JsonUtils.class.getSimpleName();
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