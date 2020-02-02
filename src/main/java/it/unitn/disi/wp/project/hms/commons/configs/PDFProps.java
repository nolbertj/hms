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
 * PDF bean that read its properties file
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 25.11.2019
 */
public class PDFProps {

    static private final String ERROR_MSG = "PDFProperties class must be initialized with init()";

    static private Integer MAX_FILE_SIZE;

    private PDFProps(){
        super();
    }

    static private PDFProps instance = null;

    static public void init() throws IOException, InstantiationException {
        if(instance == null) {
            Properties props = PropertiesLoader.get("pdf.properties");

            MAX_FILE_SIZE = 1024 * 1024 * Integer.valueOf(props.getProperty("maxFileSize")); // MB to bytes

            instance = new PDFProps();
        }
        else throw new InstantiationException("PDFProps class was already initialized!");
    }

    /**
     * @return la dimensione massima che l'avatar dovrà avere (in bytes)
     */
    static public Integer getMaxFileSize() {
        if(instance != null) return MAX_FILE_SIZE;
        else throw new NullPointerException(ERROR_MSG);
    }

}
