package com.nulp.vp.labs_aplication.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.nulp.vp.labs_aplication.API.ApiService;
import com.nulp.vp.labs_aplication.API.ApiUtils;
import com.nulp.vp.labs_aplication.Adapter.CustomAdapter;
import com.nulp.vp.labs_aplication.Model.Film;
import com.nulp.vp.labs_aplication.Model.ListFilms;
import com.nulp.vp.labs_aplication.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private CustomAdapter adapter;
    private ApiService apiService;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        loadFilms();
    }

    public void loadFilms() {
        apiService.getAnswers().enqueue(new Callback<ListFilms>() {
            @Override
            public void onResponse(Call<ListFilms> call, Response<ListFilms> response) {

                if (response.isSuccessful()) {
                    adapter.updateAnswers(response.body().getFilms());
                    Log.d("MainActivity", "films loaded from API");
                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<ListFilms> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }
        });
    }

    private void init() {
        apiService = ApiUtils.getSOService();
        mRecyclerView = findViewById(R.id.rv_info);
        adapter = new CustomAdapter(this, new ArrayList<Film>(0), new CustomAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                Toast.makeText(MainActivity.this, "Film id is:  " + id, Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }


}
