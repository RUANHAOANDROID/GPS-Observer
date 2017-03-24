package com.example.location.gps;
import android.content.Context;
import android.location.GpsStatus.NmeaListener;
import android.location.LocationListener;
/**
 * Created by hao.ruan on 2017/3/24.
 */

public class GpsManager {
    private static Builder mBuilder;
    private Observable observable;
    private LocationListener mLocationListener;
    private NmeaListener nmeaListener;

    private GpsManager(Builder builder) {
        mBuilder = builder;
        this.mLocationListener = builder.mLocationListener;
        this.nmeaListener = builder.nmeaListener;
        observable = new Observable(mBuilder.context);

    }

    public void start() {
        observable.gpsStart(mBuilder.provider, mBuilder.minTime, mBuilder.minDistance, nmeaListener, mLocationListener);
    }

    public void stop() {
        observable.gpsStop(mLocationListener);
    }

    public void removeLocationListener() {
        observable.removeLocationListener(mLocationListener);
    }

    public static class Builder {
        private Context context;
        private String provider;
        private long minTime;
        private float minDistance;
        private LocationListener mLocationListener;
        private NmeaListener nmeaListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setProvider(String provider) {
            this.provider = provider;
            return this;
        }

        public Builder setMinTime(long minTime) {
            this.minTime = minTime;
            return this;
        }

        public Builder setMinDistance(float minDistance) {
            this.minDistance = minDistance;
            return this;
        }

        public Builder setLocationListener(LocationListener listener) {
            this.mLocationListener = listener;
            return this;
        }

        public Builder setNmeaListener(NmeaListener nmeaListener) {
            this.nmeaListener = nmeaListener;
            return this;
        }

        public GpsManager builder() {
            if (mLocationListener == null) {
                throw new IllegalArgumentException("invalid listener: " + mLocationListener);
            }
            return new GpsManager(this);
        }
    }
}
