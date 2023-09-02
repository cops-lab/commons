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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.c0ps.test.TestLoggerUtils;

public class MemoryUsageUtilsTest {

    @BeforeEach
    public void setup() {
        TestLoggerUtils.clearLog();
    }

    @Test
    public void usage() {
        MemoryUsageUtils.logMemoryUsage();
        var logs = TestLoggerUtils.getFormattedLogs(MemoryUsageUtils.class);
        assertEquals(2, logs.size());
        assertTrue(logs.get(0).startsWith("INFO Used memory: "));
        assertTrue(logs.get(1).startsWith("INFO Heap Size: "));
    }

    @Test
    public void maxMemory() {
        MemoryUsageUtils.logMaxMemory();
        var logs = TestLoggerUtils.getFormattedLogs(MemoryUsageUtils.class);
        assertEquals(1, logs.size());
        assertTrue(logs.get(0).startsWith("INFO Max memory: "));
    }
}