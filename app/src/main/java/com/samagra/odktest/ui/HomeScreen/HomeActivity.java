package com.samagra.odktest.ui.HomeScreen;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.widget.Toolbar;
import androidx.test.espresso.IdlingResource;

import com.androidnetworking.AndroidNetworking;
import com.google.android.material.snackbar.Snackbar;
import com.samagra.ancillaryscreens.AncillaryScreensDriver;
import com.samagra.commons.Constants;
import com.samagra.commons.CustomEvents;
import com.samagra.commons.ExchangeObject;
import com.samagra.commons.InternetMonitor;
import com.samagra.commons.InternetStatus;
import com.samagra.commons.MainApplication;
import com.samagra.commons.Modules;
import com.samagra.commons.SimpleIdlingResource;
import com.samagra.commons.TaskScheduler.Manager;
import com.samagra.notification_module.AppNotificationUtils;
import com.samagra.notification_module.NotificationRenderingActivity;
import com.samagra.odktest.R;
import com.samagra.odktest.UtilityFunctions;
import com.samagra.odktest.base.BaseActivity;
import com.tingyik90.snackprogressbar.SnackProgressBar;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


/**
 * View part of the Home Screen. This class only handles the UI operations, all the business logic is simply
 * abstracted from this Activity. It <b>must</b> implement the {@link HomeMvpView} and extend the {@link BaseActivity}
 *
 * @author Pranav Sharma
 */
public class HomeActivity extends BaseActivity implements HomeMvpView, View.OnClickListener {

    @BindView(R.id.welcome_text)
    public TextView welcomeTextView;
    @BindView(R.id.helpline_button)
    public Button helplineButton;
    @BindView(R.id.student_address_book)
    public LinearLayout studentAddressBook;
    @BindView(R.id.send_sms)
    public LinearLayout sendSMS;
    @BindView(R.id.track_sms)
    public LinearLayout trackSMS;
    @BindView(R.id.assessment)
    public LinearLayout assessment;
    @BindView(R.id.parent)
    public RelativeLayout parent;
    @BindView(R.id.btn_notification)
    public Button btn_notification;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    Button cancelDialogButton, changePassButton;

    private PopupMenu popupMenu;
    private Disposable logoutListener = null;
    private Disposable internetListener = null;
    private Snackbar progressSnackbar = null;

    private Unbinder unbinder;

    private SnackProgressBar snackProgressBar;

