package com.vvalentim.gui;

import java.io.InputStream;
import java.net.URL;

/**
 * Utility class which manages the access to this project's assets.
 * Helps keeping the assets files structure organized.
 */
public class ResourcesLoader {
    private ResourcesLoader() {}

    public static URL loadURL(String path) {
        return ResourcesLoader.class.getResource(path);
    }

    public static String load(String path) {
        return loadURL(path).toString();
    }

    public static InputStream loadStream(String name) {
        return ResourcesLoader.class.getResourceAsStream(name);
    }
}
