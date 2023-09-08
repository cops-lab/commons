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

import static dev.c0ps.commons.ResourceUtils.getTestResource;
import static dev.c0ps.test.TestLoggerUtils.assertLogsContain;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.c0ps.test.TestLoggerUtils;

public class ResourceUtilsTest {

    private static final String CONTENT_SOME = "some content";
    private static final String CONTENT_OTHER = "other content";

    @BeforeEach
    public void setup() {
        TestLoggerUtils.clearLog();
    }

    @Test
    public void get_relativeFiles() {
        var path = "somefile.txt";
        var f = getTestResource(path);
        assertTrue(f.exists());
        assertTrue(f.getPath().endsWith(path));
    }

    @Test
    public void get_filesInSubfolders() {
        var path = "subfolder/otherfile.txt";
        var f = getTestResource(path);
        assertTrue(f.exists());
        // the "replace" makes this test pass on Windows
        assertTrue(f.getPath().replace(File.separatorChar, '/').endsWith(path));
    }

    @Test
    public void get_filesInSubfoldersWindows() {
        var path = "subfolder\\otherfile.txt";
        var f = getTestResource(path);
        assertTrue(f.exists());
        // the "replace" makes this test pass on Windows
        assertTrue(f.getPath().replace('\\', '/').endsWith(path.replace('\\', '/')));
        var expectedLog = "INFO Do not use backslashes in resource paths (subfolder\\otherfile.txt), use forward slashes instead.";
        assertLogsContain(ResourceUtils.class, expectedLog);
    }

    @Test
    public void get_fileNotFound() {
        var path = "doesNotExist.txt";
        var e = assertThrows(IllegalArgumentException.class, () -> {
            getTestResource(path);
        });
        assertEquals("Resource not found: " + path, e.getMessage());
    }

    @Test
    public void open_relativeFiles() {
        var actual = openAndRead("somefile.txt");
        var expected = CONTENT_SOME;
        assertEquals(expected, actual);
    }

    @Test
    public void open_filesInSubfolders() {
        var actual = openAndRead("subfolder/otherfile.txt");
        var expected = CONTENT_OTHER;
        assertEquals(expected, actual);
    }

    @Test
    public void open_filesInSubfoldersWindows() {
        var actual = openAndRead("subfolder\\otherfile.txt");
        var expected = CONTENT_OTHER;
        assertEquals(expected, actual);
        var expectedLog = "INFO Do not use backslashes in resource paths (subfolder\\otherfile.txt), use forward slashes instead.";
        assertLogsContain(ResourceUtils.class, expectedLog);
    }

    @Test
    public void open_fileNotFound() {
        var path = "doesNotExist.txt";
        var e = assertThrows(IllegalArgumentException.class, () -> {
            ResourceUtils.openResourceAsStream(path);
        });
        assertEquals("Resource not found: " + path, e.getMessage());
    }

    @Test
    public void read_relativeFiles() {
        var actual = ResourceUtils.readResourceToString("somefile.txt", UTF_8);
        var expected = CONTENT_SOME;
        assertEquals(expected, actual);
    }

    @Test
    public void read_filesInSubfolders() {
        var actual = ResourceUtils.readResourceToString("subfolder/otherfile.txt", UTF_8);
        var expected = CONTENT_OTHER;
        assertEquals(expected, actual);
    }

    @Test
    public void read_filesInSubfoldersWindows() {
        var actual = ResourceUtils.readResourceToString("subfolder\\otherfile.txt", UTF_8);
        var expected = CONTENT_OTHER;
        assertEquals(expected, actual);
        var expectedLog = "INFO Do not use backslashes in resource paths (subfolder\\otherfile.txt), use forward slashes instead.";
        assertLogsContain(ResourceUtils.class, expectedLog);
    }

    @Test
    public void read_fileNotFound() {
        var path = "doesNotExist.txt";
        var e = assertThrows(IllegalArgumentException.class, () -> {
            ResourceUtils.readResourceToString(path, UTF_8);
        });
        assertEquals("Resource not found: " + path, e.getMessage());
    }

    private static String openAndRead(String path) {
        try (var is = ResourceUtils.openResourceAsStream(path)) {
            var res = new ByteArrayOutputStream();
            var buffer = new byte[1024];
            int numRead = 0;
            while ((numRead = is.read(buffer)) != -1) {
                res.write(buffer, 0, numRead);
            }
            return res.toString(UTF_8.name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}