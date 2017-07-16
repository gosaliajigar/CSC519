package edu.itu.primenumbers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 *
 * @author "Jigar Gosalia"
 */
public class MainActivityFragment extends Fragment {


    static final int MAX_UPPER = 1000000000;
    static final int MAX_DIFF = 100000;

    private static ArrayAdapter<String> mForecastAdapter = null;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create some dummy data for the ListView. Here's sample weekly forecast
        String[] data = {};
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(data));
        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source (like our dummy forecast) and
        // use it to populate the ListView it's attached to.
        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(), // The current context (this activity)
                R.layout.list_item_forecast, // The name of the layout ID
                R.id.list_item_forecast_textview, // The ID of the textview to populate.
                weekForecast);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // Get a reference to the ListView and attach this adapter to it.
        final ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);
        return rootView;
    }
    public static class PrimeNumberCalculationTask extends AsyncTask<String, Void, List<Integer>> {

        @Override
        protected List<Integer> doInBackground(String... data) {
            List<Integer> results = new ArrayList<Integer>();
            // Check if data is not null and has at least 2 data points
            // (city and forecast days) else display suitable error message
            if (data != null
                    && data.length > 1) {
                try {
                    List<Integer> integers = getPrimesBetween(Integer.valueOf(data[0]), Integer.valueOf(data[1]));
                    if (integers != null && integers.size() > 0) {
                        results.addAll(integers);
                    }
                } catch (Exception exception) {
                    // Log exception
                    Log.d("EXCEPTION",exception.toString());
                }
            } else {
                return new ArrayList<Integer>();
            }
            return results;
        }

        @Override
        protected void onPostExecute(List<Integer> result) {
            super.onPostExecute(result);
            if (result != null) {
                mForecastAdapter.clear();
                for (Integer integer : result) {
                    mForecastAdapter.add(String.valueOf(integer));
                }
                // New data is back from the server. Hooray!
            }
        }

        static List<Integer> getPrimesBetween(int lower, int upper) {
            List<Integer> primes = new ArrayList<Integer>();
            if (lower >= 1 && upper <= MAX_UPPER && upper - lower <= MAX_DIFF) {
                for (int current = lower; current <= upper; current++) {
                    int squareRoot = (int) Math.sqrt(current);
                    boolean isPrime = true;
                    for (int i = 2; i <= squareRoot; i++) {
                        if (current % i == 0) {
                            isPrime = false;
                            break;
                        }
                    }
                    if (isPrime) {
                        primes.add(current);
                    }
                }
            }
            return primes;
        }
    }

}
