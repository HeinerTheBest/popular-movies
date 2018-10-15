package com.heinerthebest.heiner.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private final static String THEMOVIEDB_BASE_URL =
            "https://api.themoviedb.org/3/discover/movie";
    private final static String PARAM_KEY = "api_key";

    //TODO add key in mKey
    private final static String mKey = "";
    private final static String PARAM_SORT = "sort_by";
    private final static String sortByPopular = "popularity.asc";
    private final static String sortByTopRated = "vote_average.asc";
    private final static String PARAM_MOVIE_ID = "movie_id";



    public static URL buildUrlByPopular() {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_KEY,mKey)
                .appendQueryParameter(PARAM_SORT, sortByPopular)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static URL buildUrlById(String id) {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_MOVIE_ID,id)
                .appendQueryParameter(PARAM_KEY,mKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }



    public static URL buildUrlByTopRated() {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_KEY,mKey)
                .appendQueryParameter(PARAM_SORT, sortByTopRated)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }



    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }



}


