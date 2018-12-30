package com.heinerthebest.heiner.popularmovies.utilities;

public class Constant
{

    final String ENDPOINT_IMAGE_W500 = "http://image.tmdb.org/t/p/w500/";
    final String ENDPOINT_DEFAULT_IMAGE = "http://reelcinemas.ae/Images/Movies/not-found/no-poster.jpg";


    public Constant() {
    }

    public String getENDPOINT_IMAGE_W500(String url_poster) {
        return ENDPOINT_IMAGE_W500+url_poster;
    }

    public String getENDPOINT_DEFAULT_IMAGE() {
        return ENDPOINT_DEFAULT_IMAGE;
    }
}
