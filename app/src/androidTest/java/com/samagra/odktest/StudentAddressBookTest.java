package com.samagra.odktest;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.samagra.odktest.ui.HomeScreen.HomeActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.samagra.odktest.EspressoTools.waitFor;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;


public class StudentAddressBookTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);


    @Test
    public void StudentAddressBookTest() throws InterruptedException {

        ViewInteraction linearLayout = onView(allOf(withId(R.id.student_address_book))).check(matches(isDisplayed()));
        linearLayout.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        // if internet not connected (Spinner view is Display or not)
       /* ViewInteraction spinner_grade_spinner = onView(allOf(withId(com.samagra.odktest.R.id.grade_spinner),withText("Class 1"))).check(matches(isDisplayed()));
        ViewInteraction spinner_section_spinner = onView(allOf(withId(com.samagra.odktest.R.id.student_address_book),withText("Section A"))).check(matches(isDisplayed()));

*/
        //Add Student Action Button
        ViewInteraction floatingActionButton_AddStudent = onView(
                allOf(withId(R.id.fab_add_student),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        floatingActionButton_AddStudent.perform(click());

        // if internet not connected (Spinner view is Display or not)

      /*  ViewInteraction spinner_grade_spinner = onView(allOf(withId(com.samagra.odktest.R.id.grade_spinner),withText("Class 1"))).check(matches(isDisplayed()));
        ViewInteraction spinner_section_spinner = onView(allOf(withId(com.samagra.odktest.R.id.student_address_book),withText("Section A"))).check(matches(isDisplayed()));
        ViewInteraction appCompatEditText_studentName_offline = onView(allOf(withId(com.samagra.odktest.R.id.name))).check(matches(isDisplayed()));
        ViewInteraction appCompatEditText_student_FathersName_offline = onView(allOf(withId(com.samagra.odktest.R.id.father_name))).check(matches(isDisplayed()));
        ViewInteraction appCompatEditText_student_contact_number_offline = onView(allOf(withId(com.samagra.odktest.R.id.contact_number))).check(matches(isDisplayed()));
        ViewInteraction appCompatEditText_student_roll_number_offline = onView(allOf(withId(com.samagra.odktest.R.id.admission_number))).check(matches(isDisplayed()));
        ViewInteraction appCompatButton_Addstudent_offline = onView(allOf(withId(com.samagra.odktest.R.id.admission_number))).check(matches(isDisplayed()));
        ViewInteraction appCompatButton_Addstudent = onView(allOf(withId(com.samagra.odktest.R.id.add_student), withText("Add Student"))).check(matches(isDisplayed()));
*/

                //both online and offine case are same (snackbar msg change)
                //Add Student Name
        ViewInteraction appCompatEditText_studentName = onView(allOf(withId(R.id.name))).check(matches(isDisplayed()));
        appCompatEditText_studentName.perform(scrollTo(), replaceText("Raj"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(1000));

        //Add Student Fathers Name
        ViewInteraction appCompatEditText_student_FathersName = onView(allOf(withId(R.id.father_name))).check(matches(isDisplayed()));
        appCompatEditText_student_FathersName.perform(scrollTo(), replaceText("Rajesh"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(1000));


        //Add Student contact_number(wrong)
        ViewInteraction appCompatEditText_student_contact_number_invalid = onView(allOf(withId(R.id.contact_number))).check(matches(isDisplayed()));
        appCompatEditText_student_contact_number_invalid.perform(scrollTo(), replaceText("89551593230"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(1000));


        /*//Add Student roll_number(wrong 10001)
        ViewInteraction appCompatEditText_student_roll_number_invalid = onView(allOf(withId(com.samagra.odktest.R.id.admission_number),
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.ScrollView")),
                                0),
                        7))).check(matches(isDisplayed()));
        appCompatEditText_student_roll_number_invalid.perform(scrollTo(), replaceText("10001"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditText_student_roll_number_invalid.perform(pressImeActionButton());
        onView(isRoot()).perform(waitFor(1000));*/


        //Add Student roll_number
        ViewInteraction appCompatEditText_student_roll_number = onView(allOf(withId(R.id.admission_number),
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.ScrollView")),
                                0),
                        7))).check(matches(isDisplayed()));
        appCompatEditText_student_roll_number.perform(scrollTo(), replaceText("1000"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(1000));
        appCompatEditText_student_roll_number.perform(pressImeActionButton());
        onView(isRoot()).perform(waitFor(1000));


        //Add Student Gender_Male
        ViewInteraction appCompatRadioButton_gender_male = onView(allOf(withId(R.id.gender_male), withText("Male"),
                childAtPosition(
                        allOf(withId(R.id.gender),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8)),
                        1))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatRadioButton_gender_male.perform(scrollTo(), click());

        //Add Student Gender_Female
        ViewInteraction appCompatRadioButton_gender_female = onView(allOf(withId(R.id.gender_female), withText("Female"),
                childAtPosition(
                        allOf(withId(R.id.gender),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        8)),
                        2))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatRadioButton_gender_female.perform(scrollTo(), click());


        //Add Student Category_general
        ViewInteraction appCompatRadioButton_CategoryGeneral = onView(allOf(withId(R.id.category_general), withText("General"),
                childAtPosition(
                        allOf(withId(com.samagra.ancillaryscreens.R.id.category),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        9)),
                        1))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatRadioButton_CategoryGeneral.perform(scrollTo(), click());

        //Add Student Category_Obc
        ViewInteraction appCompatRadioButton_Categoryobc = onView(allOf(withId(R.id.category_OBC), withText("OBC"),
                childAtPosition(
                        allOf(withId(com.samagra.ancillaryscreens.R.id.category),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        9)),
                        2))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatRadioButton_Categoryobc.perform(scrollTo(), click());

        //Add Student Category_SC
        ViewInteraction appCompatRadioButton_CategorySC = onView(allOf(withId(R.id.category_SC), withText("SC"),
                childAtPosition(
                        allOf(withId(com.samagra.ancillaryscreens.R.id.category),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        9)),
                        3))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatRadioButton_CategorySC.perform(scrollTo(), click());

        //Add Student Category_ST
        ViewInteraction appCompatRadioButton_CategorySt = onView(allOf(withId(R.id.category_ST), withText("ST"),
                childAtPosition(
                        allOf(withId(com.samagra.ancillaryscreens.R.id.category),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        9)),
                        4))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatRadioButton_CategorySt.perform(scrollTo(), click());

        //Add Student Cwsn_yes
        ViewInteraction appCompatRadioButton_cwsn_yes = onView(allOf(withId(R.id.cwsn_yes), withText("Yes"),
                childAtPosition(
                        allOf(withId(R.id.is_cwsn),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        10)),
                        1))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatRadioButton_cwsn_yes.perform(scrollTo(), click());

        //Add Student Cwsn_NO
        ViewInteraction appCompatRadioButton_cwsn_no = onView(allOf(withId(R.id.cwsn_no), withText("No"),
                childAtPosition(
                        allOf(withId(R.id.is_cwsn),
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        10)),
                        2))).check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatRadioButton_cwsn_no.perform(scrollTo(), click());

        //Add Student Data
        ViewInteraction appCompatButton_Addstudent = onView(allOf(withId(R.id.add_student), withText("Add Student"),
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                11),
                        0)));

        appCompatButton_Addstudent.perform(click());
        appCompatButton_Addstudent.check(matches(isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));

       /* //Snackbar
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("The mobile number is invalid. Please enter a valid mobile number."))));
        Thread.sleep(1000);*/

        //Add Student contact_number
        ViewInteraction appCompatEditText_student_contact_number = onView(allOf(withId(R.id.contact_number))).check(matches(isDisplayed()));
        appCompatEditText_student_contact_number.perform(scrollTo(), replaceText("8955159323"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(1000));

        //Add Student Data
        ViewInteraction appCompatButton_Addstudent1 = onView(
                allOf(withId(R.id.add_student), withText("Add Student"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        11),
                                0)));
        onView(isRoot()).perform(waitFor(1000));
        appCompatButton_Addstudent1.perform(scrollTo(), click());
        onView(isRoot()).perform(waitFor(1000));

        //Edit Student Data

        //item selection
       /* ViewInteraction appCompatImageView_edit =  onView(withId(R.id.edit))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        Thread.sleep(2000);*/

        onView(withIndex(withId(R.id.edit), 2)).perform(click());


        onView(isRoot()).perform(waitFor(1000));
        //Update Student Name
        ViewInteraction appCompatEditText_updateStudentName = onView(allOf(withId(R.id.name))).check(matches(isDisplayed()));
        appCompatEditText_updateStudentName.perform(scrollTo(), replaceText("Hello"), closeSoftKeyboard());
        onView(isRoot()).perform(waitFor(1000));


        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.add_student), withText("Update"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        11),
                                0)));
        onView(isRoot()).perform(waitFor(1000));
        appCompatButton2.perform(scrollTo(), click());
        onView(isRoot()).perform(waitFor(1000));


        pressBack();

        //Popup Save data buyyon click
        ViewInteraction appCompatButton_Save = onView(
                allOf(withId(R.id.save_students), withText("Save"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                1),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatButton_Save.perform(click());

        onView(isRoot()).perform(waitFor(1000));





        //Popup Add Now Button Click
        ViewInteraction appCompatButton_addnow = onView(
                allOf(withId(R.id.add_students), withText("Add Now"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatButton_addnow.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        //offline snackbar (save click)
        /*onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("Data will be updated once you are connected to the internet."))));
        Thread.sleep(1000);*/


        //Fab Save data
        ViewInteraction floatingActionButton_save = onView(
                allOf(withId(R.id.fab_save),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        floatingActionButton_save.perform(click());

        onView(isRoot()).perform(waitFor(1000));

        //Popup add now button click
        ViewInteraction appCompatButton_addnow1 = onView(
                allOf(withId(R.id.add_students), withText("Add Now"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatButton_addnow1.perform(click());
        onView(isRoot()).perform(waitFor(1000));

        //offline snackbar (save click)
        /*onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(withText(("Data will be updated once you are connected to the internet."))));
       onView(isRoot()).perform(waitFor(1000));*/


        pressBack();

        ViewInteraction linearLayout_studentbook = onView(allOf(withId(R.id.student_address_book))).check(matches(isDisplayed()));
        linearLayout_studentbook.perform(click());
        onView(isRoot()).perform(waitFor(1000));


        //item selection

        onView(withIndex(withId(R.id.is_selected), 2)).perform(click());


        //fab delete item click
        ViewInteraction floatingActionButton2 = onView(
                allOf(withId(R.id.fab_delete),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        floatingActionButton2.perform(click());

        onView(isRoot()).perform(waitFor(1000));

        //Popup delete data button click
        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.delete_students), withText("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        onView(isRoot()).perform(waitFor(1000));
        appCompatButton6.perform(click());

        onView(isRoot()).perform(waitFor(1000));

        pressBack();

        onView(isRoot()).perform(waitFor(1000));

        ViewInteraction linearLayout_studentbook1 = onView(allOf(withId(R.id.student_address_book))).check(matches(isDisplayed()));
        linearLayout_studentbook1.perform(click());
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


    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }


    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }

}


