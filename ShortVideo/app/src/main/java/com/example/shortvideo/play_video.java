package com.example.shortvideo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.VideoView;

public class play_video extends AppCompatActivity {

    private VideoView videoView;
    private static String TAG = "play_video";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        VideoInfo videoInfo = extras.getParcelable("VideoInfo");

        videoView = findViewById(R.id.videoView1);
        assert videoInfo != null;
        videoView.setVideoURI(Uri.parse(videoInfo.feedurl));
        videoView.start();

//        videoView.setOnClickListener(v -> {
//            if (videoView.isPlaying())
//                videoView.pause();
//            else
//                videoView.start();
//        });
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN) {
                    if (videoView.isPlaying())
                        videoView.pause();
                    else
                        videoView.start();
                }
                return true;
            }
        });
    }
}

