package com.heinerthebest.heiner.popularmovies.Interfaces;

import com.heinerthebest.heiner.popularmovies.models.Query;
import com.heinerthebest.heiner.popularmovies.utilities.Constant;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderInterface
{

    Constant constant = new Constant();

    @GET("popular?"+ Constant.PARAM_KEY +"="+ Constant.KEY)
    Call<Query> getMovieByPopular();

    @GET("top_rated?"+ Constant.PARAM_KEY +"="+ Constant.KEY)
    Call<Query> getMovieByRated();


}
