package com.example.lz.android_eventbus_sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class StickyEventsActivity extends Activity {

    private static final String TAG = StickyEventsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticky_events);

        //手动获取和删除粘性事件
        //Getting and Removing sticky Events manually
        MessageEvent stickyEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);
        Log.e(TAG, "getting:" + stickyEvent.message);
        // Better check that an event was actually posted before
        if (stickyEvent != null) {
            // "Consume" the sticky event
            //要删除（消耗）粘性事件，以便它们不再被传递
            EventBus.getDefault().removeStickyEvent(stickyEvent);
            Log.e(TAG, "remove:" + stickyEvent.message);
            // Now do something with it
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    //    Now a new Activity gets started.
//    During registration all sticky subscriber methods will immediately get the previously posted sticky event:
    // UI updates must run on MainThread
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        Log.e(TAG, "onEvent:" + event.message);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
