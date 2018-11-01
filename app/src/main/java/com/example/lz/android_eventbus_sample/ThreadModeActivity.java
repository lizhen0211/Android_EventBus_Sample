package com.example.lz.android_eventbus_sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ThreadModeActivity extends Activity {

    private static final String TAG = ThreadModeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_mode);
    }

//    订阅者将在发布事件的同一线程中调用。这是默认值。
//    事件传递是同步完成的，一旦发布完成，所有订阅者都将被调用。
//    此ThreadMode意味着开销最小，因为它完全避免了线程切换。
//    因此，这是已知完成的简单任务的推荐模式，是一个非常短的时间而不需要主线程。
//    使用此模式的事件处理程序应该快速返回以避免阻止发布线程，这可能是主线程

    // Called in the same thread (default)
    // ThreadMode is optional here
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessagePost(MessageEvent event) {
        Log.e(TAG, Thread.currentThread().getName() + ":POSTING:" + event.message);
    }

    //    订阅者将在Android的主线程（有时称为UI线程）中调用。
//    如果发布线程是主线程，则将直接调用事件处理程序方法（与ThreadMode.POSTING所描述的同步）。
//    使用此模式的事件处理程序必须快速返回以避免阻塞主线程。
    // Called in Android UI's main thread
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageMain(MessageEvent event) {
        Log.e(TAG, Thread.currentThread().getName() + ":MAIN:" + event.message);
    }

    //      订阅者将在Android的主线程中调用。该事件总是排队等待以后交付给订阅者，因此对post的调用将立即返回。
//      这为事件处理提供了更严格且更一致的顺序（因此名称为MAIN_ORDERED）。
//      例如，如果您在具有MAIN线程模式的事件处理程序中发布另一个事件，
//      则第二个事件处理程序将在第一个事件处理程序之前完成（因为它是同步调用的 - 将其与方法调用进行比较）。
//      使用MAIN_ORDERED，第一个事件处理程序将完成，然后第二个事件处理程序将在稍后的时间点调用（一旦主线程具有容量）。
//      使用此模式的事件处理程序必须快速返回以避免阻塞主线程。
    // Called in Android UI's main thread
    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMessageMainOrder(MessageEvent event) {
        Log.e(TAG, Thread.currentThread().getName() + ":MAIN_ORDERED:" + event.message);
    }

    //    订阅者将在后台线程中调用。如果发布线程不是主线程，则将在发布线程中直接调用事件处理程序方法。
//    如果发布线程是主线程，则EventBus使用单个后台线程，该线程将按顺序传递其所有事件。
//    使用此模式的事件处理程序应尝试快速返回以避免阻塞后台线程。
    // Called in the background thread
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageBackground(MessageEvent event) {
        Log.e(TAG, Thread.currentThread().getName() + ":BACKGROUND:" + event.message);
    }

    //    事件处理程序方法在单独的线程中调用。这始终独立于发布线程和主线程。
//    发布事件永远不会等待使用此模式的事件处理程序方法。如果事件处理程序的执行可能需要一些时间，
//    例如用于网络访问，则应使用此模式。避免同时触发大量长时间运行的异步处理程序方法来限制并发线程数。
//    EventBus使用线程池从已完成的异步事件处理程序通知中有效地重用线程。
    // Called in a separate thread
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageASYNC(MessageEvent event) {
        Log.e(TAG, Thread.currentThread().getName() + ":ASYNC:" + event.message);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onPostEventClick(View view) {
        //EventBus.getDefault().post(new MessageEvent("Hello everyone!"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                EventBus.getDefault().post(new MessageEvent("Hello everyone!"));
            }
        }).start();
    }
}
