package com.example.shortvideo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.NumberViewHolder> {
    private static final String TAG = "ItemAdapter";

    private int mNumberItems;
    private List<VideoInfo> videoInfoList;

    private final ListItemClickListener mOnClickListener;

    private static int viewHolderCount;

    public ItemAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
        viewHolderCount = 0;
        mNumberItems=0;
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);

        NumberViewHolder viewHolder = new NumberViewHolder(view);

        viewHolderCount++;
        Log.i(TAG,"onCreateView:"+videoInfoList.toString());
        Log.i(TAG,"onCreateView");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder numberViewHolder, int position) {
        numberViewHolder.bind(position);
        VideoInfo curr = videoInfoList.get(position);
        numberViewHolder.video_description.setText(curr.description);
        // TODO make the cover fit
        Context context1=numberViewHolder.cover1.getContext();
        Glide.with(context1)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(0)
                                .centerCrop()
                )
                .load(curr.feedurl)
                .apply(RequestOptions.centerCropTransform())
                .into(numberViewHolder.cover1);
        Context context2=numberViewHolder.cover2.getContext();
        Glide.with(context2)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(10000000)
                                .centerCrop()
                )
                .load(curr.feedurl)
//                .apply(RequestOptions.circleCropTransform())
//                .bitmapTransform(new CropSquareTransformation())
                .apply(RequestOptions.centerCropTransform())
//                .apply(RequestOptions.bitmapTransform(new CropSquareTransformation()))
                .into(numberViewHolder.cover2);
        Log.i(TAG,"onBindVie:"+videoInfoList.toString());
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public void setData(List<VideoInfo> videoInfoList1) {
        mNumberItems = videoInfoList1.size();
        videoInfoList = videoInfoList1;
        notifyDataSetChanged();
        Log.i(TAG,videoInfoList.toString());
        Log.i(TAG,"setData");
    }

    public class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView cover1;
        private ImageView cover2;
        private TextView video_description;

        public NumberViewHolder(@NonNull View itemView) {
            super(itemView);
            video_description = itemView.findViewById(R.id.rv_description);
            cover1= itemView.findViewById(R.id.rv_cover1);
            cover2=itemView.findViewById(R.id.rv_cover2);

            itemView.setOnClickListener(this);
        }

        public void bind(int position) {
            ;
        }

        @Override
        public void onClick(View v) {
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

