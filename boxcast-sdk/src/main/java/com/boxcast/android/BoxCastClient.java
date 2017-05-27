package com.boxcast.android;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.boxcast.android.model.Broadcast;
import com.boxcast.android.model.BroadcastList;
import com.boxcast.android.model.BroadcastView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//
// BoxCast SDK for Android
// Created by camdenfullmer on 5/17/17.
//

/**
 * Use this class to make remote calls to the BoxCast API endpoint.
 */
public class BoxCastClient {

    private static BoxCastClient instance = null;
    private final OkHttpClient mClient = new OkHttpClient();
    private final String apiUrl = "https://api.boxcast.com";

    private BoxCastClient() {
    }

    /**
     * Use this method to get the singleton instance for making API calls.
     * @return The singleton instance.
     */
    public static BoxCastClient getInstance() {
        if (instance == null) {
            instance = new BoxCastClient();
        }
        return instance;
    }

    /**
     * Gets the live broadcasts for a channel.
     * @param channelId The channel id to list broadcasts from.
     * @param callback The callback to be called when finished loading the broadcasts.
     */
    public void getLiveBroadcasts(String channelId, final BoxCastCallback<BroadcastList> callback) {
        QueryBuilder builder = new QueryBuilder();
        builder.append(QueryBuilder.Logic.OR, "timeframe", "current");
        String query = builder.build();
        findBroadcasts(channelId, query, callback);
    }

    /**
     * Gets the archived broadcasts for a channel.
     * @param channelId The channel id to list broadcasts from.
     * @param callback The callback to be called when finished loading the broadcasts.
     */
    public void getArchivedBroadcasts(String channelId, final BoxCastCallback<BroadcastList> callback) {
        QueryBuilder builder = new QueryBuilder();
        builder.append(QueryBuilder.Logic.OR, "timeframe", "past");
        String query = builder.build();
        findBroadcasts(channelId, query, callback);
    }

    /**
     * Gets a detailed broadcast.
     * @param channelId The channel id.
     * @param broadcastId The broadcast id.
     * @param callback The callback to be called when finished loading the broadcast.
     */
    public void getBroadcast(final String channelId, String broadcastId, final BoxCastCallback<Broadcast> callback) {
        Request request = new Request.Builder()
                .url(apiUrl + "/broadcasts/" + broadcastId)
                .get()
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    JSONObject object = new JSONObject(responseData);
                    final Broadcast broadcast = new Broadcast(channelId, object);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(broadcast);
                        }
                    });

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(e);
                        }
                    });
                }
            }
        });
    }

    /**
     * Gets a view into a broadcast.
     * @param broadcastId The broadcast id.
     * @param callback The callback to be called when finished loading the broadcast view.
     */
    public void getBroadcastView(String broadcastId, final BoxCastCallback<BroadcastView> callback) {
        Request request = new Request.Builder()
                .url(apiUrl + "/broadcasts/" + broadcastId + "/view")
                .get()
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    JSONObject object = new JSONObject(responseData);
                    final BroadcastView broadcastView = new BroadcastView(object);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(broadcastView);
                        }
                    });

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(e);
                        }
                    });
                }
            }
        });
    }

    private void findBroadcasts(final String channelId, String query, final BoxCastCallback<BroadcastList> callback) {
        @SuppressWarnings("ConstantConditions")
        HttpUrl url = HttpUrl.parse(apiUrl + "/channels/" + channelId + "/broadcasts")
                .newBuilder()
                .addQueryParameter("q", query)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull final IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    JSONArray array = new JSONArray(responseData);
                    final BroadcastList list = new BroadcastList(channelId, array);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(list);
                        }
                    });

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(e);
                        }
                    });
                }
            }
        });
    }

    private void runOnUiThread(Runnable task) {
        new Handler(Looper.getMainLooper()).post(task);
    }

}
