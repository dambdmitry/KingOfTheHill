package edu.lab.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {


    public static Properties properties = new Properties();

    private static final String applicationPropertiesPath = "application.properties";

    public static String getProperty(String nameProperty){
        if(properties.isEmpty()){
            try(InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(applicationPropertiesPath)){
                properties.load(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return properties.getProperty(nameProperty);
    }

    public static class Context{
        public static final String CONTROLLER_SERVER_HOST = "controller.server.host";
        public static final String CONTROLLER_SERVER_PORT = "controller.server.port";
    }

}
