package com.samagra.odktest.listeners;

public interface UserUpdatedListener {
    void onSuccess();
    void onFailure(Exception exception);
}
