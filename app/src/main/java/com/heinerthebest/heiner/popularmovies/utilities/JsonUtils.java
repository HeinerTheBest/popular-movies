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
                JSONObject movieJson;
                for (int i=0;i<jArray.length();i++)
                {
                    movieJson = jArray.getJSONObject(i);
                    movies.add( new Movie(
                            movieJson.getInt("id"),
                            movieJson.getString("title"),
                            movieJson.getInt("vote_average"),
                            movieJson.getString("overview"),
                            movieJson.getString("poster_path"),
                            movieJson.getString("release_date")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }


}
