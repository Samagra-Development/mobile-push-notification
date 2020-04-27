package com.samagra.ancillaryscreens.screens.splash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.androidnetworking.error.ANError;
import com.samagra.ancillaryscreens.AncillaryScreensDriver;
import com.samagra.ancillaryscreens.R;
import com.samagra.ancillaryscreens.base.BasePresenter;
import com.samagra.ancillaryscreens.data.network.BackendCallHelper;
import com.samagra.ancillaryscreens.screens.login.LoginPresenter;
import com.samagra.commons.Constants;
import com.samagra.commons.ExchangeObject;
import com.samagra.commons.Modules;

import org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * The presenter for the Splash Screen. This class controls the interactions between the View and the data.
 * Must implement {@link com.samagra.ancillaryscreens.screens.splash.SplashContract.Presenter}
 *
 * @author Pranav Sharma
 */
public class SplashPresenter<V extends SplashContract.View, I extends SplashContract.Interactor> extends BasePresenter<V, I> implements SplashContract.Presenter<V, I> {

    private static final boolean EXIT = true;

    @Inject
    public SplashPresenter(I mvpInteractor, BackendCallHelper apiHelper, CompositeDisposable compositeDisposable) {
        super(mvpInteractor, apiHelper, compositeDisposable);
    }


    @Override
    public void startUnzipTask() {

    }

    // Currently this function is not used.
    @Override
    public void startGetFormListCall() {

    }

    /**
     * Request the storage permissions which is necessary for ODK to read write data related to forms
     */
    @Override
    public void requestStoragePermissions() {
        init();
    }

    /**
     * Decides the next screen and moves to the decided screen.
     * This decision is based on the Login status which is managed by the {@link com.samagra.ancillaryscreens.screens.login.LoginActivity}
     * in this module.
     *
     * @see com.samagra.ancillaryscreens.screens.login.LoginActivity
     * @see com.samagra.ancillaryscreens.data.prefs.CommonsPrefsHelperImpl
     */
    @Override
    public void moveToNextScreen() {
        if (getMvpInteractor().isLoggedIn() && !getMvpInteractor().getRefreshToken().equals("")) {
            updateJWT();
            Timber.e("Moving to Home");
            Intent intent = new Intent(Constants.INTENT_LAUNCH_HOME_ACTIVITY);
            ExchangeObject.SignalExchangeObject signalExchangeObject = new ExchangeObject.SignalExchangeObject(Modules.MAIN_APP, Modules.ANCILLARY_SCREENS, intent, true);
            AncillaryScreensDriver.mainApplication.getEventBus().send(signalExchangeObject);
        } else {
            Timber.e("Launching Login");
            AncillaryScreensDriver.launchLoginScreen(getMvpView().getActivityContext());
        }
    }

    /**
     * This function initialises the {@link SplashActivity} by setting up the layout and updating necessary flags in
     * the {@link android.content.SharedPreferences}.
     */
    private void init() {
        getMvpView().showActivityLayout();
        PackageInfo packageInfo = null;
        try {
            packageInfo = getMvpView().getActivityContext().getPackageManager()
                    .getPackageInfo(getMvpView().getActivityContext().getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e, "Unable to get package info");
        }

        boolean firstRun = getMvpInteractor().isFirstRun();
        boolean showSplash = getMvpInteractor().isShowSplash();

        // if you've increased version code, then update the version number and set firstRun to true
        boolean appUpdated = getMvpInteractor().updateVersionNumber(packageInfo);
        if (appUpdated)
            firstRun = true;

        if (firstRun || showSplash)
            getMvpInteractor().updateFirstRunFlag(false);
        getMvpView().showSimpleSplash();
    }




    private void updateJWT() {
        boolean firstRun = getMvpInteractor().isFirstRun();
        if (!firstRun) {
            if (getMvpInteractor().isLoggedIn()) {
                String refreshToken = getMvpInteractor().getRefreshToken();
                String apiKey = getMvpView().getActivityContext().getResources().getString(R.string.fusionauth_api_key);
                getCompositeDisposable().add(getApiHelper()
                        .refreshToken(apiKey, refreshToken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(updatedToken -> {
                            getMvpInteractor().updateToken(updatedToken.getString("token"));
                        }, throwable -> {
                            if (throwable instanceof ANError)
                                Timber.e("ERROR BODY %s ERROR CODE %s, ERROR DETAIL %s", ((ANError) (throwable)).getErrorBody(), ((ANError) (throwable)).getErrorCode(), ((ANError) (throwable)).getErrorDetail());
                        }));
            }
        }
    }
}
