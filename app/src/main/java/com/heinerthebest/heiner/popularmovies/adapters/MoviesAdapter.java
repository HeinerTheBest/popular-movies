package com.heinerthebest.heiner.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.heinerthebest.heiner.popularmovies.R;
import com.heinerthebest.heiner.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>
{



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View movieView = inflater.inflate(R.layout.movie_list_item,parent,false);

        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        Picasso.get()
                .load(movie.getmImageURL().equals("null")?"https://dev-patel.net/news/wp-content/uploads/2017/04/Filmography_NoPoster.jpg":"http://image.tmdb.org/t/p/w500/"+movie.getmImageURL())
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return (mMovies == null) ? 0 : mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ImageView mImageView;

        ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.small_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickPosition = getAdapterPosition();
            mMovieClickListener.onMovieClick(clickPosition);
        }
    }

    private List<Movie> mMovies;
    final private MovieClickListener mMovieClickListener;

    public MoviesAdapter(List<Movie> movies, MovieClickListener listener) {
        mMovies = movies;
        mMovieClickListener = listener;
    }

    public interface MovieClickListener{
        void onMovieClick(int clickedMovieIndex);

}

}
