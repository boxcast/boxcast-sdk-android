package com.boxcast.android.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//
// BoxCast SDK for Android
// Created by camdenfullmer on 5/17/17.
//

/**
 * Class represents a list of BoxCast broadcasts.
 */
public class BroadcastList {

    private ArrayList<Broadcast> mBroadcasts;

    public BroadcastList(String channelId, JSONArray array) throws JSONException {
        mBroadcasts = new ArrayList<>();
        for (int i = 0; i < array.length(); ++i) {
            JSONObject obj = array.getJSONObject(i);
            Broadcast broadcast = new Broadcast(channelId, obj);
            mBroadcasts.add(broadcast);
        }
    }

    /**
     * Returns the list of broadcasts.
     * @return ArrayList
     */
    public ArrayList<Broadcast> getBroadcasts() {
        return mBroadcasts;
    }

}
