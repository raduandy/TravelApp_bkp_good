package com.example.raduc.travelapp;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class tab1fragment extends Fragment {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    Location mLocation;

    private static final String TAG = "TaG1Fragment";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                mGoogleMap = map;
                LatLng mLocation = new LatLng(44.439663, 26.096306);
               // map.setLatLngBoundsForCameraTarget(mLocation);
                map.addMarker(new MarkerOptions().position(mLocation)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title("Marker in Bucharest"));
                float zoomLevel = 13.0f; //This goes up to 21
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(mLocation, zoomLevel));
            }
        });
    }


}
