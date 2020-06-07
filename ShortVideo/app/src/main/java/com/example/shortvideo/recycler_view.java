package com.example.shortvideo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class recycler_view extends AppCompatActivity implements ItemAdapter.ListItemClickListener {

    private static String TAG = "RecyclerView";
    private static List<VideoInfo> videoInfoList;

    private ItemAdapter mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = findViewById(R.id.rv_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("RecyclerView");

        getVideoInfo();
        mAdapter = new ItemAdapter(this);
        recyclerView.setAdapter(mAdapter);
        Log.i(TAG, "end");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getVideoInfo() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getVideoInfo().enqueue(new Callback<List<VideoInfo>>() {
            @Override
            public void onResponse(Call<List<VideoInfo>> call, Response<List<VideoInfo>> response) {
                if (response.isSuccessful())
                    Log.i(TAG, "successful");
                if (response.body() != null) {
                    List<VideoInfo> videoInfoList = response.body();
                    for(VideoInfo temp : videoInfoList) {
                        temp.avatar = temp.avatar.replace("http", "https");
                        temp.feedurl = temp.feedurl.replace("http", "https");
                    }
                    mAdapter.setData(videoInfoList);
                    mAdapter.notifyDataSetChanged();
                    Log.i(TAG, response.body().toString());
                    VideoInfo temp = videoInfoList.get(1);
                    Log.i(TAG, temp.feedurl);
                }
            }

            @Override
            public void onFailure(Call<List<VideoInfo>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        Log.d(TAG, "onListItemClick: ");
        Intent intent = new Intent(this, play_video.class);
        intent.putExtra("VideoInfo", videoInfoList.get(clickedItemIndex));
        startActivity(intent);
    }
}
