package com.nulp.labs_aplication.app.detail;

import com.nulp.labs_aplication.api.model.Images;
import com.nulp.labs_aplication.api.model.Movie;

/**
 * Created by Vova0199 on 18/11/2018.
 */

public interface DetailContract {

    interface View {

        void showLoading();

        void showContent(Movie movie);

        void showError();

        void onConfigurationSet(Images images);

    }

    interface Presenter {

        void start(int movieId);

    }

}
