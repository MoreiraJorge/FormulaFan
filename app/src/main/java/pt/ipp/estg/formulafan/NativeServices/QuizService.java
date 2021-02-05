package pt.ipp.estg.formulafan.NativeServices;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import pt.ipp.estg.formulafan.Activities.FormulaFanMainActivity;
import pt.ipp.estg.formulafan.R;

public class QuizService extends Service {

    private static final String CHANNEL_ID = "1";
    private static final int NOTIFICATION_ID = 1;
    private static final int TIME_BETWEEN_REQUESTS = 300000;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public QuizService() {
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.formula_fan_service);
            String description = getString(R.string.formula_fan_service_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

        Intent notificationIntent = new Intent(this, FormulaFanMainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.formula_fan_service))
                .setContentText("Sempre que existir um novo desafio receberá uma notificação!")
                .setSmallIcon(R.drawable.ic_baseline_emoji_events_24)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setTicker("")
                .build();

        startForeground(NOTIFICATION_ID, notification);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(TIME_BETWEEN_REQUESTS);
        locationRequest.setFastestInterval(TIME_BETWEEN_REQUESTS);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (checkPermission(getApplicationContext())) {
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    Log.d("Test", locationResult.getLocations().get(0).toString());
                }
            };
            startLocationUpdates();
        } else {
            Toast.makeText(getApplicationContext(), "Habilite a premissão de localização para receber desafios!",
                    Toast.LENGTH_LONG).show();
        }
        return START_STICKY;
    }

    public static boolean checkPermission(final Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    @Override
    public void onDestroy() {
        if (locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
        super.onDestroy();
    }
}