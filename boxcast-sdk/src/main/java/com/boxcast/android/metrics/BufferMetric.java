package com.boxcast.android.metrics;

import android.util.Log;

import org.json.JSONObject;

//
// BoxCast SDK for Android
// Created by camdenfullmer on 5/24/17.
//

public class BufferMetric extends Metric {

    private final String TAG = BufferMetric.class.getSimpleName();

    public BufferMetric(int time, int totalTime, int videoHeight) {
        super(time, totalTime, videoHeight);
    }

    @Override
    JSONObject toJSONObject() {
        JSONObject object = super.toJSONObject();
        try {
            object.put("action", "buffer");
        } catch (Exception e) {
            Log.e(TAG, "error creating JSON object: " + e.toString());
        }
        return object;
    }
}
