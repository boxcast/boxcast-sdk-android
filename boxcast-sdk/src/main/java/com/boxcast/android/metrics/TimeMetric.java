package com.boxcast.android.metrics;

import android.util.Log;

import org.json.JSONObject;

//
// BoxCast SDK for Android
// Created by camdenfullmer on 5/24/17.
//

public class TimeMetric extends Metric {

    private final String TAG = TimeMetric.class.getSimpleName();

    public TimeMetric(int time, int totalTime, int videoHeight) {
        super(time, totalTime, videoHeight);
    }

    @Override
    JSONObject toJSONObject() {
        JSONObject object = super.toJSONObject();
        try {
            object.put("action", "time");
        } catch (Exception e) {
            Log.e(TAG, "error creating JSON object: " + e.toString());
        }
        return object;
    }
}
