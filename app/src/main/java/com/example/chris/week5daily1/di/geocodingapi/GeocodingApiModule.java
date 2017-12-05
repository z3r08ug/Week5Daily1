package com.example.chris.week5daily1.di.geocodingapi;


import com.example.chris.week5daily1.geocodingapi.GeocodingApiPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 11/29/2017.
 */

@Module
public class GeocodingApiModule
{
    @Provides
    @Singleton
    GeocodingApiPresenter providerGeocodingApiPresenter()
    {
        return new GeocodingApiPresenter();
    }
}
