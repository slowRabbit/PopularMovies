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
    MovieItemClickListener movieItemClickListener;

    public MovieAdapter(List<Movie> data, MovieItemClickListener movieItemClickListener)
    {
        this.data=data;
        this.movieItemClickListener=movieItemClickListener;
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

    public interface MovieItemClickListener{
        void onClick(int position);
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

    class MovieHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener{

        public MovieHolderClass(View itemView) {
            super(itemView);
            ivMoviePoster=(ImageView)itemView.findViewById(R.id.movieposter);
            tvRating=(TextView)itemView.findViewById(R.id.tv_movieRating);
            tvTitle=(TextView)itemView.findViewById(R.id.tv_movieTitle);
            itemView.setOnClickListener(this);
        }

        void Bind(Movie movie)
        {
            tvTitle.setText(movie.getTitle());
            tvRating.setText(movie.getVoteAverage());
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            movieItemClickListener.onClick(position);
        }
    }

}
