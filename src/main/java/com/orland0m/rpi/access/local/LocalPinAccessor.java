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
package com.orland0m.rpi.access.local;

import com.orland0m.rpi.access.BaseAccessor;
import com.orland0m.rpi.middleware.exception.AccessorDownException;
import com.orland0m.rpi.middleware.exception.PinBusyException;
import com.orland0m.rpi.middleware.pin.InputPin;
import com.orland0m.rpi.middleware.pin.OutputPin;
import com.orland0m.rpi.middleware.pin.WiringPi;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

/**
 * Class that creates local access pin objects based on user requests
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public class LocalPinAccessor extends BaseAccessor {
    private final GpioController controller;

    public LocalPinAccessor() {
        controller = GpioFactory.getInstance();
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#shutdown()
     */
    @Override
    public void shutdown()
    throws IllegalArgumentException, PinBusyException, AccessorDownException, AccessorDownException {
        super.shutdown();
        controller.shutdown();
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getInPin(int)
     */
    @Override
    public InputPin getInPin(int physicalPinNumber)
    throws IllegalArgumentException, PinBusyException, AccessorDownException {
        WiringPi gpio = WiringPi.fromPhysicalPin(physicalPinNumber);
        return getInGpio(gpio);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getInGpio(int)
     */
    @Override
    public InputPin getInGpio(int gpioNumber) throws IllegalArgumentException, PinBusyException,
        AccessorDownException {
        WiringPi gpio = WiringPi.fromGpioAddress(gpioNumber);
        return getInGpio(gpio);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getInGpio(java.lang.String)
     */
    @Override
    public InputPin getInGpio(String gpioName) throws IllegalArgumentException, PinBusyException,
        AccessorDownException {
        WiringPi gpio = WiringPi.fromGpioName(gpioName);
        return getInGpio(gpio);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getInGpio(com.orland0m.rpi.middleware.pin.WiringPi)
     */
    @Override
    public InputPin getInGpio(WiringPi gpio) throws IllegalArgumentException, PinBusyException,
        AccessorDownException {
        assertNotDown();
        InputPin retVal = getForInputOrRelease(gpio);

        if(retVal == null) {
            retVal = new LocalInputPin(gpio, controller);
            registerProvisionedPin(retVal);
        }

        return retVal;
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getOutPin(int)
     */
    @Override
    public OutputPin getOutPin(int physicalPinNumber) throws IllegalArgumentException,
        PinBusyException {
        WiringPi gpio = WiringPi.fromPhysicalPin(physicalPinNumber);
        return getOutGpio(gpio);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getOutGpio(int)
     */
    @Override
    public OutputPin getOutGpio(int gpioNumber) throws IllegalArgumentException, PinBusyException,
        AccessorDownException {
        WiringPi gpio = WiringPi.fromGpioAddress(gpioNumber);
        return getOutGpio(gpio);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getOutGpio(java.lang.String)
     */
    @Override
    public OutputPin getOutGpio(String gpioName) throws IllegalArgumentException, PinBusyException,
        AccessorDownException {
        WiringPi gpio = WiringPi.fromGpioName(gpioName);
        return getOutGpio(gpio);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#getOutGpio(com.orland0m.rpi.middleware.pin.WiringPi)
     */
    @Override
    public OutputPin getOutGpio(WiringPi gpio) throws IllegalArgumentException, PinBusyException,
        AccessorDownException {
        assertNotDown();
        OutputPin retVal = getForOutputOrRelease(gpio);

        if(retVal == null) {
            retVal = new LocalOutputPin(gpio, controller);
            registerProvisionedPin(retVal);
        }

        return retVal;
    }
}