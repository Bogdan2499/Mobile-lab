package com.nulp.labs_aplication.app.detail;

import com.nulp.labs_aplication.app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vova0199 on 18/11/2018.
 */

@Module
public class DetailModule {
    private final DetailContract.View DetailView;

    DetailModule(DetailContract.View DetailView) {
        this.DetailView = DetailView;
    }

    @Provides
    @ActivityScope
    DetailContract.View provideDetailView() {
        return DetailView;
    }

}

