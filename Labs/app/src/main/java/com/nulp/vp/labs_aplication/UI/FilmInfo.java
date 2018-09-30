package com.nulp.vp.labs_aplication.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nulp.vp.labs_aplication.R;

public class FilmInfo extends AppCompatActivity {

    private TextView tvTitle, tvDescription, tvRate;
    private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_info);
        init();
        getIncomingIntent();
    }

    private void getIncomingIntent() {
        String imageName = getIntent().getStringExtra("title");
        String imageAbout = getIntent().getStringExtra("description");
        String imageURL = getIntent().getStringExtra("image_path");
        String voteAverage = getIntent().getStringExtra("rate_average");
        setInfo(imageURL, imageName, imageAbout, voteAverage);
    }


    private void setInfo(String imageUrl, String title, String description, String rateAverage) {
        tvTitle.setText(title);
        tvDescription.setText(description);
        tvRate.setText(rateAverage);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w200" + imageUrl)
                .into(image);
    }

    private void init() {
        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);
        tvRate = findViewById(R.id.tv_vote_average);
        image = findViewById(R.id.img_poster);

    }
}

