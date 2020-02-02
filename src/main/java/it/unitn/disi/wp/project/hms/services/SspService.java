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
import it.unitn.disi.wp.project.hms.persistence.dao.SspDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.*;
import it.unitn.disi.wp.project.hms.persistence.utils.DatatableResponse;
import it.unitn.disi.wp.project.hms.persistence.utils.RESTService;
import it.unitn.disi.wp.project.hms.persistence.utils.Select2Response;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * REST Web Service per {@link Ssp}
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 03.01.2020
 */
@Path("ssp")
public class SspService extends RESTService {

    /** {@link RESTConfig#getURL()}/ssp */
    static public String getURL() {
        return RESTConfig.getURL() + "/ssp";
    }

    private SspDAO sspDAO;
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
                sspDAO = daoFactory.getDAO(SspDAO.class);
                pazienteService = resourceContext.getResource(PazienteService.class);
            } catch (DAOFactoryException ex) {
                throw new RuntimeException(new ServletException(ex));
            }
        }
    }

    @GET
    @Path("{id: [0-9]*}/richiami")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRichiami(@PathParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt = new DatatableResponse(request,response);

            Integer idSSP = Integer.parseInt(id);

            Long conteggioRichiami=sspDAO.getCountRichiami(idSSP, dt.getSearchValue());

            if (dt.getLength() < 0) {
                dt.setLength(conteggioRichiami - dt.getStart());
            }

            List<EsameRichiamo> richiami = sspDAO.pageRichiamiBySearchValue(
                dt.getSearchValue(), idSSP, dt.getStart(), dt.getLength(), dt.getOrderColumn(), dt.getOrderDir()
            );

            dt.setParams(conteggioRichiami,conteggioRichiami,richiami);

            responseBuilder = Response.ok().entity(dt);
        }
        catch (IOException | DAOException ex) {
            LOG(this,ex);
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    static public String searchForSuggestionBoxURL() {
        return getURL() + "/pazienti/";
    }

    @GET
    @Path("pazienti/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForSuggestionBox(@PathParam("term") String term) {
        Response.ResponseBuilder responseBuilder;
        Response responseToCheck = pazienteService.searchForSuggestionBox(term);
        if (responseToCheck.getStatus() == Response.Status.OK.getStatusCode()) {
            responseBuilder = Response.ok().entity(responseToCheck.getEntity());
        } else {
            responseBuilder = Response.status(responseToCheck.getStatus());
        }
        return responseBuilder.build();
    }

    @GET
    @Path("pazienti/{id}/esami/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchEsamePaziente(@PathParam("id") String idPaziente, @PathParam("term") String term) {
        Response.ResponseBuilder responseBuilder;

        if(idPaziente.equalsIgnoreCase("undefined") || idPaziente.isEmpty())
            return Response.noContent().entity(new Select2Response()).build();

        Response responseToCheck = pazienteService.searchErogabileBySSP(term, idPaziente);

        if (responseToCheck.getStatus() == Response.Status.OK.getStatusCode())
        {
            Select2Response select2Response = (Select2Response)responseToCheck.getEntity();

            List<EsamePrescritto> esamiCompleti = (List<EsamePrescritto>)select2Response.getResults();

            List<EsamePrescritto> esamiNonErogatiErogabiliDaSsp = esamiCompleti.stream().filter(
                e-> (e.getDataErogazione() == null)
            ).collect(Collectors.toList());

            responseBuilder = Response.ok().entity(new Select2Response(esamiNonErogatiErogabiliDaSsp));
        }
        else {
            responseBuilder = Response.status(responseToCheck.getStatus());
        }

        return responseBuilder.build();
    }

    /**
     * Ritorna una lista di {@link Ambulatorio} della provincia del {@link Paziente} (in json)
     *
     * @param id {@link Ssp#getId()}
     * @return {@link List<Ambulatorio>}
     */
    @GET
    @Path("{id: [0-9]*}/listaAmbulatori")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getListaAmbulatori(@PathParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt=new DatatableResponse(request, response);

            Long countAmbulatori;
            List<Ambulatorio> ambulatori;

            Integer idSsp = null;
            try {
                idSsp = Integer.parseInt(id);
            } catch (NumberFormatException ex) {
                LOG(this,ex);
                return Response.status(422).build();
            }

            countAmbulatori = sspDAO.getCountAmbulatori(idSsp, dt.getSearchValue());
            if (dt.getLength()<0) {
                dt.setLength(countAmbulatori - dt.getStart());
            }
            ambulatori = sspDAO.pageAmbulatoriBySearchValue(idSsp, dt.getSearchValue(), dt.getStart(), dt.getLength(), dt.getOrderColumn(), dt.getOrderDir());

            dt.setParams(countAmbulatori, countAmbulatori, ambulatori);

            responseBuilder = Response.ok().entity(dt);
        }
        catch (DAOException | IOException ex) {
            LOG(this,"Impossible to access the persistence layer: " + ex.getMessage());
            responseBuilder = Response.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

}