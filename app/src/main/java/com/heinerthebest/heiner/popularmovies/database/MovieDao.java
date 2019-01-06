package com.heinerthebest.heiner.popularmovies.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.heinerthebest.heiner.popularmovies.models.Movie;

import java.util.ArrayList;
import java.util.List;


@Dao
public interface MovieDao {

    @Query("select * from Movie where favorite = 1")
    List<Movie> loadAllFavoriteMovies();

    @Query("select * from Movie order by popularity DESC")
    List<Movie> loadPopularMovies();

    @Query("SELECT * FROM movie ORDER BY vote_average DESC")
    List<Movie> loadTopRatedMovies();


    @Query("SELECT * FROM movie")
    List<Movie> loadMovies();



    @Query("SELECT * FROM movie Where id =(:idMovie)")
    Movie loadMovie(String idMovie);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(ArrayList<Movie> movies);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovies(ArrayList<Movie> movies);

}
