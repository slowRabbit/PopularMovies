package com.cyris.popularmovies.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    List<Movie> popularMovieData, topRatedMovieData;
    MovieAdapter popularMovieAdapter, topRatedMovieAdapter;
    ProgressDialog progressDialog;

    boolean isPopular=true;
    private  Menu menu;
    //if popular is true we will fetch and add data in popularMovieData
    //else we will fetch and add data in topRatedMovieList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(true);

        movieList=(RecyclerView) findViewById(R.id.movieListRecyclerView);
        popularMovieData=new ArrayList<Movie>();
        topRatedMovieData=new ArrayList<Movie>();

        popularMovieAdapter=new MovieAdapter(popularMovieData, this);
        topRatedMovieAdapter=new MovieAdapter(topRatedMovieData, this);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 2);
        movieList.setAdapter(popularMovieAdapter);
        movieList.setLayoutManager(gridLayoutManager);
        movieList.setHasFixedSize(true);

        //--getting popular and top rated movie list
        getPopularMovieListFromServer();
        isPopular=false;
        getPopularMovieListFromServer();
        isPopular=true;
        //--

        setTitle("Popular Movies");

    }

    @Override
    public void onClick(int position) {

        Toast.makeText(this, "Item clicked", Toast.LENGTH_SHORT).show();

    }

    private void getPopularMovieListFromServer() {

        progressDialog.setTitle("Fetching Movie List");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        MyVolley.init(this);
        RequestQueue queue = MyVolley.getRequestQueue();
        AppUrlDetails appDetails=new AppUrlDetails();
        String movieTypeString=null;
        if(isPopular)
        {
            movieTypeString="popular";
        }
        else
        {
            movieTypeString="top_rated";
        }

        String urlForFetching=appDetails.getBaseUrlForMovieDetails()+movieTypeString+appDetails.getApiKey();
        JsonObjectRequest myReq = new JsonObjectRequest(Request.Method.GET, urlForFetching
                , movieListPopularSuccessListener(), movieListPopularErrorListener()) {

        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(myReq);


    }

    private com.android.volley.Response.Listener<JSONObject> movieListPopularSuccessListener() {
        return new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject serverResponse) {

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

                        if(isPopular)
                        {
                            popularMovieData.add(movie);
                        }
                        else
                        {
                            topRatedMovieData.add(movie);
                        }

                       // Log.i("MovieList", title+"-"+releaseDate+"-"+posterPath+"-"+voteAverage+"-"+overView+"-"+backdropImagePath);

                    }
                    popularMovieAdapter.notifyDataSetChanged();
                    isPopular=!isPopular;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.hide();

            }
        };
    }


    private com.android.volley.Response.ErrorListener movieListPopularErrorListener() {
        return new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //btnCreateClassroom.setEnabled(false);
                Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_LONG).show();
                progressDialog.hide();
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menu=menu;
        getMenuInflater().inflate(R.menu.mainactivity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        isPopular=!isPopular;

        if(isPopular)
        {
            //popularMovieData is populated in popularMovieAdapter
            movieList.setAdapter(popularMovieAdapter);
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.toprated_icon1));
            setTitle("Popular Movies");
        }
        else
        {
            //topRated movie data is populated in popularMovieAdapter
            movieList.setAdapter(topRatedMovieAdapter);
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.popular_icon1));
            setTitle("Top Rated Movies");
        }

        return super.onOptionsItemSelected(item);
    }
}
