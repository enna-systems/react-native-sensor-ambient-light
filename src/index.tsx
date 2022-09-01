import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-sensor-ambient-light' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

const SensorAmbientLight = NativeModules.SensorAmbientLight  ? NativeModules.SensorAmbientLight  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function startUpdateLightSensor(): void {
  return SensorAmbientLight.startUpdateLightSensor();
}

export function stopUpdateLightSensor(): void {
  return SensorAmbientLight.stopUpdateLightSensor();
}
