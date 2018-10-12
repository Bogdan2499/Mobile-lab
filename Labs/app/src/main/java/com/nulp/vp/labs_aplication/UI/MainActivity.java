package com.nulp.vp.labs_aplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nulp.vp.labs_aplication.API.ApiService;
import com.nulp.vp.labs_aplication.API.ApiUtils;
import com.nulp.vp.labs_aplication.Adapter.CustomAdapter;
import com.nulp.vp.labs_aplication.Model.Film;
import com.nulp.vp.labs_aplication.Model.ListFilms;
import com.nulp.vp.labs_aplication.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private CustomAdapter adapter;
    private ApiService apiService;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbarText;
    @BindView(R.id.include_main)
    View myLayout;
    @BindView(R.id.btn_split)
    Button btnFavourite;
    @BindView(R.id.rv_info)
    RecyclerView mRecyclerView;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void loadFilms() {
        apiService.getAnswers().enqueue(new Callback<ListFilms>() {
            @Override
            public void onResponse(Call<ListFilms> call, Response<ListFilms> response) {

                if (response.isSuccessful()) {
                    adapter.updateAnswers(response.body().getFilms());
                    Log.d("MainActivity", "films loaded from API");
                } else {
                    tvError.setText("Помилка");

                }
            }

            @Override
            public void onFailure(Call<ListFilms> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }
        });
    }

    @OnClick(R.id.btn_split)
    void submitButton(View view) {
        Intent intent = new Intent(MainActivity.this, FavouriteFilm.class);
        startActivity(intent);
    }

    private void init() {
        apiService = ApiUtils.getSOService();
        ButterKnife.bind(this);
        tvToolbarText = ButterKnife.findById(myLayout, R.id.tv_toolbar);
        btnFavourite = ButterKnife.findById(myLayout, R.id.btn_split);

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFilms();
                pullToRefresh.setRefreshing(false);
            }
        });

        tvToolbarText.setText(R.string.list_of_films);
        btnFavourite.setText(R.string.favourite_films);

        adapter = new CustomAdapter(this, new ArrayList<Film>(0));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        loadFilms();
    }

}