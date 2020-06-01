package com.example.shortvideo;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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
        public ImageView play_arrow;
        public ImageView avatar;
        public TextView likecount;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.vp_videoView);
//            play_arrow=itemView.findViewById(R.id.play_arrow);
            avatar=itemView.findViewById(R.id.avatar);
            likecount=itemView.findViewById(R.id.likecount);

            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            VideoInfo videoInfo = videoInfoList.get(position);
            videoView.setVideoURI(Uri.parse(videoInfo.feedurl));
            videoView.start();
            Context context=avatar.getContext();
            Glide.with(context)
                    .load(videoInfo.avatar)
                .apply(RequestOptions.circleCropTransform())
//                .bitmapTransform(new CropSquareTransformation())
//                    .apply(RequestOptions.centerCropTransform())
//                .apply(RequestOptions.bitmapTransform(new CropSquareTransformation()))
                    .into(avatar);
            likecount.setText(videoInfo.likecount);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(clickedPosition);
            }
            if (videoView.isPlaying()) {
                videoView.pause();
//                play_arrow.setVisibility(View.VISIBLE);
            }
            else {
                videoView.start();
//                play_arrow.setVisibility(View.GONE);
//                videoView.resume();
            }
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
