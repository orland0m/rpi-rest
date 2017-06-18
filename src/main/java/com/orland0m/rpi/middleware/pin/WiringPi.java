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
    GPIO_8(8, 3), GPIO_9(9, 5), GPIO_7(7, 7),
    GPIO_0(0, 11), GPIO_2(2, 13), GPIO_3(3, 15),
    GPIO_12(12, 19), GPIO_13(13, 21), GPIO_14(14, 23),
    GPIO_30(30, 27), GPIO_21(21, 29), GPIO_22(22, 31), GPIO_23(23, 33), GPIO_24(24, 35), GPIO_25(25, 37),
    GPIO_15(15, 8), GPIO_16(16, 10), GPIO_1(1, 12),
    GPIO_4(4, 16), GPIO_5(5, 18),
    GPIO_6(6, 22), GPIO_10(10, 24), GPIO_11(11, 26), GPIO_31(31, 28),
    GPIO_26(26, 32),
    GPIO_27(27, 36), GPIO_28(28, 38), GPIO_29(29, 40);

    /*! A map used to translate GPIO number into a WiringPi object */
    private static final Map<Integer, WiringPi> intToWiringPiMap;
    /*! A map used to translate a GPIO_* string into a WiringPi object */
    private static final Map<String, WiringPi> stringToWiringPiMap;
    /*! A map used to translate a physical pin into a WiringPi object */
    private static final Map<Integer, WiringPi> physicalToWiringPiMap;

    static {
        intToWiringPiMap = new HashMap<>();
        stringToWiringPiMap = new HashMap<>();
        physicalToWiringPiMap = new HashMap<>();

        for(WiringPi gpio : WiringPi.values()) {
            intToWiringPiMap.put(gpio.getGpioAddress(), gpio);
            stringToWiringPiMap.put(gpio.toString(), gpio);
            physicalToWiringPiMap.put(gpio.getPhysicalPin(), gpio);
        }
    }

    /*! This WiringPi's GPIO number */
    private final int gpioNumber;
    /*! This GPIOs physical pin */
    private final int physicalPinNumber;

    /**
     * Initializes a WiringPi object with the given GPIO number
     *
     * @param gpioNumber The GPIO number to be used
     */
    private WiringPi(int gpioNumber, int physicalPinNumber) {
        this.gpioNumber = gpioNumber;
        this.physicalPinNumber = physicalPinNumber;
    }

    /*! Returns the integer value associated with this GPIO object */
    public int getGpioAddress() {
        return gpioNumber;
    }

    /*! Returns this GPIO's physical pin number */
    public int getPhysicalPin() {
        return physicalPinNumber;
    }

    /**
     * Parses the given GPIO number and returns the corresponding WiringPi object
     *
     * @param gpioNumber The GPIO number
     * @return The WiringPi reference
     * @throws IllegalArgumentException If the given GPIO number does not exist
     */
    public static WiringPi fromGpioAddress(int gpioNumber)
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
    public static WiringPi fromGpioName(String name)
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
