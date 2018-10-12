package com.nulp.vp.labs_aplication.UI;

import android.database.Cursor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nulp.vp.labs_aplication.Adapter.CustomAdapter;
import com.nulp.vp.labs_aplication.DB.DBHelp;
import com.nulp.vp.labs_aplication.Model.Film;
import com.nulp.vp.labs_aplication.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavouriteFilm extends AppCompatActivity {
    private Cursor c;
    private CustomAdapter adapter;

    @BindView(R.id.tv_toolbar)
    TextView tvToolbarText;
    @BindView(R.id.rv_info_fav)
    RecyclerView mRecyclerView;
    @BindView(R.id.pullToRefreshFav)
    SwipeRefreshLayout pullToRefresh;
    @BindView(R.id.toolbar_favourite)
    View myLayout;
    @BindView(R.id.btn_split)
    Button btnSaveDel;
    private ArrayList<Film> films = new ArrayList<>();

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

    private void init() {
        ButterKnife.bind(this);
        tvToolbarText = ButterKnife.findById(myLayout, R.id.tv_toolbar);
        btnSaveDel = ButterKnife.findById(myLayout, R.id.btn_split);
        btnSaveDel.setVisibility(View.GONE);
        tvToolbarText.setText("Favourite films");
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
