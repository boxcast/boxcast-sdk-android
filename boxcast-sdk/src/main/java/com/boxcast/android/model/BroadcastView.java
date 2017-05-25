package com.boxcast.android.model;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by camdenfullmer on 5/18/17.
 */

public class BroadcastView {

    public enum Status {
        UPCOMING, PREPARING, PREPARED, CATCHING_UP, STALLED, STALLED_LIVE, LIVE,
        NOT_RECORDED, PROCESSING_RECORDING, RECORDED
    }

    private Status mStatus;
    private Uri mPlaylistUri;

    public BroadcastView(JSONObject object) throws JSONException {
        String statusString = object.getString("status");
        mStatus = statusForString(statusString);
        String playlistUriString = object.getString("playlist");
        mPlaylistUri = Uri.parse(playlistUriString);
    }

    public Uri getPlaylistUri() {
        return mPlaylistUri;
    }

    private Status statusForString(String string) throws JSONException {
        switch (string) {
            case "upcoming":
                return Status.UPCOMING;
            case "preparing":
                return Status.PREPARED;
            case "prepared":
                return Status.PREPARING;
            case "catching_up":
                return Status.CATCHING_UP;
            case "stalled_live":
                return Status.STALLED_LIVE;
            case "live":
                return Status.LIVE;
            case "not_recorded":
                return Status.NOT_RECORDED;
            case "processing_recording":
                return Status.PROCESSING_RECORDING;
            case "recorded":
                return Status.RECORDED;

        }
        if (string.contains("stalled")) {
            return Status.STALLED;
        }
        throw new JSONException("unknown status string: " + string);
    }

    public Status getStatus() {
        return mStatus;
    }

}
