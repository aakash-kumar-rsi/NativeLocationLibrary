
package com.reactlibrary;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.reactlibrary.utils.GpsUtils;

import static android.app.Activity.RESULT_OK;

public class RNNativeLocationLibraryModule extends ReactContextBaseJavaModule implements ActivityEventListener {

  private final ReactApplicationContext reactContext;

  boolean isGPS=false;
  private static final String GPS_START_ERROR = "GPS_START_ERROR";
  private Promise promise;

  RNLocationProvider locationProvider;

  public RNNativeLocationLibraryModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    reactContext.addActivityEventListener(this);
  }

  @Override
  public String getName() {
    return "RNNativeLocationLibrary";
  }

  @ReactMethod
  public void startLocationUpdates( final Promise promise) {
    if (getCurrentActivity() == null || promise == null) return;

    this.promise = promise;


    new GpsUtils(getCurrentActivity()).turnGPSOn(new GpsUtils.onGpsListener() {
      @Override
      public void gpsStatus(boolean isGPSEnable) {
        // turn on GPS
        isGPS = isGPSEnable;

        promise.resolve(isGPS);

        startLocationListener();
      }
    });
  }

  @ReactMethod
  public void stopLocationListener() {
    if(locationProvider != null) {
      locationProvider.stopLocationUpdates();
    }
  }

  @ReactMethod
  public void openSettings() {
    final Intent i = new Intent();
    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    i.addCategory(Intent.CATEGORY_DEFAULT);
    i.setData(Uri.parse("package:" + reactContext.getPackageName()));
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
    reactContext.startActivity(i);
  }

  @Override
  public void onNewIntent(Intent intent) {
  }

  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    if (requestCode == GpsUtils.GPS_REQUEST  && promise != null) {
      if (resultCode == RESULT_OK ) {
        promise.resolve(true);
        startLocationListener();
      } else {
        promise.reject(GPS_START_ERROR, "Denied");
      }
      this.promise = null;
    }

  }

  private void startLocationListener(){
    if(locationProvider == null){
      locationProvider = new RNLocationProvider(reactContext);
      locationProvider.startLocationUpdates();
    }
  }

}