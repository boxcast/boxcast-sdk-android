package com.boxcast.android.metrics;

import android.util.Log;

import org.json.JSONObject;

//
// BoxCast SDK for Android
// Created by camdenfullmer on 5/24/17.
//

public class PlayMetric extends Metric {

    private final String TAG = PlayMetric.class.getSimpleName();

    public PlayMetric(int time, int totalTime, int videoHeight) {
        super(time, totalTime, videoHeight);
    }

    @Override
    JSONObject toJSONObject() {
        JSONObject object = super.toJSONObject();
        try {
            object.put("action", "play");
        } catch (Exception e) {
            Log.e(TAG, "error creating JSON object: " + e.toString());
        }
        return object;
    }
}
