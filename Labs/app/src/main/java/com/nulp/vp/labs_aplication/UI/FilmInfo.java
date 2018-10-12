package com.nulp.vp.labs_aplication.UI;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nulp.vp.labs_aplication.DB.DBHelp;
import com.nulp.vp.labs_aplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilmInfo extends AppCompatActivity {
    private DBHelp dbHelp;
    private Cursor cursor;
    private int isFavourite = 0;

    private String title, description, imageURL, voteAverage;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbarText;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_vote_average)
    TextView tvRate;
    @BindView(R.id.toolbar_info)
    View toolbarLayout;
    @BindView(R.id.btn_split)
    Button btnSaveDelete;
    @BindView(R.id.img_poster)
    ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_info);
        init();
    }

    private void getIncomingIntent() {
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        imageURL = getIntent().getStringExtra("image_path");
        voteAverage = getIntent().getStringExtra("rate_average");
        setInfo(imageURL, title, description, voteAverage);
    }


    private void setInfo(String imageUrl, String title, String description, String rateAverage) {
        tvTitle.setText(title);
        tvToolbarText.setText(title);
        tvDescription.setText(description);
        tvRate.setText(rateAverage);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w200" + imageUrl)
                .into(image);
    }

    private void checkFilm() {
        cursor = dbHelp.queueAll();
        while (cursor.moveToNext()) {
            String titleFav = cursor.getString(0);
            String descriptionFav = cursor.getString(1);
            if (titleFav.equals(title) || descriptionFav.equals(description)) {
                isFavourite++;
            }

        }
        if (isFavourite == 0) {
            btnSaveDelete.setText(R.string.save);
        } else btnSaveDelete.setText(R.string.delete);

    }

    private void init() {
        ButterKnife.bind( this );
        tvToolbarText = ButterKnife.findById(toolbarLayout, R.id.tv_toolbar);
        btnSaveDelete = ButterKnife.findById(toolbarLayout, R.id.btn_split);
        dbHelp = new DBHelp(this);
        getIncomingIntent();
        checkFilm();
    }

    @OnClick(R.id.btn_split)
    void saveDelete(View view) {
        if (isFavourite == 0) {
            boolean insert = dbHelp.insert(title, description, imageURL, voteAverage);
            if (insert) {
                Toast.makeText(FilmInfo.this, "Фільм додано до улюблених", Toast.LENGTH_SHORT).show();
                checkFilm();
            }
        } else {
            dbHelp.delete(title);
            isFavourite = 0;
            checkFilm();
            Toast.makeText(FilmInfo.this, "Фільм видалено з улюблених", Toast.LENGTH_SHORT).show();
        }
    }
}


