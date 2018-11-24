package com.nulp.labs_aplication.app.detail;

import com.nulp.labs_aplication.app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vova0199 on 18/11/2018.
 */

@Module
class DetailModule {
    private final DetailContract.View mDetailView;

    DetailModule(DetailContract.View DetailView) {
        this.mDetailView = DetailView;
    }

    @Provides
    @ActivityScope
    DetailContract.View provideDetailView() {
        return mDetailView;
    }

}

