package com.heinerthebest.heiner.popularmovies.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heinerthebest.heiner.popularmovies.Interfaces.JsonPlaceHolderInterface;
import com.heinerthebest.heiner.popularmovies.R;
import com.heinerthebest.heiner.popularmovies.adapters.MoviesAdapter;
import com.heinerthebest.heiner.popularmovies.database.AppDataBase;
import com.heinerthebest.heiner.popularmovies.models.QueryForMovies;
import com.heinerthebest.heiner.popularmovies.models.Movie;
import com.heinerthebest.heiner.popularmovies.utilities.Constant;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieClickListener{

    ArrayList<Movie> movies;
    Context context;
    ProgressBar mProgressBar;
    RecyclerView rvMovies;
    TextView mErrorText;
    boolean byTopRated = true;
    boolean byPopulated = false;
    boolean byFavorite = false;
    Constant constant = new Constant();
    Retrofit retrofit;
    JsonPlaceHolderInterface jsonPlaceHolderInterface;
    Call<QueryForMovies> call;
    Boolean getInfo = true;
    List<Movie> tmpMovies;
    final String Tag = MainActivity.class.getSimpleName();
    private AppDataBase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mDb = AppDataBase.getsInstance(getApplicationContext());
        mProgressBar = findViewById(R.id.progressBar);
        mErrorText = findViewById(R.id.tv_error);
        rvMovies = findViewById(R.id.rv_movies);
        setRetrofit();
        //TODO The first time it don't show anything but I don;t found the error in my code, i need refresh in the app for get the posters.
        makeMovieSearchByTopRated();
    }

    private void setRetrofit()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(constant.THE_MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderInterface = retrofit.create(JsonPlaceHolderInterface.class);
    }


    private void makeMovieSearchByTopRated()
    {
       setSearch(constant.BY_TOP_RATED);

       if(!iGotMoviesFromWeb())
       {
           rvMovies.refreshDrawableState();
           mProgressBar.setVisibility(View.GONE);
           Log.d(Tag,"it return false");
           return;
       }

        Log.d(Tag,"Yes, im here");

        rvMovies.refreshDrawableState();
        mProgressBar.setVisibility(View.GONE);

        tmpMovies = mDb.movieDao().loadTopRatedMovies();
       MoviesAdapter adapter = new MoviesAdapter(tmpMovies,MainActivity.this);
       setListOfMovies(adapter);
    }


    private void makeMovieSearchByPopulate() {
        setSearch(constant.BY_POPULATED);

        if(!iGotMoviesFromWeb())
        {
            rvMovies.refreshDrawableState();
            mProgressBar.setVisibility(View.GONE);

            return;
        }

        tmpMovies = mDb.movieDao().loadPopularMovies();
        MoviesAdapter adapter = new MoviesAdapter(tmpMovies,MainActivity.this);
        setListOfMovies(adapter);

        rvMovies.refreshDrawableState();
        mProgressBar.setVisibility(View.GONE);
    }

    //TODO 01) Create and Add makeMovieSearchByFavorite


    public void setListOfMovies(MoviesAdapter moviesAdapter)
    {
        rvMovies.setAdapter(moviesAdapter);
        rvMovies.setLayoutManager(new GridLayoutManager(context,2));
        rvMovies.setVisibility(View.VISIBLE);
        rvMovies.refreshDrawableState();
        mProgressBar.setVisibility(View.GONE);
    }



    private boolean iGotMoviesFromWeb()
    {
        if(mDb.movieDao().loadMovies().isEmpty())
        {
            Log.d(Tag,"1rs quetion");
            call = jsonPlaceHolderInterface.getMovieByRated();
            call.enqueue(new Callback<QueryForMovies>() {
                @Override
                public void onResponse(Call<QueryForMovies> call, Response<QueryForMovies> response) {
                    movies = response.body().getResults();
                    for(Movie movie:movies)
                    {
                        movie.setFavorite(false);
                    }
                    mDb.movieDao().insertMovies(movies);
                    getInfo = true;
                    Log.d(Tag,"2 Everithing ok and getinfo is = "+getInfo);
                }

                @Override
                public void onFailure(Call<QueryForMovies> call, Throwable t) {
                    Log.d(Tag, "failure " + t.getMessage());
                    mErrorText.setVisibility(View.VISIBLE);
                    getInfo = false;
                    Log.d(Tag,"3 Everithing bad and getinfo is = "+getInfo);
                }
            });

            call = jsonPlaceHolderInterface.getMovieByPopular();
            call.enqueue(new Callback<QueryForMovies>() {
                @Override
                public void onResponse(Call<QueryForMovies> call, Response<QueryForMovies> response) {
                    movies = response.body().getResults();
                    movies = response.body().getResults();
                    for(Movie movie:movies)
                    {
                        movie.setFavorite(false);
                    }
                    mDb.movieDao().insertMovies(movies);
                    getInfo = true;
                    Log.d(Tag,"4 Everithing ok and getinfo is = "+getInfo);
                }

                @Override
                public void onFailure(Call<QueryForMovies> call, Throwable t) {
                    Log.d(Tag, "failure " + t.getMessage());
                    mErrorText.setVisibility(View.VISIBLE);
                    getInfo = false;
                    Log.d(Tag,"5 Everithing bad and getinfo is = "+getInfo);
                }
            });

            Log.d(Tag,"The last 6 is "+getInfo);
        }
        else {
            return true;
        }
        return getInfo;
    }


    private void setSearch(String opc)
    {
        //Pre
        rvMovies.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mErrorText.setVisibility(View.GONE);
        ///// **************************************

        switch (opc)
        {
            case "topRated":
                byTopRated  = true;
                byPopulated = false;
                byFavorite  = false;
                break;

            case "populated":
                byTopRated  = false;
                byPopulated = true;
                byFavorite  = false;
                break;

            case "favorite":
                byTopRated  = false;
                byPopulated = false;
                byFavorite  = true;
                break;
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
                    if(byPopulated)
                    {
                        makeMovieSearchByPopulate();
                    }
                    else
                    {
                        makeMovieSearchByFavorite();
                    }
                }

                break;


            case R.id.action_top_rated:
                makeMovieSearchByTopRated();
                break;

            case R.id.action_more_populated:
                makeMovieSearchByPopulate();
                break;

            case R.id.action_favorite:
                makeMovieSearchByFavorite();
                break;
        }



        return super.onOptionsItemSelected(item);
    }

    private void makeMovieSearchByFavorite()
    {
        setSearch(constant.BY_FAVORITE);

        tmpMovies = mDb.movieDao().loadAllFavoriteMovies();
        MoviesAdapter adapter = new MoviesAdapter(tmpMovies,MainActivity.this);
        setListOfMovies(adapter);

        rvMovies.refreshDrawableState();
        mProgressBar.setVisibility(View.GONE);
    }






    @Override
    public void onMovieClick(int clickedMovieIndex) {
        Intent intent = new Intent(context,MovieDescriptionActivity.class);
        final String clickedMovieId = tmpMovies.get(clickedMovieIndex).getId();
        intent.putExtra(Intent.EXTRA_INDEX,clickedMovieId);
        startActivity(intent);

    }

}
