package com.fjsmu.app;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class ConfigUtils {

    private static Logger logger = Logger.getLogger(ConfigUtils.class);
    private static ResourceLoader resourceLoader = new DefaultResourceLoader();
    private static java.util.Properties props = null;

    static {
        try {
            reload();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        }
    }

    private ConfigUtils() {
    }

    public static void reload() throws Exception {
        if (props == null) {
            props = new java.util.Properties();
        }
        String location = "/system.properties";
        InputStream is = null;
        try {
            Resource resource = resourceLoader.getResource(location);
            is = resource.getInputStream();
            props.load(is);
        } catch (Exception ex) {
            logger.error("Could not load properties from path:{" + location + "}, {" + ex.getMessage() + "} ");
        } finally {
            IOUtils.closeQuietly(is);
        }

    }

    public static String getString(String name) {
        if (props == null) {
            return null;
        }
        try {
            if (props.getProperty(name) != null) {
                return props.getProperty(name).trim();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    public static Integer getInteger(String name) {
        String value = getString(name);
        if (value != null && !"".equals(value)) {
            return Integer.parseInt(value);
        }
        return -1;
    }

    public static boolean getBoolean(String name) {
        String value = getString(name);
        if (value != null && !"".equals(value)) {
            return Boolean.parseBoolean(value);
        }
        return false;
    }

}
