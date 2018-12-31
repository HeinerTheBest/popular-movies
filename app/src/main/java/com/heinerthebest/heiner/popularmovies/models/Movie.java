package com.heinerthebest.heiner.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable
{

    private int id;     //Information I can send in intent for get information about movies
    private String title;
    private int vote_count;
    private String overview;
    private String poster_path;
    private String release_date;
    private Boolean isFavorite;


    public String getRelease_date() {
        return release_date;
    }

    public Movie(int id, String title, int userVoted, String synopsis, String imageURL, String releaseDate)
    {
        this.id = id;
        this.title = title;
        vote_count = userVoted;
        overview = synopsis;
        poster_path = imageURL;
        release_date = releaseDate;
        isFavorite = false;
    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public int getId() {
        return id;
    }

    public String getmTitle() {
        return title;
    }


    public double getmUserRating() {
        return vote_count;
    }


    public String getOverview() {
        return overview;
    }


    public String getPoster_path() {
        return poster_path;
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(vote_count);
        dest.writeString(overview);
        dest.writeString(poster_path);
        dest.writeString(release_date);
    }

    public Movie(Parcel source) {
        id = source.readInt();
        title = source.readString();
        vote_count = source.readInt();
        overview = source.readString();
        poster_path = source.readString();
        release_date = source.readString();
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
