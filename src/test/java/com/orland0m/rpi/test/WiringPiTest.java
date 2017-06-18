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
package com.orland0m.rpi.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.orland0m.rpi.middleware.pin.WiringPi;
import com.orland0m.rpi.test.helper.Reflector;

/**
 * ULT class for WiringPi functionality
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public class WiringPiTest {
    /*! An array containing all WiringPi GPIOs */
    private static final int[] GPIOS = new int[] {
        8, 9, 7,
        0, 2, 3,
        12, 13, 14,
        30, 21, 22, 23, 24, 25,
        15, 16, 1,
        4, 5,
        6, 10, 11, 31,
        26,
        27, 28, 29
    };
    /*! An array containing all physical pins in WiringPi specification,
     * the positions in this array match their corresponding GPIO in @c GPIOS */
    private static final int[] PHYSICAL_PINS = new int[] {
        3, 5, 7,
        11, 13, 15,
        19, 21, 23,
        27, 29, 31, 33, 35, 37,
        8, 10, 12,
        16, 18,
        22, 24, 26, 28,
        32,
        36, 38, 40
    };
    /*! The number of GPIO pins available */
    private static int EXPECTED_GPIO_COUNT = 28;

    /**
     * Ensures all internal arrays of WiringPi class have valid sizes
     *
     * @throws NoSuchFieldException Thrown by reflection library ava.lang.reflect.Field
     * @throws SecurityException Thrown by reflection library ava.lang.reflect.Field
     * @throws IllegalArgumentException Thrown by reflection library ava.lang.reflect.Field
     * @throws IllegalAccessException Thrown by reflection library ava.lang.reflect.Field
     */
    @Test
    public void correctSizesTest()
    throws NoSuchFieldException,
               SecurityException,
               IllegalArgumentException,
        IllegalAccessException {
        Map<WiringPi, String> intToWiringPiMap = Reflector.reflectField(WiringPi.class, WiringPi.GPIO_0,
                "intToWiringPiMap");
        Map<WiringPi, String> stringToWiringPiMap = Reflector.reflectField(WiringPi.class, WiringPi.GPIO_0,
                "stringToWiringPiMap");
        Map<WiringPi, String> physicalToWiringPiMap = Reflector.reflectField(WiringPi.class,
                WiringPi.GPIO_0, "physicalToWiringPiMap");
        assertEquals(EXPECTED_GPIO_COUNT, intToWiringPiMap.size());
        assertEquals(EXPECTED_GPIO_COUNT, stringToWiringPiMap.size());
        assertEquals(EXPECTED_GPIO_COUNT, physicalToWiringPiMap.size());
    }

    /**
     * Ensures all GPIO can be fetched using fromInteger and that the function
     * returns a different object for each one of them
     */
    @Test
    public void gpioCompletnessTest() {
        assertEquals(EXPECTED_GPIO_COUNT, GPIOS.length);
        Set<WiringPi> references = new HashSet<>();

        for(int gpio : GPIOS) {
            WiringPi obj = WiringPi.fromGpioAddress(gpio);
            assertFalse(references.contains(obj));
            assertEquals(gpio, obj.getGpioAddress());
            references.add(obj);
        }

        assertEquals(EXPECTED_GPIO_COUNT, references.size());
    }

    /**
     * Ensures all GPIO toString calls return a valid name string
     */
    @Test
    public void toStringCompletnessTest() {
        assertEquals(EXPECTED_GPIO_COUNT, GPIOS.length);

        for(int gpio : GPIOS) {
            WiringPi obj = WiringPi.fromGpioAddress(gpio);
            assertEquals("GPIO_" + gpio, obj.toString());
        }
    }

    /**
     * Ensures all physical pins can be converted into WiringPi objects,
     * and ensures fromPhysicalPin returns a different object for each pin
     */
    @Test
    public void physicalPinsCompletnessTest() {
        assertEquals(EXPECTED_GPIO_COUNT, GPIOS.length);
        assertEquals(EXPECTED_GPIO_COUNT, PHYSICAL_PINS.length);
        Set<WiringPi> references = new HashSet<>();

        for(int i = 0; i < EXPECTED_GPIO_COUNT; i++) {
            WiringPi obj = WiringPi.fromPhysicalPin(PHYSICAL_PINS[i]);
            assertFalse(references.contains(obj));
            assertEquals(GPIOS[i], obj.getGpioAddress());
            assertEquals(PHYSICAL_PINS[i], obj.getPhysicalPin());
            references.add(obj);
        }

        assertEquals(EXPECTED_GPIO_COUNT, references.size());
    }

    /**
     * Ensures all GPIO names can be turned into WringPi objects,
     * and ensures fromString returns a different object for each name
     */
    @Test
    public void fromStringCompletnessTest() {
        Set<WiringPi> references = new HashSet<>();

        for(int i = 0; i < EXPECTED_GPIO_COUNT; i++) {
            String gpioName = "GPIO_" + GPIOS[i];
            WiringPi obj = WiringPi.fromGpioName(gpioName);
            assertFalse(references.contains(obj));
            assertEquals(GPIOS[i], obj.getGpioAddress());
            assertEquals(gpioName, obj.toString());
            references.add(obj);
        }

        assertEquals(EXPECTED_GPIO_COUNT, references.size());
    }
}
