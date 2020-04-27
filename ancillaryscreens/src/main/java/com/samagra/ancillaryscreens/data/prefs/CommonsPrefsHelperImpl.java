package com.samagra.ancillaryscreens.data.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.samagra.ancillaryscreens.data.network.model.LoginResponse;
import com.samagra.ancillaryscreens.di.ApplicationContext;
import com.samagra.ancillaryscreens.di.PreferenceInfo;
import com.samagra.commons.GeneralKeys;


import javax.inject.Inject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Solid implementation of {@link CommonsPreferenceHelper}, performs the read/write operations on the {@link SharedPreferences}
 * used by the ancillaryscreens. The class is injected to all activities instead of manually creating an object.
 *
 * @author Pranav Sharma
 */
public class CommonsPrefsHelperImpl implements CommonsPreferenceHelper {

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences defaultPreferences;
    Context context;

    @Inject
    public CommonsPrefsHelperImpl(@ApplicationContext Context context, @PreferenceInfo String prefFileName) {
        this.sharedPreferences = context.getSharedPreferences(prefFileName, MODE_PRIVATE);
        this.context = context;
        defaultPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public String getCurrentUserName() {
        return defaultPreferences.getString("user.fullName", "");
    }

    @Override
    public String getCurrentUserId() {
        return defaultPreferences.getString("user.id", "");
    }

    @Override
    public String getPrefByKey(String param) {
        return defaultPreferences.getString(param, "");
    }

    @Override
    public void setCurrentUserLoginFlags() {
        SharedPreferences.Editor editor = defaultPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putBoolean("justLoggedIn", true);

        boolean firstLogIn = sharedPreferences.getBoolean("firstLoginIn", false);
        if (firstLogIn) editor.putBoolean("firstLoginIn", false);
        else editor.putBoolean("firstLoginIn", true);

        boolean firstLogIn2 = sharedPreferences.getBoolean("firstLoginIn2", false);
        if (!firstLogIn2) editor.putBoolean("firstLoginIn2", true);
        else editor.putBoolean("firstLoginIn2", false);

        editor.apply();
    }

    @Override
    public void updateToken(String token) {
        SharedPreferences.Editor editor = defaultPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    @Override
    public void setCurrentUserDetailsFromLogin(LoginResponse response) {
        boolean isProfileComplete = true;
        SharedPreferences.Editor editor = defaultPreferences.edit();
        editor.putString("refreshToken", response.refreshToken.getAsString());
        editor.putString("token", response.token.getAsString());

        if (response.user.has("email"))
            editor.putString("user.email", response.user.get("email").getAsString());
        else {
            editor.putString("user.email", "");
        }

        editor.putString("user.id", response.user.get("id").getAsString());

        if (response.user.has("username"))
            editor.putString("user.username", response.user.get("username").getAsString());
        else editor.putString("user.username", response.getUserName());

        if (response.token != null)
            editor.putString("user.token", response.token.getAsString());

        if (response.user.has("data")) {
            editor.putString("user.data", response.user.get("data").toString());
            JsonObject data = response.user.get("data").getAsJsonObject();

            if (data.has("accountName")) {
                editor.putString("user.accountName", data.get("accountName").getAsString());
            } else {
                editor.putString("user.accountName", "");
                isProfileComplete = false;
            }

            if(data.has("phone")){

            }else{
                isProfileComplete = false;
            }
            editor.putBoolean("isProfileComplete", isProfileComplete);
        }

        editor.apply();
    }

    @Override
    public void setCurrentUserAdditionalDetailsFromLogin(LoginResponse response) {
        SharedPreferences.Editor editor = defaultPreferences.edit();
        if (response.user.has("registrations")) {
            JsonArray registrations = response.user.get("registrations").getAsJsonArray();
            for (int i = 0; i < registrations.size(); i++) {
                if (registrations.get(i).getAsJsonObject().has("applicationId")) {
                    String applicationId = registrations.get(i).getAsJsonObject().get("applicationId").getAsString();
                    if (applicationId.equals("4b49c1c8-f90e-41e9-99ab-16d4af9eb269")) {
                        // This is applicationId for Shiksha Saathi
                        editor.putString("user.role", registrations.get(i).getAsJsonObject().get("roles").getAsJsonArray().get(0).getAsJsonPrimitive().getAsString());
                        editor.putString("user.roleData", registrations.get(i).getAsJsonObject().get("roles").getAsJsonArray().toString());
                    }
                }
            }
        }

        if (response.user.has("fullName"))
            editor.putString("user.fullName", response.user.get("fullName").getAsString());
        else editor.putString("user.fullName", response.user.get("username").getAsString());

        editor.putString("user.id", response.user.get("id").getAsString());

        if (response.user.has("mobilePhone"))
            editor.putString("user.mobilePhone", response.user.get("mobilePhone").getAsString());
        else editor.putString("user.mobilePhone", "");

        JsonObject userData = response.user.get("data").getAsJsonObject();
        if (userData.has("roleData")) {
            if (userData.get("roleData").getAsJsonObject().has("designation"))
                editor.putString("user.designation", response.user.get("data").getAsJsonObject().get("roleData").getAsJsonObject().get("designation").getAsJsonPrimitive().getAsString());
        }
        editor.apply();
    }

    @Override
    public boolean isFirstLogin() {
        return defaultPreferences.getBoolean("firstLoginIn", false);
    }

    @Override
    public boolean isFirstRun() {
        return defaultPreferences.getBoolean(GeneralKeys.KEY_FIRST_RUN, true);
    }

    @Override
    public void updateProfileKeyValuePair(String key, String value) {
        SharedPreferences.Editor editor = defaultPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public String getProfileContentValueForKey(String key) {
        return defaultPreferences.getString(key, "");
    }

    @Override
    public boolean isShowSplash() {
        return defaultPreferences.getBoolean(GeneralKeys.KEY_SHOW_SPLASH, false);
    }

    @Override
    public Long getLastAppVersion() {
        return sharedPreferences.getLong(GeneralKeys.KEY_LAST_VERSION, 0);
    }

    @Override
    public void updateLastAppVersion(long updatedVersion) {
        SharedPreferences.Editor editor = defaultPreferences.edit();
        editor.putLong(GeneralKeys.KEY_LAST_VERSION, updatedVersion);
        editor.apply();
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void updateFirstRunFlag(boolean value) {
        SharedPreferences.Editor editor = defaultPreferences.edit();
        editor.putBoolean(GeneralKeys.KEY_FIRST_RUN, false);
        editor.commit();
    }

    @Override
    public boolean isLoggedIn() {
        return defaultPreferences.getBoolean("isLoggedIn", false);
    }

    @Override
    public int getPreviousVersion() {
        return context.getSharedPreferences("VersionPref", MODE_PRIVATE).getInt("appVersionCode", 0);
    }

    @Override
    public void updateAppVersion(int currentVersion) {
        SharedPreferences.Editor editor = context.getSharedPreferences("VersionPref", MODE_PRIVATE).edit();
        editor.putInt("appVersionCode", currentVersion);
        editor.putBoolean("isAppJustUpdated", true);
        editor.commit();
    }

    @Override
    public String getRefreshToken() {
        return defaultPreferences.getString("refreshToken", "");
    }

}
