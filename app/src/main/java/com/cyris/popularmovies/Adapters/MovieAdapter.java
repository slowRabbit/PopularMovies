package com.cyris.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyris.popularmovies.Model.Movie;
import com.cyris.popularmovies.R;

import java.util.List;

/**
 * Created by cyris on 4/4/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolderClass>{

    List<Movie> data;
    ImageView ivMoviePoster;
    TextView tvTitle, tvRating;
    Context context;

    public MovieAdapter(List<Movie> data)
    {
        this.data=data;
    }

    @Override
    public MovieHolderClass onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.movie_list_item, parent, shouldAttachToParentImmediately);
        MovieHolderClass viewHolder = new MovieHolderClass(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieHolderClass holder, int position) {

        Movie movie=data.get(position);
        holder.Bind(movie);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MovieHolderClass extends RecyclerView.ViewHolder{

        public MovieHolderClass(View itemView) {
            super(itemView);
            ivMoviePoster=(ImageView)itemView.findViewById(R.id.movieposter);
            tvRating=(TextView)itemView.findViewById(R.id.tv_movieRating);
            tvTitle=(TextView)itemView.findViewById(R.id.tv_movieTitle);
        }

        void Bind(Movie movie)
        {
            tvTitle.setText(movie.getTitle());
            tvRating.setText(movie.getVoteAverage());
        }

    }

}
