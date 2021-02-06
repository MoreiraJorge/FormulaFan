package pt.ipp.estg.formulafan.NativeServices;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

import pt.ipp.estg.formulafan.Activities.QuizActivity;
import pt.ipp.estg.formulafan.R;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    public static final String CLOSEST_CIRCUIT = "pt.ipp.pt.estg.cmu.closestCircuit";
    private static final int NOTIFICATION_ID = 2;

    private Context context;
    private String circuitName;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
            circuitName = triggeringGeofences.get(0).getRequestId();

            createNotification();
        }
    }

    private void createNotification() {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, QuizService.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_emoji_events_24)
                .setContentTitle("Novo Quiz!")
                .setContentText("Tem um novo quiz para realizar!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Tem um novo quiz para realizar!"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent clickIntent = new Intent(context, QuizActivity.class);
        clickIntent.putExtra(CLOSEST_CIRCUIT, circuitName);
        clickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent clickPendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);

        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(clickPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}