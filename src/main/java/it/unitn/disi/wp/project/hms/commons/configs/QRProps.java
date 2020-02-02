/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.configs;

import it.unitn.disi.wp.project.hms.commons.utils.PropertiesLoader;

import java.io.IOException;
import java.util.Properties;

/**
 * QR class that reads its properties file
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 31.12.2019
 */
public class QRProps {

    static private final String ERROR_MSG = "QRProps class must be initialized with init()";

    static private String EXTENSION;

    static private QRProps instance = null;

    static public void init() throws IOException, InstantiationException {
        if(instance == null) {
            Properties props = PropertiesLoader.get("qr.properties");

            EXTENSION = props.getProperty("extension");

            instance = new QRProps();
        }
        else throw new InstantiationException("QRProps class was already initialized!");
    }

    static public String getExtension() {
        if(instance != null){
            String extension = EXTENSION;
            if(extension.contains(".") || extension.charAt(0) == '.')
                extension = extension.substring(1);
            return extension;
        }
        else throw new NullPointerException(ERROR_MSG);
    }

}
