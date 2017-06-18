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
package com.orland0m.rpi.access;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.orland0m.rpi.middleware.exception.AccessorDownException;
import com.orland0m.rpi.middleware.exception.PinBusyException;
import com.orland0m.rpi.middleware.pin.InputPin;
import com.orland0m.rpi.middleware.pin.OutputPin;
import com.orland0m.rpi.middleware.pin.PinAccessor;
import com.orland0m.rpi.middleware.pin.RpiPin;
import com.orland0m.rpi.middleware.pin.WiringPi;

/**
 * Abstract class with common functionality for pin accessors to manage pin objects
 * and misc features.
 * NOTE: Functions grab the down mutex instead of calling assertNotDown because
 * they need the accessor to stay in a valid state while performing an operation.
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public abstract class BaseAccessor implements PinAccessor {
    /*! Logger object reference */
    final static Logger logger = Logger.getLogger(BaseAccessor.class);
    /*! Message used when the user tries to use an accessor but it's already down */
    private static final String ACCESSOR_DOWN_MSG =
        "Accessor already down, cannot keep using this instance";
    /*! Message used when the register a pin that is currently in use */
    private static final String PIN_IN_USE_MSG =
        "You must first release the GPIO pin before trying to register it again";
    /*! Message used when the user tries to registter a null pin object */
    private static final String NULL_PIN_MSG = "Trying to register a null pin object";
    /*! Map containing the currently provisioned pins */
    private final Map<WiringPi, RpiPin> provisionedPins;
    /*! Memory address used to have exclusive access to the isDown flag  */
    private final Object downLock  = new Object();
    /*! Whether this accessor is down or not */
    private boolean isDown;

    /**
     * Initializes common fields
     */
    protected BaseAccessor() {
        provisionedPins = new HashMap<>();
        isDown = false;
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#shutdown()
     */
    @Override
    public void shutdown() throws PinBusyException,
        AccessorDownException {
        Iterator<Map.Entry<WiringPi, RpiPin>> iter;

        synchronized(downLock) {
            if(isDown) {
                throw new AccessorDownException(ACCESSOR_DOWN_MSG);

            } else {
                synchronized(provisionedPins) {
                    iter = provisionedPins.entrySet().iterator();

                    while(iter.hasNext()) {
                        Map.Entry<WiringPi, RpiPin> entry = iter.next();
                        RpiPin pin = entry.getValue();
                        logger.trace("Releasing " + pin.getGpioInfo() + "...");

                        if(pin.isValid()) {
                            pin.markInvalid();
                        }

                        iter.remove();
                        logger.trace("Successfully released " + pin.getGpioInfo());
                    }
                }

                isDown = true;
            }
        }
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.PinAccessor#isDown()
     */
    @Override
    public boolean isDown() {
        boolean retVal = false;

        synchronized(downLock) {
            retVal = isDown;
        }

        return retVal;
    }

    /**
     * @throws AccessorDownException
     */
    protected void assertNotDown() throws
        AccessorDownException {
        synchronized(downLock) {
            if(isDown) {
                throw new AccessorDownException(ACCESSOR_DOWN_MSG);
            }
        }
    }

    /**
     * Registeres an already provisioned pin
     *
     * @param pin A reference to the pin object
     * @throws IllegalArgumentException If the GPIO associated with the pin is already registered
     * @throws NullPointerException  If the pin object is not initialized
     * @throws AccessorDownException If this accessor is already down
     */
    protected void registerProvisionedPin(RpiPin pin) throws IllegalArgumentException,
        NullPointerException,
        AccessorDownException {
        if(pin == null) {
            throw new NullPointerException(NULL_PIN_MSG);
        }

        synchronized(downLock) {
            if(!isDown) {
                synchronized(provisionedPins) {
                    logger.trace("Registering " + pin.getGpioInfo() + "...");

                    if(provisionedPins.containsKey(pin.getGpioInfo())) {
                        throw new IllegalArgumentException(PIN_IN_USE_MSG);

                    } else {
                        provisionedPins.put(pin.getGpioInfo(), pin);
                        logger.trace("Successfully registered " + pin.getGpioInfo());
                    }
                }

            } else {
                throw new AccessorDownException(ACCESSOR_DOWN_MSG);
            }
        }
    }

    /**
     * Returns the pin object for the given GPIO if it is already registered as an InputPin,
     * and if that pin object is in a valid state. Returns null if any of those conditions
     * is not true. For cases where the pin was already registered but it was not an InputPin,
     * the old pin object is marked invalid and unregistered.
     *
     * @param gpio The GPIO information for the pin in question
     * @return The pin object, or null if it did not exist
     * @throws PinBusyException If the GPIO is already registered as OutputPin but that pin is currently busy
     * @throws AccessorDownException If this accessor is already down
     */
    protected InputPin getForInputOrRelease(WiringPi gpio) throws PinBusyException,
        AccessorDownException {
        RpiPin pin;
        InputPin retVal = null;

        synchronized(downLock) {
            if(!isDown) {
                synchronized(provisionedPins) {
                    if(provisionedPins.containsKey(gpio)) {
                        pin = provisionedPins.get(gpio);
                        logger.trace("Trying to reuse " + pin.getGpioInfo() + " as input pin");

                        if(pin instanceof InputPin && pin.isValid()) {
                            logger.trace("Reuse possible... returning existing instance");
                            retVal = (InputPin)pin;

                        } else {
                            logger.trace("Pin already registered, releasing " + pin.getGpioInfo() + "...");

                            if(pin.isValid()) {
                                pin.markInvalid();
                            }

                            provisionedPins.remove(gpio);
                            logger.trace("Successfully released " + pin.getGpioInfo());
                        }
                    }
                }

            } else {
                throw new AccessorDownException(ACCESSOR_DOWN_MSG);
            }
        }

        return retVal;
    }

    /**
     * Returns the pin object for the given GPIO if it is already registered as an OutputPin,
     * and if that pin object is in a valid state. Returns null if any of those conditions
     * is not true. For cases where the pin was already registered but it was not an OutputPin,
     * the old pin object is marked invalid and unregistered.
     *
     * @param gpio The GPIO information for the pin in question
     * @return The pin object, or null if it did not exist
     * @throws PinBusyException If the GPIO is already registered as InputPin but that pin is currently busy
     * @throws AccessorDownException If this accessor is already down
     */
    protected OutputPin getForOutputOrRelease(WiringPi gpio) throws PinBusyException,
        AccessorDownException {
        RpiPin pin;
        OutputPin retVal = null;

        synchronized(downLock) {
            if(!isDown) {
                synchronized(provisionedPins) {
                    if(provisionedPins.containsKey(gpio)) {
                        pin = provisionedPins.get(gpio);
                        logger.trace("Trying to reuse " + pin.getGpioInfo() + " as output pin");

                        if(pin instanceof OutputPin && pin.isValid()) {
                            logger.trace("Reuse possible... returning existing instance");
                            retVal = (OutputPin)pin;

                        } else {
                            logger.trace("Pin already registered, releasing " + pin.getGpioInfo() + "...");

                            if(pin.isValid()) {
                                pin.markInvalid();
                            }

                            provisionedPins.remove(gpio);
                            logger.trace("Successfully released " + pin.getGpioInfo());
                        }
                    }
                }

            } else {
                throw new AccessorDownException(ACCESSOR_DOWN_MSG);
            }
        }

        return retVal;
    }
}