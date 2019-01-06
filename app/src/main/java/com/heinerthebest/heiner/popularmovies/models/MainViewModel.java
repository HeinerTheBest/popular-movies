package com.heinerthebest.heiner.popularmovies.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.heinerthebest.heiner.popularmovies.database.AppDataBase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        Log.d("heinerTheBest","constructor");
        AppDataBase appDataBase = AppDataBase.getsInstance(this.getApplication());
        movies = appDataBase.movieDao().loadTopRatedMovies();
    }

     public LiveData<List<Movie>> getMovies() {
            Log.d("heinerTheBest","Obtenemos pelicula");
         return movies;

     }

    public void setMovies(LiveData<List<Movie>> movies) {
        this.movies = movies;
                    Log.d("heinerTheBest","Seteamos pelicula");

    }
}
