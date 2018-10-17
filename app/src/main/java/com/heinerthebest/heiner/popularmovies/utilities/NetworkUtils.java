package com.heinerthebest.heiner.popularmovies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    private final static String THEMOVIEDB_BASE_URL =
            "http://api.themoviedb.org/3/movie";
           // "https://api.themoviedb.org/3/discover/movie";
    private final static String PARAM_KEY = "api_key";

    //TODO add key in mKey
    private final static String mKey = "";



    public static URL buildUrlByPopular() {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                .appendEncodedPath("popular" )
                .appendQueryParameter(PARAM_KEY,mKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d("HeinerTheBest","url is "+url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static URL buildUrlByTopRated() {
        Uri builtUri = Uri.parse(THEMOVIEDB_BASE_URL).buildUpon()
                .appendEncodedPath("top_rated" )
                .appendQueryParameter(PARAM_KEY,mKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.d("HeinerTheBest","url is "+url);
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


