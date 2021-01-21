package pt.ipp.estg.formulafan.Utils;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.lifecycle.LiveData;

public class InternetUtil extends LiveData<Boolean> {

    private BroadcastReceiver broadcastReceiver = null;
    private Application application;

    public InternetUtil(Application application) {
        this.application = application;
    }

    public boolean isInternetOn() {
        ConnectivityManager connectivityManager = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network activeNetwork = connectivityManager.getActiveNetwork();
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(activeNetwork);
        return activeNetwork != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onActive() {
        registerBroadCastReceiver();
        super.onActive();
    }

    @Override
    protected void onInactive() {
        unRegisterBroadCastReceiver();
        super.onInactive();
    }

    private void registerBroadCastReceiver() {
        if(broadcastReceiver == null) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Bundle extras = intent.getExtras();
                    NetworkInfo info = extras.getParcelable("networkInfo");
                    setValue( info.getState() == NetworkInfo.State.CONNECTED);
                }
            };

            application.registerReceiver(broadcastReceiver, filter);
        }
    }

    private void unRegisterBroadCastReceiver() {
        if (broadcastReceiver != null) {
            application.unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }
}
