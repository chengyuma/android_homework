package com.example.shortvideo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

public class play_video extends AppCompatActivity {

    private VideoView videoView;

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
        VideoInfo videoInfo = extras.getParcelable("VideoInfo");

        videoView = findViewById(R.id.videoView1);
        videoView.setVideoURI(Uri.parse(videoInfo.feedurl));
        videoView.start();
    }
}

