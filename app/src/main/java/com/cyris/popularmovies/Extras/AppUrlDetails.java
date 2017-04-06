package com.cyris.popularmovies.Extras;

/**
 * Created by cyris on 6/4/17.
 */

public class AppUrlDetails {

    String baseUrlForImages="http://image.tmdb.org/t/p/w300//";
    String baseUrlForBackdropImage="http://image.tmdb.org/t/p/w500//";
    String baseUrlForMovieDetails="http://api.themoviedb.org/3/movie/";
    String apiKey="?api_key=10ddbecf738d8e66efa0e9681199ce21";

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrlForBackdropImage() {
        return baseUrlForBackdropImage;
    }

    public String getBaseUrlForImages() {
        return baseUrlForImages;
    }

    public String getBaseUrlForMovieDetails() {
        return baseUrlForMovieDetails;
    }
}
