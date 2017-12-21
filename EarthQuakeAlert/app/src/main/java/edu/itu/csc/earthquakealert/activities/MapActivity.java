package edu.itu.csc.earthquakealert.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.itu.csc.earthquakealert.R;
import edu.itu.csc.earthquakealert.pojos.EarthQuakeInfo;
import edu.itu.csc.earthquakealert.settings.SettingsActivity;
import edu.itu.csc.earthquakealert.utils.Utils;

/**
 * Map activity to draw google map with color markers (as per magnitude) at earthquake locations.
 *
 * @author "Jigar Gosalia"
 *
 */
public class MapActivity extends AppCompatActivity {

    private MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#228B22")));

        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception exception) {
            Log.d(MainActivity.APP_TAG, "MapsInitializer Exception: " + exception.toString());
            exception.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        loadMapData(this, mMapView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mini, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                loadMapData(this, mMapView);
                break;
            case R.id.action_settings:
                startActivity(new Intent(MapActivity.this, SettingsActivity.class));
                break;
            default:
                Toast.makeText(this, "No Such Action Supported!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * AsyncTask to fetch map related data from USGS.
     */
    public static class MapDataFetchTask extends AsyncTask<String, Void, List<EarthQuakeInfo>> {
        private Context context;
        private MapView mMapView;
        private GoogleMap googleMap;
        private List<EarthQuakeInfo> mResult;
        private ProgressDialog dialog;
        private Map<String, EarthQuakeInfo> earthQuakeInfoMap;

        public MapDataFetchTask(Context context, MapView mMapView) {
            this.context = context;
            this.mMapView = mMapView;
            earthQuakeInfoMap = new HashMap<String, EarthQuakeInfo>();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, "Map Information", "Drawing map with quakes data from USGS ... ", false);
        }

        @Override
        protected List<EarthQuakeInfo> doInBackground(String... data) {
            String url = Utils.urlType.get("today");
            String magnitude = "3.0";
            String duration = "last24hr";
            String distance = "miles";
            if (data != null
                    && data.length > 2) {
                return Utils.getEarthQuakeData("MapActivity", Utils.urlType.get(data[1]), data[0], data[1], data[2]);
            }
            return Utils.getEarthQuakeData("MapActivity", url, magnitude, duration, distance);
        }

        @Override
        protected void onPostExecute(List<EarthQuakeInfo> result) {
            super.onPostExecute(result);
            mResult = result;
            if (dialog != null
                    && dialog.isShowing()) {
                dialog.dismiss();
            }

            if (mResult != null) {
                mMapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap mMap) {
                        googleMap = mMap;

                        googleMap.clear();

                        // For showing a move to my location button
                        googleMap.setMyLocationEnabled(true);

                        googleMap.getUiSettings().setZoomControlsEnabled(true);

                        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {
                                handleInfoWindowClick(marker, context);
                            }
                        });

                        if (mResult != null && mResult.size() > 0) {
                            earthQuakeInfoMap.clear();
                            for (EarthQuakeInfo earthquakeInfo : mResult) {
                                earthQuakeInfoMap.put(earthquakeInfo.getTitle(), earthquakeInfo);
                                try {
                                    long timestamp = Long.parseLong(earthquakeInfo.getTime());
                                    Marker marker = googleMap.addMarker(new MarkerOptions()
                                            .position(new LatLng(earthquakeInfo.getLatitude(), earthquakeInfo.getLongitude()))
                                            .title(earthquakeInfo.getTitle())
                                            .snippet(earthquakeInfo.getMagnitude() + " magnitude : " + Utils.getDateTime(timestamp))
                                            .icon(BitmapDescriptorFactory.defaultMarker(Utils.getMarkerColorFromMagnitude(earthquakeInfo.getMagnitude()))));
                                } catch (Exception exception) {
                                    Log.e(MainActivity.APP_TAG, "getMapAsync Exception: " + exception.toString());
                                }
                            }
                        } else {
                            Toast.makeText(context, "No data found with given filters!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                Toast.makeText(context, "Couldn't fetch quakes data from USGS, check your internet connection and try again!", Toast.LENGTH_LONG).show();
            }
        }

        /**
         * Display Earthquake Details when clicked on Info Window of Marker.
         *
         * @param marker
         * @param context
         */
        public void handleInfoWindowClick(Marker marker, Context context) {
            if (earthQuakeInfoMap != null && earthQuakeInfoMap.size() > 0) {
                EarthQuakeInfo earthQuakeInfo = earthQuakeInfoMap.get(marker.getTitle());
                if (earthQuakeInfo != null) {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    final View detailedView = inflater.inflate(R.layout.activity_details, null);

                    TextView title = (TextView) detailedView.findViewById(R.id.title_data);
                    title.setText(earthQuakeInfo.getTitle());

                    TextView location = (TextView) detailedView.findViewById(R.id.location_data);
                    location.setText(earthQuakeInfo.getFormattedPlace());

                    TextView coordinates = (TextView) detailedView.findViewById(R.id.coordinates_data);
                    coordinates.setText(earthQuakeInfo.getFormattedCoordinates());

                    TextView time = (TextView) detailedView.findViewById(R.id.time_data);
                    time.setText(earthQuakeInfo.getFormattedTime());

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                    String distance = prefs.getString("distance", null);
                    TextView depth = (TextView) detailedView.findViewById(R.id.depth_data);
                    depth.setText(Utils.getFormattedDepth(Utils.getConvertedDepth(earthQuakeInfo.getDepth(), distance), distance));

                    TextView eventId = (TextView) detailedView.findViewById(R.id.event_id_data);
                    eventId.setText(earthQuakeInfo.getEventId());

                    TextView significance = (TextView) detailedView.findViewById(R.id.significance_data);
                    significance.setText(earthQuakeInfo.getSignificance());

                    TextView status = (TextView) detailedView.findViewById(R.id.review_status_data);
                    status.setText(earthQuakeInfo.getStatus());

                    TextView urlLinkData = (TextView) detailedView.findViewById(R.id.url_link_data);
                    urlLinkData.setVisibility(View.GONE);
                    TextView urlLinkText = (TextView) detailedView.findViewById(R.id.url_link_text);
                    urlLinkText.setVisibility(View.GONE);

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final View titleView = inflater.inflate(R.layout.activity_title, null);
                    builder.setCustomTitle(titleView).setPositiveButton("OK", null);
                    builder.setView(detailedView);
                    AlertDialog dialogBox = builder.create();
                    dialogBox.show();
                }
            }
        }
    }

    /**
     * load map data as per user filters.
     *
     * @param context
     * @param mMapView
     */
    private void loadMapData(Context context, MapView mMapView) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String magnitude = prefs.getString(context.getString(R.string.pref_magnitude_key), null);
        String duration = prefs.getString(context.getString(R.string.pref_duration_key), null);
        String distance = prefs.getString(context.getString(R.string.pref_distance_key), null);
        new MapDataFetchTask(context, mMapView).execute(magnitude, duration, distance);
    }
}
