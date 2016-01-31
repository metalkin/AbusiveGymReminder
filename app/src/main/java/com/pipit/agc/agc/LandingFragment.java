package com.pipit.agc.agc;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Eric on 12/8/2015.
 */
public class LandingFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static TextView _lastLocationTxt;
    private Button _resetCountButton;
    private Button _updateLocationButton;
    private Context _context;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LandingFragment newInstance(int sectionNumber) {
        LandingFragment fragment = new LandingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public LandingFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.landing_fragment, container, false);

        final SharedPreferences prefs = getActivity().getApplicationContext().getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_MULTI_PROCESS);
        String lastLocation=prefs.getString("locationlist", "");
        _lastLocationTxt = (TextView) rootView.findViewById(R.id.lastLocation);
        _lastLocationTxt.setText(lastLocation);
        _resetCountButton = (Button) rootView.findViewById(R.id.button);
        _updateLocationButton = (Button) rootView.findViewById(R.id.updatelocationbutton);

        _resetCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("trackcount", 0);
                editor.putString("locationlist", "");
                editor.commit();
                updateLastLocation("");
            }
        });

        _updateLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(_context==null){
            _context=getActivity();
        }
    }

    public void updateLastLocation(String txt){
        _lastLocationTxt.setText(txt);
        SharedPreferences prefs = _context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("locationlist", txt);
        editor.commit();
    }

    public void addLineToLog(String txt){
        SharedPreferences prefs = _context.getSharedPreferences(Constants.SHARED_PREFS, Context.MODE_MULTI_PROCESS);
        String prev = prefs.getString("locationlist", " ");
        updateLastLocation(prev+"\n"+txt);
    }
}


