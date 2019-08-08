package com.statsperform.sdm.getdressed;

import java.io.InputStream;
import java.util.Properties;

public class Config {
	private static final String PROP_FILE_NAME = "config.properties";
	// WARNING this is here to fail fast on load if config file can not be found.
	private static final Properties CONFIG = new Config().getProperties();

	public static Properties get() {
		return CONFIG;
	}

	private Config() {
	}

	private Properties getProperties() {
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new RuntimeException("Property file '" + PROP_FILE_NAME + "' not found!");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (Exception e) {
					/* ignore */}
			}
		}
		return properties;
	}
}
