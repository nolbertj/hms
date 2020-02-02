/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.services;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.configs.RESTConfig;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.persistence.dao.FarmaciaDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Farmacia;
import it.unitn.disi.wp.project.hms.persistence.utils.RESTService;
import it.unitn.disi.wp.project.hms.persistence.utils.Select2Response;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service per {@link Farmacia}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 03.01.2020
 */
@Path("farmacia")
public class FarmaciaService extends RESTService {

    /** areaPrivata/services/farmacia */
    static public String getURL() {
        return RESTConfig.getURL() + "/farmacia";
    }

    static public final int MIN_LENGHT_FOR_SUGGESTION_BOX = 2;

    private PazienteService pazienteService;
    private FarmaciaDAO farmaciaDAO;

    @Override
    @Context
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
        if (servletContext != null) {
            DAOFactory daoFactory = (DAOFactory) this.servletContext.getAttribute(Attr.DAO_FACTORY);
            if (daoFactory == null) {
                throw new RuntimeException(new ServletException("Impossible to get dao factory for storage system"));
            }
            try {
                farmaciaDAO = daoFactory.getDAO(FarmaciaDAO.class);
                pazienteService = resourceContext.getResource(PazienteService.class);
            } catch (DAOFactoryException ex) {
                throw new RuntimeException(new ServletException(ex));
            }
        }
    }

    static public String searchForSuggestionBoxURL() {
        return getURL() + "/pazienti/";
    }

    @GET
    @Path("pazienti/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForSuggestionBox(@PathParam("term") String term) {
        Response.ResponseBuilder responseBuilder;
        /**
         * In questo modo evito che all farmacia venga ritornata la lista di TUTTI i pazienti
         * Essa potrà ottenere una lista di pazienti dopo aver digitato almeno {@link MIN_LENGHT_FOR_SUGGESTION_BOX} caratteri
         */
        if(term.length() < MIN_LENGHT_FOR_SUGGESTION_BOX) {
            responseBuilder = Response.ok().entity(new Select2Response());
        } else {
            Response responseToCheck = pazienteService.searchForSuggestionBox(term);
            if (responseToCheck.getStatus() == Response.Status.OK.getStatusCode()) {
                responseBuilder = Response.ok().entity(responseToCheck.getEntity());
            } else {
                responseBuilder = Response.status(responseToCheck.getStatus());
            }
        }
        return responseBuilder.build();
    }

    static public String getRicettePazienteURL() {
        return getURL() + "/pazienti/ricette?idPaziente=";
    }

    @GET
    @Path("pazienti/ricette")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRicette(@QueryParam("idPaziente") String idPaziente) {
        Response.ResponseBuilder responseBuilder;

        Response responseToCheck = pazienteService.getRicette(idPaziente,"false");
        if (responseToCheck.getStatus() == Response.Status.OK.getStatusCode()) {
            responseBuilder = Response.ok().entity(responseToCheck.getEntity());
        } else {
            responseBuilder = Response.status(responseToCheck.getStatus());
        }

        return responseBuilder.build();
    }

}
