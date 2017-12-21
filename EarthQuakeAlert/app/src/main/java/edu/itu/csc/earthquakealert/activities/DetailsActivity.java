package edu.itu.csc.earthquakealert.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import edu.itu.csc.earthquakealert.R;
import edu.itu.csc.earthquakealert.utils.Utils;

/**
 * Details activity to display detailed information about the earthquakes.
 *
 * @author "Jigar Gosalia"
 *
 */
public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#228B22")));

        Intent intent = getIntent();

        TextView title = (TextView) findViewById(R.id.title_data);
        title.setText(intent.getStringExtra("title"));

        TextView location = (TextView) findViewById(R.id.location_data);
        location.setText(intent.getStringExtra("location"));

        TextView coordinates = (TextView) findViewById(R.id.coordinates_data);
        coordinates.setText(intent.getStringExtra("coordinates"));

        TextView time = (TextView) findViewById(R.id.time_data);
        time.setText(intent.getStringExtra("time"));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String distance = prefs.getString(this.getString(R.string.pref_distance_key), null);
        TextView depth = (TextView) findViewById(R.id.depth_data);
        depth.setText(Utils.getFormattedDepth(Utils.getConvertedDepth(intent.getDoubleExtra("depth", 0), distance), distance));

        TextView eventId = (TextView) findViewById(R.id.event_id_data);
        eventId.setText(intent.getStringExtra("eventid"));

        TextView significance = (TextView) findViewById(R.id.significance_data);
        significance.setText(intent.getStringExtra("significance"));

        TextView status = (TextView) findViewById(R.id.review_status_data);
        status.setText(intent.getStringExtra("status"));

        String url = intent.getStringExtra("url");
        TextView urlLink = (TextView) findViewById(R.id.url_link_data);
        urlLink.setMovementMethod(LinkMovementMethod.getInstance());
        urlLink.setText(Html.fromHtml(getResources().getString(R.string.link).replace("HREF", url)));
    }
}
