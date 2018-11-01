package com.example.lz.android_eventbus_sample;

import android.app.Activity;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class PrioritiesAndEventCancellationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priorities_and_event_cancellation);
    }

//    您可以通过在注册期间为订户提供优先级来更改事件传递的顺序。
//    在同一传递线程（ThreadMode）中，较高优先级的订户将在优先级较低的其他订户之前接收事件。默认优先级为0。
    //    Note: the priority does not affect the order of delivery among subscribers with different ThreadModes!
    @Subscribe(priority = 1)
    public void onEventPriority(MessageEvent event) {

    }

//    您可以通过从订阅者的事件处理方法调用
//    cancelEventDelivery （Object event）来取消事件传递过程 。
//    任何进一步的活动交付将被取消，后续订阅者将不会收到该活动。

//    事件通常由优先级较高的订户取消。取消仅限于在发布线程（运行事件处理方法 ThreadMode 。PostThread）
    // Called in the same thread (default)
    @Subscribe
    public void onEventCancelDelivery(MessageEvent event) {
        // Process the event

        // Prevent delivery to other subscribers
        EventBus.getDefault().cancelEventDelivery(event);
    }
}
