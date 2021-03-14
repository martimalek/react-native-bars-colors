import { NativeModules, Platform } from 'react-native';

const { BarsColors } = NativeModules;

const changeNavBarColor = async (...args) => {
    if (Platform.OS !== 'android') return;

    return BarsColors.changeNavBarColor(...args);
};

export default {
    changeNavBarColor,
};
