package com.cyris.popularmovies.Activities;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.cyris.popularmovies.Extras.AppUrlDetails;
import com.cyris.popularmovies.Extras.CustomVolleyRequest;
import com.cyris.popularmovies.Model.Movie;
import com.cyris.popularmovies.R;

import java.util.Random;

public class MovieDetailActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    private ImageLoader imageLoader;
    NetworkImageView networkImageView;
    int chosenColor;

    TextView tvReleaseDate, tvOverView, tvVoteAverage;
    TextView tvBackgroundVoteAvg, tvBackgroundReleaseDate, tvBackgroundOverview;

    Movie recievedMovieObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        recievedMovieObject= (Movie) getIntent().getSerializableExtra("MovieObject");
        networkImageView=(NetworkImageView)findViewById(R.id.backdropImageForMovie);
        tvOverView=(TextView)findViewById(R.id.tv_overview);
        tvReleaseDate=(TextView)findViewById(R.id.tv_release_date);
        tvVoteAverage=(TextView)findViewById(R.id.tv_vote_average);
        tvBackgroundOverview=(TextView)findViewById(R.id.backgroundOverview);
        tvBackgroundReleaseDate=(TextView)findViewById(R.id.backgroundReleaseDate);
        tvBackgroundVoteAvg=(TextView)findViewById(R.id.backgroundVoteAverage);

        //--setting toolbar and action bars
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        chosenColor=R.color.colorPrimary;
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        if(recievedMovieObject!=null)
        {

            collapsingToolbar.setTitle(recievedMovieObject.getTitle());
            tvVoteAverage.setText(recievedMovieObject.getVoteAverage());
            tvReleaseDate.setText(recievedMovieObject.getReleaseDate());
            tvOverView.setText(recievedMovieObject.getOverView());
        }

        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbar.setExpandedTitleTextColor(ColorStateList.valueOf(Color.WHITE));
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        //----

        AppUrlDetails appUrlDetails=new AppUrlDetails();
        String imageurl=appUrlDetails.getBaseUrlForBackdropImage()+recievedMovieObject.getBackdropImagePath();

        imageLoader = CustomVolleyRequest.getInstance(this)
                .getImageLoader();
        imageLoader.get(imageurl, ImageLoader.getImageListener(networkImageView,
                R.drawable.movie_icon_256, android.R.drawable
                        .ic_dialog_alert));
        networkImageView.setImageUrl(imageurl, imageLoader);


        //working for palette
        imageLoader.get(imageurl, new ImageLoader.ImageListener() {

            public void onErrorResponse(VolleyError arg0) {
                networkImageView.setImageResource(R.drawable.movie_icon_256); // set an error image if the download fails
                Toast.makeText(getApplicationContext(), "Cannot fetch movie poster", Toast.LENGTH_SHORT).show();
            }

            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    networkImageView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_in));
                    networkImageView.setImageBitmap(response.getBitmap());
                    changeStatusAndActionBarColorUsingPalette();
                } else
                    networkImageView.setImageResource(R.drawable.movie_icon_256); // set the loading image while the download is in progress
            }
        });



    }

    private void changeStatusAndActionBarColorUsingPalette() {

        Bitmap bitmap = ((BitmapDrawable)networkImageView.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {

                //work with the palette here
                int defaultValue = 0x000000;
                chosenColor = palette.getDarkMutedColor(defaultValue);
                int darkerColorChosen=getDarkerColor(chosenColor, 0.8f);
                collapsingToolbar.setContentScrimColor(chosenColor);

                //changing the status bar color

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                    window.setStatusBarColor(darkerColorChosen);
                }
                tvBackgroundVoteAvg.setBackgroundColor(chosenColor);
                tvBackgroundReleaseDate.setBackgroundColor(chosenColor);
                tvBackgroundOverview.setBackgroundColor(chosenColor);

            }
        });


    }

    public static int getDarkerColor(int color, float factor) {

        //user factor less than 1.0f , try 0.8f
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r,255),
                Math.min(g,255),
                Math.min(b,255));
    }

}
