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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AssertsTest {

    @Test
    public void assertNull_fail() {
        var e = assertThrows(AssertsException.class, () -> {
            Asserts.assertNull("");
        });
        assertEquals("Should be null.", e.getMessage());
    }

    @Test
    public void assertNull_ok() {
        var out = Asserts.assertNull(null);
        assertNull(out);
    }

    @Test
    public void assertNotNull_fail() {
        var e = assertThrows(AssertsException.class, () -> {
            Asserts.assertNotNull(null);
        });
        assertEquals("Should not be null.", e.getMessage());
    }

    @Test
    public void assertNotNull_ok() {
        var in = "";
        var out = Asserts.assertNotNull(in);
        assertSame(in, out);
    }

    @Test
    public void assertContains_fail() {
        var e = assertThrows(AssertsException.class, () -> {
            Asserts.assertContains(new String[] { "a", "b", "c" }, "x");
        });
        assertEquals("Expected element 'x' not contained in array.", e.getMessage());
    }

    @Test
    public void assertContains_ok() {
        var in = "a";
        var out = Asserts.assertContains(new String[] { "a", "b", "c" }, in);
        assertSame(in, out);
    }

    @Test
    public void assertContains_okWithNullAllowed() {
        var in = "a";
        var out = Asserts.assertContains(new String[] { null, "a", "b", "c" }, in);
        assertSame(in, out);
    }

    @Test
    public void assertContains_okForNullElement() {
        var out = Asserts.assertContains(new String[] { "a", null }, null);
        assertNull(out);
    }

    @Test
    public void assertNotNullOrEmpty_failNull() {
        var e = assertThrows(AssertsException.class, () -> {
            Asserts.assertNotNullOrEmpty(null);
        });
        assertEquals("String is null or empty.", e.getMessage());
    }

    @Test
    public void assertNotNullOrEmpty_failEmpty() {
        var e = assertThrows(AssertsException.class, () -> {
            Asserts.assertNotNullOrEmpty("");
        });
        assertEquals("String is null or empty.", e.getMessage());
    }

    @Test
    public void assertNotNullOrEmpty_ok() {
        var in = "...";
        var out = Asserts.assertNotNullOrEmpty(in);
        assertSame(in, out);
    }

    @Test
    public void assertNullOrNotEmpty_okNull() {
        var out = Asserts.assertNullOrNotEmpty(null);
        assertNull(out);
    }

    @Test
    public void assertNullOrNotEmpty_okNonEmpty() {
        var in = "...";
        var out = Asserts.assertNullOrNotEmpty(in);
        assertSame(in, out);
    }

    @Test
    public void assertNullOrNotEmpty_failEmpty() {
        var e = assertThrows(AssertsException.class, () -> {
            Asserts.assertNullOrNotEmpty("");
        });
        assertEquals("String is null or empty.", e.getMessage());
    }

    @Test
    public void assertTrue_okStd() {
        var out = Asserts.assertTrue(true);
        assertTrue(out);
    }

    @Test
    public void assertTrue_failStd() {
        var e = assertThrows(AssertsException.class, () -> {
            Asserts.assertTrue(false);
        });
        assertEquals("Should be true.", e.getMessage());
    }

    @Test
    public void assertTrue_okCustom() {
        var out = Asserts.assertTrue(true, "...");
        assertTrue(out);
    }

    @Test
    public void assertTrue_failCustom() {
        var e = assertThrows(AssertsException.class, () -> {
            Asserts.assertTrue(false, "abc");
        });
        assertEquals("abc", e.getMessage());
    }

    @Test
    public void assertFalse_okStd() {
        var out = Asserts.assertFalse(false);
        assertFalse(out);
    }

    @Test
    public void assertFalse_failStd() {
        var e = assertThrows(AssertsException.class, () -> {
            Asserts.assertFalse(true);
        });
        assertEquals("Should be false.", e.getMessage());
    }

    @Test
    public void assertFalse_okCustom() {
        var out = Asserts.assertFalse(false, "...");
        assertFalse(out);
    }

    @Test
    public void assertFalse_failCustom() {
        var e = assertThrows(AssertsException.class, () -> {
            Asserts.assertFalse(true, "abc");
        });
        assertEquals("abc", e.getMessage());
    }
}