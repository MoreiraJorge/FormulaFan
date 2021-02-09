package pt.ipp.estg.formulafan.NativeServices;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import pt.ipp.estg.formulafan.Activities.LiveQuizActivity;
import pt.ipp.estg.formulafan.R;

import static pt.ipp.estg.formulafan.NativeServices.GeofenceBroadcastReceiver.CIRCUIT;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "1";
    private static final int NOTIFICATION_ID = 3;

    private Context context;
    private String circuitName;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        circuitName = intent.getStringExtra(CIRCUIT);
        if (circuitName != null) {
            createNotification();
        }
    }

    private void createNotification() {
        createNotificationChannel();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_emoji_events_24)
                .setContentTitle("Novo Quiz!")
                .setContentText("Tem um novo quiz para realizar de um evento live!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Tem um novo quiz para realizar de um evento live!"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent clickIntent = new Intent(context, LiveQuizActivity.class);
        clickIntent.putExtra(CIRCUIT, circuitName);
        clickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent clickPendingIntent = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(clickPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.formula_fan_service);
            String description = context.getString(R.string.formula_fan_service_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
