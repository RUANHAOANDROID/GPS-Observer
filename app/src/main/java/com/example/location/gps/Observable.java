package com.example.location.gps;

/**
 * Created by hao.ruan on 2017/3/24.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.GpsStatus.NmeaListener;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.provider.Settings;

public class Observable {
    private Context context;
    private LocationManager mLocationManager = null;
    private LocationProvider mProvider;
    private boolean mStarted;

    public Observable(Context context) {
        this.context = context;
    }

    public boolean isOff() {
        // 判断GPS是否正常启动
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            return true;
        }
        return false;
    }

    /**
     * 打开GPS
     * （这个方法在仅在Activity中调用有效）
     */
    public void preomptEnableGps() {
        new AlertDialog.Builder(context).setMessage("是否打开GPS")
                .setPositiveButton("打开", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
    }

    public synchronized void gpsStart(String provider, long minTime, float minDistance, NmeaListener nmeaListener,
                                      LocationListener mLocationListener) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mProvider = mLocationManager.getProvider(LocationManager.GPS_PROVIDER);
        if (isOff()) {
            preomptEnableGps();
        }
        if (!mStarted) {
            if (nmeaListener != null) {
                mLocationManager.addNmeaListener(nmeaListener);
            }
            mLocationManager.requestLocationUpdates(mProvider.getName(), minTime, minDistance, mLocationListener);
        }
    }

    public void removeLocationListener(LocationListener listener) {
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(listener);
        }
    }

    public void removeNmeaListener(NmeaListener listener) {
        if (mLocationManager != null) {
            mLocationManager.removeNmeaListener(listener);
        }

    }

    public synchronized void gpsStop(LocationListener listener) {
        if (mStarted) {
            mStarted = !mStarted;
            if (mLocationManager != null) {
                mLocationManager.removeUpdates(listener);
            }
        }

    }
}

