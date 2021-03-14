declare module 'react-native-bars-colors';

type HexColor = string;

export function changeNavBarColor(navbarColor: HexColor, statusBarColor: HexColor, light: Boolean): Promise<Boolean>;
