/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.services;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.configs.RESTConfig;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.MedicoBaseDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.MedicoBase;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;
import it.unitn.disi.wp.project.hms.persistence.entities.PazienteForMedic;
import it.unitn.disi.wp.project.hms.persistence.utils.DatatableResponse;
import it.unitn.disi.wp.project.hms.persistence.utils.RESTService;
import it.unitn.disi.wp.project.hms.persistence.utils.Select2Response;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * REST Web Service per {@link MedicoBase}
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 03.01.2020
 */
@Path("medicoBase")
public class MedicoBaseService extends RESTService {

    /** {@link RESTConfig#getURL()}/medicoBase */
    static public String getURL() {
        return RESTConfig.getURL() + "/medicoBase";
    }

    private MedicoBaseDAO medicoBaseDAO;
    private PazienteService pazienteService;

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
                pazienteService = resourceContext.getResource(PazienteService.class);
                medicoBaseDAO = daoFactory.getDAO(MedicoBaseDAO.class);
            } catch (DAOFactoryException ex) {
                throw new RuntimeException(new ServletException(ex));
            }
        }
    }

    @GET
    @Path("{id: [0-9]*}/pazienti")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPazienti(@PathParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt = new DatatableResponse(request,response);

            Integer idMedico = Integer.parseInt(id);

            Long conteggioPazienti = medicoBaseDAO.getCountPazienti(idMedico, dt.getSearchValue());

            if (dt.getLength() < 0) {
                dt.setLength(conteggioPazienti - dt.getStart());
            }
            List<PazienteForMedic> pazienti = medicoBaseDAO.pagePazientiBySearchValue(
                dt.getSearchValue(), idMedico, dt.getStart(), dt.getLength(), dt.getOrderColumn(), dt.getOrderDir()
            );

            dt.setParams(conteggioPazienti,conteggioPazienti,pazienti);

            responseBuilder = Response.ok().entity(dt);
        }
        catch (IOException | DAOException ex) {
            LOG(this,ex);
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    @GET
    @Path("{id: [0-9]*}/pazienti/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForSuggestionBox(@PathParam("id") String id, @PathParam("term") String term) {
        Response.ResponseBuilder responseBuilder;

        if(Utils.isNullOrEmpty(term) || term.equalsIgnoreCase("undefined") || term.length() <1)
            return Response.noContent().entity(new Select2Response()).build();

        try {
            Integer idMedico = Integer.parseInt(id);
            List<Paziente> pazienti = medicoBaseDAO.searchPazientiAttuali(idMedico,term);

            responseBuilder = Response.ok().entity(new Select2Response(pazienti));
        }
        catch (NumberFormatException | DAOException ex) {
            LOG(this,ex);
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    @GET
    @Path("{id: [0-9]*}/pazienti/visite")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVisitePaziente(@QueryParam("idPaziente") String idPaziente) {
        Response.ResponseBuilder responseBuilder;
        Response responseToCheck = pazienteService.getVisite(idPaziente);
        if(responseToCheck.getStatus() == Response.Status.OK.getStatusCode()) {
            responseBuilder = Response.ok().entity(responseToCheck.getEntity());
        } else {
            responseBuilder = Response.status(responseToCheck.getStatus());
        }
        return responseBuilder.build();
    }


}