package com.plainpixel.finalapplication;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.plainpixel.finalapplication.RegisterPage;
import com.plainpixel.finalapplication.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;


public class SignupTest {


    @Rule
    public ActivityTestRule<RegisterPage> activityRule = new ActivityTestRule<>(RegisterPage.class);

    @Test
    public void passwordValidation() {
        // Type name
        Espresso.onView(ViewMatchers.withId(R.id.name))
                .perform(ViewActions.typeText("John Doe"), ViewActions.closeSoftKeyboard());

        // Type contact number
        Espresso.onView(ViewMatchers.withId(R.id.contact_number))
                .perform(ViewActions.typeText("1234567890"), ViewActions.closeSoftKeyboard());

        // Type email
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("example@example.com"), ViewActions.closeSoftKeyboard());

        // Type short password
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("short"), ViewActions.closeSoftKeyboard());

        // Click on the sign-up button
        Espresso.onView(withId(R.id.sign_up)).perform(ViewActions.click());

        // Check if error message for password length is displayed
        Espresso.onView(withId(R.id.password))
                .check(matches(hasErrorText("At least 8 Characters required")));
    }

    @Test
    public void mobileNumberValidation() {
        // Type name
        Espresso.onView(ViewMatchers.withId(R.id.name))
                .perform(ViewActions.typeText("John Doe"), ViewActions.closeSoftKeyboard());

        // Type invalid contact number (less than 10 digits)
        Espresso.onView(ViewMatchers.withId(R.id.contact_number))
                .perform(ViewActions.typeText("12345"), ViewActions.closeSoftKeyboard());

        // Type email
        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText("example@example.com"), ViewActions.closeSoftKeyboard());

        // Type valid password
        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText("validpassword"), ViewActions.closeSoftKeyboard());

        // Click on the sign-up button
        Espresso.onView(withId(R.id.sign_up)).perform(ViewActions.click());

        // Check if error message for invalid mobile number is displayed
        Espresso.onView(withId(R.id.contact_number))
                .check(matches(hasErrorText("Invalid length of contact number")));
    }
}
