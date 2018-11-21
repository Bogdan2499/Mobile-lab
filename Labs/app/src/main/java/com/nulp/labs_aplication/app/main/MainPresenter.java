package com.nulp.labs_aplication.app.main;

import android.annotation.SuppressLint;
import android.support.annotation.VisibleForTesting;

import com.nulp.labs_aplication.api.ApiService;
import com.nulp.labs_aplication.api.model.Configuration;
import com.nulp.labs_aplication.api.model.Movies;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vova0199 on 18/11/2018.
 */

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mView;
    private ApiService mApiService;

    private int mPage = 1;

    @Inject
    MainPresenter(MainContract.View mView, ApiService apiService) {
        this.mView = mView;
        this.mApiService = apiService;
    }

    @Override
    public void start() {
        mView.showLoading(false);
        getMovies(true);
        getConfiguration();
    }

    private void getConfiguration() {
        Call<Configuration> call = mApiService.getConfiguration();
        call.enqueue(new Callback<Configuration>() {
            @Override
            public void onResponse(Call<Configuration> call, Response<Configuration> response) {
                if (response.isSuccessful()) {
                    mView.onConfigurationSet(response.body().images);
                }
            }

            @Override
            public void onFailure(Call<Configuration> call, Throwable t) {
            }
        });
    }

    @Override
    public void onPullToRefresh() {
        mPage = 1; // reset
        mView.showLoading(true);
        getMovies(true);
    }

    @Override
    public void onScrollToBottom() {
        mPage++;
        mView.showLoading(true);
        getMovies(false);
    }

    private void getMovies(final boolean isRefresh) {
        Call<Movies> call = mApiService.getMovies(getReleaseDate(),
                ApiService.SortBy.RELEASE_DATE_DESCENDING, mPage);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                if (response.isSuccessful()) {
                    mView.showContent(response.body().movies, isRefresh);
                } else {
                    mView.showError();
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                mView.showError();
            }
        });
    }

    @VisibleForTesting
    public String getReleaseDate() {
        Calendar cal = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        return format1.format(cal.getTime());
    }

    @Override
    public void finish() {

    }

}
