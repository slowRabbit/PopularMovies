package com.cyris.popularmovies.Model;

/**
 * Created by cyris on 4/4/17.
 */

public class Movie {

    String title;
    String releaseDate;
    String posterPath;
    String voteAverage;
    String overView;
    String backdropImagePath;

    public Movie(String title, String releaseDate, String posterPath, String voteAverage, String overView, String backdropImagePath)
    {
        this.title=title;
        this.releaseDate=releaseDate;
        this.posterPath=posterPath;
        this.voteAverage=voteAverage;
        this.overView=overView;
        this.backdropImagePath=backdropImagePath;
    }

    public String getBackdropImagePath() {
        return backdropImagePath;
    }

    public String getOverView() {
        return overView;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }
}
