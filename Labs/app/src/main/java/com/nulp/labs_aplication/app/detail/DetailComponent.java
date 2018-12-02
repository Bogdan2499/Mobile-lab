package com.nulp.labs_aplication.app.detail;

import com.nulp.labs_aplication.app.ActivityScope;
import com.nulp.labs_aplication.app.AppComponent;

import dagger.Component;

/**
 * Created by Vova0199 on 18/11/2018.
 */

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = DetailModule.class
)
interface DetailComponent {

    void inject(DetailActivity DetailActivity);

}

