package edu.itu.csc519.csc519_hw4_89753;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Iterator;
import java.util.List;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 *
 * @author "Jigar Gosalia"
 */
public class MapsMarkerActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    private String cityName = "";

    private double longitude;

    private double latitude;

    static final int numberOptions = 10;

    String [] optionArray = new String[numberOptions];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Test whether geocoder is present on platform
        if(Geocoder.isPresent()){
            cityName = getIntent().getStringExtra("CITY_NAME");
            geocodeLocation(cityName);
        } else {
            String noGoGeo = "FAILURE: No Geocoder on this platform.";
            Toast.makeText(this, noGoGeo, Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(latitude, longitude);
        // If cityName is not available then use
        // Default Location.
        String markerDisplay = "Default Location";
        if (cityName != null
                && cityName.length() > 0) {
            markerDisplay = "Marker in " + cityName;
        }
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title(markerDisplay));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    /**
     * Method to geocode location passed as string (e.g., "Pentagon"), which
     * places the corresponding latitude and longitude in the variables lat and lon.
     *
     * @param placeName
     */
    private void geocodeLocation(String placeName){

        // Following adapted from Conder and Darcey, pp.321 ff.
        Geocoder gcoder = new Geocoder(this);

        // Note that the Geocoder uses synchronous network access, so in a serious application
        // it would be best to put it on a background thread to prevent blocking the main UI if network
        // access is slow. Here we are just giving an example of how to use it so, for simplicity, we
        // don't put it on a separate thread.  See the class RouteMapper in this package for an example
        // of making a network access on a background thread. Geocoding is implemented by a backend
        // that is not part of the core Android framework, so we use the static method
        // Geocoder.isPresent() to test for presence of the required backend on the given platform.

        try{
            List<Address> results = null;
            if(Geocoder.isPresent()){
                results = gcoder.getFromLocationName(placeName, numberOptions);
            } else {
                Log.i(MainActivity.APP_TAG, "No Geocoder found");
                return;
            }
            Iterator<Address> locations = results.iterator();
            String raw = "\nRaw String:\n";
            String country;
            int opCount = 0;
            while(locations.hasNext()){
                Address location = locations.next();
                if(opCount == 0 && location != null){
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
                country = location.getCountryName();
                if(country == null) {
                    country = "";
                } else {
                    country =  ", " + country;
                }
                raw += location+"\n";
                optionArray[opCount] = location.getAddressLine(0)+", "
                        +location.getAddressLine(1)+country+"\n";
                opCount ++;
            }
            // Log the returned data
            Log.d(MainActivity.APP_TAG, raw);
            Log.d(MainActivity.APP_TAG, "\nOptions:\n");
            for(int i=0; i<opCount; i++){
                Log.i(MainActivity.APP_TAG, "("+(i+1)+") "+optionArray[i]);
            }
            Log.d(MainActivity.APP_TAG, "latitude=" + latitude + ";longitude=" + longitude);
        } catch (Exception e){
            Log.d(MainActivity.APP_TAG, "I/O Failure; do you have a network connection?",e);
        }
    }
}