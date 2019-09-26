
# react-native-native-location-library

## Getting started

`$ npm install react-native-native-location-library --save`

### Mostly automatic installation

`$ react-native link react-native-native-location-library`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNNativeLocationLibraryPackage;` to the imports at the top of the file
  - Add `new RNNativeLocationLibraryPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-native-location-library'
  	project(':react-native-native-location-library').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-native-location-library/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-native-location-library')
  	```


## Usage
```javascript
import RNNativeLocationLibrary from 'react-native-native-location-library';

// TODO: What to do with the module?
RNNativeLocationLibrary;
```
  