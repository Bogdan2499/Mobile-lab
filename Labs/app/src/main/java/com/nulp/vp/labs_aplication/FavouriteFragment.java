package com.nulp.vp.labs_aplication;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nulp.vp.labs_aplication.Adapter.CustomAdapter;
import com.nulp.vp.labs_aplication.DB.DBHelp;
import com.nulp.vp.labs_aplication.Model.Film;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vova0199 on 03.11.2018.
 */
public class FavouriteFragment extends Fragment implements CustomAdapter.OnItemCLickListener{
    private Cursor c;
    private CustomAdapter adapter;
    private ArrayList<Film> films = new ArrayList<>();
    @BindView(R.id.rv_info_fav)
    RecyclerView mRecyclerView;
    @BindView(R.id.pullToRefreshFav)
    SwipeRefreshLayout pullToRefresh;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, null);
        init(view);
        return view;
    }
    private void loadFilms() {
        films.clear();
        String title, description, imageURL, voteAverage;
        DBHelp db = new DBHelp(getActivity());
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

    private void init(View view) {
        ButterKnife.bind(this, view);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFilms();
                pullToRefresh.setRefreshing(false);
            }
        });
        loadFilms();
        adapter = new CustomAdapter(getActivity(), films, this);
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
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.hide(FavouriteFragment.this);
        fragmentTransaction.add(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }
}
