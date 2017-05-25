package com.boxcast.android.metrics;

import android.util.Log;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by camdenfullmer on 5/24/17.
 */

class Metric {

    private final String TAG = Metric.class.getSimpleName();
    private int mTime;
    private int mTotalTime;
    private int mVideoHeight;
    private Date mTimestamp;

    Metric(int time, int totalTime, int videoHeight) {
        mTime = time;
        mTotalTime = totalTime;
        mVideoHeight = videoHeight;
        mTimestamp = new Date();
    }

    JSONObject toJSONObject() {
        JSONObject object = new JSONObject();
        try {
            object.put("position", (double) mTime / 1000);
            object.put("duration", (double) mTotalTime / 1000);
            if (mVideoHeight != 0) {
                object.put("videoHeight", mVideoHeight);
            }
            TimeZone tz = TimeZone.getTimeZone("UTC");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            dateFormat.setTimeZone(tz);
            object.put("timestamp", dateFormat.format(mTimestamp));
        } catch (Exception e) {
            Log.e(TAG, "error creating JSON object: " + e.toString());
        }

        return object;
    }

    @Override
    public String toString() {
        return super.toString() +
                " time: " + Double.toString((double)mTime / 1000) + " seconds, " +
                "totalTime: " + Double.toString((double)mTotalTime / 1000) + " seconds, " +
                "videoHeight: " + Integer.toString(mVideoHeight) + " " +
                "timestamp: " + mTimestamp.toString();
    }

}
