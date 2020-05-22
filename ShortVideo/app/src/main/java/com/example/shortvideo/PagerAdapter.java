package com.example.shortvideo;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.VideoViewHolder> {

    private static final String TAG = "PagerAdapter";

    private List<VideoInfo> videoInfoList;
    private int mNumberItems;
    private final ListItemClickListener mOnClickListener;
    private static int viewHolderCount;


    public PagerAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
        viewHolderCount = 0;
        mNumberItems = 0;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.pager_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        VideoViewHolder viewHolder = new VideoViewHolder(view);

        viewHolderCount++;
        Log.i(TAG, "onCreateView:" + videoInfoList.toString());
        Log.i(TAG, "onCreateView");
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.bind(position);
        Log.i(TAG, "onBindViewHolder  " + position + "    " + mNumberItems);
        VideoInfo videoInfo = videoInfoList.get(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public void setData(List<VideoInfo> videoInfoList1) {
        mNumberItems = videoInfoList1.size();
        videoInfoList = videoInfoList1;
        notifyDataSetChanged();
        Log.i(TAG, videoInfoList.toString());
        Log.i(TAG, "setData");
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public VideoView videoView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.vp_videoView);

            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            VideoInfo videoInfo = videoInfoList.get(position);
            videoView.setVideoURI(Uri.parse(videoInfo.feedurl));
            videoView.start();
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(clickedPosition);
            }
            if (videoView.isPlaying())
                videoView.pause();
            else
                videoView.start();
//                videoView.resume();

        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
