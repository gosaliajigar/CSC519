package com.example.android.weather.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.weather.app.data.WeatherContract;

/**
 * {@link ForecastAdapter} exposes a list of weather forecasts
 * from a {@link Cursor} to a {@link android.widget.ListView}.
 */
public class ForecastAdapter extends CursorAdapter {

    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;

    // Flag to determine if we want to use a separate view for "today".
    private boolean mUseTodayLayout = true;

    /**
     * Cache of the children views for a forecast list item.
     */
    public static class ViewHolder {
        public final ImageView iconView;
        public final TextView dateView;
        public final TextView descriptionView;
        public final TextView highTempView;
        public final TextView lowTempView;

        public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
            descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
            highTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
            lowTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
        }
    }

    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Choose the layout type
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                layoutId = R.layout.list_item_forecast_today;
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                layoutId = R.layout.list_item_forecast;
                break;
            }
        }

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int viewType = getItemViewType(cursor.getPosition());
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                // TO DO 1
                // ForecastFragment.COL_WEATHER_CONDITION_ID is the hard coded column index
                // based on FORECAST_COLUMNS. It could easily get outdated if FORECAST_COLUMNS
                // is changed. Hence using column name to get the column index of weather_id.
                // get weather_id column index by using column name
                int columnIndex = cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID);
                // get weather_id using column index
                int weatherId = cursor.getInt(columnIndex);
                viewHolder.iconView.setImageResource(Utility.getArtResourceForWeatherCondition(weatherId));
                Log.d(MainActivity.APP_TAG, "TO DO 1: bindView today(viewType=" + viewType + "&columnIndex=" + columnIndex + "&weatherId=" + weatherId + ")");
                // TO DO 1 END
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                // TO DO 2
                // ForecastFragment.COL_WEATHER_CONDITION_ID is the hard coded column index
                // based on FORECAST_COLUMNS. It could easily get outdated if FORECAST_COLUMNS
                // is changed. Hence using column name to get the column index of weather_id.
                // get weather_id column index by using column name
                int columnIndex = cursor.getColumnIndex(WeatherContract.WeatherEntry.COLUMN_WEATHER_ID);
                // get weather_id using column index
                int weatherId = cursor.getInt(columnIndex);
                viewHolder.iconView.setImageResource(Utility.getIconResourceForWeatherCondition(weatherId));
                Log.d(MainActivity.APP_TAG, "TO DO 2: bindView future day(viewType=" + viewType + "&columnIndex=" + columnIndex + "&weatherId=" + weatherId + ")");
                // TO DO 2 END
                break;
            }
        }

        // Read date from cursor
        String dateString = cursor.getString(ForecastFragment.COL_WEATHER_DATE);
        // Find TextView and set formatted date on it
        viewHolder.dateView.setText(Utility.getFriendlyDayString(context, dateString));

        // Read weather forecast from cursor
        String description = cursor.getString(ForecastFragment.COL_WEATHER_DESC);
        // Find TextView and set weather forecast on it
        viewHolder.descriptionView.setText(description);

        // Read user preference for metric or imperial temperature units
        boolean isMetric = Utility.isMetric(context);
        Log.d(MainActivity.APP_TAG, "isMetric: " + isMetric);

        // Read high temperature from cursor
        double high = cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        viewHolder.highTempView.setText(Utility.formatTemperature(context, high, isMetric));

        // Read low temperature from cursor
        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
        viewHolder.lowTempView.setText(Utility.formatTemperature(context, low, isMetric));
    }

    public void setUseTodayLayout(boolean useTodayLayout) {
        mUseTodayLayout = useTodayLayout;
    }

    @Override
    public int getItemViewType(int position) {
        // TO DO 3
        int type = -1;
        if (position == VIEW_TYPE_TODAY && mUseTodayLayout) {
            type = VIEW_TYPE_TODAY;
        } else {
            type = VIEW_TYPE_FUTURE_DAY;
        }
        Log.d(MainActivity.APP_TAG, "TO DO 3: getItemViewType(" + position + ") = " + type);
        // TO DO 3 END
        return type;
    }

    @Override
    public int getViewTypeCount() {
        // TO DO 4
        Log.d(MainActivity.APP_TAG, "TO DO 4: getViewTypeCount");
        return VIEW_TYPE_COUNT;
        // TO DO 4 END
    }
}