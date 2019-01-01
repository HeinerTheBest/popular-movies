package com.heinerthebest.heiner.popularmovies.Interfaces;

import android.util.Log;

import com.heinerthebest.heiner.popularmovies.models.QueryForMovies;
import com.heinerthebest.heiner.popularmovies.models.QueryForReviews;
import com.heinerthebest.heiner.popularmovies.models.QueryForTrailers;
import com.heinerthebest.heiner.popularmovies.models.Trailer;
import com.heinerthebest.heiner.popularmovies.utilities.Constant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderInterface
{

    Constant constant = new Constant();

    @GET("popular?"+ Constant.PARAM_KEY +"="+ Constant.KEY)
    Call<QueryForMovies> getMovieByPopular();

    @GET("top_rated?"+ Constant.PARAM_KEY +"="+ Constant.KEY)
    Call<QueryForMovies> getMovieByRated();

    @GET("{id}/videos?"+ Constant.PARAM_KEY +"="+ Constant.KEY)
    Call<QueryForTrailers>getTrailersOfMovie(@Path("id") int id_movie);

    @GET("{movie_id}/reviews?"+ Constant.PARAM_KEY +"="+ Constant.KEY)
    Call<QueryForReviews>getReviewsOfMovie(@Path("movie_id") int id);
}
