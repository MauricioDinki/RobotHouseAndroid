package com.mauriciodinki.robothouse.ui.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mauriciodinki.robothouse.R;
import com.mauriciodinki.robothouse.domain.Movement;
import com.mauriciodinki.robothouse.ui.adapters.MovementAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovementFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ArrayList<Movement> dataset;
    private RecyclerView mMovementRecords;
    private MovementAdapter adapter;
    private SwipeRefreshLayout swipeLayout;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MovementAdapter(getActivity());
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
        mMovementRecords.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMovementRecords.setAdapter(adapter);

    }

    public ArrayList<Movement> parser (JSONArray response) {

        ArrayList<Movement> moveAux = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {

            Movement movement = new Movement();
            try {
                JSONObject jsonObject = (JSONObject) response.get(i);
                movement.setDate(jsonObject.getString("full_date"));
                movement.setTime(jsonObject.getString("full_time"));
                moveAux.add(movement);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return moveAux;
    }

    public void getData () {

        String URL = "http://robot.ngrok.io/movement";

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                dataset = new ArrayList<>();
                dataset = parser(response);
                /*Log.e("Data del Request", Integer.toString(dataset.size()));*/
                adapter.addAll(dataset);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error del request", error.toString());
            }
        });

        queue.add(request);

    }

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(), "Refresh", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*Log.e("Data Actual", Integer.toString(adapter.getDataset().size()));
                adapter.clearAll();*/


                String URL = "http://robot.ngrok.io/movement";

                RequestQueue queue = Volley.newRequestQueue(getActivity());

                JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dataset = new ArrayList<>();
                        dataset = parser(response);
                        adapter.updateList(dataset);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error del request", error.toString());
                    }
                });

                queue.add(request);


                swipeLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
