package com.samagra.odktest;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.samagra.ancillaryscreens.R;
import com.samagra.ancillaryscreens.screens.login.LoginActivity;
import com.samagra.ancillaryscreens.screens.passReset.ChangePasswordActivity;
import com.samagra.ancillaryscreens.screens.splash.SplashActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
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
import static com.samagra.odktest.EspressoTools.waitFor;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
@RunWith(AndroidJUnit4ClassRunner.class)

public class ForgotPasswordTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);


    @Test
    public void forgotPasswordTest() throws InterruptedException {


        //Username view
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.login_username))).check(matches(isDisplayed()));
        appCompatEditText.perform(replaceText("110"), closeSoftKeyboard());
        appCompatEditText.perform(closeSoftKeyboard());

        //view.setId(View.generateViewId());

        //Password View
        ViewInteraction appCompatEditText1 = onView(allOf(withId(R.id.login_password))).check(matches(isDisplayed()));
        appCompatEditText1.perform(replaceText("mandi1234"), closeSoftKeyboard());
        appCompatEditText1.perform(closeSoftKeyboard());

        //perform ime click
        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.login_password), withText("mandi1234"))).check(matches(isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        //Perform submit action
        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.login_submit), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                3),
                        isDisplayed()));
        appCompatButton3.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        /*//Snackbar (offline)
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText((R.string.internet_not_connected))));
        onView(isRoot()).perform(waitFor(1000));*/



        ViewInteraction appCompatTextView = onView(allOf(withId(R.id.forgot_password),withText("Forgot Password?"))).check(matches(isDisplayed()));
        appCompatTextView.perform(closeSoftKeyboard());
        appCompatTextView.perform(click());

                /*//Snackbar (offline)
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText((R.string.internet_not_connected))));
        onView(isRoot()).perform(waitFor(1000));*/


        ViewInteraction appCompatEditText3 = onView(allOf(withId(R.id.user_phone))).check(matches(isDisplayed()));
        appCompatEditText3.perform(replaceText("8955159323"), closeSoftKeyboard());



        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.phone_submit), withText("Send OTP"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.parent_ll),
                                        2),
                                1),
                        isDisplayed()));
        appCompatButton.perform(click());


        ViewInteraction textInputEditText = onView(allOf(withId(R.id.otp))).check(matches(isDisplayed()));
        textInputEditText.perform(replaceText("0926"), closeSoftKeyboard());


        ViewInteraction textInputEditText2 = onView(allOf(withId(R.id.new_password))).check(matches(isDisplayed()));
        textInputEditText2.perform(replaceText("test12345"), closeSoftKeyboard());


        ViewInteraction textInputEditText3 = onView(allOf(withId(R.id.confirm_password))).check(matches(isDisplayed()));
        textInputEditText3.perform(replaceText("test12345"), closeSoftKeyboard());



        ViewInteraction textInputEditText4 = onView(allOf(withId(R.id.confirm_password),withText("test12345"))).check(matches(isDisplayed()));
        textInputEditText4.perform(pressImeActionButton());


       /* ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.password_submit),withText("Resend OTP"))).check(matches(isDisplayed()));
        appCompatButton2.perform(closeSoftKeyboard());
        appCompatButton2.perform(click());*/


        ViewInteraction appCompatButton4 = onView(allOf(withId(R.id.password_submit),withText("Submit"))).check(matches(isDisplayed()));
        appCompatButton4.perform(closeSoftKeyboard());
        appCompatButton4.perform(click());
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
