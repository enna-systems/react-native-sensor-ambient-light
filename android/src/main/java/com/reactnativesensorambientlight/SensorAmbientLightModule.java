package com.reactnativesensorambientlight;

import androidx.annotation.NonNull;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

@ReactModule(name = SensorAmbientLightModule.NAME)
public class SensorAmbientLightModule extends ReactContextBaseJavaModule implements SensorEventListener {
  public static final String NAME = "SensorAmbientLight";
  private final ReactApplicationContext reactContext;
  private final SensorManager sensorManager;
  private final Sensor sensor;

  public SensorAmbientLightModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.sensorManager = (SensorManager)reactContext.getSystemService(reactContext.SENSOR_SERVICE);
    this.sensor = this.sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  private void sendEvent(@NonNull WritableMap params) {
    try {
      this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
        .emit("AmbientLightSensor", params);
    } catch (RuntimeException e) {
      Log.e(NAME, "sensor event for AmbientLightSensor could not been sent!");
    }
  }

  @Override
  public void onSensorChanged(SensorEvent sensorEvent) {
    WritableMap map = Arguments.createMap();
    float sensorValue = sensorEvent.values[0];
    float maxRange = sensorEvent.sensor.getMaximumRange();
    map.putDouble("lightValue", sensorValue);
    map.putDouble("maxRange", maxRange);
    sendEvent(map);
  }

  @ReactMethod
  public void startUpdateLightSensor() {
    if (sensor == null) {
      return;
    }
    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
  }

  @ReactMethod
  public void stopUpdateLightSensor() {
    if (sensor == null) {
      return;
    }
    sensorManager.unregisterListener(this);
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }
}
