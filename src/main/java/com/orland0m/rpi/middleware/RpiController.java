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
package com.orland0m.rpi.middleware;

import com.orland0m.rpi.access.local.LocalPinAccessor;
import com.orland0m.rpi.access.rest.RestConfig;
import com.orland0m.rpi.middleware.exception.AccessorDownException;
import com.orland0m.rpi.middleware.exception.PinBusyException;
import com.orland0m.rpi.middleware.pin.InputPin;
import com.orland0m.rpi.middleware.pin.OutputPin;
import com.orland0m.rpi.middleware.pin.PinAccessor;
import com.orland0m.rpi.middleware.pin.WiringPi;

/**
 * Middleware class used to have transparent access to GPIO. This class
 * can use a local pin accessor or a REST based PIN accessor
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public class RpiController implements PinAccessor {
    /*! Middleware object for pin access */
    private PinAccessor middleware;

    public RpiController(RestConfig config) {
        // Will create a rest based pin accessor
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Initializes a controller object that uses a local pin accessor
     */
    public RpiController() {
        middleware = new LocalPinAccessor();
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#shutdown()
     */
    @Override
    public void shutdown()
    throws IllegalArgumentException, PinBusyException, AccessorDownException, AccessorDownException,
        AccessorDownException, AccessorDownException {
        middleware.shutdown();
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#isDown()
     */
    @Override
    public boolean isDown() {
        return middleware.isDown();
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getInPin(int)
     */
    @Override
    public InputPin getInPin(int physicalPinNumber)  throws IllegalArgumentException, PinBusyException,
        AccessorDownException, AccessorDownException {
        return middleware.getInPin(physicalPinNumber);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getInGpio(int)
     */
    @Override
    public InputPin getInGpio(int gpioNumber)  throws IllegalArgumentException, PinBusyException,
        AccessorDownException, AccessorDownException {
        return middleware.getInGpio(gpioNumber);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getInGpio(java.lang.String)
     */
    @Override
    public InputPin getInGpio(String gpioName)  throws IllegalArgumentException, PinBusyException,
        AccessorDownException, AccessorDownException {
        return middleware.getInGpio(gpioName);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getInGpio(com.orland0m.rpi.middleware.pin.WiringPi)
     */
    @Override
    public InputPin getInGpio(WiringPi gpio)  throws IllegalArgumentException, PinBusyException,
        AccessorDownException, AccessorDownException {
        return middleware.getInGpio(gpio);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getOutPin(int)
     */
    @Override
    public OutputPin getOutPin(int physicalPinNumber) throws IllegalArgumentException,
        PinBusyException {
        return middleware.getOutPin(physicalPinNumber);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getOutGpio(int)
     */
    @Override
    public OutputPin getOutGpio(int gpioNumber)  throws IllegalArgumentException, PinBusyException,
        AccessorDownException, AccessorDownException {
        return middleware.getOutGpio(gpioNumber);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getOutGpio(java.lang.String)
     */
    @Override
    public OutputPin getOutGpio(String gpioName)  throws IllegalArgumentException, PinBusyException,
        AccessorDownException, AccessorDownException {
        return middleware.getOutGpio(gpioName);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getOutGpio(com.orland0m.rpi.middleware.pin.WiringPi)
     */
    @Override
    public OutputPin getOutGpio(WiringPi gpio)  throws IllegalArgumentException, PinBusyException,
        AccessorDownException, AccessorDownException {
        return middleware.getOutGpio(gpio);
    }
}