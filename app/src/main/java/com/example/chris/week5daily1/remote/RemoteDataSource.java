package com.example.chris.week5daily1.remote;


import com.example.chris.week5daily1.model.Geocoding.GeocodingResponse;
import com.example.chris.week5daily1.model.ReverseGeocoding.ReverseGeocodingResponse;
import com.example.chris.week5daily1.util.Constants;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by chris on 12/2/2017.
 */

public class RemoteDataSource
{
    
    public static Retrofit create()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                //add converter to parse the response
                .addConverterFactory(GsonConverterFactory.create())
                //add call adapter to convert the response to RxJava observable
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        
        return retrofit;
    }
    
    public static Observable<GeocodingResponse> getGeocodingResponse(Map<String, String> map)
    {
        Retrofit retrofit = create();
        RemoteService remoteService = retrofit.create(RemoteService.class);
        return remoteService.getGeocodingResponse(map);
    }
    
    public static Observable<ReverseGeocodingResponse> getReverseGeocodingResponse(Map<String, String> map)
    {
        Retrofit retrofit = create();
        RemoteService remoteService = retrofit.create(RemoteService.class);
        return remoteService.getReverseGeocodingResponse(map);
    }
}
