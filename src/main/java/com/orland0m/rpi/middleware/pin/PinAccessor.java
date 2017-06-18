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

import com.orland0m.rpi.middleware.exception.AccessorDownException;
import com.orland0m.rpi.middleware.exception.PinBusyException;

/**
 * Common interface for pin accessors, this interface is shared exposed
 * by the middleware and implemented by the local and REST accessors
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public interface PinAccessor {
    /**
     * Shuts down this pin accessor
     *
     * @throws PinBusyException If called while a pin is marked busy
     * @throws AccessorDownException If this accessor has already been shutdown
     */
    void shutdown() throws PinBusyException, AccessorDownException;

    /**
     * Checks if this pin accessor has been shutdown
     *
     * @return True if this pin accessor is already down
     */
    boolean isDown();

    /**
     * Provisions the given pin as input pin and returns a manager object
     *
     * @param physicalPinNumber The physical pin number
     * @return A reference to the pin manager
     * @throws IllegalArgumentException If the provided pin number is invalid
     * @throws PinBusyException If the desired pin is already provisioned as output pin, and it's
     * currently marked as busy
     * @throws AccessorDownException If this accessor has already been shutdown
     */
    InputPin getInPin(int physicalPinNumber) throws IllegalArgumentException, PinBusyException,
                 AccessorDownException;
    /**
     * Provisions the given pin as input pin and returns a manager object
     *
     * @param gpioNumber The GPIO port number
     * @return A reference to the pin manager
     * @throws IllegalArgumentException If the provided pin number is invalid
     * @throws PinBusyException If the desired pin is already provisioned as output pin, and it's
     * currently marked as busy
     * @throws AccessorDownException If this accessor has already been shutdown
     */
    InputPin getInGpio(int gpioNumber) throws IllegalArgumentException, PinBusyException,
                 AccessorDownException;
    /**
     * Provisions the given pin as input pin and returns a manager object
     *
     * @param gpioName The GPIO port name (e.g. GPIO_0, GPIO_1,...)
     * @return A reference to the pin manager
     * @throws IllegalArgumentException If the provided pin number is invali
     * @throws PinBusyException If the desired pin is already provisioned as output pin, and it's
     * currently marked as busy
     * @throws AccessorDownException If this accessor has already been shutdown
     */
    InputPin getInGpio(String gpioName) throws IllegalArgumentException, PinBusyException,
                 AccessorDownException;
    /**
     * Provisions the given pin as input pin and returns a manager object
     *
     * @param gpio Provisions the given pin as input pin and returns a manager object
     * @return A reference to the pin manager
     * @throws IllegalArgumentException If the provided pin number is invalid
     * @throws PinBusyException If the desired pin is already provisioned as output pin, and it's
     * currently marked as busy
     * @throws AccessorDownException If this accessor has already been shutdown
     */
    InputPin getInGpio(WiringPi gpio) throws IllegalArgumentException, PinBusyException,
                 AccessorDownException;

    /**
     * Provisions the given pin as output pin and returns a manager object
     *
     * @param physicalPinNumber The physical pin number
     * @return A reference to the pin manager
     * @throws IllegalArgumentException If the provided pin number is invalid
     * @throws PinBusyException If the desired pin is already provisioned as input pin, and it's
     * currently marked as busy
     * @throws AccessorDownException If this accessor has already been shutdown
     */
    OutputPin getOutPin(int physicalPinNumber) throws IllegalArgumentException, PinBusyException,
                  AccessorDownException;
    /**
     * Provisions the given pin as output pin and returns a manager object
     *
     * @param gpioNumber The GPIO port number
     * @return A reference to the pin manager
     * @throws IllegalArgumentException If the provided pin number is invalid
     * @throws PinBusyException If the desired pin is already provisioned as input pin, and it's
     * currently marked as busy
     * @throws AccessorDownException If this accessor has already been shutdown
     */
    OutputPin getOutGpio(int gpioNumber) throws IllegalArgumentException, PinBusyException,
                  AccessorDownException;
    /**
     *  Provisions the given pin as output pin and returns a manager object
     *
     * @param gpioName The GPIO port name (e.g. GPIO_0, GPIO_1,...)
     * @return A reference to the pin manager
     * @throws IllegalArgumentException If the provided pin number is invalid
     * @throws PinBusyException If the desired pin is already provisioned as input pin, and it's
     * currently marked as busy
     * @throws AccessorDownException If this accessor has already been shutdown
     */
    OutputPin getOutGpio(String gpioName) throws IllegalArgumentException, PinBusyException,
                  AccessorDownException;
    /**
     * Provisions the given pin as output pin and returns a manager object
     *
     * @param gpio The gpio's WiringPi object
     * @return A reference to the pin manager
     * @throws IllegalArgumentException If the provided pin number is invalid
     * @throws PinBusyException If the desired pin is already provisioned as input pin, and it's
     * currently marked as busy
     * @throws AccessorDownException If this accessor has already been shutdown
     */
    OutputPin getOutGpio(WiringPi gpio) throws IllegalArgumentException, PinBusyException,
                  AccessorDownException;
}