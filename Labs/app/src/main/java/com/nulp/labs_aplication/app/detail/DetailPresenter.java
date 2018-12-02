package com.nulp.labs_aplication.app.detail;

import android.support.annotation.NonNull;

import com.nulp.labs_aplication.api.ApiService;
import com.nulp.labs_aplication.api.model.Configuration;
import com.nulp.labs_aplication.api.model.Images;
import com.nulp.labs_aplication.api.model.Movie;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vova0199 on 18/11/2018.
 */

public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View mView;
    private ApiService mApiService;

    private Images mImages;

    @Inject
    DetailPresenter(DetailContract.View view, ApiService apiService) {
        this.mView = view;
        this.mApiService = apiService;
    }

    @Override
    public void start(int movieId) {
        mView.showLoading();

        if (mImages == null) {
            getConfiguration(movieId);
        } else {
            mView.onConfigurationSet(mImages);
            getMovie(movieId);
        }
    }

    private void getConfiguration(final int movieId) {
        Call<Configuration> call = mApiService.getConfiguration();
        call.enqueue(new Callback<Configuration>() {
            @Override
            public void onResponse(@NonNull Call<Configuration> call, @NonNull Response<Configuration> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    mImages = response.body().images;
                    mView.onConfigurationSet(mImages);
                    getMovie(movieId);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Configuration> call, @NonNull Throwable t) {
            }
        });
    }

    private void getMovie(int movieId) {
        Call<Movie> call = mApiService.getMovie(movieId);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.isSuccessful()) {
                    mView.showContent(response.body());
                } else {
                    mView.showError();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {
                mView.showError();
            }
        });
    }

}
