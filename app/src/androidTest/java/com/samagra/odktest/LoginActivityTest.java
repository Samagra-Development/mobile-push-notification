package com.samagra.odktest;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.samagra.ancillaryscreens.R;
import com.samagra.ancillaryscreens.screens.login.LoginActivity;
import com.samagra.ancillaryscreens.screens.login.LoginContract;
import com.samagra.ancillaryscreens.screens.login.LoginPresenter;
import com.samagra.ancillaryscreens.screens.splash.SplashActivity;
import com.samagra.odktest.ui.HomeScreen.HomeActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.androidnetworking.AndroidNetworking.initialize;
import static com.samagra.odktest.EspressoTools.waitFor;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4ClassRunner.class)

public class LoginActivityTest{

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE");

    @Test
    public void loginActivityTest()  throws InterruptedException  {

      /*try {
            setMobileData(InstrumentationRegistry.getInstrumentation().getTargetContext(), false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }*/

         //TestButler.setGsmState(false);
       // TestButler.setWifiState(false);


        //Username view
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.login_username))).check(matches(isDisplayed()));
        appCompatEditText.perform(replaceText("110"), closeSoftKeyboard());
        appCompatEditText.perform(closeSoftKeyboard());

        //Password View
        ViewInteraction appCompatEditText1 = onView(allOf(withId(R.id.login_password))).check(matches(isDisplayed()));
        appCompatEditText1.perform(replaceText("mandi1234"), closeSoftKeyboard());
        appCompatEditText1.perform(closeSoftKeyboard());

        //perform ime click
        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.login_password), withText("mandi1234"))).check(matches(isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());


        ViewInteraction appCompatButton3 = onView(allOf(withId(R.id.login_submit), withText("Submit"),childAtPosition(
                childAtPosition(
                        withClassName(is("android.widget.LinearLayout")),
                        2),
                3)));
        appCompatButton3.perform(click());
        appCompatButton3.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(2000));



        /*//Snackbar (offline)
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText((R.string.internet_not_connected))));
        onView(isRoot()).perform(waitFor(1000));*/

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText((R.string.incorrect_credentials))));
        onView(isRoot()).perform(waitFor(1000));


        //Username view
        ViewInteraction appCompatEditText22 = onView(allOf(withId(R.id.login_username))).check(matches(isDisplayed()));
        appCompatEditText22.perform(replaceText("110"), closeSoftKeyboard());
        appCompatEditText22.perform(closeSoftKeyboard());


        //Password View
        ViewInteraction appCompatEditText23 = onView(allOf(withId(R.id.login_password))).check(matches(isDisplayed()));
        appCompatEditText23.perform(replaceText("mandi12345"), closeSoftKeyboard());
        appCompatEditText23.perform(closeSoftKeyboard());

        //perform ime click
        ViewInteraction appCompatEditText24 = onView(allOf(withId(R.id.login_password), withText("mandi12345"))).check(matches(isDisplayed()));
        appCompatEditText24.perform(pressImeActionButton());

        ViewInteraction appCompatButton31 = onView(allOf(withId(R.id.login_submit), withText("Submit"),childAtPosition(
                childAtPosition(
                        withClassName(is("android.widget.LinearLayout")),
                        2),
                3)));
        appCompatButton31.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatButton31.perform(click());


        /*//Snackbar (offline)
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText((R.string.internet_not_connected))));
        onView(isRoot()).perform(waitFor(1000));*/

        ViewInteraction linearLayout = onView(allOf(withId(com.samagra.odktest.R.id.assessment))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(500));
        linearLayout.perform(click());


        /*ViewInteraction appCompatButton4 = onView(allOf(withId(com.samagra.odktest.R.id.next_button), withText("Next"))).check(matches(isDisplayed()));
        Thread.sleep(2000);
        appCompatButton4.perform(click());*/

       // pressBack();


    }


    protected  void setMobileData(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
        iConnectivityManagerField.setAccessible(true);
        final Object iConnectivityManger = iConnectivityManagerField.get(conman);
        final Class iConnectivityManagerClass = Class.forName(iConnectivityManger.getClass().getName());
        final Method setMobileDataEnableMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnable", Boolean.TYPE);
        setMobileDataEnableMethod.setAccessible(true);
        setMobileDataEnableMethod.invoke(iConnectivityManger, enabled);
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
