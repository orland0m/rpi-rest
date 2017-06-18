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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.orland0m.rpi.middleware.pin.PinAccessor;

/**
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public abstract class BaseAccessor implements PinAccessor {
    protected final Map<Integer, Integer> pysicalToGpio;
    protected final Set<Integer> gpioPins;

    protected BaseAccessor() {
        pysicalToGpio = new HashMap<>();
        gpioPins = new HashSet<>();
        
        for(int i : new int[] {
                    8, 9, 7,
                    0, 2, 3,
                    12, 13, 14,
                    30, 21, 22, 23, 24, 25,
                    15, 16, 1,
                    4, 5,
                    6, 10, 11, 31,
                    26,
                    27, 28, 29
                }) {
            gpioPins.add(i);
        }
    }
}