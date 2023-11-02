package com.example.moviesflix.Utils;

import java.security.PublicKey;

public class Endpoints
{
    public static String BASE_URL = "https://api.themoviedb.org/3/";

    //MOVIES API
    public static String POPULAR_MOVIES = BASE_URL+"movie/popular";
    public static String TOP_RATED_MOVIES = BASE_URL+"movie/top_rated";
    public static String TRENDING_MOVIES = BASE_URL+"trending/movie/day";
    public static String UPCOMING_MOVIES = BASE_URL+"movie/upcoming";

    //TV SHOWS API
    public static String POPULAR_TVSHOWS = BASE_URL+"tv/popular";
    public static String TOP_RATED_TVSHOWS = BASE_URL+"tv/top_rated";
    public static String TRENDING_TVSHOWS = BASE_URL+"trending/tv/day";
    public static String UPCOMING_TVSHOWS = BASE_URL+"tv/upcoming";

    //Download page API
    public static String MOVIES_COLLECTION = BASE_URL+"discover/movie";



}
