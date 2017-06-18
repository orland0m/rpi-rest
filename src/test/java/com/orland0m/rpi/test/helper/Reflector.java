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
package com.orland0m.rpi.test.helper;

import java.lang.reflect.Field;

/**
 * Class used to read private elements form objects in order to test their integrity
 *
 * @author Orlando Miramontes <https://github.com/orland0m>
 */
public class Reflector {
    /**
     * Access an objects field and returns a cased instance for the caller to use
     *
     * @param typeClass The class object belonging to the object being analyzed
     * @param srcObj A reference to the object being analyzed
     * @param fieldName The field name the user needs
     * @return The parsed value ready to be used
     * @throws NoSuchFieldException Thrown by reflection library ava.lang.reflect.Field
     * @throws SecurityException Thrown by reflection library ava.lang.reflect.Field
     * @throws IllegalArgumentException Thrown by reflection library ava.lang.reflect.Field
     * @throws IllegalAccessException Thrown by reflection library ava.lang.reflect.Field
     */
    @SuppressWarnings("unchecked")
    public static <RetVal, SourceType> RetVal reflectField(Class<SourceType> typeClass,
            SourceType srcObj,
            String fieldName)
    throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        //https://stackoverflow.com/questions/34571/how-do-i-test-a-class-that-has-private-methods-fields-or-inner-classes
        Field accessor = typeClass.getDeclaredField(fieldName);
        accessor.setAccessible(true);
        Object data = accessor.get(srcObj);
        return (RetVal) data;
    }
}