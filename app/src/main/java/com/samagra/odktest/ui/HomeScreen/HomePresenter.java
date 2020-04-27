package com.samagra.odktest.ui.HomeScreen;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.samagra.odktest.MyApplication;
import com.samagra.odktest.base.BasePresenter;

import java.util.HashMap;

import javax.inject.Inject;

/**
 * The Presenter class for Home Screen. This class controls interaction between the View and Data.
 * This class <b>must</b> implement the {@link HomeMvpPresenter} and <b>must</b> be a type of {@link BasePresenter}.
 *
 * @author Pranav Sharma
 */
public class HomePresenter<V extends HomeMvpView, I extends HomeMvpInteractor> extends BasePresenter<V, I> implements HomeMvpPresenter<V, I> {

    /**
     * The injected values is provided through {@link com.samagra.odktest.di.modules.ActivityAbstractProviders}
     */
    @Inject
    public HomePresenter(I mvpInteractor) {
        super(mvpInteractor);
    }


    public void start() {

    }

    public void resetProgress() {

    }

    public void startStudentDownloadTasks() {
    }

    public void getCurrentStudentListForForms() {

    }


    // ****************************************************
    // Control of the five buttons.
    // ****************************************************

    public void onAddressBookClicked(View v) {
    }

    public void onSendSMSClicked(View v) {

    }


    public void onTrackSMSClicked(View v) {

    }

    public void onAssessmentClicked(View v) {
    }

    @Override
    public void onHelplineButtonClicked(View v) {
    }


    @Override
    public void setWelcomeText() {
        getMvpView().updateWelcomeText(getMvpInteractor().getUserName());
    }


    @Override
    public void downloadForms(HashMap<String, String> formsToBeDownloaded) {
    }

    @Override
    public void applySettings() {

    }

    @Override
    public boolean isNetworkConnected() {
        if (getMvpView() != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getMvpView()
                    .getActivityContext()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        } else {
            return MyApplication.isOnline;
        }
    }


}
