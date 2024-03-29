package com.plainpixel.finalapplication;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class LogInTest {

    @Rule
    public ActivityTestRule<ForgotPassword> activityRule = new ActivityTestRule<>(ForgotPassword.class);

    @Test
    public void testEmptyEmailField() {
        // Click on the reset button without entering email
        Espresso.onView(withId(R.id.resetButton)).perform(ViewActions.click());

        // Check if error message for empty email field is displayed
        Espresso.onView(withId(R.id.email)).check(ViewAssertions.matches(hasErrorText("Email field cannot be empty")));
    }

    @Test
    public void testResetPasswordSuccess() {
        // Enter a valid email address
        Espresso.onView(withId(R.id.email))
                .perform(ViewActions.typeText("example@example.com"), ViewActions.closeSoftKeyboard());

        // Click on the reset button
        Espresso.onView(withId(R.id.resetButton)).perform(ViewActions.click());

        // Check if a success message is displayed
        // (You might want to check if a toast message is displayed or verify if the login page is launched)
        // For simplicity, let's assume you have a toast message with the text "Reset password link has been sent"
        // You can replace it with the actual method you use to display success messages.
        Espresso.onView(ViewMatchers.withText("Reset password link has been sent")).inRoot(new ToastMatcher())
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }


}
