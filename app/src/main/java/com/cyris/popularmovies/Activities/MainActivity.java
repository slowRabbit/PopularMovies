package com.cyris.popularmovies.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cyris.popularmovies.Adapters.MovieAdapter;
import com.cyris.popularmovies.Extras.AppUrlDetails;
import com.cyris.popularmovies.Extras.MyVolley;
import com.cyris.popularmovies.Model.Movie;
import com.cyris.popularmovies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickListener{

    RecyclerView movieList;
    List<Movie> data;
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList=(RecyclerView) findViewById(R.id.movieListRecyclerView);
        data=new ArrayList<Movie>();

        movieAdapter=new MovieAdapter(data, this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 2);
        movieList.setAdapter(movieAdapter);
        movieList.setLayoutManager(gridLayoutManager);
        movieList.setHasFixedSize(true);

        getPopularMovieListFromServer();

    }

    @Override
    public void onClick(int position) {

        Toast.makeText(this, "Item clicked", Toast.LENGTH_SHORT).show();

    }

    private void getPopularMovieListFromServer() {
        MyVolley.init(this);
        RequestQueue queue = MyVolley.getRequestQueue();
        AppUrlDetails appDetails=new AppUrlDetails();
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, appDetails.getBaseUrlForMovieDetails()+"popular"+appDetails.getApiKey()
                , movieListPopularSuccessListener(), movieListPopularErrorListener()) {

        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(2000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);


    }

    private com.android.volley.Response.Listener<JSONObject> movieListPopularSuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {


//                try {
//                    JSONArray array=serverResponse.getJSONArray("results");
//                    JSONObject singleMovie;
//                    String movieTitle;
//                    String imageLink;
//                    String overView;
//                    String userRating;
//                    String releaseDate;
//                    String id;
//                    Movie movie;
//
//                    for(int i=0; i<array.length(); i++)
//                    {
//                        singleMovie=array.getJSONObject(i);
//                        movieTitle=singleMovie.getString("original_title");
//                        imageLink=singleMovie.getString("poster_path");
//                        overView=singleMovie.getString("overview");
//                        userRating=singleMovie.getString("vote_average");
//                        releaseDate=singleMovie.getString("release_date");
//                        id=singleMovie.getString("id");
//                        movie=new Movie(movieTitle, imageLink, overView, userRating, releaseDate, id);
//                        movieListPopular.add(movie);
//                    }

                try {
                    JSONArray jsonArray=serverResponse.getJSONArray("results");
                    Log.i("MovieList", jsonArray.toString());
                    for(int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String title=jsonObject.getString("original_title");
                        String releaseDate=jsonObject.getString("release_date");
                        String posterPath=jsonObject.getString("poster_path");
                        String voteAverage=jsonObject.getString("vote_average");
                        String overView=jsonObject.getString("overview");
                        String backdropImagePath=jsonObject.getString("backdrop_path");

                        Movie movie=new Movie(title, releaseDate, posterPath, voteAverage, overView, backdropImagePath);
                        data.add(movie);

                        Log.i("MovieList", title+"-"+releaseDate+"-"+posterPath+"-"+voteAverage+"-"+overView+"-"+backdropImagePath);

                    }
                    movieAdapter.notifyDataSetChanged();
                    onMovieListFetched();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }

    private void onMovieListFetched() {



    }

    private com.android.volley.Response.ErrorListener movieListPopularErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //btnCreateClassroom.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
            }
        };

    }

}
