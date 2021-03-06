package com.nulp.labs_aplication.app.main;

import com.nulp.labs_aplication.app.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vova0199 on 18/11/2018.
 */

@Module
class MainModule {
    private final MainContract.View mMainView;

    MainModule(MainContract.View mainView) {
        this.mMainView = mainView;
    }

    @Provides
    @ActivityScope
    MainContract.View provideMainView() {
        return mMainView;
    }

}

