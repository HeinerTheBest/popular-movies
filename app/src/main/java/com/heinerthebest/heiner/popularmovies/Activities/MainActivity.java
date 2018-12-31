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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heinerthebest.heiner.popularmovies.Interfaces.JsonPlaceHolderInterface;
import com.heinerthebest.heiner.popularmovies.R;
import com.heinerthebest.heiner.popularmovies.adapters.MoviesAdapter;
import com.heinerthebest.heiner.popularmovies.models.Query;
import com.heinerthebest.heiner.popularmovies.models.Movie;
import com.heinerthebest.heiner.popularmovies.utilities.Constant;

import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieClickListener {

    ArrayList<Movie> movies;
    Context context;
    ProgressBar mProgressBar;
    RecyclerView rvMovies;
    TextView mErrorText;
    boolean byTopRated = true;
    URL movieSearchUrlByTopRated,movieSearchUrlByPopulated;
    Constant constant = new Constant();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mProgressBar = findViewById(R.id.progressBar);
        mErrorText = findViewById(R.id.tv_error);


        //Todo Creting test with retrofit

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderInterface jsonPlaceHolderInterface = retrofit.create(JsonPlaceHolderInterface.class);

        Call<Query> call = jsonPlaceHolderInterface.getMovieByRated();

        call.enqueue(new Callback<Query>() {
            @Override
            public void onResponse(Call<Query> call, Response<Query> response) {
                Log.d("TestRetrofit","Succes "+response.body().getResults().get(0).getmTitle());


                //todo add the procces inside a if(!response.isSuccessful()) if is bad put code: response.code

            }

            @Override
            public void onFailure(Call<Query> call, Throwable t) {
                Log.d("TestRetrofit","failure "+t.getMessage());
            }
        });

        //todo Finish test with retrofit


        rvMovies = findViewById(R.id.rv_movies);
        Log.d("HeinerTheBest","created");
        //TODO I called two times because for one reason in the first call it show me a empty screen.
        makeMovieSearchByTopRated();

    }

    private void makeMovieSearchByTopRated() {
        setSearch(constant.BY_TOP_RATED);

        //Creating retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(constant.THE_MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Call Json
        JsonPlaceHolderInterface jsonPlaceHolderInterface = retrofit.create(JsonPlaceHolderInterface.class);

        //Calling for get movie by rate
        Call<Query> call = jsonPlaceHolderInterface.getMovieByRated();

        //Start Proccess
        call.enqueue(new Callback<Query>() {
            @Override
            public void onResponse(Call<Query> call, Response<Query> response) {
                Log.d("TestRetrofit","Succes "+response.code());
                movies = response.body().getResults();
                MoviesAdapter adapter = new MoviesAdapter(movies,MainActivity.this);
                rvMovies.setAdapter(adapter);
                rvMovies.setLayoutManager(new GridLayoutManager(context,2));
                rvMovies.setVisibility(View.VISIBLE);
                //todo add the procces inside a if(!response.isSuccessful()) if is bad put code: response.code
            }

            @Override
            public void onFailure(Call<Query> call, Throwable t) {
                Log.d("TestRetrofit","failure "+t.getMessage());
                mErrorText.setVisibility(View.VISIBLE);
            }
        });

        rvMovies.refreshDrawableState();
        mProgressBar.setVisibility(View.GONE);


        //todo delete the next line if the one on this is better
        //new MovieQueryTask().execute(movieSearchUrlByTopRated);
    }

    private void makeMovieSearchByPopulate() {
        setSearch(constant.BY_POPULATED);

        //Creating retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(constant.THE_MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Call Json
        JsonPlaceHolderInterface jsonPlaceHolderInterface = retrofit.create(JsonPlaceHolderInterface.class);

        //Calling for get movie by rate
        Call<Query> call = jsonPlaceHolderInterface.getMovieByPopular();

        //Start Proccess
        call.enqueue(new Callback<Query>() {
            @Override
            public void onResponse(Call<Query> call, Response<Query> response) {
                Log.d("TestRetrofit","Succes "+response.code());
                movies = response.body().getResults();
                MoviesAdapter adapter = new MoviesAdapter(movies,MainActivity.this);
                rvMovies.setAdapter(adapter);
                rvMovies.setLayoutManager(new GridLayoutManager(context,2));
                rvMovies.setVisibility(View.VISIBLE);
                //todo add the procces inside a if(!response.isSuccessful()) if is bad put code: response.code
            }

            @Override
            public void onFailure(Call<Query> call, Throwable t) {
                Log.d("TestRetrofit","failure "+t.getMessage());
                mErrorText.setVisibility(View.VISIBLE);
            }
        });

        rvMovies.refreshDrawableState();
        mProgressBar.setVisibility(View.GONE);



        //todo delete the next line if the one on this is better
        //new MovieQueryTask().execute(movieSearchUrlByPopulated);
    }

    //TODO 01) Create and Add makeMovieSearchByFavorite


    private void setSearch(String opc)
    {

        //Pre
        rvMovies.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mErrorText.setVisibility(View.GONE);
        ///// **************************************

        switch (opc)
        {
            case "BY_TOP_RATED":
                byTopRated = true;
                break;

            case "BY_POPULATED":
                byTopRated = false;
                break;

            //TODO 02) Add by  Favorite
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
        Intent intent = new Intent(context,MovieDescriptionActivity.class);
        intent.putExtra(Intent.EXTRA_INDEX,clickedMovieIndex);
        intent.putParcelableArrayListExtra("cars", movies);

        startActivity(intent);


    }



}
