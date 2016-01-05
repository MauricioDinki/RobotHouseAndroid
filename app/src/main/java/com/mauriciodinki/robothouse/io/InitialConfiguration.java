package com.mauriciodinki.robothouse.io;

import android.app.Application;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by mauriciodinki on 31/12/15.
 */
public class InitialConfiguration extends Application {

    private static InitialConfiguration instance;
    private RequestQueue requestQueue;
    private Cache cache;
    private Network network;

    @Override
    public void onCreate() {
        super.onCreate();

        cache = new DiskBasedCache(getCacheDir(), 1024*1024);
        network = new BasicNetwork(new HurlStack());

        requestQueue = new RequestQueue(cache, network);
        instance = this;

        requestQueue.start();

        Parse.initialize(this, "Aww7CW93skUK4TRUm3ejKF9DvlXXGaBao1EysDlj", "pr7Op4F58U0Daj5sCVGdcMao08wBJCngIC8szysE");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public synchronized static InitialConfiguration getInstance() {
        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
