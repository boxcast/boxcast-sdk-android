package com.boxcast.android.metrics;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.boxcast.android.model.Broadcast;
import com.boxcast.android.model.BroadcastView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//
// BoxCast SDK for Android
// Created by camdenfullmer on 5/24/17.
//

public class MetricsConsumer {

    private final String TAG = MetricsConsumer.class.getSimpleName();
    private final String metricsUrl = "https://metrics.boxcast.com";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient mClient;
    private Broadcast mBroadcast;
    private BroadcastView mBroadcastView;
    private String mViewId;
    private Context mContext;

    public MetricsConsumer(Context context) {
        mContext = context;
        mClient = new OkHttpClient();
        mViewId = UUID.randomUUID().toString();
    }

    public void consume(Metric metric) {
        if (mBroadcast == null || mBroadcastView == null) {
            Log.e(TAG, "broadcast or broadcast view was not set");
            return;
        }

        Log.d(TAG, "consuming metric: " + metric.toString());

        JSONObject object = metric.toJSONObject();
        try {
            object.put("is_live", mBroadcastView.getStatus() == BroadcastView.Status.LIVE);
            object.put("account_id", mBroadcast.getAccountId());
            object.put("broadcast_id", mBroadcast.getId());
            object.put("channel_id", mBroadcast.getChannelId());
            object.put("view_id", mViewId);
            object.put("viewer_id", getViewerId());
        } catch (Exception e) {
            Log.e(TAG, "failed to create JSON object: " + e.toString());
        }

        RequestBody body = RequestBody.create(JSON, object.toString());
        Request request = new Request.Builder()
                .url(metricsUrl + "/player/interaction")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "failed to post metric: " + e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.d(TAG, "posted metric: " + response.toString());
                } else {
                    Log.e(TAG, "failed to post metric: " + response.toString());
                }
            }
        });
    }

    public void setBroadcast(Broadcast broadcast) {
        if (broadcast.getAccountId() == null) {
            Log.w(TAG, "a detailed broadcast is required");
            return;
        }
        mBroadcast = broadcast;
    }

    public void setBroadcastView(BroadcastView broadcastView) {
        mBroadcastView = broadcastView;
    }

    private String getViewerId() {
        SharedPreferences prefs = mContext.getSharedPreferences("com.boxcast.sdk-preferences", 0);
        if (!prefs.contains("viewer-id")) {
            String viewerId = UUID.randomUUID().toString();
            SharedPreferences.Editor editor =  prefs.edit();
            editor.putString("viewer-id", viewerId);
            editor.apply();
            return viewerId;
        } else {
            return prefs.getString("viewer-id", UUID.randomUUID().toString());
        }
    }

}
