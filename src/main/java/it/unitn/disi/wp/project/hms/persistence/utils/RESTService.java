/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;


/**
 * Classe astratta contenente gli attributi comuni per le classe che avranno lo scopo di effettuare un servizio REST
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 4.11.2019
 */
public abstract class RESTService {

    @Context
    protected ResourceContext resourceContext;

    @Context
    protected HttpServletRequest request;

    @Context
    protected HttpServletResponse response;

    @Context
    protected ServletContext servletContext;

    protected RESTService(){
        super();
    }

    public abstract void setServletContext(ServletContext servletContext);

}
