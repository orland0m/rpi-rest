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

import com.orland0m.rpi.middleware.exception.InvalidatedPinException;
import com.orland0m.rpi.middleware.exception.PinBusyException;

/**
 * Common interface for all RaspberryPi pins
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public interface RpiPin {
    /**
     * Returns true if this pin is in a high state
     *
     * @return True if the pin is in a high state
     * @throws InvalidatedPinException If the pin object has already been invalidated
     */
    boolean isUp() throws InvalidatedPinException;
    /**
     * Returns true if this pin is in a low state
     *
     * @return True if the pin is in a low state
     * @throws InvalidatedPinException If the pin object has already been invalidated
     */
    boolean isDown() throws InvalidatedPinException;
    /**
     * Returns the GPIO information object of this pin
     *
     * @return A reference to the GPIO info object
     * @throws InvalidatedPinException If the pin object has already been invalidated
     */
    WiringPi getGpioInfo();

    /**
     * Marks this pin as busy
     *
     * @throws PinBusyException If the pin was already busy at the time of the call
     * @throws InvalidatedPinException If the pin object has already been invalidated
     */
    void markBusy() throws PinBusyException, InvalidatedPinException;
    /**
     * Returns true if this pin is currently free
     *
     * @return True if this pin is currently free
     * @throws InvalidatedPinException If the pin object has already been invalidated
     */
    boolean isFree() throws InvalidatedPinException;
    /**
     * Returns true if this pin is currently busy
     *
     * @return True if this pin is currently busy
     * @throws InvalidatedPinException If the pin object has already been invalidated
     */
    boolean isBusy() throws InvalidatedPinException;
    /**
     * Marks this pin as free
     *
     * @throws InvalidatedPinException If the pin object has already been invalidated
     */
    void markFree() throws InvalidatedPinException;
    /**
     * Invalidates this pin, ensuring all further calls to it fail
     *
     * @throws PinBusyException If the pin was busy at the time of the call
     * @throws InvalidatedPinException If the pin object has already been invalidated
     */
    void markInvalid() throws PinBusyException, InvalidatedPinException;
    /**
     * Returns true if the resource is currently valid
     *
     * @return True if the resource is currently valid
     */
    boolean isValid();
}