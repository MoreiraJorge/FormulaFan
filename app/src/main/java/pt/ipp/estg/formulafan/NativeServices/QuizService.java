package pt.ipp.estg.formulafan.NativeServices;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.R;
import pt.ipp.estg.formulafan.Repositories.CurrentRaceRepository;

public class QuizService extends LifecycleService {

    public static final String CHANNEL_ID = "1";
    private static final int NOTIFICATION_ID = 1;
    private static final float GEOFENCE_RADIUS_IN_METERS = 5000f;
    private static final int TIME_BETWEEN_REQUESTS = 5000;

    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private GeofencingClient geofencingClient;
    private PendingIntent geofencePendingIntent;

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

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.formula_fan_service))
                .setContentText("Sempre que existir um novo desafio receberá uma notificação!")
                .setSmallIcon(R.drawable.ic_baseline_emoji_events_24)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setTicker("")
                .build();

        startForeground(NOTIFICATION_ID, notification);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(TIME_BETWEEN_REQUESTS);
        locationRequest.setFastestInterval(TIME_BETWEEN_REQUESTS);

        geofencingClient = LocationServices.getGeofencingClient(getApplicationContext());
    }

    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (checkPermission(getApplicationContext())) {
            setLocationUpdates();
            setGeofencingSettings();
        } else {
            Toast.makeText(getApplicationContext(), "Habilite a premissão de localização para receber desafios!",
                    Toast.LENGTH_LONG).show();
        }
        return START_NOT_STICKY;
    }

    private boolean checkPermission(final Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void setLocationUpdates() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
            }
        };
        startLocationUpdates();
    }

    @SuppressLint("MissingPermission")
    private void setGeofencingSettings() {
        List<Geofence> geofenceList = new ArrayList<>();

        CurrentRaceRepository currentRaceRepository = new CurrentRaceRepository(getApplication());
        currentRaceRepository.getAllRaces().observe(this, (races) -> {
            if (races.size() > 0) {
                for (Race race : races) {
                    //Creating Geofecing list
                    geofenceList.add(new Geofence.Builder()
                            .setRequestId(race.circuit.circuitName)
                            .setCircularRegion(
                                    race.circuit.location.lat,
                                    race.circuit.location.lng,
                                    GEOFENCE_RADIUS_IN_METERS
                            )
                            .setExpirationDuration(Geofence.NEVER_EXPIRE)
                            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                                    Geofence.GEOFENCE_TRANSITION_EXIT)
                            .build());
                }

                //Setting Request
                GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
                builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
                builder.addGeofences(geofenceList);
                GeofencingRequest request = builder.build();

                //Adding Geofencing Request
                Intent geoIntent = new Intent(getApplicationContext(), GeofenceBroadcastReceiver.class);
                geofencePendingIntent = PendingIntent.getBroadcast(this, 0, geoIntent, PendingIntent.
                        FLAG_UPDATE_CURRENT);
                geofencingClient.addGeofences(request, geofencePendingIntent);
            }
        });

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    @Override
    public void onDestroy() {
        if (geofencePendingIntent != null) {
            geofencingClient.removeGeofences(geofencePendingIntent);
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
        super.onDestroy();
    }
}