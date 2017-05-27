package com.boxcast.android.metrics;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.boxcast.client.android.BuildConfig;

import org.json.JSONObject;

//
// BoxCast SDK for Android
// Created by camdenfullmer on 5/24/17.
//

public class SetupMetric extends Metric {

    private final String TAG = SetupMetric.class.getSimpleName();
    private Context mContext;

    public SetupMetric(Context context) {
        super(0, 0, 0);
        mContext = context;
    }

    @Override
    JSONObject toJSONObject() {
        JSONObject object = super.toJSONObject();
        try {
            object.put("action", "setup");
            object.put("user_agent", getUserAgent());
            object.put("platform", "Android");
            object.put("browser", "Android");
            object.put("os", "Android");
            object.put("browser_version", Build.VERSION.RELEASE);
            object.put("model", Build.MODEL);
            object.put("product_type", "");
            object.put("system_version", Build.VERSION.RELEASE);
            object.put("vendor_identifier", Build.MANUFACTURER);
            object.put("player_version", BuildConfig.VERSION_NAME);
            object.put("host", getAppName() + " for Android");
            object.put("language", "");
            object.put("remote_ip", "");
        } catch (Exception e) {
            Log.e(TAG, "error creating JSON object: " + e.toString());
        }
        return object;
    }

    private String getUserAgent() {
        String appName = "Unknown";
        String appVersion = "Unknown";
        String appBundleId = "Unknown";
        String appBuild = "Unknown";

        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            appName = getAppName();
            appVersion = info.versionName;
            appBundleId = info.packageName;
            appBuild = Integer.toString(info.versionCode);
        } catch (Exception e) {
            Log.e(TAG, "error building user agent: " + e.toString());
        }

        return appName + "/" + appVersion + " (" + appBundleId + "; build:" + appBuild +
                "; Android " + Build.VERSION.RELEASE + ")  BoxCast SDK/" + BuildConfig.VERSION_NAME;
    }

    private String getAppName() throws PackageManager.NameNotFoundException {
        PackageManager manager = mContext.getPackageManager();
        PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
        ApplicationInfo applicationInfo = manager.getApplicationInfo(info.packageName, 0);
        return manager.getApplicationLabel(applicationInfo).toString();
    }
}
