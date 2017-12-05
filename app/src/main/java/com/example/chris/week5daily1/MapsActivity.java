package com.example.chris.week5daily1;

import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener
{
    
    private GoogleMap mMap;
    private CameraPosition cameraPosition;
    private List<LatLng> latLngs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    
    
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        
        mMap.setOnMarkerClickListener(this);
        int i = 1;
        latLngs = getIntent().getParcelableArrayListExtra("addresses");
        if (latLngs != null)
            for (LatLng latlng : latLngs)
            {
                mMap.addMarker(new MarkerOptions().position(latlng).title(i+""));
                i++;
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
            }
        
    }
    
    @Override
    public boolean onMarkerClick(Marker marker)
    {
        cameraPosition = new CameraPosition.Builder()
                .target(marker.getPosition())
                .zoom(18).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        return true;
    }
    
    public void changeMapType(View view)
    {
        switch (view.getId())
        {
            case R.id.btnType1:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case R.id.btnType2:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.btnType3:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.btnType4:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
    }
    
    public void zoomOut(View view)
    {
        if (latLngs != null)
        {
            cameraPosition = new CameraPosition.Builder()
                    .target(latLngs.get(0))
                    .zoom(1).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }
}
