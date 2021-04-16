package com.adjudication.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFile {

	private Properties properties;
	
	public PropertyFile(String propertyFilePath) {
		validateFileExists(propertyFilePath);
		loadPropertyContent(propertyFilePath);
	}

	private void validateFileExists(String propertyFilePath) {
		File propertyFile = new File(propertyFilePath);
		if (!propertyFile.exists())
			throw new RuntimeException("property file does not exist");
	}

	private void loadPropertyContent(String propertyFilePath) {

		properties = new Properties();
		File file = new File(propertyFilePath);
		InputStream input;

		try {
			input = new FileInputStream(file);
			properties.load(input);
			return;
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		throw new RuntimeException("could not load the property file content");
	}

	public String getProperty(String name) {
		try {
			return properties.getProperty(name);
		} catch (Exception exception) {
			throw new RuntimeException("cannot find the " + name);
		}
	}

}
