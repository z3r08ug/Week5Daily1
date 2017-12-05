package com.example.chris.week5daily1.remote;


import com.example.chris.week5daily1.model.Geocoding.GeocodingResponse;
import com.example.chris.week5daily1.model.ReverseGeocoding.ReverseGeocodingResponse;
import com.example.chris.week5daily1.util.Constants;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by chris on 12/2/2017.
 */

public interface RemoteService
{
    //json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key=
    @GET("json")
    Observable<GeocodingResponse> getGeocodingResponse(@QueryMap Map<String, String> map);
    //json?latlng=40.714224,-73.961452&key=
    @GET("json")
    Observable<ReverseGeocodingResponse> getReverseGeocodingResponse(@QueryMap Map<String, String> map);
}
