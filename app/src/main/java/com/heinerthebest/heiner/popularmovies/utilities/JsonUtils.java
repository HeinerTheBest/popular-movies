package com.heinerthebest.heiner.popularmovies.utilities;

import com.heinerthebest.heiner.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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


}
