/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logger personalizzato in base a quanto specificato nel file <a href="">log4j2.properties</a>
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 3.11.2019
 */
public final class CustomLogger {

    /**Livello da assegnare al log*/
    public enum LEVEL {INFO, WARNING, ERROR, TRACE}

    private CustomLogger() {
        super();
    }

    /**
     * Genera un log col messaggio specificato nel log assieme alla classe e da cui viene richiamato il log e il livello assegnato
     *
     * @param clazz classe da cui viene richiamato il metodo
     * @param level {@link LEVEL} da asseggnare al log
     * @param message messaggio da mostrare nel log
     * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
     * @since 3.11.2019
     */
    static public void LOG(Object clazz, LEVEL level, String message)  {
        if(clazz==null)
            throw new NullPointerException("Class is required!");

        Logger logger = LogManager.getLogger(clazz.getClass());

        switch (level) {
            case INFO:
                logger.info(message);
                break;
            case WARNING:
                logger.warn(message);
                break;
            case ERROR:
                logger.error(message);
                break;
            case TRACE:
                logger.trace(message);
                break;
            default:
                LOG(clazz,message);
                break;
        }
    }

    /**
     * Genera un log di default [{@link LEVEL#INFO}] con il messaggio specificato.
     * @param clazz classe da cui viene richiamato il metodo
     * @param message messaggio da mostrare nel log
     * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
     * @since 3.11.2019
     */
    static public void LOG(Object clazz, String message) {
        LOG(clazz, LEVEL.INFO, message);
    }

    /**
     * Genera un log di {@link LEVEL#ERROR} con il messaggio dell'eccezzione generata.
     * @param clazz classe da cui viene richiamato il metodo
     * @param exception eccezzione
     * @see Exception#getMessage()
     * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
     * @since 3.11.2019
     */
    static public void LOG(Object clazz, Exception exception) {
        LOG(clazz, LEVEL.ERROR, exception.getMessage());
    }

}
