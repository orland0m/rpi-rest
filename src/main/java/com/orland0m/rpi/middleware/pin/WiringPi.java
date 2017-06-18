/*
 * The MIT License
 *
 * Copyright 2017 Orlando Miramontes <https://github.com/orland0m>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.orland0m.rpi.middleware.pin;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates all GPIO constant management based on WiringPI GPIO scheme
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public enum WiringPi {
    GPIO_8(8), GPIO_9(9), GPIO_7(7),
    GPIO_0(0), GPIO_2(2), GPIO_3(3),
    GPIO_12(12), GPIO_13(13), GPIO_14(14),
    GPIO_30(30), GPIO_21(21), GPIO_22(22), GPIO_23(23), GPIO_24(24), GPIO_25(25),
    GPIO_15(15), GPIO_16(16), GPIO_1(1),
    GPIO_4(4), GPIO_5(5),
    GPIO_6(6), GPIO_10(10), GPIO_11(11), GPIO_31(31),
    GPIO_26(26),
    GPIO_27(27), GPIO_28(28), GPIO_29(29);

    /*! A map used to translate GPIO number into a WiringPi object */
    private static final Map<Integer, WiringPi> intToWiringPiMap;
    /*! A map used to translate a GPIO_* string into a WiringPi object */
    private static final Map<String, WiringPi> stringToWiringPiMap;
    /*! A map used to translate a physical pin into a WiringPi object */
    private static final Map<Integer, WiringPi> physicalToWiringPiMap;
    /*! A map used to translate a WiringPi object into its physical pin */
    private static final Map<WiringPi, Integer> wiringPiToPhysicalMap;

    static {
        intToWiringPiMap = new HashMap<>();
        stringToWiringPiMap = new HashMap<>();
        physicalToWiringPiMap = new HashMap<>();
        wiringPiToPhysicalMap = new HashMap<>();

        // GPIO number to WiringPi map
        intToWiringPiMap.put(8, GPIO_8);
        intToWiringPiMap.put(9, GPIO_9);
        intToWiringPiMap.put(7, GPIO_7);

        intToWiringPiMap.put(0, GPIO_0);
        intToWiringPiMap.put(2, GPIO_2);
        intToWiringPiMap.put(3, GPIO_3);

        intToWiringPiMap.put(12, GPIO_12);
        intToWiringPiMap.put(13, GPIO_13);
        intToWiringPiMap.put(14, GPIO_14);

        intToWiringPiMap.put(30, GPIO_30);
        intToWiringPiMap.put(21, GPIO_21);
        intToWiringPiMap.put(22, GPIO_22);
        intToWiringPiMap.put(23, GPIO_23);
        intToWiringPiMap.put(24, GPIO_24);
        intToWiringPiMap.put(25, GPIO_25);

        intToWiringPiMap.put(15, GPIO_15);
        intToWiringPiMap.put(16, GPIO_16);
        intToWiringPiMap.put(1, GPIO_1);

        intToWiringPiMap.put(4, GPIO_4);
        intToWiringPiMap.put(5, GPIO_5);

        intToWiringPiMap.put(6, GPIO_6);
        intToWiringPiMap.put(10, GPIO_10);
        intToWiringPiMap.put(11, GPIO_11);
        intToWiringPiMap.put(31, GPIO_31);

        intToWiringPiMap.put(26, GPIO_26);

        intToWiringPiMap.put(27, GPIO_27);
        intToWiringPiMap.put(28, GPIO_28);
        intToWiringPiMap.put(29, GPIO_29);

        // String to WiringPi map
        for(int key : intToWiringPiMap.keySet()) {
            stringToWiringPiMap.put("GPIO_" + key, intToWiringPiMap.get(key));
        }

        // Physical pin to WiringPi map
        physicalToWiringPiMap.put(3, GPIO_8);
        physicalToWiringPiMap.put(5, GPIO_9);
        physicalToWiringPiMap.put(7, GPIO_7);

        physicalToWiringPiMap.put(11, GPIO_0);
        physicalToWiringPiMap.put(13, GPIO_2);
        physicalToWiringPiMap.put(15, GPIO_3);

        physicalToWiringPiMap.put(19, GPIO_12);
        physicalToWiringPiMap.put(21, GPIO_13);
        physicalToWiringPiMap.put(23, GPIO_14);

        physicalToWiringPiMap.put(27, GPIO_30);
        physicalToWiringPiMap.put(29, GPIO_21);
        physicalToWiringPiMap.put(31, GPIO_22);
        physicalToWiringPiMap.put(33, GPIO_23);
        physicalToWiringPiMap.put(35, GPIO_24);
        physicalToWiringPiMap.put(37, GPIO_25);

        physicalToWiringPiMap.put(8, GPIO_15);
        physicalToWiringPiMap.put(10, GPIO_16);
        physicalToWiringPiMap.put(12, GPIO_1);

        physicalToWiringPiMap.put(16, GPIO_4);
        physicalToWiringPiMap.put(18, GPIO_5);

        physicalToWiringPiMap.put(22, GPIO_6);
        physicalToWiringPiMap.put(24, GPIO_10);
        physicalToWiringPiMap.put(26, GPIO_11);
        physicalToWiringPiMap.put(28, GPIO_31);

        physicalToWiringPiMap.put(32, GPIO_26);

        physicalToWiringPiMap.put(36, GPIO_27);
        physicalToWiringPiMap.put(38, GPIO_28);
        physicalToWiringPiMap.put(40, GPIO_29);

        // Physical pin to WiringPi
        for(int physicalPin : physicalToWiringPiMap.keySet()) {
            wiringPiToPhysicalMap.put(physicalToWiringPiMap.get(physicalPin), physicalPin);
        }
    }

    /*! This WiringPi's GPIO number */
    private final int gpioNumber;

    /**
     * Initializes a WiringPi object with the given GPIO number
     *
     * @param gpioNumber The GPIO number to be used
     */
    private WiringPi(int gpioNumber) {
        this.gpioNumber = gpioNumber;
    }

    /*! Returns the integer value associated with this GPIO object */
    public int toInt() {
        return gpioNumber;
    }

    /*! Returns this GPIO's physical pin number */
    public int getPhysicalPin() {
        return wiringPiToPhysicalMap.get(this);
    }

    /**
     * Parses the given GPIO number and returns the corresponding WiringPi object
     *
     * @param gpioNumber The GPIO number
     * @return The WiringPi reference
     * @throws IllegalArgumentException If the given GPIO number does not exist
     */
    public static WiringPi fromInteger(int gpioNumber)
    throws IllegalArgumentException {

        if(!intToWiringPiMap.containsKey(gpioNumber)) {
            throw new IllegalArgumentException("Trying to fetch invalid WiringPi GPIO pin: " + gpioNumber);
        }

        return intToWiringPiMap.get(gpioNumber);
    }

    /**
     * Parses a physical pin number and returns the corresponding WiringPi object
     *
     * @param pinNumber The physical pin number
     * @return The WiringPi reference
     * @throws IllegalArgumentException If the given physical pin number does not exist
     */
    public static WiringPi fromPhysicalPin(int pinNumber)
    throws IllegalArgumentException {
        if(!physicalToWiringPiMap.containsKey(pinNumber)) {
            throw new IllegalArgumentException("Trying to fetch invalid WiringPi GPIO pin: " + pinNumber);
        }

        return physicalToWiringPiMap.get(pinNumber);
    }

    /**
     * Parses the given string and returns the corresponding WiringPi object
     *
     * @param name The name of the GPIO
     * @return The WiringPi reference
     * @throws IllegalArgumentException If the given name does not exist as a valid GPIO
     */
    public static WiringPi fromString(String name)
    throws IllegalArgumentException {
        if(!stringToWiringPiMap.containsKey(name)) {
            throw new IllegalArgumentException("Trying to fetch invalid WiringPi GPIO pin: " + name);
        }

        return stringToWiringPiMap.get(name);
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return "GPIO_" + gpioNumber;
    }
}
