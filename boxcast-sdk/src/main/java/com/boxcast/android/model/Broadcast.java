package com.boxcast.android.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by camdenfullmer on 5/17/17.
 */

public class Broadcast {

    private String mId;
    private String mName;
    private String mDescription;
    private String mAccountId;
    private String mChannelId;
    private Uri mThumbnailUri;

    public Broadcast(JSONObject object) throws JSONException {
        mId = object.getString("id");
        mName = object.getString("name");
        mDescription = object.getString("description");
        String thumbnailUriString = object.getString("preview");
        mThumbnailUri = Uri.parse(thumbnailUriString);
        mChannelId = object.getString("channel_id");
        if (object.has("account_id")) {
            mAccountId = object.getString("account_id");
        }
    }

    public @NonNull String getName() {
        return mName;
    }

    public @NonNull String getDescription() {
        return mDescription;
    }

    public @NonNull String getId() {
        return mId;
    }

    public @NonNull Uri getThumbnailUri() {
        return mThumbnailUri;
    }

    public @NonNull String getChannelId() {
        return mChannelId;
    }

    public @Nullable String getAccountId() {
        return mAccountId;
    }
}
