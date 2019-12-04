package com.example.androidcapstone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private List<Place.Field> fields;
    final int PLACE_PICKER_REQ_CODE = 1;
    private GoogleMap mMap;
    Button setButton;

    private final String API_KEY = "AIzaSyDwM42eGcN2nLPGLa11y3fFIG55OGL28Wg";
    String locName, locAddress;
    LatLng latLng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener((GoogleMap.OnMyLocationButtonClickListener) this);
        mMap.setOnMyLocationClickListener((GoogleMap.OnMyLocationClickListener) this);

        setButton = findViewById(R.id.buttonSetLocation);
        fields = Arrays.asList(Place.Field.NAME, Place.Field.ID, Place.Field.LAT_LNG);
        // Initialize Places.
        Places.initialize(getApplicationContext(), API_KEY);

        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(MapsActivity.this);
                startActivityForResult(intent, PLACE_PICKER_REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case PLACE_PICKER_REQ_CODE:
                    Place place = Autocomplete.getPlaceFromIntent(data);
                    locName = place.getName();
                    locAddress = place.getAddress();
                    latLng = place.getLatLng();
                    mMap.addMarker(new MarkerOptions().position(latLng).title(locName + "\n Address:" + locAddress));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                    mMap.animateCamera(update);
                    break;
            }
        }
        catch(Exception e){

        }
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
