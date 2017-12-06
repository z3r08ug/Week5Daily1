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


/**
 * Created by chris on 12/5/2017.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class GeocoderActivityTest
{
    String LatLng = "33.8914325, -84.47465810000001";
    String address = "1 S Lincoln Trace Ave, Smyrna, GA 30080, USA";
    
    @Rule
    public ActivityTestRule<GeocoderActivity> mActivityTestRule = new ActivityTestRule<>(GeocoderActivity.class);
    
    @Test
    public void clickGeocodingButton_displays_LatLng()
    {
        onView(withId(R.id.btnGGC)).perform(click());
        onView(withText(LatLng)).check(matches(isDisplayed()));
    }
    
    @Test
    public void clickReverseGeocodingButton_displays_Address()
    {
        onView(withId(R.id.btnRGC)).perform(click());
        onView(withText(address)).check(matches(isDisplayed()));
    }
    
    @Test
    public void clickViewAddressesOnMap_open_MapsActivity()
    {
        onView(withId(R.id.btnGoToMapGeocoder)).perform(click());
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }
}
