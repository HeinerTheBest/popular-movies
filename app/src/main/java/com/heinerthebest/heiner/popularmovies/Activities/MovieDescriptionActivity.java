package com.heinerthebest.heiner.popularmovies.Activities;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.heinerthebest.heiner.popularmovies.Interfaces.JsonPlaceHolderInterface;
import com.heinerthebest.heiner.popularmovies.R;
import com.heinerthebest.heiner.popularmovies.database.AppDataBase;
import com.heinerthebest.heiner.popularmovies.models.Movie;
import com.heinerthebest.heiner.popularmovies.models.QueryForMovies;
import com.heinerthebest.heiner.popularmovies.models.QueryForReviews;
import com.heinerthebest.heiner.popularmovies.models.QueryForTrailers;
import com.heinerthebest.heiner.popularmovies.models.Review;
import com.heinerthebest.heiner.popularmovies.models.Trailer;
import com.heinerthebest.heiner.popularmovies.utilities.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDescriptionActivity extends AppCompatActivity {
    ImageView mMoviePoster;
    TextView mTitle;
    TextView mReleaseDate;
    TextView mRating;
    TextView mSynopsis;
    Button   mPlayTrailer;
    TextView mReviews;
    Movie movie;
    Constant constant = new Constant();
    Retrofit retrofit;
    JsonPlaceHolderInterface jsonPlaceHolderInterface;
    final String TAG = MovieDescriptionActivity.class.getSimpleName();
    String trailerUrl;
    private AppDataBase mDb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);
        mDb = AppDataBase.getsInstance(getApplicationContext());

        String idMovie = getIntent().getStringExtra(Intent.EXTRA_INDEX);

        if(!idMovie.isEmpty()) {
            movie = mDb.movieDao().loadMovie(idMovie);
            setViews();
            fillDescription();
            setRetrofit();
            setReviews();

            mPlayTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl)));
                    Log.i(TAG, "Video Playing....");
                }
            });

        }
    }

    private void setReviews() {
        Call<QueryForReviews> call;
        call = jsonPlaceHolderInterface.getReviewsOfMovie(movie.getId());
        call.enqueue(new Callback<QueryForReviews>() {
            @Override
            public void onResponse(Call<QueryForReviews> call, Response<QueryForReviews> response) {
                ArrayList<Review> reviews = response.body().getResults();
                if(!reviews.isEmpty()) {
                    for (Review review : reviews) {
                        mReviews.append(" *" + review.getAuthor() + ": " + review.getContent() + "/n/n");
                    }
                }
            }

            @Override
            public void onFailure(Call<QueryForReviews> call, Throwable t) {
                Log.d(TAG,"Failure "+t.getMessage());
            }
        });

    }

    private void setTrailer() {
        Call<QueryForTrailers> call;
        call = jsonPlaceHolderInterface.getTrailersOfMovie(movie.getId());
        call.enqueue(new Callback<QueryForTrailers>() {
            @Override
            public void onResponse(Call<QueryForTrailers> call, Response<QueryForTrailers> response) {
               trailerUrl = response.body().getResults().get(0).getYoutubeUrl();
            }

            @Override
            public void onFailure(Call<QueryForTrailers> call, Throwable t) {
                Log.d(TAG,"Failure "+t.getMessage());
            }
        });
    }

    private void setRetrofit()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl(constant.THE_MOVIE_DB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderInterface = retrofit.create(JsonPlaceHolderInterface.class);
        setTrailer();

    }


    private void setViews()
    {
        mTitle = findViewById(R.id.tv_title);
        mReleaseDate = findViewById(R.id.tv_release_date);
        mRating = findViewById(R.id.tv_user_rating);
        mSynopsis = findViewById(R.id.tv_synopsis);
        mMoviePoster = findViewById(R.id.img_poster);
        mPlayTrailer = findViewById(R.id.play_trailer_btn);
        mReviews = findViewById(R.id.tv_reviews);
    }

    private void fillDescription() {
        if(movie != null) {
            String title = movie.getmTitle();
            String release = movie.getRelease_date();
            String rating = movie.getVote_average() + "/10";
            String synopsis = movie.getOverview();

            Picasso.get()
                    .load(getPosterUrl())
                    .into(mMoviePoster);
            mTitle.setText(title);
            mReleaseDate.setText(release);
            mRating.setText(rating);
            mSynopsis.setText(synopsis);
        }
    }

    private String getPosterUrl() {
        String url = movie.getPoster_path();
        if(!url.equals("null"))
            return constant.getENDPOINT_IMAGE_W500(url);

        return constant.getENDPOINT_DEFAULT_IMAGE();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.description_menu, menu);
        if(movie.isFavorite())
            menu.getItem(0).setIcon(R.drawable.ic_favorite_black_24dp);
        else
            menu.getItem(0).setIcon(R.drawable.ic_favorite_border_black_24dp);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.action_is_favorite:

                if(movie.isFavorite())
                {
                    movie.setFavorite(false);
                    mDb.movieDao().updateMovie(movie);
                    item.setIcon(R.drawable.ic_favorite_border_black_24dp);
                }
                else
                {
                    movie.setFavorite(true);
                    mDb.movieDao().updateMovie(movie);
                    item.setIcon(R.drawable.ic_favorite_black_24dp);
                }


                break;

        }



        return super.onOptionsItemSelected(item);
    }


}
