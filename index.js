
import { NativeModules } from 'react-native';

const { RNNativeLocationLibrary } = NativeModules;
import RNNativeLocationPermissions, {PermissionStatus} from "./permissions";

export {RNNativeLocationLibrary, RNNativeLocationPermissions, PermissionStatus};
