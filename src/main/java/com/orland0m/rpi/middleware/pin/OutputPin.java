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

/**
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public class OutputPin implements RpiPin {

    public OutputPin(int number, String name, PinState initialState) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean isHigh() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public PinState getState() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setState(PinState state) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void up() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void down() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void toggle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void pulse(int millis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}