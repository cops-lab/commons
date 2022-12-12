/*
 * Copyright 2022 Delft University of Technology
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

import org.junit.jupiter.api.Test;

public class AssertsExceptionTest {

    @Test
    public void noArgsInit() {
        new AssertsException();
    }

    @Test
    public void msgInit() {
        var e = new AssertsException("...");
        assertEquals("...", e.getMessage());
    }

    @Test
    public void exceptionInit() {
        var inner = new RuntimeException();
        var e = new AssertsException(inner);
        assertEquals(inner, e.getCause());
    }

    @Test
    public void msgExceptionInitInit() {
        var inner = new RuntimeException();
        var e = new AssertsException("...", inner);
        assertEquals("...", e.getMessage());
        assertEquals(inner, e.getCause());
    }
}