package com.nulp.labs_aplication.app;

import android.app.Application;

import com.nulp.labs_aplication.R;
import com.nulp.labs_aplication.api.ApiModule;

/**
 * Created by Vova0199 on 18/11/2018.
 */

public class App extends Application {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule(getString(R.string.base_url), getString(R.string.api_key)))
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent(Application application) {
        return ((App) application).getAppComponent();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}

