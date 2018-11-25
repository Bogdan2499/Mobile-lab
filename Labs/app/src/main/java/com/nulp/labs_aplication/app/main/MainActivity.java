package com.nulp.labs_aplication.app.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nulp.labs_aplication.R;
import com.nulp.labs_aplication.api.model.Images;
import com.nulp.labs_aplication.api.model.Movie;
import com.nulp.labs_aplication.app.App;
import com.nulp.labs_aplication.app.detail.DetailActivity;
import com.nulp.labs_aplication.app.main.view.EndlessScrollListener;
import com.nulp.labs_aplication.app.main.view.MoviesAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements
        MainContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        EndlessScrollListener.ScrollToBottomListener,
        MoviesAdapter.ItemClickListener {

    @Inject
    MainPresenter mPresenter;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mContentView;
    @BindView(R.id.textView)
    View mErrorView;
    @BindView(R.id.progressBar)
    View mLoadingView;

    private MoviesAdapter mMoviesAdapter;
    private EndlessScrollListener mEndlessScrollListener;
    private Images mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupContentView();
        DaggerMainComponent.builder()
                .appComponent(App.getAppComponent(getApplication()))
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    private void setupContentView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mEndlessScrollListener = new EndlessScrollListener(linearLayoutManager, this);
        mContentView.setLayoutManager(linearLayoutManager);
        mContentView.addOnScrollListener(mEndlessScrollListener);
    }

    @Override
    public void onRefresh() {
        mEndlessScrollListener.onRefresh();
        mPresenter.onPullToRefresh();
    }

    @Override
    public void onScrollToBottom() {
        mPresenter.onScrollToBottom();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void showLoading(boolean isRefresh) {
        if (isRefresh) {
            if (!mSwipeRefreshLayout.isRefreshing()) {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        } else {
            mLoadingView.setVisibility(View.VISIBLE);
            mContentView.setVisibility(View.GONE);
            mErrorView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showContent(List<Movie> movies, boolean isRefresh) {
        if (mMoviesAdapter == null) {
            mMoviesAdapter = new MoviesAdapter(movies, this, mImages, this);
            mContentView.setAdapter(mMoviesAdapter);
        } else {
            if (isRefresh) {
                mMoviesAdapter.clear();
            }
            mMoviesAdapter.addAll(movies);
            mMoviesAdapter.notifyDataSetChanged();
        }

        // Delay SwipeRefreshLayout animation by 1.5 seconds
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        }, 1500);

        mLoadingView.setVisibility(View.GONE);
        mContentView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        mSwipeRefreshLayout.setRefreshing(false);
        mLoadingView.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConfigurationSet(Images images) {
        this.mImages = images;

        if (mMoviesAdapter != null) {
            mMoviesAdapter.setmImages(images);
        }
    }

    @Override
    public void onItemClick(int movieId, String movieTitle) {
        startActivity(DetailActivity.onItemClick(this, movieId, movieTitle));
    }

    @OnClick(R.id.textView)
    void onClickErrorView() {
        mPresenter.start();
    }

}
