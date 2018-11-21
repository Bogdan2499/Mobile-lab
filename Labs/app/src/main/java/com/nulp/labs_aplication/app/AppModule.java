package com.nulp.labs_aplication.app;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Vova0199 on 18/11/2018.
 */

@Module
public class AppModule {
    private final Application mApplication;

    AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }
}

