/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.c0ps.commons;

public class Asserts {

    private Asserts() {
        // do not initialize this class
    }

    public static <T> T assertNull(T o) {
        if (o == null) {
            return o;
        }
        throw $("Should be null.");
    }

    public static <T> T assertNotNull(T o) {
        if (o == null) {
            throw $("Should not be null.");
        }
        return o;
    }

    public static <T> T assertContains(T[] ts, T e) {
        for (T a : ts) {
            if (a == null) {
                if (e == null) {
                    return null;
                }
            } else {
                if (a.equals(e)) {
                    return e;
                }
            }
        }
        throw $("Expected element '%s' not contained in array.", e);
    }

    public static String assertNotNullOrEmpty(String s) {
        if (s == null || s.isEmpty()) {
            throw $("String is null or empty.");
        }
        return s;
    }

    public static String assertNullOrNotEmpty(String s) {
        if (s == null || !s.isEmpty()) {
            return s;
        }
        throw $("String is null or empty.");
    }

    public static boolean assertTrue(boolean condition, String failMsg) {
        if (condition) {
            return true;
        }
        throw $(failMsg);
    }

    public static boolean assertTrue(boolean condition) {
        if (condition) {
            return true;
        }
        throw $("Should be true.");
    }

    public static boolean assertFalse(boolean condition, String failMsg) {
        if (condition) {
            throw $(failMsg);
        }
        return false;
    }

    public static boolean assertFalse(boolean condition) {
        if (condition) {
            throw $("Should be false.");
        }
        return false;
    }

    private static RuntimeException $(String msg, Object... args) {
        var s = String.format(msg, args);
        return new AssertsException(s);
    }
}