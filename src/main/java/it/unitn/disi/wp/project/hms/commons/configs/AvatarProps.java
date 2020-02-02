/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.configs;

import it.unitn.disi.wp.project.hms.commons.utils.PropertiesLoader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * AvatarConfig bean that read its properties file
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 1.11.2019
 */
public class AvatarProps {

    static private final String ERROR_MSG = "AvatarConfig class must be initialized with init()";

    static private Integer MAX_FILE_SIZE;
    static private String EXTENSION;
    static private Integer RESIZE_SIZE;
    static private List<String> ALLOWED_EXTENSIONS;
    static private Boolean METADATA_REQUESTED;

    private AvatarProps(){
        super();
    }

    static private AvatarProps instance = null;

    static public void init() throws IOException, InstantiationException {
        if(instance == null) {
            Properties props = PropertiesLoader.get("avatar.properties");

            MAX_FILE_SIZE = 1024 * 1024 * Integer.valueOf(props.getProperty("maxFileSize")); // MB to bytes
            EXTENSION = props.getProperty("extension");
            RESIZE_SIZE = Integer.valueOf(props.getProperty("resizeSize")); // KB
            ALLOWED_EXTENSIONS = Arrays.asList(props.getProperty("allowedExtensions").split("/"));
            ALLOWED_EXTENSIONS.forEach(extension-> { if(extension.charAt(0)!='.') extension = "." + extension; });
            METADATA_REQUESTED = Boolean.valueOf(props.getProperty("metadataRequested"));

            instance = new AvatarProps();
        }
        else throw new InstantiationException("AvatarConfig class was already initialized!");
    }

    /**
     * Ritorno una {@code List<String>} delle estensioni permesse
     * @return {@link List} of allowed extensions
     * @throws NullPointerException se la classe {@link AvatarProps} non è stata inizializzata
     */
    static public List<String> getAllowedExtensions() {
        if(instance != null) return ALLOWED_EXTENSIONS;
        else throw new NullPointerException(ERROR_MSG);
    }

    /**
     * @return la dimensione massima che l'avatar dovrà avere (in bytes)
     */
    static public Integer getMaxFileSize() {
        if(instance != null) return MAX_FILE_SIZE;
        else throw new NullPointerException(ERROR_MSG);
    }

    /**
     * Ritorno l'estensione finale che dovrà avere l'avatar.<br>
     * <strong>NOTA: </strong>se non è stata configurata col '.' iniziale, questo verrà posto dal metodo.
     *
     * @return estensione che dovrà avere l'avatar
     * @throws NullPointerException se la classe {@link AvatarProps} non è stata inizializzata
     */
    static public String getExtension() {
        if(instance != null){
            String extension = EXTENSION;
            if(extension.charAt(0) == '.')
                return EXTENSION;
            else {
                extension = '.' + EXTENSION;
                return extension;
            }
        }
        else throw new NullPointerException(ERROR_MSG);
    }

    /**
     * Ritorna la dimensione (<i>in bytes</i>) cui dovrà essere ridimensionato l'avatar.
     *
     * @throws NullPointerException se la classe {@link AvatarProps} non è stata inizializzata
     * @see AvatarProps#init
     */
    static public Integer getResizeSize() {
        if(instance != null) return RESIZE_SIZE;
        else throw new NullPointerException(ERROR_MSG);
    }

    static public boolean metadataRequested() {
        if(instance != null) return (METADATA_REQUESTED==null ? false:METADATA_REQUESTED);
        else throw new NullPointerException(ERROR_MSG);
    }

}
