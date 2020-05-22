package com.example.shortvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_RecyclerView).setOnClickListener(this);
        findViewById(R.id.btn_ViewPager2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_RecyclerView:
                startActivity(new Intent(this, recycler_view.class));
                break;
            case R.id.btn_ViewPager2:
                startActivity(new Intent(this, view_pager2.class));
                break;
        }
    }
}

