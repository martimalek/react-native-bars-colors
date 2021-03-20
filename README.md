# react-native-bars-colors

Small react-native library to control the color of both the status bar and the navigation bar in Android.

## Getting started

With npm:

`$ npm install react-native-bars-colors --save`

With yarn:

`$ yarn add react-native-bars-colors`

## Usage
```javascript
import BarsColors from 'react-native-bars-colors';

// Change the color of both bars
BarsColors.changeNavBarColor(color, isLightTheme);

// Change the color of each bar independently
BarsColors.changeNavBarColor(navbarColor, statusBarColor, isLightTheme);

```
## Limitations

This is a module made because of personal needs and for now only supports Android. PRs are welcome ;)

The color format should be in HEX with 6 chars, not 3. Ex. #RRGGBB (This will be changed ASAP so that #RGB format is supported)
