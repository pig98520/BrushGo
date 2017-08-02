package com.example.brush.brushgo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

/**
 * Created by pig98520 on 2017/5/28.
 */

public class AlarmNotificationReceiver extends BroadcastReceiver{
    @Override

    public void onReceive(Context context, Intent intent) {
        String time=Calendar.getInstance().getTime().getHours()+":"+Calendar.getInstance().getTime().getMinutes();
        Intent notifiIntent =new Intent(context,Home_Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notifiIntent, 0); //點擊後回到

        NotificationCompat.Builder builder=new NotificationCompat.Builder(context);
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.tooth_icon)
                .setContentTitle("BrushGo")
                .setContentText(time+"到了喔,快點打開BrushGo來刷牙吧")
                /* .setContentInfo("推播資訊")*/
                .setContentIntent(pendingIntent) //點擊後回到APP
                .setDefaults(Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND);

        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,builder.build());
    }

}
