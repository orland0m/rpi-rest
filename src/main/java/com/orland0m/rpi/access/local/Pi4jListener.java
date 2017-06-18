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

import com.orland0m.rpi.middleware.event.PinStateListener;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Adapter class used to redirect pi4j events into local listeners
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public class Pi4jListener implements GpioPinListenerDigital {

    /*! A reference to the pin object listening for events */
    private final LocalInputPin pin;
    /*! A reference to the  */
    private final PinStateListener listener;

    /**
     * @param pin
     * @param listener
     * @throws NullPointerException
     */
    public Pi4jListener(LocalInputPin pin, PinStateListener listener) throws NullPointerException {
        this.pin = pin;
        this.listener = listener;

        if(pin == null || listener == null) {
            throw new NullPointerException("Pin and listener objects must be valid objects");
        }
    }

    /* (non-Javadoc)
     * @see com.pi4j.io.gpio.event.GpioPinListenerDigital#handleGpioPinDigitalStateChangeEvent(com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent)
     */
    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        listener.onPinStateChange(pin);
    }
}