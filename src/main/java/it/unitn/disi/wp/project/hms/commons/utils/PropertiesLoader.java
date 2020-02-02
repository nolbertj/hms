/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */

package it.unitn.disi.wp.project.hms.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe che ritorna un oggetto di tipo {@link Properties} caricato da un file
 * <i>.properties</i> salvato all'interno del folder <i>src/main/resources</i><br><hr>
 * HOW TO USE<br>Dichiarare un oggetto <strong><i>Properties x</i></strong> e richiamare questa classe tramite il metodo
 * <strong><i>get()</i></strong><br>
 * Esempio:<br>
 * {@code Properties props = PropertiesLoader.get("nomeFile.properties");}
 * Dopodichè sarà sufficiente richiedere un parametro del file <i>nomeFile.properties</i> nel seguente modo:<br>
 * {@code String param = props.getProperty("nomeParametro");}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 08.10.2019
 */
public class PropertiesLoader {

    private PropertiesLoader(){
        super();
    }

    /**
     * Ritorna un oggetto di tipo {@link Properties} localizzato nel folder <i>src/main/resources/properties</i>
     * @param fileName nome del file con estensione <i>.properties</i>(esempio: {@code database.properties})
     * @return oggetto di tipo {@link Properties}
     */
    static public Properties get(String fileName) throws IOException {

        Properties properties;

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream input = classloader.getResourceAsStream("/properties/" + fileName); //by default getResourceAsStream get files inside /resources folder

            properties = new Properties();
            properties.load(input);

        } catch (IOException ex) {
            throw new IOException("Can't find " + fileName + " file!");
        }

        return properties;
    }
    
}
