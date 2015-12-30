package com.mauriciodinki.robothouse.ui.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mauriciodinki.robothouse.R;
import com.mauriciodinki.robothouse.domain.Movement;
import com.mauriciodinki.robothouse.io.MovementApiService;
import com.mauriciodinki.robothouse.ui.adapters.MovementAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovementFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<Movement> dataset;
    private RecyclerView mMovementRecords;
    private MovementAdapter adapter;
    private SwipeRefreshLayout swipeLayout;
    private MovementApiService apiService;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apiService.getMovementData(new MovementApiService.VolleyCallback() {
            @Override
            public void onSuccess(ArrayList<Movement> result) {
                dataset = new ArrayList<>();
                dataset = result;
                adapter.addAll(dataset);
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MovementAdapter(getActivity());
        apiService = new MovementApiService(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movement, container, false);

        mMovementRecords = (RecyclerView) root.findViewById(R.id.movement_recycler);

        setUpMovementRecords();

        swipeLayout = (SwipeRefreshLayout) root.findViewById(R.id.movement_swipe_layout);
        swipeLayout.setOnRefreshListener(this);

        return root;
    }

    private void setUpMovementRecords() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mMovementRecords.setLayoutManager(linearLayoutManager);
        mMovementRecords.setAdapter(adapter);

    }

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(), "Loading Movements", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                apiService.getMovementData(new MovementApiService.VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Movement> result) {
                        dataset = new ArrayList<>();
                        dataset = result;
                        adapter.updateList(dataset);
                    }
                });

                swipeLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
