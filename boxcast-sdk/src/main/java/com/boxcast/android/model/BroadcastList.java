package com.boxcast.android.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by camdenfullmer on 5/17/17.
 */

public class BroadcastList {

    private ArrayList<Broadcast> mBroadcasts;

    public BroadcastList(JSONArray array) throws JSONException {
        mBroadcasts = new ArrayList<>();
        for (int i = 0; i < array.length(); ++i) {
            JSONObject obj = array.getJSONObject(i);
            Broadcast broadcast = new Broadcast(obj);
            mBroadcasts.add(broadcast);
        }
    }

    public ArrayList<Broadcast> getBroadcasts() {
        return mBroadcasts;
    }

}
