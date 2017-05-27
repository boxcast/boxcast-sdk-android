package com.boxcast.android.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

//
// BoxCast SDK for Android
// Created by camdenfullmer on 5/17/17.
//

/**
 * Class represents a BoxCast broadcast.
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

    /**
     * Returns the name for a broadcast.
     * @return String
     */
    public @NonNull String getName() {
        return mName;
    }

    /**
     * Returns the description for a broadcast.
     * @return String
     */
    public @NonNull String getDescription() {
        return mDescription;
    }

    /**
     * Returns the ID for the broadcast.
     * @return String
     */
    public @NonNull String getId() {
        return mId;
    }

    /**
     * Returns the thumbnail URI for the broadcast.
     * @return Uri
     */
    public @NonNull Uri getThumbnailUri() {
        return mThumbnailUri;
    }

    /**
     * Returns the channel ID for the broadcast.
     * @return String
     */
    public @NonNull String getChannelId() {
        return mChannelId;
    }

    /**
     * Returns the account ID for the broadcast.
     * @return String
     */
    public @Nullable String getAccountId() {
        return mAccountId;
    }
}
