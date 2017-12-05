package com.example.chris.week5daily1.geocodingapi;

import android.util.Log;


import com.example.chris.week5daily1.model.Geocoding.GeocodingResponse;
import com.example.chris.week5daily1.model.ReverseGeocoding.ReverseGeocodingResponse;
import com.example.chris.week5daily1.remote.RemoteDataSource;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Admin on 11/29/2017.
 */

public class GeocodingApiPresenter implements GeocodingApiContract.Presenter
{
    GeocodingApiContract.View view;
    public static final String TAG = GeocodingApiPresenter.class.getSimpleName() + "_TAG";
    private GeocodingResponse geocodingResponse;
    private ReverseGeocodingResponse reverseGeocodingResponse;
    
    
    @Override
    public void attachView(GeocodingApiContract.View view)
    {
        this.view = view;
    }

    @Override
    public void detachView()
    {

    }

    @Override
    public void getGeocodingResponse(Map<String, String> map)
    {

        Log.d(TAG, "getGeocodingResponse: ");
        RemoteDataSource.getGeocodingResponse(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GeocodingResponse>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        view.showProgress("Downloading data...");
                    }

                    @Override
                    public void onNext(GeocodingResponse response)
                    {
                        geocodingResponse = response;
                        Log.d(TAG, "onNext: " + response.toString());
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        view.showError(e.toString());
                        Log.d(TAG, "onError: "+e.toString());
                    }

                    @Override
                    public void onComplete()
                    {
                        view.setGeocodingResponse(geocodingResponse);
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
    
    @Override
    public void getReverseGeocodingResponse(Map<String, String> map)
    {
        RemoteDataSource.getReverseGeocodingResponse(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ReverseGeocodingResponse>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        view.showProgress("Downloading weather data...");
                    }
                
                    @Override
                    public void onNext(ReverseGeocodingResponse response)
                    {
                        reverseGeocodingResponse = response;
                        Log.d(TAG, "onNext: " + response.toString());
                    }
                
                    @Override
                    public void onError(Throwable e)
                    {
                        view.showError(e.toString());
                        Log.d(TAG, "onError: "+e.toString());
                    }
                
                    @Override
                    public void onComplete()
                    {
                        view.setReverseGeocodingResponse(reverseGeocodingResponse);
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }
}
