package com.samagra.odktest;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.samagra.commons.Constants;

import java.util.HashMap;

import io.reactivex.annotations.NonNull;

/**
 * This class contains Utility function that can be accessed anywhere throughout the module 'app'.
 * All the functions in this class will be public and static.
 *
 * @author Pranav Sharma
 */
public class UtilityFunctions {


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * This method provides a snackbar with an indeterminate circular laoding spinner. While using it make sure that
     * multiple objects of snackbar and not created since this method will always return a new Snackbar.
     *
     * @param container - The parent root container for the snackbar (Usually the view with id android.R.id.content
     * @param context   - The current activity context
     * @param message   - The String message that needs to be displayed in the snackbar
     */
    public static Snackbar getSnackbarWithProgressIndicator(@NonNull View container, @NonNull Context context, String message) {
        Snackbar bar = Snackbar.make(container, message, Snackbar.LENGTH_INDEFINITE);
        ViewGroup contentLay = (ViewGroup) bar.getView().findViewById(com.google.android.material.R.id.snackbar_text).getParent();
        ProgressBar item = new ProgressBar(context);
        item.setScaleY(0.8f);
        item.setScaleX(0.8f);
        item.setInterpolator(new AccelerateInterpolator());
        item.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        contentLay.addView(item);
        return bar;
    }
}
