package com.barscolors.src.HexFormatter;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class HexFormatterTest {

    @org.junit.Test
    public void formatValid() throws Exception {
        String validRRGGBBHex = "#AABBCC";
        String validRGBHex = "#ABC";

        String formattedRRGGBBHex = HexFormatter.format(validRRGGBBHex);
        String formattedRGBHex = HexFormatter.format(validRGBHex);

        assertEquals(formattedRRGGBBHex, validRRGGBBHex);
        assertEquals(formattedRGBHex, validRRGGBBHex);
    }

    @org.junit.Test(expected = HexFormatException.class)
    public void formatInvalid() throws Exception {
        HexFormatter.format("#");
        HexFormatter.format("#A");
        HexFormatter.format("#AB");
        HexFormatter.format("#AABC");
        HexFormatter.format("#AABBC");
    }
}