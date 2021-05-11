package com.barscolors.src.HexFormatter;

public class HexFormatter {
    public static String format(String hexColor) throws Exception {
        if (hexColor.length() == 7) return hexColor;
        if (hexColor.length() != 4) throw new HexFormatException();

        char r = hexColor.charAt(1);
        char g = hexColor.charAt(2);
        char b = hexColor.charAt(3);
        return hexColor.substring(0, 2) + r + g + g + b + b;
    }
}

