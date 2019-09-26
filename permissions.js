import { PermissionsAndroid } from "react-native";

export const PermissionStatus = Object.freeze({
    authorized      : 'authorized',
    denied          : 'denied',
    restricted      : 'restricted',
    undetermined    : 'undetermined'
});

export default class RNNativeLocationPermissions {
  static checkLocationPermission = _ => {
    if (Platform.OS === "android") {
      return PermissionsAndroid.check(
        PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION
      ).then(granted => {
        if (granted) return PermissionStatus.authorized;

        return PermissionsAndroid.request(
          PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION
        ).then(response => {
          if (response === PermissionsAndroid.RESULTS.GRANTED) {
            return PermissionStatus.authorized;
          } else if (response === PermissionsAndroid.RESULTS.DENIED) {
            return PermissionStatus.denied;
          } else if (response === PermissionsAndroid.RESULTS.NEVER_ASK_AGAIN) {
            return PermissionStatus.restricted;
          }
        }).catch(err => {
            console.log(err);
            return PermissionStatus.undetermined;
        });
      });
    }
  };
}
