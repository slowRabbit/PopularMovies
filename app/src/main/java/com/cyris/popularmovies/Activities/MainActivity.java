package com.cyris.popularmovies.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;

import com.cyris.popularmovies.Adapters.MovieAdapter;
import com.cyris.popularmovies.Model.Movie;
import com.cyris.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView movieList;
    List<Movie> data;
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList=(RecyclerView) findViewById(R.id.movieListRecyclerView);
        data=new ArrayList<Movie>();
        Movie m1=new Movie("Never Back Down", "date", "poster path", "8.4", "string overview", "backdrop path");
        Movie m2=new Movie("Gangs of waseypur part 2", "date", "poster path", "9.6", "string overview", "backdrop path");

        for(int i=0; i<24; i++)
        {
            data.add(m1);
            data.add(m2);
        }

        movieAdapter=new MovieAdapter(data);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this, 2);
        movieList.setAdapter(movieAdapter);
        movieList.setLayoutManager(gridLayoutManager);

    }
}
