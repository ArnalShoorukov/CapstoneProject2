package com.mosquefinder.arnal.prayertimesapp.Fragment;



import android.graphics.Color;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mosquefinder.arnal.prayertimesapp.R;
import com.mosquefinder.arnal.prayertimesapp.data.TimesPreferences;

import static com.mosquefinder.arnal.prayertimesapp.R.id.map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback {
    double latitude, longitude ;

    public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_maps, container, false);


        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double coord[] =  TimesPreferences.getLocationCoordinates(getActivity());

        latitude = coord[0];
        longitude = coord[1];

        Polyline line = googleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(21.422510, 39.826168), new LatLng(latitude, longitude))
                .width(6)
                .color(Color.GREEN));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude),15));
    }
}