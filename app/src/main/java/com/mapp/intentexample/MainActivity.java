package com.mapp.intentexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.mapp.intentexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(this);

    }

    public void onClickExplicitIntent(){
        //An Explicit intent will open an internal activity
        Intent myIntent = new Intent(this, ImplicitIntent.class);
        startActivity(myIntent);
    }

    public void onClickImplicitIntent(){
        //An Implicit intent will open an external app.
        //The app must have registered the action to know what app can open the send parameter
        //Also can add a filter using package name to send the parameter to an specific app
        Uri webpage = Uri.parse("https://www.android.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }

    public void onClickPendingIntent(){
        //A Pending intent will be waiting for a call. Is created but will be executed when the
        //message or event arrives.

        Intent intent = new Intent(this, PendingIntentActivity.class);

        // Creating a pending intent and wrapping our intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        try {
            // Perform the operation associated with our pendingIntent
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public void launchPendingNotification(){
        //In this case we have a Pending intent. This Pending inten will be launched when the user tap the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent intent = new Intent(this, PendingIntentActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("Notifications Title");
        builder.setContentText("Your notification content here.");
        builder.setSubText("Tap to view the website.");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // This is necessary in Android 8 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "1";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel title",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        // Will display the notification in the notification bar
        notificationManager.notify(1, builder.build());
    }

    public void onClickStickyIntent(){
        //A Sticky intent is any intent who is attached to a stickyBroadcast.
        //This will be waiting for the broadcast event to be launched.

        //Can add an event form system (Battery low, connection network, etc)
        IntentFilter customFilter = new IntentFilter("StickyIntent");
        MyReceiver myReceiver = new MyReceiver();
        registerReceiver(myReceiver, customFilter);

        //Send the custom action
        Intent stickyIntent = new Intent();
        stickyIntent.setAction("StickyIntent");

        //Is deprecated because no security, no protection and other problems.
        //Instead use EventBus or RxJava
        sendStickyBroadcast(stickyIntent);
    }

}