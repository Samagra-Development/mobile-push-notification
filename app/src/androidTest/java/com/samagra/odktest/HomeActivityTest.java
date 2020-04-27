package com.samagra.odktest;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import com.samagra.ancillaryscreens.R;
import com.samagra.odktest.ui.HomeScreen.HomeActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


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
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.samagra.odktest.EspressoTools.waitFor;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4ClassRunner.class)

public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);


    @Test
    public void homeActivityTest() throws InterruptedException {

        ViewInteraction linearLayout = onView(allOf(withId(com.samagra.odktest.R.id.student_address_book))).check(matches(isDisplayed()));
        linearLayout.perform(click());
        onView(isRoot()).perform(waitFor(2000));

        pressBack();

        ViewInteraction linearLayout2 = onView(allOf(withId(com.samagra.odktest.R.id.send_sms))).check(matches(isDisplayed()));
        //linearLayout2.perform(click());
       // pressBack();

        ViewInteraction linearLayout3 = onView(allOf(withId(com.samagra.odktest.R.id.track_sms))).check(matches(isDisplayed()));
       // linearLayout3.perform(click());
       // pressBack();

        ViewInteraction linearLayout4 = onView(allOf(withId(com.samagra.odktest.R.id.assessment))).check(matches(isDisplayed()));
       // linearLayout4.perform(click());
       // pressBack();

        ViewInteraction appCompatButton2 = onView(allOf(withId(R.id.helpline_button), withText("Support"))).check(matches(isDisplayed()));
        //appCompatButton2.perform(click());
        //pressBack();

        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("\u200E\u200F\u200E\u200E\u200E\u200E\u200E\u200F\u200E\u200F\u200F\u200F\u200E\u200E\u200E\u200E\u200E\u200F\u200E\u200E\u200F\u200E\u200E\u200E\u200E\u200F\u200F\u200F\u200F\u200F\u200E\u200F\u200F\u200E\u200F\u200F\u200E\u200E\u200E\u200E\u200F\u200F\u200F\u200F\u200F\u200F\u200F\u200E\u200F\u200F\u200F\u200F\u200F\u200E\u200F\u200E\u200E\u200F\u200F\u200E\u200F\u200E\u200E\u200E\u200E\u200E\u200F\u200F\u200F\u200E\u200F\u200E\u200E\u200E\u200E\u200E\u200F\u200F\u200E\u200F\u200F\u200E\u200E\u200F\u200E\u200F\u200E\u200F\u200F\u200F\u200F\u200F\u200E\u200ENavigate up\u200E\u200F\u200E\u200E\u200F\u200E"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.parent),
                                                0)),
                                1),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.title), withText("About Us"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());
        onView(isRoot()).perform(waitFor(2000));
        pressBack();

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("\u200E\u200F\u200E\u200E\u200E\u200E\u200E\u200F\u200E\u200F\u200F\u200F\u200E\u200E\u200E\u200E\u200E\u200F\u200E\u200E\u200F\u200E\u200E\u200E\u200E\u200F\u200F\u200F\u200F\u200F\u200E\u200F\u200F\u200E\u200F\u200F\u200E\u200E\u200E\u200E\u200F\u200F\u200F\u200F\u200F\u200F\u200F\u200E\u200F\u200F\u200F\u200F\u200F\u200E\u200F\u200E\u200E\u200F\u200F\u200E\u200F\u200E\u200E\u200E\u200E\u200E\u200F\u200F\u200F\u200E\u200F\u200E\u200E\u200E\u200E\u200E\u200F\u200F\u200E\u200F\u200F\u200E\u200E\u200F\u200E\u200F\u200E\u200F\u200F\u200F\u200F\u200F\u200E\u200ENavigate up\u200E\u200F\u200E\u200E\u200F\u200E"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.parent),
                                                0)),
                                1),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatImageButton3.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.title), withText("Tutorial"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());
        onView(isRoot()).perform(waitFor(2000));
        /*//Snackbar (offline)
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText((R.string.internet_not_connected))));
        onView(isRoot()).perform(waitFor(1000));*/

        pressBack();

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("\u200E\u200F\u200E\u200E\u200E\u200E\u200E\u200F\u200E\u200F\u200F\u200F\u200E\u200E\u200E\u200E\u200E\u200F\u200E\u200E\u200F\u200E\u200E\u200E\u200E\u200F\u200F\u200F\u200F\u200F\u200E\u200F\u200F\u200E\u200F\u200F\u200E\u200E\u200E\u200E\u200F\u200F\u200F\u200F\u200F\u200F\u200F\u200E\u200F\u200F\u200F\u200F\u200F\u200E\u200F\u200E\u200E\u200F\u200F\u200E\u200F\u200E\u200E\u200E\u200E\u200E\u200F\u200F\u200F\u200E\u200F\u200E\u200E\u200E\u200E\u200E\u200F\u200F\u200E\u200F\u200F\u200E\u200E\u200F\u200E\u200F\u200E\u200F\u200F\u200F\u200F\u200F\u200E\u200ENavigate up\u200E\u200F\u200E\u200E\u200F\u200E"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.parent),
                                                0)),
                                1),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(android.R.id.title), withText("Profile"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatTextView3.perform(click());

        onView(isRoot()).perform(waitFor(1000));

        //Edit Profile(Usename)
        ViewInteraction floatingActionButton = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        floatingActionButton.perform(click());


        //If internet off Edit profile item is view
       /* ViewInteraction appCompatEditText_offline = onView(allOf(withId(R.id.edit_user_account_name))).check(matches(isDisplayed()));
        ViewInteraction appCompatEditText3_offline = onView(allOf(withId(R.id.edit_user_phone))).check(matches(isDisplayed()));
        ViewInteraction appCompatEditText5_offline = onView(allOf(withId(R.id.edit_user_email))).check(matches(isDisplayed()));*/



        //Edit Username
        ViewInteraction appCompatEditText = onView(allOf(withId(R.id.edit_user_account_name))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditText.perform(click());
        appCompatEditText.perform(replaceText("Chakshu"), closeSoftKeyboard());



        //Update Profile(username)
        ViewInteraction floatingActionButton2 = onView(allOf(withId(R.id.fab),childAtPosition(
                allOf(withId(R.id.parent),
                        childAtPosition(
                                withId(android.R.id.content),
                                0)),
                4)));

        floatingActionButton2.perform(click());
        floatingActionButton2.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));

        //Snackbar (offline)
        /*onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("User details failed to update. Please check your internet!"))));
        onView(isRoot()).perform(waitFor(1000));*/


        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("User details updated successfully."))));
        onView(isRoot()).perform(waitFor(1000));


        //Edit Profile (Mobile No.)
        ViewInteraction floatingActionButton3 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        floatingActionButton3.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        //Edit Phone No. (update mobile no)
        ViewInteraction appCompatEditText3 = onView(allOf(withId(R.id.edit_user_phone))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditText3.perform(replaceText("8955159325"), closeSoftKeyboard());


        //Update Profile (Mobile No.)
        ViewInteraction floatingActionButton4 = onView(allOf(withId(R.id.fab),childAtPosition(
                allOf(withId(R.id.parent),
                        childAtPosition(
                                withId(android.R.id.content),
                                0)),
                4)));

        floatingActionButton4.perform(click());
        floatingActionButton4.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(2000));

        //Snackbar (offline)
        /*onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("User details failed to update. Please check your internet!"))));
        onView(isRoot()).perform(waitFor(1000));*/

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("User details updated successfully."))));
        onView(isRoot()).perform(waitFor(1000));



        //Update Profile (Exist Mobile No.)
        ViewInteraction floatingActionButton55 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        floatingActionButton55.perform(click());
        onView(isRoot()).perform(waitFor(1000));


        //Edit Phone No. (Exist Mobile No.)
        ViewInteraction appCompatEditText1 = onView(allOf(withId(R.id.edit_user_phone))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditText1.perform(click());
        appCompatEditText1.perform(replaceText("9805126955"));
        onView(isRoot()).perform(waitFor(1000));

        //Update Profile (Exist Mobile No.)
        ViewInteraction floatingActionButton5 = onView(allOf(withId(R.id.fab),childAtPosition(
                allOf(withId(R.id.parent),
                        childAtPosition(
                                withId(android.R.id.content),
                                0)),
                4)));

        floatingActionButton5.perform(click());
        floatingActionButton5.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(2000));

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("Multiple users with the same phone number found. Please ensure that your contact number is not used by any other account or Contact Admin."))));
        onView(isRoot()).perform(waitFor(2000));


        //Update Profile (11 No.)
        ViewInteraction floatingActionButton11d = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        floatingActionButton11d.perform(click());
        onView(isRoot()).perform(waitFor(1000));


        //Edit PExistMobile (11 digit)
        ViewInteraction appCompatEditTextExistMobile = onView(allOf(withId(R.id.edit_user_phone))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditTextExistMobile.perform(click());
        appCompatEditTextExistMobile.perform(replaceText("89551593230"), closeSoftKeyboard());

        onView(isRoot()).perform(waitFor(1000));
        //Update Profile (11Mobile No.)

        ViewInteraction floatingActionButton11 = onView(allOf(withId(R.id.fab),childAtPosition(
                allOf(withId(R.id.parent),
                        childAtPosition(
                                withId(android.R.id.content),
                                0)),
                4)));

        floatingActionButton11.perform(click());
        floatingActionButton11.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(2000));

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("The mobile number is invalid. Please enter a valid mobile number."))));
        onView(isRoot()).perform(waitFor(1000));


        //Edit PExistMobile (10 digit)
        ViewInteraction appCompatEditText10 = onView(allOf(withId(R.id.edit_user_phone))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditText10.perform(click());
        appCompatEditText10.perform(replaceText("8955159323"), closeSoftKeyboard());

        //Update Profile (10Mobile No.)
        ViewInteraction floatingActionButton10 = onView(allOf(withId(R.id.fab),childAtPosition(
                allOf(withId(R.id.parent),
                        childAtPosition(
                                withId(android.R.id.content),
                                0)),
                4)));

        floatingActionButton10.perform(click());
        floatingActionButton10.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(2000));

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("User details updated successfully."))));
        onView(isRoot()).perform(waitFor(1000));


        //Edit Profile ( Email Address)
        ViewInteraction floatingActionButton6 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        floatingActionButton6.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        //Edit Email (Wrong Email ID)
        ViewInteraction appCompatEditText5 = onView(allOf(withId(R.id.edit_user_email))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditText5.perform(replaceText("chakshugautam@gmail.com"), closeSoftKeyboard());


        //Update Profile (Email ID)
        ViewInteraction floatingActionButton7 = onView(allOf(withId(R.id.fab),childAtPosition(
                allOf(withId(R.id.parent),
                        childAtPosition(
                                withId(android.R.id.content),
                                0)),
                4)));

        floatingActionButton7.perform(click());
        floatingActionButton7.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(2000));

        //Snackbar (offline)
        /*onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("User details failed to update. Please check your internet!"))));
        Thread.sleep(1000);*/

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("User details updated successfully."))));
        onView(isRoot()).perform(waitFor(1000));

        //Edit Profile (Email Address)
        ViewInteraction floatingActionButton8 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        floatingActionButton8.perform(click());
        onView(isRoot()).perform(waitFor(1000));


        //Edit Email
        ViewInteraction appCompatEditText2 = onView(allOf(withId(R.id.edit_user_email))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditText2.perform(click());
        appCompatEditText2.perform(replaceText("chakshu@gmail.com"), closeSoftKeyboard());


        //Update Profile ( Email ID)
        ViewInteraction floatingActionButton9= onView(allOf(withId(R.id.fab),childAtPosition(
                allOf(withId(R.id.parent),
                        childAtPosition(
                                withId(android.R.id.content),
                                0)),
                4)));

        floatingActionButton9.perform(click());
        floatingActionButton9.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(2000));

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("User details updated successfully."))));
        onView(isRoot()).perform(waitFor(2000));

        //Edit Profile (ExistEmail Address)
        ViewInteraction floatingActionButtonExistEmail = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        floatingActionButtonExistEmail.perform(click());
        onView(isRoot()).perform(waitFor(2000));


        //Edit Email
        ViewInteraction appCompatEditTextExistEmail = onView(allOf(withId(R.id.edit_user_email))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditTextExistEmail.perform(click());
        appCompatEditTextExistEmail.perform(replaceText("rishabh@samagragovernance.in"), closeSoftKeyboard());


        //Update Profile ( Email ID)

        ViewInteraction floatingActionButtonExistEmail1 = onView(allOf(withId(R.id.fab),childAtPosition(
                allOf(withId(R.id.parent),
                        childAtPosition(
                                withId(android.R.id.content),
                                0)),
                4)));
        floatingActionButtonExistEmail1.perform(click());
        floatingActionButtonExistEmail1.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("Multiple users with the same email found. Please ensure that your email is not used by any other account or Contact Admin."))));
        onView(isRoot()).perform(waitFor(1000));




        //Update Profile ( Email ID)
        ViewInteraction floatingActionButtonExistEmail12 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        floatingActionButtonExistEmail12.perform(click());
        onView(isRoot()).perform(waitFor(1000));


        //Edit Email
        ViewInteraction appCompatEditTextExistEmail11 = onView(allOf(withId(R.id.edit_user_email))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditTextExistEmail11.perform(click());
        appCompatEditTextExistEmail11.perform(replaceText("chakshu@#gmail.com"), closeSoftKeyboard());

        //Update Profile ( Email ID)
        ViewInteraction floatingActionButtonExistEmail11 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        floatingActionButtonExistEmail11.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        //Commit snackbar bcz snackbar is not define in code snipt

        /*onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText((R.string.invalid_email_address))));

        onView(isRoot()).perform(waitFor(1000));*/

        ViewInteraction floatingActionButtonExistEmail16 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        floatingActionButtonExistEmail16.perform(click());
        onView(isRoot()).perform(waitFor(1000));


        //Edit Email
        ViewInteraction appCompatEditTextExistEmail12 = onView(allOf(withId(R.id.edit_user_email))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditTextExistEmail12.perform(click());
        appCompatEditTextExistEmail12.perform(replaceText("chakshu@gmail.com"), closeSoftKeyboard());

        //Update Profile ( Email ID)
        ViewInteraction floatingActionButton13= onView(allOf(withId(R.id.fab),childAtPosition(
                allOf(withId(R.id.parent),
                        childAtPosition(
                                withId(android.R.id.content),
                                0)),
                4)));

        floatingActionButton13.perform(click());
        floatingActionButton13.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("User details updated successfully."))));
        onView(isRoot()).perform(waitFor(1000));



        //Reset Password (Not matched)
        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.fab_edit_password), withText("Reset Password"),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatButton10.perform(click());
        onView(isRoot()).perform(waitFor(1000));

    //Snackbar (offline)
        /*onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText((R.string.internet_not_connected))));
        onView(isRoot()).perform(waitFor(1000)); */

        ViewInteraction textInputEditText5 = onView(
                allOf(withId(R.id.otp),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        textInputEditText5.perform(replaceText("1000"), closeSoftKeyboard());


        ViewInteraction textInputEditText6 = onView(
                allOf(withId(R.id.new_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        textInputEditText6.perform(replaceText("test123456"), closeSoftKeyboard());

        ViewInteraction textInputEditText7 = onView(
                allOf(withId(R.id.confirm_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        textInputEditText7.perform(replaceText("test123456"), closeSoftKeyboard());

        ViewInteraction textInputEditText8 = onView(
                allOf(withId(R.id.confirm_password), withText("test123456"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        textInputEditText8.perform(pressImeActionButton());


        ViewInteraction appCompatButton5= onView(allOf(withId(R.id.password_submit), withText("Submit"), childAtPosition(
                childAtPosition(
                        withId(R.id.parent_ll),
                        2),
                4)));

        appCompatButton5.perform(click());
        appCompatButton5.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("This OTP is incorrect."))));
        onView(isRoot()).perform(waitFor(1000));




        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.otp),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        textInputEditText.perform(replaceText("9248"), closeSoftKeyboard());


        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.new_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        textInputEditText2.perform(replaceText("test123456"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.confirm_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        textInputEditText3.perform(replaceText("test12345"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.confirm_password), withText("test12345"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        textInputEditText4.perform(pressImeActionButton());



        ViewInteraction appCompatButton4= onView(allOf(withId(R.id.password_submit), withText("Submit"), childAtPosition(
                childAtPosition(
                        withId(R.id.parent_ll),
                        2),
                4)));

        appCompatButton4.perform(click());
        appCompatButton4.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("Passwords didn't match."))));
        onView(isRoot()).perform(waitFor(1000));


        //correct password update // commit bcz otp chane all time
       /* ViewInteraction textInputEditText55 = onView(
                allOf(withId(R.id.otp),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
onView(isRoot()).perform(waitFor(1000));
        textInputEditText55.perform(replaceText("9276"), closeSoftKeyboard());


        ViewInteraction textInputEditText56 = onView(
                allOf(withId(R.id.new_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        textInputEditText56.perform(replaceText("mandi12345"), closeSoftKeyboard());

        ViewInteraction textInputEditText57 = onView(
                allOf(withId(R.id.confirm_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        textInputEditText57.perform(replaceText("mandi12345"), closeSoftKeyboard());

        ViewInteraction textInputEditText58 = onView(
                allOf(withId(R.id.confirm_password), withText("mandi12345"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.google.android.material.textfield.TextInputLayout")),
                                        0),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        textInputEditText58.perform(pressImeActionButton());



        ViewInteraction appCompatButton59= onView(allOf(withId(R.id.password_submit), withText("Submit"), childAtPosition(
                childAtPosition(
                        withId(R.id.parent_ll),
                        2),
                4)));

        appCompatButton59.perform(click());
        appCompatButton59.check(matches(isDisplayed()));
        Thread.sleep(2000);

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("The password has been successfully changed. Redirecting you to profile page"))));
        Thread.sleep(1000);*/




        pressBack();

        //Update Profile (RemoveMobileMobile No.)
        ViewInteraction floatingActionButtonRemoveMobile = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        floatingActionButtonRemoveMobile.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        //Edit RemoveMobile (10 digit)
        ViewInteraction appCompatEditTextRemoveMobile = onView(allOf(withId(R.id.edit_user_phone))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditTextRemoveMobile.perform(click());
        appCompatEditTextRemoveMobile.perform(replaceText(""), closeSoftKeyboard());


        //Update Profile (ExistMobileMobile No.)
        ViewInteraction floatingActionButtonRemoveMobile1 = onView(
                allOf(withId(R.id.fab),
                        childAtPosition(
                                allOf(withId(R.id.parent),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()));
        floatingActionButtonRemoveMobile1.perform(click());
        onView(isRoot()).perform(waitFor(1000));


        //Reset Password (Not matched

        ViewInteraction appCompatButton11= onView(allOf(withId(R.id.fab_edit_password), withText("Reset Password"),childAtPosition(
                allOf(withId(R.id.parent),
                        childAtPosition(
                                withId(android.R.id.content),
                                0)),
                5)));

        appCompatButton11.perform(click());
        appCompatButton11.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));

        //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("Please enter Phone number and save data to change the password."))));
        onView(isRoot()).perform(waitFor(1000));



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
