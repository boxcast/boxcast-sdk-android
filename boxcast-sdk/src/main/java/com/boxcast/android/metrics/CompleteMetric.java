package com.boxcast.android.metrics;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by camdenfullmer on 5/24/17.
 */

public class CompleteMetric extends Metric {

    private final String TAG = CompleteMetric.class.getSimpleName();

    public CompleteMetric(int time, int totalTime, int videoHeight) {
        super(time, totalTime, videoHeight);
    }

    @Override
    JSONObject toJSONObject() {
        JSONObject object = super.toJSONObject();
        try {
            object.put("action", "complete");
        } catch (Exception e) {
            Log.e(TAG, "error creating JSON object: " + e.toString());
        }
        return object;
    }
}
