package com.example.shortvideo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Adapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class view_pager2 extends AppCompatActivity implements ItemAdapter.ListItemClickListener {

    private static String TAG = "ViewPager2";
    private static List<VideoInfo> videoInfoList;
    private ViewPager2 viewPager2;
    private PagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager2);

        getVideoInfo();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        viewPager2 = findViewById(R.id.viewPager2);
        mAdapter = new PagerAdapter(this);
        viewPager2.setAdapter(mAdapter);
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
                    List<VideoInfo> res = response.body();
                    mAdapter.setData(videoInfoList);
                    mAdapter.notifyDataSetChanged();
                    Log.i(TAG, response.body().toString());
                    VideoInfo temp = res.get(1);
                    Log.i(TAG, temp.toString());
                }
            }

            @Override
            public void onFailure(Call<List<VideoInfo>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        ;
    }
}
