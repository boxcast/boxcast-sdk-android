package com.boxcast.android.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.MediaController;

import com.boxcast.android.BoxCastCallback;
import com.boxcast.android.BoxCastClient;
import com.boxcast.android.model.Broadcast;
import com.boxcast.android.model.BroadcastView;
import com.boxcast.android.view.BoxCastVideoView;

/**
 * Created by camdenfullmer on 5/18/17.
 */

public class MediaPlayerActivity extends AppCompatActivity {

    public static final String EXTRA_BROADCAST_ID = "com.boxcast.demo.EXTRA_BROADCAST_ID";
    public static final String EXTRA_CHANNEL_ID= "com.boxcast.demo.EXTRA_CHANNEL_ID";

    private BoxCastVideoView mVideoView;
    private String mBroadcastId;
    private String mChannelId;
    private BoxCastClient mClient;
    private Broadcast mBroadcast;
    private BroadcastView mBroadcastView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mVideoView = (BoxCastVideoView) findViewById(R.id.videoView);
        MediaController controller = new MediaController(this);
        controller.setAnchorView(mVideoView);
        mVideoView.setMediaController(controller);

        // Get the Intent that started this activity and extract the broadcast and channel id.
        mBroadcastId = getIntent().getStringExtra(MediaPlayerActivity.EXTRA_BROADCAST_ID);
        mChannelId = getIntent().getStringExtra(MediaPlayerActivity.EXTRA_CHANNEL_ID);

        mClient = BoxCastClient.getInstance();

        loadBroadcast();
        loadBroadcastView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void play() {
        mVideoView.setBroadcast(mBroadcast);
        mVideoView.setBroadcastView(mBroadcastView);
        mVideoView.start();
    }

    private void loadBroadcast() {
        mClient.getBroadcast(mChannelId, mBroadcastId, new BoxCastCallback<Broadcast>() {
            @Override
            public void onSuccess(Broadcast result) {
                mBroadcast = result;

                if (mBroadcast != null && mBroadcastView != null) {
                    play();
                }
            }

            @Override
            public void onFailure(Exception exception) {
                Log.e("BroadcastActivity", "failed to load broadcast: " + exception.toString());
            }
        });
    }

    private void loadBroadcastView() {
        mClient.getBroadcastView(mBroadcastId, new BoxCastCallback<BroadcastView>() {
            @Override
            public void onSuccess(BroadcastView result) {
                mBroadcastView = result;

                if (mBroadcast != null && mBroadcastView != null) {
                    play();
                }
            }

            @Override
            public void onFailure(Exception exception) {
                Log.e("BroadcastActivity", "failed to load broadcast view: " + exception.toString());
            }
        });
    }
}
