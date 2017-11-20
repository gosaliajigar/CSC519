package test.tryfragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("TEST", "F : onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("TEST", "F : onDetach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST", "F : onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TEST", "F : onStart");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("TEST", "F : onActivityCreated");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TEST", "F : onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("TEST", "F : onPause");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("TEST", "F : onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("TEST", "F : onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TEST", "F : onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("TEST", "F : onDestroyView");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("TEST", "F : onCreateView");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
