/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.samagra.customworkmanager.impl.workers;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import com.samagra.customworkmanager.ListenableWorker;
import com.samagra.customworkmanager.Logger;
import com.samagra.customworkmanager.Worker;
import com.samagra.customworkmanager.WorkerParameters;
import com.samagra.customworkmanager.impl.WorkDatabase;
import com.samagra.customworkmanager.impl.WorkManagerImpl;
import com.samagra.customworkmanager.impl.constraints.WorkConstraintsCallback;
import com.samagra.customworkmanager.impl.constraints.WorkConstraintsTracker;
import com.samagra.customworkmanager.impl.model.WorkSpec;
import com.samagra.customworkmanager.impl.utils.futures.SettableFuture;
import com.samagra.customworkmanager.impl.utils.taskexecutor.TaskExecutor;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.Collections;
import java.util.List;

/**
 * Is an implementation of a {@link Worker} that can delegate to a different {@link Worker}
 * when the constraints are met.
 *
 * @hide
 */
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
public class ConstraintTrackingWorker extends ListenableWorker implements WorkConstraintsCallback {

    private static final String TAG = Logger.tagWithPrefix("ConstraintTrkngWrkr");

    /**
     * The {@code name} of the {@link Worker} to delegate to.
     */
    public static final String ARGUMENT_CLASS_NAME =
            "com.samagra.customworkmanager.impl.workers.ConstraintTrackingWorker.ARGUMENT_CLASS_NAME";

    private WorkerParameters mWorkerParameters;

    // These are package-private to avoid synthetic accessor.
    final Object mLock;
    // Marking this volatile as the delegated workers could switch threads.
    volatile boolean mAreConstraintsUnmet;
    SettableFuture<Result> mFuture;

    @Nullable private ListenableWorker mDelegate;

    public ConstraintTrackingWorker(@NonNull Context appContext,
            @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        mWorkerParameters = workerParams;
        mLock = new Object();
        mAreConstraintsUnmet = false;
        mFuture = SettableFuture.create();
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        getBackgroundExecutor().execute(new Runnable() {
            @Override
            public void run() {
                setupAndRunConstraintTrackingWork();
            }
        });
        return mFuture;
    }

    // Package-private to avoid synthetic accessor.
    void setupAndRunConstraintTrackingWork() {
        String name = getInputData().getString(ARGUMENT_CLASS_NAME);
        if (TextUtils.isEmpty(name)) {
            Logger.get().error(TAG, "No worker to delegate to.");
            setFutureFailed();
            return;
        }

        mDelegate = getWorkerFactory().createWorkerWithDefaultFallback(
                getApplicationContext(),
                name,
                mWorkerParameters);

        if (mDelegate == null) {
            Logger.get().debug(TAG, "No worker to delegate to.");
            setFutureFailed();
            return;
        }

        WorkDatabase workDatabase = getWorkDatabase();

        // We need to know what the real constraints are for the delegate.
        WorkSpec workSpec = workDatabase.workSpecDao().getWorkSpec(getId().toString());
        if (workSpec == null) {
            setFutureFailed();
            return;
        }
        WorkConstraintsTracker workConstraintsTracker =
                new WorkConstraintsTracker(getApplicationContext(), getTaskExecutor(), this);

        // Start tracking
        workConstraintsTracker.replace(Collections.singletonList(workSpec));

        if (workConstraintsTracker.areAllConstraintsMet(getId().toString())) {
            Logger.get().debug(TAG, String.format("Constraints met for delegate %s", name));

            // Wrapping the call to mDelegate#doWork() in a try catch, because
            // changes in constraints can cause the worker to throw RuntimeExceptions, and
            // that should cause a retry.
            try {
                final ListenableFuture<Result> innerFuture = mDelegate.startWork();
                innerFuture.addListener(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (mLock) {
                            if (mAreConstraintsUnmet) {
                                setFutureRetry();
                            } else {
                                mFuture.setFuture(innerFuture);
                            }
                        }
                    }
                }, getBackgroundExecutor());
            } catch (Throwable exception) {
                Logger.get().debug(TAG, String.format(
                        "Delegated worker %s threw exception in startWork.", name),
                        exception);
                synchronized (mLock) {
                    if (mAreConstraintsUnmet) {
                        Logger.get().debug(TAG, "Constraints were unmet, Retrying.");
                        setFutureRetry();
                    } else {
                        setFutureFailed();
                    }
                }
            }
        } else {
            Logger.get().debug(TAG, String.format(
                    "Constraints not met for delegate %s. Requesting retry.", name));
            setFutureRetry();
        }

    }

    // Package-private to avoid synthetic accessor.
    void setFutureFailed() {
        mFuture.set(Result.failure());
    }

    // Package-private to avoid synthetic accessor.
    void setFutureRetry() {
        mFuture.set(Result.retry());
    }

    @Override
    public void onStopped() {
        super.onStopped();
        if (mDelegate != null) {
            // Stop is the method that sets the stopped and cancelled bits and invokes onStopped.
            mDelegate.stop();
        }
    }

    /**
     * @return The instance of {@link WorkDatabase}
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    @VisibleForTesting
    @NonNull
    public WorkDatabase getWorkDatabase() {
        return WorkManagerImpl.getInstance(getApplicationContext()).getWorkDatabase();
    }

    /**
     * @return The instance of {@link TaskExecutor}.
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    @VisibleForTesting
    @NonNull
    public TaskExecutor getTaskExecutor() {
        return WorkManagerImpl.getInstance(getApplicationContext()).getWorkTaskExecutor();
    }

    /**
     * @return The {@link Worker} used for delegated work
     * @hide
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    @VisibleForTesting
    @Nullable
    public ListenableWorker getDelegate() {
        return mDelegate;
    }

    @Override
    public void onAllConstraintsMet(@NonNull List<String> workSpecIds) {
        // WorkConstraintTracker notifies on the main thread. So we don't want to trampoline
        // between the background thread and the main thread in this case.
    }

    @Override
    public void onAllConstraintsNotMet(@NonNull List<String> workSpecIds) {
        // If at any point, constraints are not met mark it so we can retry the work.
        Logger.get().debug(TAG, String.format("Constraints changed for %s", workSpecIds));
        synchronized (mLock) {
            mAreConstraintsUnmet = true;
        }
    }
}