package com.appspark.hotelmanagementproject;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class AboutActivity extends OptionMenuActivity {
    private VideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mVideoView = (VideoView) findViewById(R.id.vwVideo);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);
        mVideoView.setVideoURI(uri);
        mVideoView.start();
    }
}
