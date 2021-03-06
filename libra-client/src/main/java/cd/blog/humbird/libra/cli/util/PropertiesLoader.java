package cd.blog.humbird.libra.cli.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * @author david
 * @since created by on 18/7/5 01:50
 */
public class PropertiesLoader {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

    private static final String SCHEMA_CLASSPATH = "classpath:";

    public static Properties load(String fs) {
        if (fs == null) {
            return null;
        }
        try {
            if (StringUtils.startsWith(fs, SCHEMA_CLASSPATH)) {
                return loadFromClassPath(StringUtils.substring(fs, SCHEMA_CLASSPATH.length()));
            } else {
                return loadFromFileSystem(fs);
            }
        } catch (Exception e) {
            logger.warn("exception:", e);
        }
        return null;
    }

    public static Properties loadFromFileSystem(String fs) throws IOException {
        Resource resource = new FileSystemResource(fs);
        if (!resource.exists()) {
            logger.debug("file {} doesn't exist in filesystem", fs);
            return null;
        }
        return load(resource);
    }

    public static Properties loadFromClassPath(String fs) throws IOException {
        Resource resource = new ClassPathResource(fs);
        if (!resource.exists()) {
            logger.debug("file {} doesn't exist in classpath", fs);
            return null;
        }
        return load(resource);
    }

    public static Properties load(Resource resource) throws IOException {
        try (InputStream in = resource.getInputStream()) {
            Properties properties = new Properties();
            properties.load(in);
            return properties;
        }
    }
}
