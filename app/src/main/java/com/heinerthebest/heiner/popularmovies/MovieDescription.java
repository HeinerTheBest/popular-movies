package com.heinerthebest.heiner.popularmovies;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.heinerthebest.heiner.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;
public class MovieDescription extends AppCompatActivity {
    ImageView mMoviePoster;
    TextView mTitle;
    TextView mReleaseDate;
    TextView mRating;
    TextView mSynopsis;
    Movie movie;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_description);

        //Declaration
        mTitle = findViewById(R.id.tv_title);
        mReleaseDate = findViewById(R.id.tv_release_date);
        mRating = findViewById(R.id.tv_user_rating);
        mSynopsis = findViewById(R.id.tv_synopsis);
        mMoviePoster = findViewById(R.id.img_poster);


        String url = getIntent().getStringExtra("MOVIE_URL");
        if(!url.equals("null"))
            url = "http://image.tmdb.org/t/p/w500/"+url;
        else
            url = "http://reelcinemas.ae/Images/Movies/not-found/no-poster.jpg";

        String title = getIntent().getStringExtra("TITLE");
        String release = getIntent().getStringExtra("RELEASE_DATE");
        String rating = Integer.toString(getIntent().getIntExtra("RATING",0))+"/10";
        String synopsis = getIntent().getStringExtra("SYNOPSIS");

        //Set
        Picasso.get()
                .load(url)
                .into(mMoviePoster);
        mTitle.setText(title);
        mReleaseDate.setText(release);
        mRating.setText(rating);
        mSynopsis.setText(synopsis);



    }





}
