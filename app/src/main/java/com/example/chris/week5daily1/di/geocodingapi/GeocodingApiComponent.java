package com.example.chris.week5daily1.di.geocodingapi;

import com.example.chris.week5daily1.GeocodingApiActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Admin on 11/29/2017.
 */

@Component(modules = GeocodingApiModule.class)
@Singleton
public interface GeocodingApiComponent
{
    void inject(GeocodingApiActivity geocodingApiActivity);
}
