package com.example.chris.week5daily1;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import com.example.chris.week5daily1.di.geocodingapi.DaggerGeocodingApiComponent;
import com.example.chris.week5daily1.model.Geocoding.GeocodingResponse;
import com.example.chris.week5daily1.model.ReverseGeocoding.ReverseGeocodingResponse;
import com.example.chris.week5daily1.geocodingapi.GeocodingApiContract;
import com.example.chris.week5daily1.geocodingapi.GeocodingApiPresenter;
import com.example.chris.week5daily1.util.Constants;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GeocodingApiActivity extends AppCompatActivity implements GeocodingApiContract.View
{
    @Inject
    GeocodingApiPresenter presenter;
    
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 10;
    public static final String TAG = GeocodingApiActivity.class.getSimpleName() + "_TAG";
    
    private EditText etLongitude;
    private EditText etAddress;
    private EditText etCity;
    private EditText etState;
    private EditText etLatitude;
    private TextView tvLatLong;
    private TextView tvAddress;
    private Geocoder geocoder;
    private List<Address> addresses;
    private String latitude;
    private String longitude;
    private List<LatLng> latLngs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        latLngs = getIntent().getParcelableArrayListExtra("addresses");
        if (latLngs == null)
            latLngs = new ArrayList<>();
        
        DaggerGeocodingApiComponent.create().inject(this);
        
        bindViews();
        
        geocoder = new Geocoder(this);
        addresses = new ArrayList<>();
        
        presenter.attachView(this);
    }
    
    private void bindViews()
    {
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etState = findViewById(R.id.etState);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        tvLatLong = findViewById(R.id.tvLatLong);
        tvAddress = findViewById(R.id.tvAddress);
    }
    
    @Override
    public void showError(String error)
    {
    
    }
    
    @Override
    public void setGeocodingResponse(GeocodingResponse geocodingResponse)
    {
        latitude = geocodingResponse.getResults().get(0).getGeometry().getLocation().getLat() + "";
        longitude = geocodingResponse.getResults().get(0).getGeometry().getLocation().getLng() + "";
        
        Log.d(TAG, "setGeocodingResponse: " + latitude);
        Log.d(TAG, "setGeocodingResponse: " + longitude);
        addAddressToList(latitude, longitude);
        tvLatLong.setText(latitude + ", " + longitude);
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
        intent.putParcelableArrayListExtra("addresses", (ArrayList<? extends Parcelable>)latLngs);
        startActivity(intent);
    }
    
    @Override
    public void setReverseGeocodingResponse(ReverseGeocodingResponse reverseGeocodingResponse)
    {
        String address = reverseGeocodingResponse.getResults().get(0).getFormattedAddress();
        Log.d(TAG, "setReverseGeocodingResponse: " + address);
        tvAddress.setText(address);
    }
    
    @Override
    public void showProgress(String progress)
    {
    
    }
    
    public void onReverseGeocoding(View view)
    {
        String latitude = etLatitude.getText().toString();
        String longitude = etLongitude.getText().toString();
        String latlong = latitude + "," + longitude;
        
        Map<String, String> map = new HashMap<>();
        map.put("latlng", latlong);
        map.put("key", Constants.MAPS_API_KEY);
        presenter.getReverseGeocodingResponse(map);
    }
    
    public void onGeocoding(View view)
    {
        String address;
        String street = etAddress.getText().toString();
        String city = etCity.getText().toString();
        String state = etState.getText().toString();
        address = formatAddress(street, city, state);
        Map<String, String> map = new HashMap<>();
        map.put("address", address);
        map.put("key", Constants.MAPS_API_KEY);
        presenter.getGeocodingResponse(map);
    }
    
    private String formatAddress(String street, String city, String state)
    {
        return street + "," + city + "," + state;
    }
    
    public void goToMap(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putParcelableArrayListExtra("addresses", (ArrayList<? extends Parcelable>)latLngs);
        startActivity(intent);
    }
}