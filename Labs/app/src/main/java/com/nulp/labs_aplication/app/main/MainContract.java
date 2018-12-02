package com.nulp.labs_aplication.app.main;

import com.nulp.labs_aplication.api.model.Images;
import com.nulp.labs_aplication.api.model.Movie;

import java.util.List;

/**
 * Created by Vova0199 on 18/11/2018.
 */

public interface MainContract {

    interface View {

        void showLoading(boolean isRefresh);

        void showContent(List<Movie> movies, boolean isRefresh);

        void showError();

        void onConfigurationSet(Images images);

    }

    interface Presenter {

        void start();

        void onPullToRefresh();

        void onScrollToBottom();

    }

}
