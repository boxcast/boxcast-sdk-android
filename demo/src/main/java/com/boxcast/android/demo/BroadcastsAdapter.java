package com.boxcast.android.demo;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boxcast.android.model.Broadcast;

import java.util.ArrayList;

/**
 * Created by camdenfullmer on 5/17/17.
 */

class BroadcastsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Broadcast> mLiveBroadcasts;
    private ArrayList<Broadcast> mArchivedBroadcasts;

    private static class BroadcastHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextView;
        private Broadcast mBroadcast;

        BroadcastHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        void bindBroadcast(Broadcast broadcast) {
            mBroadcast = broadcast;
            mTextView.setText(broadcast.getName());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(itemView.getContext(), BroadcastActivity.class);
            intent.putExtra(BroadcastActivity.EXTRA_BROADCAST_ID, mBroadcast.getId());
            itemView.getContext().startActivity(intent);
        }
    }

    private static class SectionHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        SectionHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(R.id.textView);
        }

        void bindTitle(String title) {
            mTextView.setText(title);
        }
    }

    BroadcastsAdapter() {
        mLiveBroadcasts = new ArrayList<>();
        mArchivedBroadcasts = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View inflatedView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.broadcasts_section, parent, false);
            return new SectionHolder(inflatedView);
        } else {
            View inflatedView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.broadcasts_row, parent, false);
            return new BroadcastHolder(inflatedView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BroadcastHolder) {
            Broadcast broadcast = getBroadcast(position);
            ((BroadcastHolder) holder).bindBroadcast(broadcast);
        } else if (holder instanceof SectionHolder) {
            String title;
            if (position == 0) {
                title = "Live Broadcasts";
            } else {
                title = "Archived Broadcasts";
            }
            ((SectionHolder) holder).bindTitle(title);
        }
    }

    @Override
    public int getItemCount() {
        return  mLiveBroadcasts.size() + mArchivedBroadcasts.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (isSection(position)) {
            return 0;
        } else {
            return 1;
        }
    }

    void setLiveBroadcasts(ArrayList<Broadcast> broadcasts) {
        mLiveBroadcasts = broadcasts;
    }

    void setArchivedBroadcasts(ArrayList<Broadcast> broadcasts) {
        mArchivedBroadcasts = broadcasts;
    }

    private boolean isSection(int position) {
        return position == 0 || position == mLiveBroadcasts.size() + 1;
    }

    private Broadcast getBroadcast(int position) {
        if (isSection(position)) {
            Log.e("NO", "OH NO");
        }

        if (position <= mLiveBroadcasts.size()) {
            return mLiveBroadcasts.get(position - 1);
        } else {
            return mArchivedBroadcasts.get(position - mLiveBroadcasts.size() - 2);
        }
    }
}
