/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.configs;

import com.alibaba.fastjson.support.jaxrs.FastJsonProvider;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import org.glassfish.jersey.server.wadl.internal.WadlResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Configurator for JAX-RX that provides RESTful WebServices for "areaPrivata" services in webApp
 *
 * <strong>NOTA: </strong>viene inizializzata in automatico al deploy.
 * @see <a href="https://books.google.it/books?id=VY92BgAAQBAJ">Java EE 7 Development</a>[p. 305]
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 30.10.2019
 */
@ApplicationPath("areaPrivata/services")
public class RESTConfig extends Application {

    /** areaPrivata/services */
    static public String getURL() {
        return "areaPrivata/services";
    }

    private final String ROOT = "it.unitn.disi.wp.project.hms.";
    private final String SERVICE_PKG = ROOT + "services";

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        //=========== RISORSE OBBLIGATORIE =======================
        resources.add(FastJsonProvider.class);
        resources.add(WadlResource.class);
        //========================================================
        //resources.add(PazienteService.class);
        //Piuttosto che aggiungere a manina ciascuna classe implementante servizi REST,
        //carico in automatico tutte le classi poste in nel package "services"
        try {
            resources.addAll(Utils.getClasses(SERVICE_PKG));
        }
        catch (ClassNotFoundException | IOException ex) {
            LOG(this, ex);
            throw new RuntimeException("Fail to load the RESTful resources");
        }
        LOG(this,"loaded " + (resources.size()-2)  + " RESTful Web Services for WebApp");
        return resources;
    }

}