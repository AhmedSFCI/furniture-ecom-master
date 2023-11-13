/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.furniture.ecom._config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author HP
 */
public class PropertiesFileConfig {

    InputStream inputStream;

    public Properties getPropValues(String fileName) throws IOException {
        Properties properties = new Properties();
        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + fileName + "' not found in the classpath");
            }
        } catch (Exception e) {
        } finally {
            inputStream.close();
        }
        return properties;
    }
}
