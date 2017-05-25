package com.boxcast.android.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.VideoView;

import com.boxcast.android.metrics.BufferMetric;
import com.boxcast.android.metrics.CompleteMetric;
import com.boxcast.android.metrics.MetricsConsumer;
import com.boxcast.android.metrics.PauseMetric;
import com.boxcast.android.metrics.PlayMetric;
import com.boxcast.android.metrics.SeekMetric;
import com.boxcast.android.metrics.SetupMetric;
import com.boxcast.android.metrics.TimeMetric;
import com.boxcast.android.model.Broadcast;
import com.boxcast.android.model.BroadcastView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by camdenfullmer on 5/24/17.
 */

public class BoxCastVideoView extends VideoView implements MediaPlayer.OnCompletionListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener {

    private MetricsConsumer mMetricsConsumer;
    private int mTotalTime;
    private int mLastPlayTime;
    private boolean sentSetupMetric;
    private Timer mIntervalTimer;
    private int mVideoHeight;

    public BoxCastVideoView(Context context) {
        super(context);
        mMetricsConsumer = new MetricsConsumer(context.getApplicationContext());
        mTotalTime = 0;
        mLastPlayTime = -1;
        mVideoHeight = 0;
        setUpListeners();
    }

    public BoxCastVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMetricsConsumer = new MetricsConsumer(context.getApplicationContext());
        mTotalTime = 0;
        mLastPlayTime = -1;
        mVideoHeight = 0;
        setUpListeners();
    }

    public BoxCastVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mMetricsConsumer = new MetricsConsumer(context.getApplicationContext());
        mTotalTime = 0;
        mLastPlayTime = -1;
        mVideoHeight = 0;
        setUpListeners();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public BoxCastVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mMetricsConsumer = new MetricsConsumer(context.getApplicationContext());
        mTotalTime = 0;
        mLastPlayTime = -1;
        mVideoHeight = 0;
        setUpListeners();
    }

    @Override
    public void finalize() throws Throwable {
        super.finalize();
    }

    public void setBroadcast(Broadcast broadcast) {
        mMetricsConsumer.setBroadcast(broadcast);
    }

    public void setBroadcastView(BroadcastView broadcastView) {
        mMetricsConsumer.setBroadcastView(broadcastView);
        setVideoURI(broadcastView.getPlaylistUri());
    }

    @Override
    public void start() {
        int currentPosition = getCurrentPosition();
        if (!sentSetupMetric) {
            mMetricsConsumer.consume(new SetupMetric(getContext().getApplicationContext()));
            sentSetupMetric = true;
        }
        if (mIntervalTimer == null) {
            startIntervalTimer();
        }
        if (mLastPlayTime == -1) {
            mMetricsConsumer.consume(new PlayMetric(currentPosition, mTotalTime, mVideoHeight));
        }

        updateTotalTime(currentPosition);
        mLastPlayTime = currentPosition;

        super.start();
    }

    @Override
    public void pause() {
        int currentPosition = getCurrentPosition();
        updateTotalTime(currentPosition);
        mMetricsConsumer.consume(new PauseMetric(currentPosition, mTotalTime, mVideoHeight));

        super.pause();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelIntervalTimer();
    }

    @Override
    public void seekTo(int msec) {
        int currentPosition = getCurrentPosition();
        updateTotalTime(currentPosition);
        mMetricsConsumer.consume(new SeekMetric(currentPosition, mTotalTime, mVideoHeight, msec));

        super.seekTo(msec);
    }

    private void setUpListeners() {
        setOnCompletionListener(this);
        setOnPreparedListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setOnInfoListener(this);
        }
    }

    private void updateTotalTime(int currentPosition) {
        if (mLastPlayTime != -1) {
            mTotalTime += currentPosition - mLastPlayTime;
            mLastPlayTime = -1;
        }
    }

    private void startIntervalTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                int totalTime;
                int currentPosition = getCurrentPosition();
                if (mLastPlayTime != -1) {
                    totalTime = mTotalTime + currentPosition - mLastPlayTime;
                } else {
                    totalTime = mTotalTime;
                }
                mMetricsConsumer.consume(new TimeMetric(currentPosition, totalTime, mVideoHeight));
            }
        };
        mIntervalTimer = new Timer();
        mIntervalTimer.schedule(task, 60 * 1000, 60 * 1000);
    }

    private void cancelIntervalTimer() {
        mIntervalTimer.cancel();
        mIntervalTimer = null;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mMetricsConsumer.consume(new CompleteMetric(getCurrentPosition(), mTotalTime, mVideoHeight));
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        int currentPosition = getCurrentPosition();
        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
            updateTotalTime(currentPosition);
            mMetricsConsumer.consume(new BufferMetric(currentPosition, mTotalTime, mVideoHeight));
        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
            mLastPlayTime = getCurrentPosition();
            mMetricsConsumer.consume(new PlayMetric(currentPosition, mTotalTime, mVideoHeight));
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setOnVideoSizeChangedListener(this);
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        mVideoHeight = height;
    }
}
