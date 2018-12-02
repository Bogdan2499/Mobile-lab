package com.nulp.labs_aplication.app.main;

import com.nulp.labs_aplication.app.ActivityScope;
import com.nulp.labs_aplication.app.AppComponent;

import dagger.Component;

/**
 * Created by Vova0199 on 18/11/2018.
 */

@ActivityScope
@Component(
        dependencies = AppComponent.class,
        modules = MainModule.class
)
interface MainComponent {

    void inject (MainActivity mainActivity);

}

