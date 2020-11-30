package edu.mssucis385.clickergame;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PointIntentService extends IntentService {


    public PointIntentService() {
        super("PointIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Get millis from the activity and start a new TimerModel


        // Create notification channel
        createNotificationChannel();

        CountDownTimer timer = new CountDownTimer(Points.getTimerLength(), Points.getTimerTick()) {
            @Override
            public void onTick(long l) {
                Points.addPoints();
            }

            @Override
            public void onFinish() {
                createNotification("Timer Done. Play more to reset AFK timer");
                Points.setTimerCanRun(false);
            }
            };
        timer.start();
        timer.onFinish();
        }

    private final String CHANNEL_ID_TIMER = "channel_timer";
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < 26) return;

        CharSequence name = getString(R.string.channel_name_completed);
        String description = getString(R.string.channel_description_completed);
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID_TIMER, name, importance);
        channel.setDescription(description);

        // Register channel with system
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
    private final int NOTIFICATION_ID = 0;

    private void createNotification(String text) {

        // Create notification with various properties
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID_TIMER)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        // Get compatibility NotificationManager
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Post notification using ID.  If same ID, this notification replaces previous one
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}