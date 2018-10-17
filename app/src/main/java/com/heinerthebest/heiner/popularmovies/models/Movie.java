package com.heinerthebest.heiner.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable
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

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeInt(mUserVoted);
        dest.writeString(mSynopsis);
        dest.writeString(mImageURL);
        dest.writeString(mReleaseDate);
    }

    public Movie(Parcel source) {
        mId = source.readInt();
        mTitle = source.readString();
        mUserVoted = source.readInt();
        mSynopsis = source.readString();
        mImageURL = source.readString();
        mReleaseDate = source.readString();
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
