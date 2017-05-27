package com.boxcast.android.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.boxcast.android.BoxCastCallback;
import com.boxcast.android.BoxCastClient;
import com.boxcast.android.model.Broadcast;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by camdenfullmer on 5/18/17.
 */

public class BroadcastActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_BROADCAST_ID = "com.boxcast.demo.BROADCAST_ID";
    public static final String EXTRA_CHANNEL_ID = "com.boxcast.demo.CHANNEL_ID";

    private String mBroadcastId;
    private String mChannelId;
    private BoxCastClient mClient;
    private SimpleDraweeView mImageView;
    private TextView mDescriptionTextView;
    private Button mPlayButton;
    private Broadcast mBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);

        mImageView = (SimpleDraweeView) findViewById(R.id.imageView);
        mDescriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        mPlayButton = (Button) findViewById(R.id.playButton);
        mPlayButton.setOnClickListener(this);

        // Get the Intent that started this activity and extract the broadcast and channel id.
        Intent intent = getIntent();
        mBroadcastId = intent.getStringExtra(BroadcastActivity.EXTRA_BROADCAST_ID);
        mChannelId = intent.getStringExtra(BroadcastActivity.EXTRA_CHANNEL_ID);

        mClient = BoxCastClient.getInstance();

        loadBroadcast();
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

    private void loadBroadcast() {
        mClient.getBroadcast(mChannelId, mBroadcastId, new BoxCastCallback<Broadcast>() {
            @Override
            public void onSuccess(Broadcast result) {
                mBroadcast = result;
                mImageView.setImageURI(result.getThumbnailUri());
                mDescriptionTextView.setText(result.getDescription());
                setTitle(mBroadcast.getName());
            }

            @Override
            public void onFailure(Exception exception) {
                Log.e("BroadcastActivity", "failed to load broadcast: " + exception.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mPlayButton) {
            Intent intent = new Intent(this, MediaPlayerActivity.class);
            intent.putExtra(MediaPlayerActivity.EXTRA_BROADCAST_ID, mBroadcast.getId());
            intent.putExtra(MediaPlayerActivity.EXTRA_CHANNEL_ID, mBroadcast.getChannelId());
            startActivity(intent);
        }
    }
}
