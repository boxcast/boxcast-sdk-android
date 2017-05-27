package com.boxcast.android;

//
// BoxCast SDK for Android
// Created by camdenfullmer on 5/17/17.
//

/**
 * Interface definition for a callback to be invoked when a resource from BoxCast is loaded.
 * @param <T>
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
public interface BoxCastCallback<T> {

    /**
     * Called when the resource has been successfully loaded.
     * @param result The resource that was loaded.
     */
    public void onSuccess(final T result);

    /**
     * Called when the resource failed to load.
     * @param exception The exception that was thrown.
     */
    public void onFailure(final Exception exception);
}
