package com.barscolors.src.HexFormatter;

public class HexFormatException extends Exception {
    public HexFormatException() {
        super("The color format should be either #RGB or #RRGGBB");
    }
}
