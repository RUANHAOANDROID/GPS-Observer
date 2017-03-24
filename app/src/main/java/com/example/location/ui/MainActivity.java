package com.example.location.ui;

import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.location.R;
import com.example.location.gps.GpsManager;

public class MainActivity extends AppCompatActivity implements LocationListener, GpsStatus.NmeaListener {
    private GpsManager gps;
    private TextView tv_lon, tv_lat,tv_gnss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_lat = (TextView) findViewById(R.id.tv_lat);
        tv_lon = (TextView) findViewById(R.id.tv_lon);
        tv_gnss = (TextView) findViewById(R.id.tv_gnss);
        gps = new GpsManager.Builder(getApplication()).setProvider("test").setMinTime(5).setMinDistance(5).setLocationListener(this).setNmeaListener(this).builder();
        gps.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gps.stop();
    }

    @Override
    public void onLocationChanged(Location location) {
        tv_lat.setText("lat:"+location.getLatitude());
        tv_lon.setText("lon:"+location.getLongitude());
        Log.e("LOCATION",location.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onNmeaReceived(long timestamp, String nmea) {
        tv_gnss.setText(nmea);
        //Log.e("NMEA",nmea);
    }
}
