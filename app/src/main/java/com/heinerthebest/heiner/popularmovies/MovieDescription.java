package com.heinerthebest.heiner.popularmovies;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.heinerthebest.heiner.popularmovies.models.Movie;
import com.heinerthebest.heiner.popularmovies.utilities.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDescription extends AppCompatActivity {
    ImageView mMoviePoster;
    TextView mTitle;
    TextView mReleaseDate;
    TextView mRating;
    TextView mSynopsis;
    Movie movie;
    Constant constant;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);

        ArrayList<Movie> movies = getIntent().getParcelableArrayListExtra("cars");
        movie = movies.get(getIntent().getIntExtra(Intent.EXTRA_INDEX,0));

        constant = new Constant();
        setViews();
        fillDescription();
    }


    private void setViews()
    {
        mTitle = findViewById(R.id.tv_title);
        mReleaseDate = findViewById(R.id.tv_release_date);
        mRating = findViewById(R.id.tv_user_rating);
        mSynopsis = findViewById(R.id.tv_synopsis);
        mMoviePoster = findViewById(R.id.img_poster);
    }

    private void fillDescription() {
        if(movie != null) {
            String title = movie.getmTitle();
            String release = movie.getmReleaseDate();
            String rating = movie.getmUserRating() + "/10";
            String synopsis = movie.getmSynopsis();

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
        String url = movie.getmImageURL();
        if(!url.equals("null"))
            return constant.getENDPOINT_IMAGE_W500(url);

        return constant.getENDPOINT_DEFAULT_IMAGE();
    }


}
