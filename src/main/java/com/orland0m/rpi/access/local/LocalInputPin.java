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

import com.orland0m.rpi.access.BasePin;
import com.orland0m.rpi.middleware.event.PinStateListener;
import com.orland0m.rpi.middleware.exception.InvalidatedPinException;
import com.orland0m.rpi.middleware.exception.PinBusyException;
import com.orland0m.rpi.middleware.pin.InputPin;
import com.orland0m.rpi.middleware.pin.WiringPi;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Specialized implementation for local input pin access. NOTE: local pins
 * never mark the pin as 'busy' because access calls are immediate and there
 * is no delay between the time the user issues the call and the time
 * that the request is serviced. This delay does exist for REST based
 * pins for example.
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public class LocalInputPin extends BasePin implements InputPin {
    /*! A reference to the pi4j pin object */
    private final GpioPinDigitalInput pin;

    /**
     * Initializes a local input pin object for the given GPIO object
     *
     * @param gpio The GPIO pin information
     */
    public LocalInputPin(WiringPi gpio, GpioController controller) {
        super(gpio);
        pin = controller.provisionDigitalInputPin(RaspiPin.getPinByAddress(gpio.getGpioAddress()),
                gpio.toString(),
                PinPullResistance.PULL_DOWN);
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.RpiPin#isHigh()
     */
    @Override
    public boolean isUp() throws InvalidatedPinException {
        assertValidity();
        return pin.isHigh();
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.RpiPin#isLow()
     */
    @Override
    public boolean isDown() throws InvalidatedPinException {
        return !isUp();
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.InputPin#addListener(com.orland0m.rpi.middleware.event.PinStateListener)
     */
    @Override
    public void addListener(PinStateListener listener) throws InvalidatedPinException {
        assertValidity();
        pin.addListener(new Pi4jListener(this, listener));
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.RpiPin#markInvalid()
     */
    @Override
    public void markInvalid() throws PinBusyException, InvalidatedPinException {
        super.markInvalid();
        pin.removeAllListeners();
        pin.unexport();
        pin.setPullResistance(PinPullResistance.OFF);
    }
}