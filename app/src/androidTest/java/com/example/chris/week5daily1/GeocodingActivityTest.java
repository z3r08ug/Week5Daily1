package com.example.chris.week5daily1;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GeocodingActivityTest
{
    
    @Rule
    public ActivityTestRule<GeocodingActivity> mActivityTestRule = new ActivityTestRule<>(GeocodingActivity.class);
    
    @Test
    public void clickGeocoderButton_opens_GeocoderActivity()
    {
        onView(withId(R.id.btnGoToGeocoder)).perform(click());
        onView(withId(R.id.tvLatLongField)).check(matches(isDisplayed()));
    }
    
    @Test
    public void clickGeocodingApiButton_open_GeocodingApiActivity()
    {
        onView(withId(R.id.btnGoToApiGeocoding)).perform(click());
        onView(withId(R.id.tvEnterLat)).check(matches(isDisplayed()));
    }
    
    @Test
    public void clickViewAddressesOnMap_open_MapsActivity()
    {
        onView(withId(R.id.btnGoToMap)).perform(click());
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }
}
