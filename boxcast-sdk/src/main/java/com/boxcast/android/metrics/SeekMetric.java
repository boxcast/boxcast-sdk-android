package com.boxcast.android.metrics;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by camdenfullmer on 5/24/17.
 */

public class SeekMetric extends Metric {

    private final String TAG = SeekMetric.class.getSimpleName();

    private int mToTime;

    public SeekMetric(int time, int totalTime, int videoHeight, int toTime) {
        super(time, totalTime, videoHeight);
        mToTime = toTime;
    }

    @Override
    JSONObject toJSONObject() {
        JSONObject object = super.toJSONObject();
        try {
            object.put("action", "seek");
            object.put("offset", (double)mToTime / 1000);
        } catch (Exception e) {
            Log.e(TAG, "error creating JSON object: " + e.toString());
        }
        return object;
    }
}
