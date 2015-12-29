package com.mauriciodinki.robothouse.ui.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mauriciodinki.robothouse.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovementFragment extends Fragment {


    public MovementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movement, container, false);
    }

}
