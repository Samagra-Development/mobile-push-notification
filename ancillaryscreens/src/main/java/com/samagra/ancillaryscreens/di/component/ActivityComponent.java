package com.samagra.ancillaryscreens.di.component;

import com.samagra.ancillaryscreens.di.PerActivity;
import com.samagra.ancillaryscreens.di.modules.CommonsActivityAbstractProviders;
import com.samagra.ancillaryscreens.di.modules.CommonsActivityModule;
import com.samagra.ancillaryscreens.screens.login.LoginActivity;
import com.samagra.ancillaryscreens.screens.splash.SplashActivity;

import dagger.Component;

/**
 * A {@link Component} annotated interface defines connection between provider of objects ({@link dagger.Module})
 * and the objects which express a dependency. It is implemented internally by Dagger at build time.
 * The modules mentioned in {@link Component} are the classes that are required to inject the activities mentioned
 * in this interface methods.
 *
 * @author Pranav Sharma
 */
@PerActivity
@Component(modules = {CommonsActivityModule.class, CommonsActivityAbstractProviders.class})
public interface ActivityComponent {

    void inject(LoginActivity loginActivity);

    void inject(SplashActivity splashActivity);
}
