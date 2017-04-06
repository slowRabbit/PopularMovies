package com.cyris.popularmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bumptech.glide.Glide;
import com.cyris.popularmovies.Extras.AppUrlDetails;
import com.cyris.popularmovies.Extras.CustomVolleyRequest;
import com.cyris.popularmovies.Model.Movie;
import com.cyris.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by cyris on 4/4/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolderClass>{

    List<Movie> data;

    Context context;
    MovieItemClickListener movieItemClickListener;
    private int lastPosition = -1;


    public MovieAdapter(List<Movie> data, MovieItemClickListener movieItemClickListener)
    {
        this.data=data;
        this.movieItemClickListener=movieItemClickListener;
    }

    @Override
    public void onViewDetachedFromWindow(MovieHolderClass holder) {
        super.onViewDetachedFromWindow(holder);
        ((MovieHolderClass)holder).clearAnimation();
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
        setAnimation(holder.itemView, position);

    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.move_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
        else if(position<lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.move_down);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MovieHolderClass extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView ivMoviePoster;
        TextView tvTitle, tvRating;
        private ImageLoader imageLoader;

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

            AppUrlDetails appUrlDetails=new AppUrlDetails();
            String imageurl=appUrlDetails.getBaseUrlForImages()+movie.getPosterPath();

            Picasso.with(context).load(imageurl).into(ivMoviePoster);
            //setImageViewAfterFetchingFromUrl(movie);

        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            movieItemClickListener.onClick(position);
        }

        public void clearAnimation()
        {
            itemView.clearAnimation();
        }

    }

}
