import { NativeModules } from 'react-native';

const { BarsColor } = NativeModules;

export default {
    changeNavBarColor: BarsColor.changeNavBarColor,
}
