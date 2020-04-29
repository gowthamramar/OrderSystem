package com.techware.clickkart.fragments;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techware.clickkart.R;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */

public class LastFragment extends Fragment {


    private Context context;
    private View rootView;

    //////////////////////
    public LastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getContext();
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        return rootView;
    }



}
