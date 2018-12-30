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
    URL movieSearchUrlByTopRated,movieSearchUrlByPopulated;

    final String BY_TOP_RATED = "topRated";
    final String BY_POPULATED = "populated";
    final String BY_FAVORITE  = "favorite";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mProgressBar = findViewById(R.id.progressBar);
        mErrorText = findViewById(R.id.tv_error);
        movieSearchUrlByTopRated = NetworkUtils.buildUrlByTopRated();
        movieSearchUrlByPopulated = NetworkUtils.buildUrlByPopular();

        rvMovies = findViewById(R.id.rv_movies);
        Log.d("HeinerTheBest","created");
        //TODO I called two times because for one reason in the first call it show me a empty screen.
        makeMovieSearchByTopRated();
    }


    private void makeMovieSearchByTopRated() {
        setSearch(BY_TOP_RATED);
        new MovieQueryTask().execute(movieSearchUrlByTopRated);
    }

    private void makeMovieSearchByPopulate() {
        setSearch(BY_POPULATED);
        new MovieQueryTask().execute(movieSearchUrlByPopulated);
    }

    //TODO 01) Create and Add makeMovieSearchByFavorite


    private void setSearch(String opc)
    {
        switch (opc)
        {
            case BY_TOP_RATED:
                byTopRated = true;
                break;

            case BY_POPULATED:
                byTopRated = false;
                break;

            //TODO 02) Add by  Favorite
        }
    }




    @SuppressLint("StaticFieldLeak")
    public class MovieQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("HeinerTHeBest","Starting pre query");
            rvMovies.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
            mErrorText.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(URL... params) {
            Log.d("HeinerTHeBest","Starting do background");
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
            Log.d("HeinerTHeBest","Starting post");
            if (movieSearchResults != null && !movieSearchResults.equals("")) {
                Log.d("HeinerTHeBest","Everything ok");
                movies = JsonUtils.parseMovieSJson(movieSearchResults);
                Log.d("HeinerTHeBest","created movies "+movies.get(0).getmImageURL());
                MoviesAdapter adapter = new MoviesAdapter(movies,MainActivity.this);
                rvMovies.setAdapter(adapter);
                rvMovies.setLayoutManager(new GridLayoutManager(context,2));
                rvMovies.setVisibility(View.VISIBLE);
            }
            else
            {
                Log.d("HeinerTHeBest","Something bad");
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
        Intent intent = new Intent(context,MovieDescription.class);
        intent.putExtra(Intent.EXTRA_INDEX,clickedMovieIndex);
        intent.putParcelableArrayListExtra("cars", movies);

        startActivity(intent);


    }



}
