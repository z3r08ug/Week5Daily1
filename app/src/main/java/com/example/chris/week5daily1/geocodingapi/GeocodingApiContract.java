package com.example.chris.week5daily1.geocodingapi;


import com.example.chris.week5daily1.model.Geocoding.GeocodingResponse;
import com.example.chris.week5daily1.model.ReverseGeocoding.ReverseGeocodingResponse;
import com.example.chris.week5daily1.util.BasePresenter;
import com.example.chris.week5daily1.util.BaseView;

import java.util.Map;

/**
 * Created by Admin on 11/29/2017.
 */

public interface GeocodingApiContract
{
    //methods for main activity
    interface View extends BaseView
    {
        void setGeocodingResponse(GeocodingResponse geocodingResponse);
        void setReverseGeocodingResponse(ReverseGeocodingResponse reverseGeocodingResponse);
        void showProgress(String progress);
    }

    interface Presenter extends BasePresenter<View>
    {
        void getGeocodingResponse(Map<String, String> map);
        void getReverseGeocodingResponse(Map<String, String> map);
    }
}
