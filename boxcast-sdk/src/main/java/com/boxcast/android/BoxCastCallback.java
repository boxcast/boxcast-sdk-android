package com.boxcast.android;

/**
 * Created by camdenfullmer on 5/17/17.
 */

@SuppressWarnings("UnnecessaryInterfaceModifier")
public interface BoxCastCallback<T> {

    public void onSuccess(final T result);

    public void onFailure(final Exception exception);
}
