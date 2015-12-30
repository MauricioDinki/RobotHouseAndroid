package com.mauriciodinki.robothouse.io;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mauriciodinki.robothouse.domain.Movement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mauriciodinki on 30/12/15.
 */
public class MovementApiService {

    Context context;
    String URL = "http://robot.ngrok.io/movement";
    ArrayList <Movement> movements;

    public MovementApiService(Context context) {
        this.context = context;
    }


    public void getMovementData(final VolleyCallback callback){

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                movements = parser(response);
                callback.onSuccess(movements);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Request Error: ", error.toString());
            }
        });

        queue.add(request);
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

    public interface VolleyCallback {
        void onSuccess(ArrayList<Movement> result);
    }

}

