package edu.itu.csc.earthquakealert.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.itu.csc.earthquakealert.R;
import edu.itu.csc.earthquakealert.adapters.EarthQuakeInfoAdapter;
import edu.itu.csc.earthquakealert.pojos.EarthQuakeInfo;
import edu.itu.csc.earthquakealert.settings.SettingsActivity;
import edu.itu.csc.earthquakealert.utils.Utils;

/**
 *
 * Main activity to display latest quake data.
 *
 * @author "Jigar Gosalia"
 *
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String APP_TAG = "CSC519_89753_PROJECT";

    private static ArrayAdapter<EarthQuakeInfo> earthquakeAdapter = null;

    private TextView updateTime;
    private TextView updateFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.title_activity_main));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateTime = (TextView) findViewById(R.id.update_time);
        updateFilter = (TextView) findViewById(R.id.update_filter);

        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.pref_general, false);

        earthquakeAdapter = new EarthQuakeInfoAdapter(this, R.layout.list_item_earthquake, new ArrayList<EarthQuakeInfo>());

        // Get a reference to the ListView and attach this adapter to it.
        ListView listView = (ListView) findViewById(R.id.listview_earthquake);
        listView.setAdapter(earthquakeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (earthquakeAdapter != null
                        && earthquakeAdapter.getItem(i) != null) {
                    EarthQuakeInfo earthQuakeInfo = earthquakeAdapter.getItem(i);
                    Log.d(MainActivity.APP_TAG, "Item selected to view details : " + earthQuakeInfo);
                    Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                    intent.putExtra("title", earthQuakeInfo.getTitle());
                    intent.putExtra("location", earthQuakeInfo.getFormattedPlace());
                    intent.putExtra("coordinates", earthQuakeInfo.getFormattedCoordinates());
                    intent.putExtra("time", earthQuakeInfo.getFormattedTime());
                    intent.putExtra("depth", earthQuakeInfo.getDepth());
                    intent.putExtra("eventid", earthQuakeInfo.getEventId());
                    intent.putExtra("significance", earthQuakeInfo.getSignificance());
                    intent.putExtra("status", earthQuakeInfo.getStatus());
                    intent.putExtra("url", earthQuakeInfo.getUrl());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
                loadLatestQuakes(this);
                break;
            case R.id.action_map:
                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.action_statistics:
                startActivity(new Intent(MainActivity.this, StatisticsActivity.class));
                break;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            default:
                Toast.makeText(this, "No Such Action Supported!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_about) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        } else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "[Subject Here]");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "[Type your message here]");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == R.id.nav_feedback) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", getString(R.string.email_address), null));
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " : Feedback (" + formatter.format(new Date()) + ")");
            intent.putExtra(Intent.EXTRA_TEXT, "[Type your message here]");
            startActivity(Intent.createChooser(intent, "Choose an Email client : "));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * AsyncTask to fetch latest quakes data from USGS.
     */
    public static class LatestDataFetchTask extends AsyncTask<String, Void, List<EarthQuakeInfo>> {

        private ProgressDialog dialog;
        private Context context;

        public LatestDataFetchTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, "Latest Quakes Data", "Collecting latest quakes data from USGS ... ", false);
        }

        @Override
        protected List<EarthQuakeInfo> doInBackground(String... data) {
            String url = Utils.urlType.get("today");
            String magnitude = "3.0";
            String duration = "last24hr";
            String distance = "miles";
            if (data != null
                    && data.length > 2) {
                return Utils.getEarthQuakeData("MainActivity", Utils.urlType.get(data[1]), data[0], data[1], data[2]);
            }
            return Utils.getEarthQuakeData("MainActivity", url, magnitude, duration, distance);
        }

        @Override
        protected void onPostExecute(List<EarthQuakeInfo> result) {
            super.onPostExecute(result);
            if (dialog != null
                    && dialog.isShowing()) {
                dialog.dismiss();
            }

            if (result != null
                && earthquakeAdapter != null) {
                earthquakeAdapter.clear();
                if (result.size() > 0) {
                    for (EarthQuakeInfo earthquakeInfo : result) {
                        earthquakeAdapter.add(earthquakeInfo);
                    }
                } else {
                    Toast.makeText(context, "No data found with given filters!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, "Couldn't fetch quakes data from USGS, check your internet connection and try again!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadLatestQuakes(this);
        Utils.updateLastViewed(MainActivity.class.getSimpleName(), this);
    }

    /**
     * load quakes data.
     *
     * @param context
     */
    private void loadLatestQuakes(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String magnitude = prefs.getString(context.getString(R.string.pref_magnitude_key), null);
        String duration = prefs.getString(context.getString(R.string.pref_duration_key), null);
        String distance = prefs.getString(context.getString(R.string.pref_distance_key), null);
        new LatestDataFetchTask(context).execute(magnitude, duration, distance);
        updateFooter();
    }

    /**
     * refresh footer with last updated time and user filters.
     *
     */
    private void updateFooter() {
        Date date = new Date();
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String magnitude = prefs.getString(this.getString(R.string.pref_magnitude_key), null);
        String duration = prefs.getString(this.getString(R.string.pref_duration_key), null);
        updateTime.setText(("Updated: " + timeFormatter.format(date)).toString());
        updateFilter.setText(("Filters: " + magnitude + "; " + Utils.durationMap.get(duration)).toString());
    }
}
