package com.reactlibrary;

import android.annotation.SuppressLint;
import android.location.Location;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class RNLocationProvider {

    ReactContext mContext;

    private LocationRequest locationRequest = new LocationRequest();
    FusedLocationProviderClient locationProviderClient;

    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 2000, LEAST_DISTANCE = 10;
    private boolean updateLocation = false;

    public RNLocationProvider(ReactContext context) {
        mContext = context;

        locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
    }
    @SuppressLint("MissingPermission")
    public void startLocationUpdates() {
        updateLocation = true;
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
//        locationRequest.setSmallestDisplacement(LEAST_DISTANCE);
        locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    public void stopLocationUpdates() {
        updateLocation = false;
        if(locationProviderClient != null) {
            locationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null || !updateLocation) {
                return;
            }

            // Map the locations to maps
            WritableArray results = Arguments.createArray();
            for (Location location : locationResult.getLocations()) {
                results.pushMap(locationToMap(location));
            }

            // Emit the event
//            Utils.emitEvent(context, "locationUpdated", results);
            mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("LocationUpdate", results);
        }
    };

    private WritableMap locationToMap(Location location) {
        WritableMap map = Arguments.createMap();

        map.putDouble("latitude", location.getLatitude());
        map.putDouble("longitude", location.getLongitude());
        map.putDouble("accuracy", location.getAccuracy());
        map.putDouble("altitude", location.getAltitude());
        map.putDouble("timestamp", location.getTime());
        map.putBoolean("fromMockProvider", location.isFromMockProvider());

        return map;
    }
}
