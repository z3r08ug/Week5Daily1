package com.example.chris.week5daily1;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeocodingActivity extends AppCompatActivity
{
    private List<LatLng> latLngs;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geocoding);
        
        latLngs = new ArrayList<>();
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        latLngs = getIntent().getParcelableArrayListExtra("addresses");
    }
    
    public void goToApi(View view)
    {
        Intent intent = new Intent(this, GeocodingApiActivity.class);
        intent.putParcelableArrayListExtra("addresses", (ArrayList<? extends Parcelable>) latLngs);
        startActivity(intent);
    }
    
    public void goToGeocoder(View view)
    {
        Intent intent = new Intent(this, GeocoderActivity.class);
        intent.putParcelableArrayListExtra("addresses", (ArrayList<? extends Parcelable>) latLngs);
        startActivity(intent);
    }
    
    public void goToMap(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putParcelableArrayListExtra("addresses", (ArrayList<? extends Parcelable>)latLngs);
        startActivity(intent);
    }
}
