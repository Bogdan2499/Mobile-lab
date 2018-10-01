package com.nulp.vp.labs_aplication;

import android.database.Cursor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nulp.vp.labs_aplication.Adapter.CustomAdapter;
import com.nulp.vp.labs_aplication.DB.DBHelp;
import com.nulp.vp.labs_aplication.Model.Film;

import java.util.ArrayList;

public class FavouriteFilm extends AppCompatActivity {
    private Cursor c;
    private RecyclerView mRecyclerView;
    private CustomAdapter adapter;
    private ArrayList<Film> films = new ArrayList<>();
    private SwipeRefreshLayout pullToRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_film);
        init();
    }
    private void loadFilms() {
        films.clear();
        String title, description, imageURL, voteAverage;
        DBHelp db = new DBHelp(this);
        c = db.queueAll();
        while (c.moveToNext()) {
            title = c.getString(0);
            description = c.getString(1);
            imageURL = c.getString(2);
            voteAverage = c.getString(3);

            Film film = new Film(title, description, imageURL, voteAverage);

            films.add(film);
        }

        if (!(films.size() < 1)) {
            mRecyclerView.setAdapter(adapter);
        }

    }
    private void init(){
        mRecyclerView = findViewById(R.id.rv_info_fav);
        pullToRefresh = findViewById(R.id.pullToRefreshFav);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFilms();
                pullToRefresh.setRefreshing(false);
            }
        });
        loadFilms();
        adapter = new CustomAdapter(this, films);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }
}
