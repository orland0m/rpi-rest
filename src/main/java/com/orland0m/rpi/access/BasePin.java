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

import org.apache.log4j.Logger;

import com.orland0m.rpi.middleware.exception.InvalidatedPinException;
import com.orland0m.rpi.middleware.exception.PinBusyException;
import com.orland0m.rpi.middleware.pin.RpiPin;
import com.orland0m.rpi.middleware.pin.WiringPi;

/**
 * Abstract pin class with common functionality to manage pin state and misc features.
 * NOTE: Functions grab the validity mutex instead of calling assertValidity because
 * they need the pin to stay in a valid state while performing an operation.
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public abstract class BasePin implements RpiPin {
    /*! Logger object reference */
    final static Logger logger = Logger.getLogger(BasePin.class);
    /*! Message used when the user tries to use an invalidated pin object */
    private static final String INVALID_PIN_MSG =
        "This pin object has already been marked invalid, you cannot keep using it";
    /*! Message used when the user tries to use a busy pin */
    private static final String PIN_BUSY_MSG =
        "This pin object is currently busy, cannot mark as busy again";
    /*! Message used when the user tries to invalidate a busy pin */
    private static final String CANNOT_MARK_INVALID =
        "This pin object is currently busy, cannot mark as invalid at this moment";
    /*! Memory address used to have exclusive access to the isValid flag */
    private final Object validMutex = new Object();
    /*! Memory address used to have exclusive access to the isBusy flag */
    private final Object busyMutex = new Object();
    /*! The GPIO pin information */
    protected final WiringPi gpio;
    /*! Whether this pin is valid */
    private boolean isValid;
    /*! Whether this pin is busy */
    private boolean isBusy;

    /**
     * Initializes common pin objects
     *
     * @param gpio The GPIO pin information
     */
    protected BasePin(WiringPi gpio) {
        this.gpio = gpio;
        isValid = true;
        isBusy = false;
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.RpiPin#getGpioInfo()
     */
    @Override
    public WiringPi getGpioInfo() {
        return gpio;
    }

    /**
     * Ensures this pin is valid at the time of the call
     *
     * @throws InvalidatedPinException If the pin has been invalidated
     */
    protected void assertValidity() throws InvalidatedPinException {
        synchronized(validMutex) {
            if(!isValid) {
                throw new InvalidatedPinException(INVALID_PIN_MSG);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.RpiPin#markBusy()
     */
    @Override
    public void markBusy() throws PinBusyException, InvalidatedPinException {
        synchronized(validMutex) {
            if(isValid) {
                synchronized(busyMutex) {
                    if(isBusy) {
                        throw new PinBusyException(PIN_BUSY_MSG);

                    } else {
                        logger.trace("Marked " + gpio + " busy");
                        isBusy = true;
                    }
                }

            } else {
                throw new InvalidatedPinException(INVALID_PIN_MSG);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.RpiPin#isFree()
     */
    @Override
    public boolean isFree() throws InvalidatedPinException {
        return !isBusy();
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.RpiPin#isBusy()
     */
    @Override
    public boolean isBusy() throws InvalidatedPinException {
        boolean isBusy = true;

        synchronized(validMutex) {
            if(isValid) {
                synchronized(busyMutex) {
                    isBusy = this.isBusy;
                }

            } else {
                throw new InvalidatedPinException(INVALID_PIN_MSG);
            }
        }

        return isBusy;
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.RpiPin#markFree()
     */
    @Override
    public void markFree() throws InvalidatedPinException {
        synchronized(validMutex) {
            if(isValid) {
                synchronized(busyMutex) {
                    isBusy = false;
                    logger.trace("Marked " + gpio + " free");
                }

            } else {
                throw new InvalidatedPinException(INVALID_PIN_MSG);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.RpiPin#markInvalid()
     */
    @Override
    public void markInvalid() throws PinBusyException, InvalidatedPinException {
        synchronized(validMutex) {
            if(isValid) {
                synchronized(busyMutex) {
                    if(isBusy) {
                        throw new PinBusyException(CANNOT_MARK_INVALID);

                    } else {
                        isValid = false;
                        logger.debug("Marked " + gpio + " invalid");
                    }
                }

            } else {
                throw new InvalidatedPinException(INVALID_PIN_MSG);
            }
        }
    }

    /* (non-Javadoc)
     * @see com.orland0m.rpi.middleware.pin.RpiPin#isValid()
     */
    @Override
    public boolean isValid() {
        boolean retVal = false;

        synchronized(validMutex) {
            retVal = isValid;
        }

        return retVal;
    }
}