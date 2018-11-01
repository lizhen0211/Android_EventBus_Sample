package com.example.lz.android_eventbus_sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSimpleDemoClick(View view) {
        Intent intent = new Intent(this, SimpleDemoActivity.class);
        startActivity(intent);
    }

    public void onThreadModelClick(View view) {
        Intent intent = new Intent(this, ThreadModeActivity.class);
        startActivity(intent);
    }

    public void onStickyEventClick(View view) {
        EventBus.getDefault().postSticky(new MessageEvent("Hello everyone!"));
        Intent intent = new Intent(this, StickyEventsActivity.class);
        startActivity(intent);
    }

}
