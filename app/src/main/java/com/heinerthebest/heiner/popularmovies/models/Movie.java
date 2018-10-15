package com.heinerthebest.heiner.popularmovies.models;

public class Movie
{
    private int mId;     //Information I can send in intent for get information about movies
    private String mTitle;
    private int mUserVoted;
    private String mSynopsis;
    private String mImageURL;
    private String mReleaseDate;


    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public Movie(int id, String title, int userVoted, String synopsis, String imageURL, String releaseDate)
    {
        mId = id;
        mTitle = title;
        mUserVoted = userVoted;
        mSynopsis = synopsis;
        mImageURL = imageURL;
        mReleaseDate = releaseDate;
    }
    public int getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }


    public double getmUserRating() {
        return mUserVoted;
    }


    public String getmSynopsis() {
        return mSynopsis;
    }


    public String getmImageURL() {
        return mImageURL;
    }

}
