package com.heinerthebest.heiner.popularmovies.utilities;

import android.util.Log;

import com.heinerthebest.heiner.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils
{

    public static ArrayList<Movie> parseMovieSJson(String json) {
    ArrayList<Movie> movies = new ArrayList<>();

        try {

            JSONArray jArray = new JSONObject(json).getJSONArray("results");
            if(jArray != null)
            {
                JSONObject MovieJson;
                for (int i=0;i<jArray.length();i++)
                {
                    MovieJson = jArray.getJSONObject(i);
                    movies.add( new Movie(
                            MovieJson.getInt("id"),
                            MovieJson.getString("title"),
                            MovieJson.getInt("vote_average"),
                            MovieJson.getString("overview"),
                            MovieJson.getString("poster_path"),
                            MovieJson.getString("release_date")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }

   /* public static ArrayList<Movie> parseMovieSJsonJustBasic(String json) {
        ArrayList<Movie> movies = new ArrayList<>();

        try {

            JSONArray jArray = new JSONObject(json).getJSONArray("results");
            if(jArray != null)
            {
                JSONObject MovieJson;
                for (int i=0;i<jArray.length();i++)
                {
                    MovieJson = jArray.getJSONObject(i);
                    movies.add( new Movie(
                            MovieJson.getInt("id"),
                            MovieJson.getString("poster_path")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }*/


    public static Movie parseMovieJson(String json) {

        try {
            JSONObject MovieJson = new JSONObject(json);

            return new Movie(
                    MovieJson.getInt("id"),
                    MovieJson.getString("original_title"),
                    MovieJson.getInt("vote_count"),
                    MovieJson.getString("overview"),
                    MovieJson.getString("poster_path"),
                    MovieJson.getString("release_date"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
