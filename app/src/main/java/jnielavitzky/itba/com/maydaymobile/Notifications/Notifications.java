package jnielavitzky.itba.com.maydaymobile.Notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import jnielavitzky.itba.com.maydaymobile.APINotify.APINotify;
import jnielavitzky.itba.com.maydaymobile.APINotify.State;
import jnielavitzky.itba.com.maydaymobile.MisVuelosActivity;
import jnielavitzky.itba.com.maydaymobile.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Cappa on 25/6/2017.
 */

public class Notifications {

    private static Integer i = 0;
    public static State state;

    public static void scheduleNotification(Context context, long delay, int notificationId) {
        //delay is after how much time(in millis) from current time you want to schedule the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("notificación")
                .setContentText("contenido")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_flight_takeoff_black_24dp)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        Intent intent = new Intent(context, MisVuelosActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    public void setState(State state){
        this.state = state;
    }


}
