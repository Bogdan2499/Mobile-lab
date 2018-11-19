package com.nulp.labs_aplication.app;

import android.app.Application;

import com.nulp.labs_aplication.api.ApiModule;
import com.nulp.labs_aplication.api.ApiService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Vova0199 on 18/11/2018.
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                ApiModule.class
        }
)
public interface AppComponent {

    Application application();

    ApiService apiService();

    void inject(App app);

}

