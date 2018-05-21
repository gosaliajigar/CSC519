package edu.itu.csc.earthquakealert.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import edu.itu.csc.earthquakealert.R;
import edu.itu.csc.earthquakealert.database.DateProvider;
import edu.itu.csc.earthquakealert.utils.Utils;

/**
 * Fragment for AboutActivity.
 *
 * @author "Jigar Gosalia"
 *
 */
public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        Map<String, String> map = Utils.getEntry(getContext());
        TextView installText = (TextView)rootView.findViewById(R.id.install_text);
        TextView installDate = (TextView)rootView.findViewById(R.id.install_data);
        TextView lastViewText = (TextView)rootView.findViewById(R.id.last_text);
        TextView lastViewDate = (TextView)rootView.findViewById(R.id.last_data);
        if (map != null && map.size() > 0) {
            if (map.get(DateProvider.INSTALL_DATE) != null) {
                installText.setVisibility(View.VISIBLE);
                installDate.setVisibility(View.VISIBLE);
                installDate.setText(Utils.getFormattedDate(Utils.formatter, map.get(DateProvider.INSTALL_DATE)));
            } else {
                installText.setVisibility(View.INVISIBLE);
                installDate.setVisibility(View.INVISIBLE);
            }
            if (map.get(DateProvider.LAST_DATE) != null) {
                lastViewText.setVisibility(View.VISIBLE);
                lastViewDate.setVisibility(View.VISIBLE);
                lastViewDate.setText(Utils.getFormattedDate(Utils.formatter, map.get(DateProvider.LAST_DATE)));
            } else {
                lastViewText.setVisibility(View.INVISIBLE);
                lastViewDate.setVisibility(View.INVISIBLE);
            }
        } else {
            installText.setVisibility(View.INVISIBLE);
            installDate.setVisibility(View.INVISIBLE);
            lastViewText.setVisibility(View.INVISIBLE);
            lastViewDate.setVisibility(View.INVISIBLE);
        }
        return rootView;
    }
}
