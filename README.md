
# react-native-native-location-library

## Getting started

`npm install git+https://github.com/aakash-kumar-rsi/NativeLocationLibrary.git`


## Usage
```javascript
import {
  RNNativeLocationLibrary,
  RNNativeLocationPermissions,
  PermissionStatus,
} from 'react-native-native-location-library';
import {NativeEventEmitter, AppState} from 'react-native';
```
  
**To check if location permission is enabled or not.**
```javascript
RNNativeLocationPermissions.checkLocationPermission()
  .then(permission => {
  
	// permission could be authorized, denied, restricted, undetermined
	// These permissions can be checked by using `PermissionStatus` object
	if (permission === PermissionStatus.authorized) {
		// do something when permission authorized
	}
	else if (permission === PermissionStatus.denied) {
		// do something when permission rejected
	}
	// & so on
  })
  .catch(err => {
	console.log(err);
  });
```

**If location permission are rejected user can open settings page of application using**
```javascript
RNNativeLocationLibrary.openSettings();
```

  
**If user authorized the location permission start start location updates**
```javascript
RNNativeLocationLibrary.startLocationUpdates()
.then(enabled => {
  // This will return if gps is enabled or not
  // If gps is not enabled it will prompt user to enable it
})
.catch(err => {
  console.log(err);
});
```

**When location updates are started & gps is enabled. Create a event listener for location updates**
```javascript
this.eventEmitter = new NativeEventEmitter(RNNativeLocationLibrary);
this.locationListener = this.eventEmitter.addListener('LocationUpdate', location => {
  // This `location` parameter will return the current location
},
);
```

**To remove listener**
```javascript
if (this.locationListener != null) {
	this.locationListener.remove();
	this.locationListener = null;
	RNNativeLocationLibrary.stopLocationListener();
}
```

**Note -Minimum Requirement**
```javascript
minSdkVersion 22
```
