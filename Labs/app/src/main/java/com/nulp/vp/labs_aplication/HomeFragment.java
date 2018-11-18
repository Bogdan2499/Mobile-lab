package com.nulp.vp.labs_aplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nulp.vp.labs_aplication.API.ApiService;
import com.nulp.vp.labs_aplication.API.ApiUtils;
import com.nulp.vp.labs_aplication.Adapter.CustomAdapter;
import com.nulp.vp.labs_aplication.Model.Film;
import com.nulp.vp.labs_aplication.Model.ListFilms;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vova0199 on 03.11.2018.
 */
public class HomeFragment extends Fragment implements CustomAdapter.OnItemCLickListener {

    private CustomAdapter adapter;
    private ApiService apiService;
    @BindView(R.id.tv_error)
    TextView tvError;
    @BindView(R.id.rv_info)
    RecyclerView mRecyclerView;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefresh;
    private ArrayList<Film> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        init(view);
        loadFilms();
        return view;
    }

    public void loadFilms() {
        apiService.getFilms().enqueue(new Callback<ListFilms>() {
            @Override
            public void onResponse(Call<ListFilms> call, Response<ListFilms> response) {
                if (response.isSuccessful()) {
                    list = (ArrayList<Film>) response.body().getFilms();
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

    private void init(View view) {
        apiService = ApiUtils.getSOService();
        ButterKnife.bind(this, view);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFilms();
                pullToRefresh.setRefreshing(false);
            }
        });
        initAdapter();
    }

    private void initAdapter() {
        adapter = new CustomAdapter(getActivity(), new ArrayList<Film>(0), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
    }

    @Override
    public void onItemClick(Film film) {
        Bundle data = new Bundle();
        data.putString("title", film.getTitle());
        data.putString("description", film.getOverview());
        data.putString("image_path", film.getPosterPath());
        data.putString("rate_average", film.getVoteAverage());
        Fragment fragment = new FilmDetailsFragment();
        fragment.setArguments(data);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
        fragmentTransaction2.addToBackStack(null);
        fragmentTransaction2.hide(HomeFragment.this);
        fragmentTransaction2.add(android.R.id.content, fragment);
        fragmentTransaction2.commit();
    }
}