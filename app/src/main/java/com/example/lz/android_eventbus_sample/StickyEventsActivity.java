package com.example.lz.android_eventbus_sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 在Android开发中，Sticky事件只指事件消费者在事件发布之后才注册的也能接收到该事件的特殊类型。
 * Android中就有这样的实例，也就是Sticky Broadcast，即粘性广播。
 * 正常情况下如果发送者发送了某个广播，而接收者在这个广播发送后才注册自己的Receiver，这时接收者便 无法接收到刚才的广播，
 * 为此Android引入了StickyBroadcast，在广播发送结束后会保存刚刚发送的广播（Intent），
 * 这样当接收者注册完Receiver 后就可以接收到刚才已经发布的广播。
 * 这就使得我们可以预先处理一些事件，让有消费者时再把这些事件投递给消费者。
 */
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
