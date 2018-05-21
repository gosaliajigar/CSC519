package edu.itu.csc.earthquakealert.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.itu.csc.earthquakealert.R;
import edu.itu.csc.earthquakealert.pojos.EarthQuakeInfo;
import edu.itu.csc.earthquakealert.utils.Utils;

/**
 * EarthQuakeInfoAdapter for displaying earth quake information on latest quake information page.
 *
 * @author "Jigar Gosalia"
 *
 */
public class EarthQuakeInfoAdapter extends ArrayAdapter<EarthQuakeInfo> {

    private Context context;

    private List<EarthQuakeInfo> data;

    public EarthQuakeInfoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<EarthQuakeInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.data = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            row = inflater.inflate(R.layout.list_item_earthquake, parent, false);

            holder = new ViewHolder();
            holder.magnitudeTextView = (TextView) row.findViewById(R.id.magnitude);
            holder.placeTextView = (TextView) row.findViewById(R.id.place);
            holder.depthTextView = (TextView) row.findViewById(R.id.depth);
            holder.timeTextView = (TextView) row.findViewById(R.id.time);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String distance = prefs.getString(context.getString(R.string.pref_distance_key), null);

        EarthQuakeInfo earthQuakeInfo = data.get(position);
        holder.magnitudeTextView.setText(earthQuakeInfo.getMagnitude());
        holder.magnitudeTextView.setTextColor(Utils.getTextColorFromMagnitude(earthQuakeInfo.getMagnitude()));
        holder.placeTextView.setText(earthQuakeInfo.getFormattedPlace());
        String depth = Utils.getFormattedDepth(Utils.getConvertedDepth(earthQuakeInfo.getDepth(), distance), distance);
        holder.depthTextView.setText("Depth: " + depth);
        holder.timeTextView.setText(earthQuakeInfo.getFormattedTime());
        return row;
    }

    static class ViewHolder {
        TextView magnitudeTextView;
        TextView placeTextView;
        TextView depthTextView;
        TextView timeTextView;
    }
}