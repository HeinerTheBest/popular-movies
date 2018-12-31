package com.heinerthebest.heiner.popularmovies.utilities;

public class Constant
{

    public final String ENDPOINT_IMAGE_W500 = "http://image.tmdb.org/t/p/w500/";
    public final String ENDPOINT_DEFAULT_IMAGE = "http://reelcinemas.ae/Images/Movies/not-found/no-poster.jpg";
    public final static String THE_MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public final static String PARAM_KEY = "api_key";

    public final String BY_TOP_RATED = "topRated";
    public final String BY_POPULATED = "populated";
    public final static String BY_FAVORITE  = "favorite";

    //TODO add key in KEY
    public static final String KEY = "4f9c18edc7a03e1e4444fae0a16350a1";

    public Constant() {
    }

    public String getENDPOINT_IMAGE_W500(String url_poster) {
        return ENDPOINT_IMAGE_W500+url_poster;
    }

    public String getENDPOINT_DEFAULT_IMAGE() {
        return ENDPOINT_DEFAULT_IMAGE;
    }
}
