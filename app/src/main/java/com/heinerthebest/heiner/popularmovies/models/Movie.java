package com.heinerthebest.heiner.popularmovies.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


@Entity
public class Movie implements Parcelable
{

    @PrimaryKey
    @NonNull
    private String id;     //Information I can send in intent for get information about movies
    private String title;
    private int vote_count;
    private String overview;
    private String poster_path;
    private String release_date;
    private Boolean favorite = false;
    private Float    vote_average;
    private Float popularity;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public void setPopularity(Float popularity) {
        this.popularity = popularity;
    }

    public String getTitle() {
        return title;
    }

    public int getVote_count() {
        return vote_count;
    }

    public Boolean isFavorite() {
        return favorite;
    }

    public Float getPopularity() {
        return popularity;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Movie(String id, String title, int vote_count, String overview, String poster_path, String release_date, Float vote_average, Float popularity) {
        this.id = id;
        this.title = title;
        this.vote_count = vote_count;
        this.overview = overview;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.favorite = false;
        this.vote_average = vote_average;
        this.popularity = popularity;
    }


    public Float getVote_average() {
        return vote_average;
    }

    public void setVote_average(Float vote_average) {
        this.vote_average = vote_average;
    }

    public String getId() {
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
        dest.writeString(id);
        dest.writeString(title);
        dest.writeInt(vote_count);
        dest.writeString(overview);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeFloat(vote_average);
    }

    public Movie(Parcel source) {
        id = source.readString();
        title = source.readString();
        vote_count = source.readInt();
        overview = source.readString();
        poster_path = source.readString();
        release_date = source.readString();
        vote_average = source.readFloat();
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
