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

public class FilmInfo extends AppCompatActivity {
    private DBHelp dbHelp;
    private Cursor cursor1;
    private Button btnSave, btnDel;
    private String title, description, imageURL, voteAverage;
    private TextView tvTitle, tvDescription, tvRate;
    private ImageView image;

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
        tvDescription.setText(description);
        tvRate.setText(rateAverage);
        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w200" + imageUrl)
                .into(image);
    }

    private void check_fav() {
        DBHelp db = new DBHelp(this);
        cursor1 = db.queueAll();
        while (cursor1.moveToNext()) {
            String titleFav = cursor1.getString(0);
            String descriptionFav = cursor1.getString(1);

            if (titleFav.equals(title) || descriptionFav.equals(description)) {
                btnSave.setVisibility(View.GONE);
                btnDel.setVisibility(View.VISIBLE);
            }
        }
    }

    private void init() {
        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);
        tvRate = findViewById(R.id.tv_vote_average);
        image = findViewById(R.id.img_poster);
        btnSave = findViewById(R.id.btn_save);
        btnDel = findViewById(R.id.btn_del);
        dbHelp = new DBHelp(this);

        getIncomingIntent();
        check_fav();
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelp.delete(title);
            }
        });

        btnSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean insert = dbHelp.insert(title, description, imageURL, voteAverage);

                        if (insert) {
                            Toast.makeText(FilmInfo.this, "Фільм додано до улюблених", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
    }

}


