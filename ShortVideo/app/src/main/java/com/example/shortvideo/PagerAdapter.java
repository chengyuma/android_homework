package com.example.shortvideo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.List;

import android.os.Handler;

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
        public ImageView avatar;
        public TextView likecount;
        public TextView nickname;
        public TextView description;
        public ImageView heart_icon;
        public ImageView heart_icon_red;
        public RelativeLayout heart_layout;
        public ImageView play_icon;
        public ImageView red_heart;
        private int clickCount = 0;
        private Handler handler;
        private boolean like = false;

        @SuppressLint("ClickableViewAccessibility")
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.vp_videoView);
            avatar = itemView.findViewById(R.id.avatar);
            likecount = itemView.findViewById(R.id.likecount);
            nickname = itemView.findViewById(R.id.nickname);
            description = itemView.findViewById(R.id.description);
            heart_icon = itemView.findViewById(R.id.heart_icon);
            heart_icon_red = itemView.findViewById(R.id.heart_icon_red);
            heart_icon_red.setVisibility(View.GONE);
            heart_layout = itemView.findViewById(R.id.heart_layout);
            play_icon = itemView.findViewById(R.id.play_icon);
            play_icon.setVisibility(View.GONE);
            red_heart = itemView.findViewById(R.id.red_heart);
            red_heart.setVisibility(View.GONE);
            heart_layout.setOnClickListener(this);
            handler = new Handler();
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.i(TAG, "setOnTouchListener");
                    clickCount++;
                    int timeout = 400;
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (clickCount == 1) {
                                if (videoView.isPlaying()) {
                                    videoView.pause();
                                    play_icon.setVisibility(View.VISIBLE);
                                    ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(play_icon, "scaleX", 2, 1);
                                    ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(play_icon, "scaleY", 2, 1);
                                    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(play_icon, "alpha", 0.7f, 1);
                                    AnimatorSet animatorSet = new AnimatorSet();
                                    animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
                                    animatorSet.setDuration(150);
                                    animatorSet.start();
                                } else {
                                    videoView.start();
                                    ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(play_icon, "alpha", 1, 0);
                                    alphaAnimator.setDuration(200);
                                    alphaAnimator.start();
                                }
                            } else if (clickCount == 2) {
                                if (!like) {
                                    int num = Integer.parseInt(likecount.getText().toString());
                                    num++;
                                    likecount.setText(String.valueOf(num));
                                    heart_icon_red.setVisibility(View.VISIBLE);
                                    ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(heart_icon_red, "scaleX", 0.3f, 1.3f);
                                    ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(heart_icon_red, "scaleY", 0.3f, 1.3f);
                                    AnimatorSet animatorSet = new AnimatorSet();
                                    animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
                                    animatorSet.setDuration(150);
                                    animatorSet.start();
                                    scaleXAnimator = ObjectAnimator.ofFloat(heart_icon_red, "scaleX", 1.3f, 1);
                                    scaleYAnimator = ObjectAnimator.ofFloat(heart_icon_red, "scaleY", 1.3f, 1);
                                    animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
                                    animatorSet.start();
                                    like = true;
                                }

//                                ImageView imageView = new ImageView(itemView.getContext());
//                                imageView.setImageDrawable(itemView.getResources().getDrawable(R.mipmap.heart_icon_red));  //这里如果用background属性，添加在边缘图片会变形
////                                ViewGroup viewGroup= (ViewGroup) itemView.getParent();
////                                viewGroup.addView(imageView);
//                                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(56, 48);
//                                layoutParams.leftMargin = (int) event.getX() - 56 / 2;
//                                layoutParams.topMargin = (int) event.getY() - 48 / 2;
//                                imageView.setLayoutParams(layoutParams);
                                ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(red_heart.getLayoutParams());
                                int dpTop = (int) event.getY() - 56 / 2;
                                int dpLeft = (int) event.getX() - 48 / 2;
                                margin.setMargins(dpLeft, dpTop, 0, 0);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin);
                                layoutParams.width = 120;
                                layoutParams.height = 120;
                                red_heart.setLayoutParams(layoutParams);
                                red_heart.setVisibility(View.VISIBLE);
                                ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(red_heart, "scaleX", 2.5f, 1.5f);
                                ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(red_heart, "scaleY", 2.5f, 1.5f);
                                ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(red_heart, "alpha", 0.7f, 1);
                                AnimatorSet animatorSet = new AnimatorSet();
                                animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
                                animatorSet.setDuration(300);
                                animatorSet.start();
                                scaleXAnimator = ObjectAnimator.ofFloat(red_heart, "scaleX", 1.5f, 4.3f);
                                scaleYAnimator = ObjectAnimator.ofFloat(red_heart, "scaleY", 1.5f, 4.3f);
                                alphaAnimator = ObjectAnimator.ofFloat(red_heart, "alpha", 1, 0);
                                animatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
                                animatorSet.setDuration(500);
                                animatorSet.start();
                            }
                            handler.removeCallbacksAndMessages(null);
                            //清空handler延时，并防内存泄漏
                            clickCount = 0;//计数清零
                        }
                    }, timeout);//延时timeout后执行run方法中的代码

                    return false;
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void bind(int position) {
            VideoInfo videoInfo = videoInfoList.get(position);
            videoView.setVideoURI(Uri.parse(videoInfo.feedurl));
            videoView.start();
            Context context = avatar.getContext();
            Glide.with(context)
                    .load(videoInfo.avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatar);

            nickname.setText("@" + videoInfo.nickname);
            description.setText(videoInfo.description);
            if (like) {
                int num = Integer.parseInt(videoInfo.likecount);
                num++;
                likecount.setText(String.valueOf(num));
            } else {
                likecount.setText(videoInfo.likecount);
            }
        }

        @Override
        public void onClick(View v) {
            Log.i(TAG, v.toString());
            if (v == heart_layout) {
                if (like) {
                    int num = Integer.parseInt(likecount.getText().toString());
                    num--;
                    likecount.setText(String.valueOf(num));
                    ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(heart_icon_red, "scaleX", 1, 0);
                    ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(heart_icon_red, "scaleY", 1, 0);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
                    animatorSet.setDuration(150);
                    animatorSet.start();
                    like = false;
                } else {
                    int num = Integer.parseInt(likecount.getText().toString());
                    num++;
                    likecount.setText(String.valueOf(num));
                    heart_icon_red.setVisibility(View.VISIBLE);
                    ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(heart_icon_red, "scaleX", 0.3f, 1.3f);
                    ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(heart_icon_red, "scaleY", 0.3f, 1.3f);
                    AnimatorSet animatorSet = new AnimatorSet();
                    animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
                    animatorSet.setDuration(150);
                    animatorSet.start();
                    scaleXAnimator = ObjectAnimator.ofFloat(heart_icon_red, "scaleX", 1.3f, 1);
                    scaleYAnimator = ObjectAnimator.ofFloat(heart_icon_red, "scaleY", 1.3f, 1);
                    animatorSet.playTogether(scaleXAnimator, scaleYAnimator);
                    animatorSet.start();

                    like = true;
                }
            }

            int clickedPosition = getAdapterPosition();
            if (mOnClickListener != null) {
                mOnClickListener.onListItemClick(clickedPosition);
            }
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
}
