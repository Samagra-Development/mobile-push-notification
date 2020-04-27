package com.samagra.ancillaryscreens.data.network.model;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

/**
 * The request object that is used while making a Login attempt. This object is required by {@link com.samagra.ancillaryscreens.data.network.BackendCallHelper#performLoginApiCall(LoginRequest)}
 * and contains the username and password fields which are the only 2 <b>required</b> fields by the fusionAuth API.
 *
 * @author Pranav Sharma
 * @see com.samagra.ancillaryscreens.data.network.BackendCallHelper#performLoginApiCall(LoginRequest)
 */
public class LoginRequest {

    private String username;
    private String password;
    private String applicationId;

    public LoginRequest(String username, String password, String applicationId) {
        this.username = username;
        this.password = password;
        this.applicationId = applicationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public JSONObject getLoginRequestJSONObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("loginId", username);
            jsonObject.put("password", password);
            jsonObject.put("applicationId", applicationId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Timber.i("Making Login call with %s", jsonObject.toString());
        return jsonObject;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationId() {
        return this.applicationId;
    }
}
