package com.example.chris.week5daily1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeocoderActivity extends AppCompatActivity
{
    public static final String TAG = GeocoderActivity.class.getSimpleName() + "_TAG";
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 10;
    public static final int REQUEST_CHECK_SETTINGS = 15;
    
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private Location locationCurrent;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Geocoder geocoder;
    private List<Address> addresses;
    private TextView tvGeocoderAddress;
    private TextView tvGeocoderLatlong;
    private List<LatLng> latLngs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geocoder);
        
        init();
        
        checkPermission();
    }
    
    private void init()
    {
        latLngs = getIntent().getParcelableArrayListExtra("addresses");
        if (latLngs == null)
            latLngs = new ArrayList<>();
        geocoder = new Geocoder(this);
        tvGeocoderAddress = findViewById(R.id.tvGeocoderAddress);
        tvGeocoderLatlong = findViewById(R.id.tvGeocoderLatlong);
        
        locationCallback = new LocationCallback()
        {
            @Override
            public void onLocationResult(LocationResult locationResult)
            {
                for (Location location : locationResult.getLocations())
                {
                    Log.d(TAG, "onLocationResult: " + location.toString());
                }
            }
        };
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }
    
    public void onGeocoding(View view)
    {
        String address = "";
        String latitude = "";
        String longitude = "";
        try
        {
            Address streetAddress = geocoder
                    .getFromLocation(locationCurrent.getLatitude(),
                            locationCurrent.getLongitude(), 1).get(0);
            address = streetAddress.getAddressLine(0);
            Log.d(TAG, "onGeocoding: " + address);
            addresses = geocoder.getFromLocationName(address, 1);
            latitude = addresses.get(0).getLatitude() + "";
            longitude = addresses.get(0).getLongitude() + "";
            addAddressToList(latitude, longitude);
            tvGeocoderLatlong.setText(latitude + ", " + longitude);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void addAddressToList(String latitude, String longitude)
    {
        LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        if (!latLngs.contains(latLng))
        {
            latLngs.add(latLng);
            Toast.makeText(this, "Saved Address", Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(this, GeocodingActivity.class);
        intent.putParcelableArrayListExtra("addresses", (ArrayList<? extends Parcelable>) latLngs);
        startActivity(intent);
    }
    
    public void onReverseGeocoding(View view)
    {
        try
        {
            addresses = geocoder.getFromLocation(locationCurrent.getLatitude(), locationCurrent.getLongitude(), 1);
            addAddressToList(locationCurrent.getLatitude()+"", locationCurrent.getLongitude()+"");
            tvGeocoderAddress.setText(addresses.get(0).getAddressLine(0));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void checkPermission()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED)
        {
            
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS))
            {
                
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                
            }
            else
            {
                
                // No explanation needed, we can request the permission.
                
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
                
                // MY_PERMISSIONS_REQUEST_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else
        {
            getLocation();
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_LOCATION:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    getLocation();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    
                }
                else
                {
                    Log.d(TAG, "onRequestPermissionsResult: denied");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    
    @SuppressLint("MissingPermission")
    public void getLocation()
    {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(GeocoderActivity.this, new OnSuccessListener<Location>()
                {
                    @Override
                    public void onSuccess(Location location)
                    {
                        Log.d(TAG, "onSuccess: " + location.toString());
                        //tvCurrentLocation.setText(location.getLatitude() + " " + location.getLongitude());
                        locationCurrent = location;
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                    
                    }
                });
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        startLocationRequest();
    }
    
    @Override
    protected void onStop()
    {
        super.onStop();
        stopLocationRequest();
    }
    
    private void stopLocationRequest()
    {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
    
    @SuppressLint("MissingPermission")
    private void startLocationRequest()
    {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }
    
    public void goToMap(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putParcelableArrayListExtra("addresses", (ArrayList<? extends Parcelable>) latLngs);
        startActivity(intent);
    }
}
