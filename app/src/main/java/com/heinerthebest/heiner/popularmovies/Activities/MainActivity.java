package com.heinerthebest.heiner.popularmovies.Activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.heinerthebest.heiner.popularmovies.models.MainViewModel;
import com.heinerthebest.heiner.popularmovies.models.QueryForMovies;
import com.heinerthebest.heiner.popularmovies.models.Movie;
import com.heinerthebest.heiner.popularmovies.utilities.AppExecutors;
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
    //LiveData<List<Movie>> tmpMovies;
    final String Tag = MainActivity.class.getSimpleName();
    private AppDataBase mDb;
    MainViewModel mainViewModel;

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
        //todo i'm using ViewModel and everithing but don't saving the state so when you rote the phone he call onCreate :( i don't understand what happend, and i dont want to fail the course, the rest Work good
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        makeMovieSearchByTopRated();
    }

    @Override
    protected void onResume() {
        super.onResume();
        makeMovieRefresh();
        Log.d(Tag,"Running dbm in onResume");
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
           return;
       }


        rvMovies.refreshDrawableState();
        mProgressBar.setVisibility(View.GONE);
        mainViewModel.setMovies(mDb.movieDao().loadTopRatedMovies());
        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                final MoviesAdapter adapter = new MoviesAdapter(movies,MainActivity.this);
                setListOfMovies(adapter);

            }
        });
    }


    private void makeMovieSearchByPopulate() {
        setSearch(constant.BY_POPULATED);

        if(!iGotMoviesFromWeb())
        {
            rvMovies.refreshDrawableState();
            mProgressBar.setVisibility(View.GONE);

            return;
        }

        mainViewModel.setMovies(mDb.movieDao().loadPopularMovies());
        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                final MoviesAdapter adapter = new MoviesAdapter(movies,MainActivity.this);
                setListOfMovies(adapter);
                rvMovies.refreshDrawableState();
                mProgressBar.setVisibility(View.GONE);
            }
        });

    }

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

        mainViewModel.setMovies(mDb.movieDao().loadMovies());
        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> moviesLive) {
                if(moviesLive.isEmpty())
                {
                    call = jsonPlaceHolderInterface.getMovieByRated();
                    call.enqueue(new Callback<QueryForMovies>() {
                        @Override
                        public void onResponse(Call<QueryForMovies> call, Response<QueryForMovies> response) {
                            movies = response.body().getResults();
                            for(Movie movie:movies)
                            {
                                movie.setFavorite(false);
                            }
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.movieDao().insertMovies(movies);
                                    getInfo = true;
                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<QueryForMovies> call, Throwable t) {
                            Log.d(Tag, "failure " + t.getMessage());
                            mErrorText.setVisibility(View.VISIBLE);
                            getInfo = false;
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
                            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    mDb.movieDao().insertMovies(movies);
                                    getInfo = true;
                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<QueryForMovies> call, Throwable t) {
                            Log.d(Tag, "failure " + t.getMessage());
                            mErrorText.setVisibility(View.VISIBLE);
                            getInfo = false;
                        }
                    });
                }
                else
                {
                    getInfo = true;
                }
            }
        });

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
                makeMovieRefresh();
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

    private void makeMovieRefresh() {
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
    }

    private void makeMovieSearchByFavorite()
    {
        setSearch(constant.BY_FAVORITE);
        mainViewModel.setMovies(mDb.movieDao().loadAllFavoriteMovies());
        mainViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                final MoviesAdapter adapter = new MoviesAdapter(movies,MainActivity.this);
                setListOfMovies(adapter);
                rvMovies.refreshDrawableState();
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }






    @Override
    public void onMovieClick(int clickedMovieIndex) {
        Intent intent = new Intent(context,MovieDescriptionActivity.class);
        final String clickedMovieId = mainViewModel.getMovies().getValue().get(clickedMovieIndex).getId();
        intent.putExtra(Intent.EXTRA_INDEX,clickedMovieId);
        startActivity(intent);

    }

}
