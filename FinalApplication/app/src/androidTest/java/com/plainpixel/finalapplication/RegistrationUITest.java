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


import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.plainpixel.finalapplication.LoginPage;
import com.plainpixel.finalapplication.R;


public class RegistrationUITest {

    @Rule
    public ActivityTestRule<LoginPage> activityRule = new ActivityTestRule<>(LoginPage.class);


//    @Test
//    public void testEmptyFields() {
//        // Simulate clicking the login button with empty fields
//        Espresso.onView(withId(R.id.login)).perform(ViewActions.click());
//
//        // Verify that error messages are displayed for empty fields
//        Espresso.onView(withId(R.id.email)).check(matches(hasErrorText("Email cannot be empty")));
//        Espresso.onView(withId(R.id.password)).check(matches(hasErrorText("Password cannot be empty")));
//    }

    @Test
    public void testSuccessfulLogin() {
        // Simulate entering valid login data and clicking the login button
        Espresso.onView(withId(R.id.email)).perform(ViewActions.typeText("test@example.com"));
        Espresso.onView(withId(R.id.password)).perform(ViewActions.typeText("password"), ViewActions.closeSoftKeyboard());
        Espresso.onView(withId(R.id.login)).perform(ViewActions.click());

        // You can add more actions/assertions here based on the behavior of your app after successful login
    }
}