    @Inject
    HomePresenter<HomeMvpView, HomeMvpInteractor> homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getIdlingResource();
        InternetMonitor.startMonitoringInternet();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getActivityComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        homePresenter.onAttach(this);
        setupToolbar();
        setupListeners();
        checkIntent();
        homePresenter.setWelcomeText();
        homePresenter.applySettings();
        Manager.enqueueAllIncompleteTasks(this);
        initializeInternetListener();
        setupProgressBar();
        homePresenter.start();
        mIdlingResource.setIdleState(true);
        AncillaryScreensDriver.updateFirebaseToken(this);
        //TODO: Uncomment the next line.
        // testSampleNotification();

        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testSampleNotification();
            }
        });

    }

    private void setupProgressBar() {

        snackProgressBar = new SnackProgressBar(SnackProgressBar.TYPE_CIRCULAR, getResources().getString(R.string.loading_data_home_screen))
                .setShowProgressPercentage(true)
                .setIsIndeterminate(false)
                .setAllowUserInput(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        customizeToolbar();
        showProgressSnackbar();
        homePresenter.resetProgress();
        homePresenter.startStudentDownloadTasks();
        homePresenter.getCurrentStudentListForForms();
    }

    private void setupListeners() {
        helplineButton.setOnClickListener(this);
        studentAddressBook.setOnClickListener(this);
        sendSMS.setOnClickListener(this);
        trackSMS.setOnClickListener(this);
        assessment.setOnClickListener(this);
    }

    private void checkIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("ShowSnackbar", false)) {
            if (homePresenter.isNetworkConnected())
                showSnackbar(getString(R.string.on_internet_saving_complete), Snackbar.LENGTH_SHORT);
            else
                showSnackbar(getString(R.string.no_internet_saving_complete), Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.helpline_button:
                homePresenter.onHelplineButtonClicked(v);
                break;
            case R.id.student_address_book:
                homePresenter.onAddressBookClicked(v);
                break;
            case R.id.send_sms:
                homePresenter.onSendSMSClicked(v);
                break;
            case R.id.track_sms:
                homePresenter.onTrackSMSClicked(v);
                break;
            case R.id.assessment:
                homePresenter.onAssessmentClicked(v);
                break;
        }
    }

    @Override
    public void updateWelcomeText(String text) {
        welcomeTextView.setText(text);
    }

    @Override
    public void showLoading(String message) {
        hideLoading();
        if (progressSnackbar == null) {
            progressSnackbar = UtilityFunctions.getSnackbarWithProgressIndicator(findViewById(android.R.id.content), getApplicationContext(), message);
        }
        progressSnackbar.setText(message);
        progressSnackbar.show();
    }

    @Override
    public void hideLoading() {
        if (progressSnackbar != null && progressSnackbar.isShownOrQueued())
            progressSnackbar.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (logoutListener != null && !logoutListener.isDisposed()) {
            AndroidNetworking.cancel(Constants.LOGOUT_CALLS);
            logoutListener.dispose();
        }
        homePresenter.onDetach();
        unbinder.unbind();
    }


    @Override
    public void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    public void customizeToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(this::initAndShowPopupMenu);
    }

    /**
     * Giving Control of the UI to XML file for better customization and easier changes
     */
    private void initAndShowPopupMenu(View v) {

        if (popupMenu == null) {
            popupMenu = new PopupMenu(HomeActivity.this, v);
            popupMenu.getMenuInflater().inflate(R.menu.home_screen_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.about_us:
                        break;
                    case R.id.tutorial_video:
                      break;
                    case R.id.profile:
                        break;
                    case R.id.logout:
                        if (homePresenter.isNetworkConnected()) {
                            if (logoutListener == null)
                                initializeLogoutListener();
                            AncillaryScreensDriver.performLogout(this);
                        } else {
                            showSnackbar(this.getResources().getString(R.string.offline_text_1), 3000);
                        }
                        break;

                    case R.id.change_lang:
                        break;
                }
                return true;
            });
        }
        popupMenu.show();
    }



    /**
     * This function subsribe to the {@link com.samagra.commons.RxBus} to listen for the Logout related events
     * and update the UI accordingly. The events being subscribed to are {@link com.samagra.commons.CustomEvents#LOGOUT_COMPLETED}
     * and {@link com.samagra.commons.CustomEvents#LOGOUT_INITIATED}
     *
     * @see com.samagra.commons.CustomEvents
     */
    @Override
    public void initializeLogoutListener() {
        logoutListener = ((MainApplication) (getApplicationContext()))
                .getEventBus()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    Timber.i("Received event Logout");
                    if (o instanceof ExchangeObject.EventExchangeObject) {
                        ExchangeObject.EventExchangeObject eventExchangeObject = (ExchangeObject.EventExchangeObject) o;
                        if (eventExchangeObject.to == Modules.MAIN_APP && eventExchangeObject.from == Modules.ANCILLARY_SCREENS) {
                            if (eventExchangeObject.customEvents == CustomEvents.LOGOUT_COMPLETED) {
                                hideLoading();
                                logoutListener.dispose();
                            } else if (eventExchangeObject.customEvents == CustomEvents.LOGOUT_INITIATED) {
                                showLoading(this.getResources().getString(R.string.logging_out));
                            }
                        }
                    }
                    if(o instanceof ExchangeObject.DataExchangeObject){
                        if(!((InternetStatus) ((ExchangeObject.DataExchangeObject) o).data).isCurrentStatus()){
                            showRetryProgressOnNoInternet();
                        }
                    }
                }, Timber::e);
    }

    public void initializeInternetListener(){
        internetListener = ((MainApplication) (getApplicationContext()))
                .getEventBus()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    Timber.i("Received event");
                    if(o instanceof ExchangeObject.DataExchangeObject){
                        if(!((InternetStatus) ((ExchangeObject.DataExchangeObject) o).data).isCurrentStatus()){
                            showRetryProgressOnNoInternet();
                        }
                    }
                }, Timber::e);
    }

    @Override
    public void showRetryProgressOnNoInternet() {
        SnackProgressBar retrySnackbar = new SnackProgressBar(
                SnackProgressBar.TYPE_NORMAL,
                getString(R.string.no_internet_retry)).setAction(getString(R.string.retry), new SnackProgressBar.OnActionClickListener() {
            @Override
            public void onActionClick() {
                homePresenter.resetProgress();
                homePresenter.start();
                homePresenter.startStudentDownloadTasks();
                homePresenter.getCurrentStudentListForForms();
                showProgressSnackbar();
            }
        }).setAllowUserInput(true);
    }

    @Override
    public void showFormsStillDownloading() {
        showSnackbar(this.getResources().getString(R.string.forms_downloading), Snackbar.LENGTH_SHORT);
    }

    //TODO: Buggy:- Fix this.
    public void testSampleNotification() {
        Intent notifyIntent = new Intent(getActivityContext(), NotificationRenderingActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        notifyIntent.putExtra(NotificationRenderingActivity.NOTIFICATION_TITLE, getActivityContext().getResources().getString(R.string.upload_results));
        notifyIntent.putExtra(NotificationRenderingActivity.NOTIFICATION_MESSAGE, "Test message");

        PendingIntent pendingNotify = PendingIntent.getActivity(getActivityContext(), 97,
                notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AppNotificationUtils.showNotification(getActivityContext(), pendingNotify, 123456, "Notification Title", "Test Notification");
    }

    @Override
    public void showChangePasswordDialog() {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.change_pass_profile_dialog);
        dialog.show();
        Window window = dialog.getWindow();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (int) (displayMetrics.heightPixels * .30);
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setLayout(width, height);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        changePassButton = dialog.findViewById(R.id.change_pass_button);
        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }


    @Override
    public void setSnackbarProgress(int progress){
    }

    @Override
    public void showProgressSnackbar(){
    }

    @Override
    public void hideProgressSnackbar(){

    }

    @Override
    public void setSnackbarProgressMessage(String message){
    }

}
