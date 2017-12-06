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
import static android.support.test.espresso.action.ViewActions.typeText;
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
public class GeocodingApiActivityTest
{
    String Lat = "33.8914325";
    String Lng = "-84.47465810000001";
    String LatLng = Lat + ", " + Lng;
    String address = "1 S Lincoln Trace Ave, Smyrna, GA 30080, USA";
    
    String street = "1 S Lincoln Trace Ave";
    String city = "Smyrna";
    String state = "GA";
    
    @Rule
    public ActivityTestRule<GeocodingApiActivity> mActivityTestRule = new ActivityTestRule<>(GeocodingApiActivity.class);
    
    
    @Test
    public void clickReverseGeocoding_yields_address()
    {
        onView(withId(R.id.etLatitude)).perform(typeText(Lat));
        onView(withId(R.id.etLongitude)).perform(typeText(Lng));
        onView(withId(R.id.btnRapi)).perform(click());
        onView(withText(address)).check(matches(isDisplayed()));
    }
    
    @Test
    public void clickGeocoding_yields_LatLng()
    {
        onView(withId(R.id.etAddress)).perform(typeText(street));
        onView(withId(R.id.etCity)).perform(typeText(city));
        onView(withId(R.id.etState)).perform(typeText(state));
        onView(withId(R.id.btnGapi)).perform(click());
        onView(withText(LatLng)).check(matches(isDisplayed()));
        
    }
}