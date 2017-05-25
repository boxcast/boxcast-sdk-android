package com.boxcast.android.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.boxcast.android.BoxCastCallback;
import com.boxcast.android.BoxCastClient;
import com.boxcast.android.model.BroadcastList;

public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID="hbk5jnhapr0pdqluujtd";
    private BroadcastsAdapter mAdapter;
    private BoxCastClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mClient = BoxCastClient.getInstance();

        setTitle("Broadcasts");
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new BroadcastsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        getBroadcasts();
    }

    void getBroadcasts() {
        mClient.getLiveBroadcasts(CHANNEL_ID, new BoxCastCallback<BroadcastList>() {

            @Override
            public void onSuccess(BroadcastList result) {
                mAdapter.setLiveBroadcasts(result.getBroadcasts());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception exception) {
                Log.e("MainActivity", "error getting live broadcasts: " + exception.toString());
            }
        });

        mClient.getArchivedBroadcasts(CHANNEL_ID, new BoxCastCallback<BroadcastList>() {

            @Override
            public void onSuccess(BroadcastList result) {
                mAdapter.setArchivedBroadcasts(result.getBroadcasts());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception exception) {
                Log.e("MainActivity", "error getting live broadcasts: " + exception.toString());
            }
        });
    }
}
