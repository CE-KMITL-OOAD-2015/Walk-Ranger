package com.example.nattachai.walkingranger;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
public class GetLocation extends Activity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        test();
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation != null) {
            //ส่ง lastLocation.getLatitude() กับ lastLocation.getLongitude() ไปเซิพเวอร์
            Toast.makeText(this,String.valueOf(lastLocation.getLatitude()) , Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(),ScarActivity.class);
          //  i.putExtra("lati",String.valueOf(lastLocation.getLatitude()));
          //  i.putExtra("longti",String.valueOf(lastLocation.getLongitude()));
            startActivity(i);
        } else {

        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    public void test(){
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
        .addApi(LocationServices.API)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .build();
    }


}