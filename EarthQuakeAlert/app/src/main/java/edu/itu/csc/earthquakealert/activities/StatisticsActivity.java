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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.itu.csc.earthquakealert.R;
import edu.itu.csc.earthquakealert.pojos.EarthQuakeInfo;
import edu.itu.csc.earthquakealert.settings.SettingsActivity;
import edu.itu.csc.earthquakealert.utils.Utils;

/**
 * Statistics Activity to display the earthquake statistics for the day, week and month.
 *
 * @author "Jigar Gosalia"
 *
 */
public class StatisticsActivity extends AppCompatActivity {

    private TextView updateTime;
    private TextView updateFilter;

    private TextView todayCount;
    private TextView weeklyCount;
    private TextView monthlyCount;

    private TextView todayText;
    private TextView weeklyText;
    private TextView monthlyText;

    private TextView noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#228B22")));

        updateTime = (TextView) findViewById(R.id.update_time);
        updateFilter = (TextView) findViewById(R.id.update_filter);

        todayCount = (TextView) findViewById(R.id.today_count);
        weeklyCount = (TextView) findViewById(R.id.week_count);
        monthlyCount = (TextView) findViewById(R.id.month_count);

        todayText = (TextView) findViewById(R.id.today_text);
        weeklyText = (TextView) findViewById(R.id.week_text);
        monthlyText = (TextView) findViewById(R.id.month_text);

        noData = (TextView) findViewById(R.id.no_data_text);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadStatistics(this);
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
                loadStatistics(this);
                break;
            case R.id.action_settings:
                startActivity(new Intent(StatisticsActivity.this, SettingsActivity.class));
                break;
            default:
                Toast.makeText(this, "No Such Action Supported!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * AsyncTask to fetch statistics related data from USGS.
     */
    public static class StatisticsDataFetch extends AsyncTask<Void, Void, Map<String, Integer>> {

        private Context context;
        private ProgressDialog dialog;
        private TextView noData;

        private TextView todayCount;
        private TextView weeklyCount;
        private TextView monthlyCount;

        private TextView todayText;
        private TextView weeklyText;
        private TextView monthlyText;

        public StatisticsDataFetch(TextView todayCount, TextView todayText, TextView weeklyCount, TextView weeklyText, TextView monthlyCount, TextView monthlyText, TextView noData, Context context) {
            this.context = context;
            this.todayCount = todayCount;
            this.todayText = todayText;
            this.weeklyCount = weeklyCount;
            this.weeklyText = weeklyText;
            this.monthlyCount = monthlyCount;
            this.monthlyText = monthlyText;
            this.noData = noData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, "Statistics Information", "Collecting statistics data from USGS ... ", false);
        }

        @Override
        protected Map<String, Integer> doInBackground(Void... voids) {
            return getCounts();
        }

        @Override
        protected void onPostExecute(Map<String, Integer> result) {
            super.onPostExecute(result);
            if (dialog != null
                    && dialog.isShowing()) {
                dialog.dismiss();
            }

            if (result != null) {
                noData.setText("");
                todayText.setText("Earthquakes Today");
                weeklyText.setText("This Week");
                monthlyText.setText("This Month");
                todayCount.setText((result.get("today") != null) ? result.get("today").toString() : "0");
                weeklyCount.setText((result.get("thisweek") != null) ? result.get("thisweek").toString() : "0");
                monthlyCount.setText((result.get("thismonth") != null) ? result.get("thismonth").toString() : "0");
            } else {
                noData.setText("Couldn't fetch quakes data from USGS, check your internet connection and try again!");
                todayText.setText("");
                weeklyText.setText("");
                monthlyText.setText("");
                todayCount.setText("");
                weeklyCount.setText("");
                monthlyCount.setText("");
            }
        }

        /**
         * Get today, weekly and monthly number of earthquakes with the given settings.
         *
         * @return
         */
        private Map<String, Integer> getCounts() {
            boolean error = false;
            Map<String, Integer> results = new HashMap<String, Integer>();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String magnitude = prefs.getString(context.getString(R.string.pref_magnitude_key), null);
            String duration = prefs.getString(context.getString(R.string.pref_duration_key), null);
            String distance = prefs.getString(context.getString(R.string.pref_distance_key), null);
            List<EarthQuakeInfo> today = Utils.getEarthQuakeData("StatisticsActivity - today", Utils.urlType.get("today"), magnitude, duration, distance);
            List<EarthQuakeInfo> weekly = Utils.getEarthQuakeData("StatisticsActivity - weekly", Utils.urlType.get("thisweek"), magnitude, duration, distance);
            List<EarthQuakeInfo> monthly = Utils.getEarthQuakeData("StatisticsActivity - monthly", Utils.urlType.get("thismonth"), magnitude, duration, distance);
            if (today != null) {
                results.put("today", today.size());
            } else {
                error = true;
            }
            if (weekly != null) {
                results.put("thisweek", weekly.size());
            } else {
                error = true;
            }
            if (monthly != null) {
                results.put("thismonth", monthly.size());
            } else {
                error = true;
            }
            return ((error) ? null : results);
        }
    }

    /**
     * load statistics
     *
     */
    private void loadStatistics(Context context) {
        todayText.setText("Earthquakes Today");
        weeklyText.setText("This Week");
        monthlyText.setText("This Month");
        noData.setText("");
        new StatisticsDataFetch(todayCount, todayText, weeklyCount, weeklyText, monthlyCount, monthlyText, noData, context).execute();
        updateFooter();
    }

    /**
     * refresh the footer with last updated time and filters.
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
