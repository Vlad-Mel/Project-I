package com.Utilities;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ApplicationProperties {

    static public String getProperty(String property) {
        Properties props = new Properties();
        FileReader reader = null;

            try {
                reader = new FileReader("./src/main/resources/application.properties");
                props.load(reader);
                return props.getProperty(property);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } finally {
                try { reader.close(); } 
                catch (IOException e) { e.printStackTrace();}
            }

        return null;
    }
    
}
