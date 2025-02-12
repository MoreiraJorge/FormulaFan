package pt.ipp.estg.formulafan.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import pt.ipp.estg.formulafan.Models.Race;
import pt.ipp.estg.formulafan.NativeServices.AlarmBroadcastReceiver;

import static pt.ipp.estg.formulafan.NativeServices.GeofenceBroadcastReceiver.CIRCUIT;

public class AlarmManagerUtil {

    public static void startAlarm(Context context, Race race) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(CIRCUIT, race.circuit.circuitName);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(race.date.getTime());

        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
    }
}
