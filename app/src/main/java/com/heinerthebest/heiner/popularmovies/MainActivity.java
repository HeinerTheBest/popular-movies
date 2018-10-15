package com.heinerthebest.heiner.popularmovies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heinerthebest.heiner.popularmovies.adapters.MoviesAdapter;
import com.heinerthebest.heiner.popularmovies.models.Movie;
import com.heinerthebest.heiner.popularmovies.utilities.JsonUtils;
import com.heinerthebest.heiner.popularmovies.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieClickListener {

    ArrayList<Movie> movies;
    Context context;
    ProgressBar mProgressBar;
    RecyclerView rvMovies;
    TextView mErrorText;
    boolean byTopRated = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mProgressBar = findViewById(R.id.progressBar);
        mErrorText = findViewById(R.id.tv_error);

        rvMovies = findViewById(R.id.rv_movies);
        URL movieSearchUrlByTopRated = NetworkUtils.buildUrlByTopRated();
        new MovieQueryTask().execute(movieSearchUrlByTopRated);
        new MovieQueryTask().execute(movieSearchUrlByTopRated);


    }


    private void makeMovieSearchByTopRated() {
        byTopRated = true;
        URL movieSearchUrlByTopRated = NetworkUtils.buildUrlByTopRated();
        new MovieQueryTask().execute(movieSearchUrlByTopRated);
    }

    private void makeMovieSearchByPopulate() {
        byTopRated = false;
        URL movieSearchUrlByPopulated = NetworkUtils.buildUrlByPopular();
        new MovieQueryTask().execute(movieSearchUrlByPopulated);
    }



    @SuppressLint("StaticFieldLeak")
    public class MovieQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            rvMovies.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            mErrorText.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubSearchResults;
        }

        @Override
        protected void onPostExecute(String movieSearchResults) {
            movies = JsonUtils.parseMovieSJson(movieSearchResults);
            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                Log.d("HEINER_THE_BEST",movies.get(0).getmImageURL());
                MoviesAdapter adapter = new MoviesAdapter(movies,MainActivity.this);
                rvMovies.setAdapter(adapter);
                rvMovies.setLayoutManager(new GridLayoutManager(context,2));
                rvMovies.setVisibility(View.VISIBLE);
            }
            else
            {
                mErrorText.setVisibility(View.VISIBLE);
            }
            rvMovies.refreshDrawableState();
            mProgressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_refresh:
                if(byTopRated)
                {
                    makeMovieSearchByTopRated();
                }
                else
                {
                    makeMovieSearchByPopulate();
                }

                break;


            case R.id.action_top_rated:
                makeMovieSearchByTopRated();
                break;

            case R.id.action_more_populated:
                makeMovieSearchByPopulate();
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClick(int clickedMovieIndex) {
        Log.d("HEINER_TEST","f"+movies.get(clickedMovieIndex).getmUserRating());
        Intent intent = new Intent(context,MovieDescription.class);
        intent.putExtra("MOVIE_URL",movies.get(clickedMovieIndex).getmImageURL());
        intent.putExtra("TITLE",movies.get(clickedMovieIndex).getmTitle());
        intent.putExtra("RELEASE_DATE",movies.get(clickedMovieIndex).getmReleaseDate());
        intent.putExtra("RATING",movies.get(clickedMovieIndex).getmUserRating());
        intent.putExtra("SYNOPSIS",movies.get(clickedMovieIndex).getmSynopsis());
        startActivity(intent);


    }



}
