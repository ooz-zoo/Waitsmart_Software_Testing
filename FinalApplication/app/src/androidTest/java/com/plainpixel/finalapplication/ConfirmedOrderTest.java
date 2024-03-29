package com.plainpixel.finalapplication;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Rule;
import org.junit.Test;

public class ConfirmedOrderTest {

    @Rule
    public ActivityScenarioRule<Confirmed_order> activityScenarioRule =
            new ActivityScenarioRule<>(Confirmed_order.class);

    @Test
    public void testUsernameDisplay() {
        // Verify that the username TextView is displayed
        Espresso.onView(withId(R.id.username)).check(matches(isDisplayed()));
    }

    @Test
    public void testDateDisplay() {
        // Verify that the day, month, and year TextViews are displayed
        Espresso.onView(withId(R.id.day)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.month)).check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.year)).check(matches(isDisplayed()));
    }

    @Test
    public void testTimelineDisplay() {
        // Verify that the timeline TextView is displayed
        Espresso.onView(withId(R.id.timeline)).check(matches(isDisplayed()));
    }
}
