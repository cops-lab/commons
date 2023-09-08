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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceUtils.class);

    private static ClassLoader cl() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static File getTestResource(String path) {
        path = fixWindowsPaths(path);
        var r = cl().getResource(path);
        if (r == null) {
            throw new IllegalArgumentException("Resource not found: " + path);
        }
        return new File(r.getPath());
    }

    public static InputStream openResourceAsStream(String path) {
        path = fixWindowsPaths(path);
        var is = cl().getResourceAsStream(path);
        if (is == null) {
            throw new IllegalArgumentException("Resource not found: " + path);
        }
        return is;
    }

    public static String readResourceToString(String path, Charset charset) {
        path = fixWindowsPaths(path);
        try (var is = openResourceAsStream(path)) {
            var bufferSize = 1024;
            var buffer = new char[bufferSize];
            var out = new StringBuilder();
            var in = new InputStreamReader(is, charset);
            var numRead = 0;
            while ((numRead = in.read(buffer, 0, buffer.length)) > 0) {
                out.append(buffer, 0, numRead);
            }
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String fixWindowsPaths(String path) {
        if (!path.contains("\\")) {
            return path;
        }
        LOG.info("Do not use backslashes in resource paths ({}), use forward slashes instead.", path);
        return path.replaceAll("\\\\", "/");
    }
}