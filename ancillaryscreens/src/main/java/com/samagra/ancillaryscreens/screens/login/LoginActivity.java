package com.samagra.ancillaryscreens.screens.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.samagra.ancillaryscreens.R;
import com.samagra.ancillaryscreens.R2;
import com.samagra.ancillaryscreens.base.BaseActivity;
import com.samagra.ancillaryscreens.data.network.model.LoginRequest;
import com.samagra.ancillaryscreens.data.network.model.LoginResponse;
import com.samagra.ancillaryscreens.utils.SnackbarUtils;
import com.samagra.commons.CommonUtilities;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * The View Part for the Login Screen, must implement {@link LoginContract.View}
 *
 * @author Pranav Sharma
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R2.id.login_username)
    public AppCompatEditText editTextUsername;
    @BindView(R2.id.login_password)
    public AppCompatEditText editTextPassword;
    @BindView(R2.id.circularProgressBar)
    public ProgressBar progressBar;
    @BindView(android.R.id.content)
    public FrameLayout content;

    @BindView(R2.id.login_submit)
    public Button submitButton;
    @BindView(R2.id.userLayout)
    public TextInputLayout userLayout;
    @BindView(R2.id.pwdLayout)
    public TextInputLayout pwdLayout;
    @BindView(R2.id.loginParentlayout)
    public ConstraintLayout loginParentLayout;


    private Unbinder unbinder;

    @Inject
    LoginPresenter<LoginContract.View, LoginContract.Interactor> loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        getActivityComponent().inject(this);
        unbinder = ButterKnife.bind(this);
        loginPresenter.onAttach(this);
        if(getIntent().getBooleanExtra("loggedOut", false)){
            SnackbarUtils.showShortSnackbar(loginParentLayout, "You have successfully logged out.");
        }
    }

    /**
     * This function should be called to inform the UI that the Login Task has been completed <b>successfully</b>.
     * The UI update to reflect successful login should be done here.
     *
     * @param loginResponse - The response in the form of {@link LoginResponse} sent by the API.
     */
    @Override
    public void onLoginSuccess(LoginResponse loginResponse) {
        loginPresenter.getMvpInteractor().persistUserData(loginResponse);
        loginPresenter.finishAndMoveToHomeScreen();
    }

    /**
     * This function should be called to inform the UI that the Login Task has been completed <b>unsuccessfully</b>
     * The UI update to reflect unsuccessful login should be done here.
     */
    @Override
    public void onLoginFailed() {
        progressBar.setVisibility(View.GONE);
        SnackbarUtils.showLongSnackbar(content, "Username or Password didn't match. Please try again");
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void finishActivity() {
        finish();
    }

    @OnClick(R2.id.login_submit)
    @Override
    public void performLogin() {
        if (CommonUtilities.isNetworkAvailable(this)) {
            if (validateInputs(editTextUsername, editTextPassword)) {
                String username = Objects.requireNonNull(editTextUsername.getText()).toString().trim();
                String password = Objects.requireNonNull(editTextPassword.getText()).toString().trim();
                progressBar.setVisibility(View.VISIBLE);
                loginPresenter.startAuthenticationTask(new LoginRequest(username, password,"4b49c1c8-f90e-41e9-99ab-16d4af9eb269" ), getActivityContext().getResources().getString(R.string.fusionauth_api_key));
            }else{
                styleDisabledButton();
            }
        } else {
            SnackbarUtils.showLongSnackbar(content, "It seems you are not connected to the Internet. Please switch on your Mobile Data to login.");
        }
    }

    @Override
    public void changePassword() {

    }

    /**
     * Validates the login credentials inputted by the user and returns appropriate messages in case of failed login attempt.
     *
     * @param editTextUsername - The {@link EditText} in which user is supposed to type in the username.
     * @param editTextPassword - The {@link EditText} in which user is supposed to type in the password.
     * @return a boolean indicating the result of validation.
     */
    @Override
    public boolean validateInputs(EditText editTextUsername, EditText editTextPassword) {
        if (TextUtils.isEmpty(editTextUsername.getText())) {
            editTextUsername.setError("Username cannot be empty");
            return false;
        }
        if (TextUtils.getTrimmedLength(editTextUsername.getText()) < 3) {
            editTextUsername.setError("Username has to be 3 or more characters");
            return false;
        }
        if (TextUtils.isEmpty(editTextPassword.getText())) {
            editTextPassword.setError("Password cannot be empty");
            return false;
        }
        if (TextUtils.getTrimmedLength(editTextPassword.getText()) < 8) {
            editTextPassword.setError("Password has to be at least 8 characters");
            return false;
        }
        return true;
    }

    @Override
    public void callHelpline() {

    }


    public boolean validateInputs1(EditText editTextUsername, EditText editTextPassword) {
        if (TextUtils.isEmpty(editTextUsername.getText())) {

            return false;
        }
        if (TextUtils.getTrimmedLength(editTextUsername.getText()) < 3) {
            return false;
        }
        if (TextUtils.isEmpty(editTextPassword.getText())) {
            return false;
        }
        if (TextUtils.getTrimmedLength(editTextPassword.getText()) < 8) {
            return false;
        }
        return true;
    }

    @OnTextChanged(R2.id.login_username)
    public void onUsernameChanged(CharSequence text){
        editTextUsername.setError(null);
        styleLiveButton();
//        if (validateInputs1(editTextUsername, editTextPassword)) {
//
//            styleLiveButton();
//        }else{
//            styleDisabledButton();
//        }
    }

    @OnTextChanged(R2.id.login_password)
    public void onPasswordChanged(CharSequence text){
        editTextPassword.setError(null);
        styleLiveButton();
//        styleLiveButton();
//        if (validateInputs1(editTextUsername, editTextPassword)) {
//            styleLiveButton();
//        }else{
//            styleDisabledButton();
//        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        loginPresenter.onDetach();
    }

    public void styleLiveButton(){
        submitButton.setBackgroundColor(getResources().getColor(R.color.button_colors));
        submitButton.setTextColor(getResources().getColor(R.color.white));
    }

    public void styleDisabledButton(){
        submitButton.setBackgroundColor(getResources().getColor(R.color.disable_button_colors));
        submitButton.setTextColor(getResources().getColor(R.color.white));
    }
}
