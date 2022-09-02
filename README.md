# react-native-sensor-ambient-light
React Native Module for android only to read ambient light sensors value
## Installation

```sh
npm install react-native-sensor-ambient-light
```

## Usage

```js
import {
  startUpdateLightSensor,
  stopUpdateLightSensor,
} from 'react-native-sensor-ambient-light';

// ...

useEffect(() => {
    if (Platform.OS === 'android') {
      startUpdateLightSensor();

      const subscription = DeviceEventEmitter.addListener(
        'AmbientLightSensor',
        (data: { lightValue: number; maxRange: number }) => {
          console.log('light', data.lightValue);
          console.log('maxRange', data.maxRange);
        },
      );

      return () => {
        stopUpdateLightSensor();
        subscription?.remove();
      };
    }
  }, []);
```

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
