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
import it.unitn.disi.wp.project.hms.persistence.dao.MedicoSpecialistaDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.EsamePrescritto;
import it.unitn.disi.wp.project.hms.persistence.entities.MedicoSpecialista;
import it.unitn.disi.wp.project.hms.persistence.entities.Referto;
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
import java.util.stream.Collectors;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * REST Web Service per servizi del medico specialista
 *
 * @author Nolbert Juarez &lt;nolbert dot juarez at studenti dot unitn dot it&gt;
 * @since 05.01.2020
 */
@Path("medicoSpecialista")
public class MedicoSpecialistaService extends RESTService {

    /** areaPrivata/services/medicoSpecialista */
    static public String getURL() {
        return RESTConfig.getURL() + "/medicoSpecialista";
    }

    private PazienteService pazienteService;
    private MedicoSpecialistaDAO medicoSpecialistaDAO;

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
                medicoSpecialistaDAO = daoFactory.getDAO(MedicoSpecialistaDAO.class);
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
        Response responseToCheck = pazienteService.searchForSuggestionBox(term);
        if (responseToCheck.getStatus() == Response.Status.OK.getStatusCode()) {
            responseBuilder = Response.ok().entity(responseToCheck.getEntity());
        } else {
            responseBuilder = Response.status(responseToCheck.getStatus());
        }
        return responseBuilder.build();
    }

    static public String getPazienteURL() {
        return getURL() + "/pazienti?id=";
    }

    @GET
    @Path("pazienti")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaziente(@QueryParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        Response responseToCheck = pazienteService.get(id);
        if(responseToCheck.getStatus() == Response.Status.OK.getStatusCode()) {
            responseBuilder = Response.ok().entity(responseToCheck.getEntity());
        } else {
            responseBuilder = Response.status(responseToCheck.getStatus());
        }
        return responseBuilder.build();
    }

    @GET
    @Path("pazienti/{id}/avatar")
    @Produces({ "image/png", "image/jpg", "image/jpeg" })
    public Response getAvatarPaziente(@PathParam("id") String id) {
        Response.ResponseBuilder responseBuilder;
        Response responseToCheck = pazienteService.getAvatar(id);
        if(responseToCheck.getStatus() == Response.Status.OK.getStatusCode()) {
            responseBuilder = Response.ok().entity(responseToCheck.getEntity());
        } else {
            responseBuilder = Response.status(responseToCheck.getStatus());
        }
        return responseBuilder.build();
    }

    static public String getEsamiPazienteURL() {
        return getURL() + "/pazienti/esami?idPaziente=";
    }

    @GET
    @Path("pazienti/esami")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEsamiPaziente(@QueryParam("idPaziente") String idPaziente) {
        Response.ResponseBuilder responseBuilder;

        Response responseToCheck = pazienteService.getEsamiPrescritti(idPaziente);
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

        Response responseToCheck = pazienteService.searchEsame(term,idPaziente);
        if (responseToCheck.getStatus() == Response.Status.OK.getStatusCode())
        {
            Select2Response select2Response = (Select2Response)responseToCheck.getEntity();

            MedicoSpecialista ms = (MedicoSpecialista)request.getSession().getAttribute(Attr.USER);

            List<EsamePrescritto> esamiCompleti = (List<EsamePrescritto>)select2Response.getResults(),
                esamiNonErogati = esamiCompleti.stream().filter(e->
                    (e.getDataErogazione() == null)
                        && e.getArea().equals(ms.getSpecialita()))
                    .collect(Collectors.toList());

            responseBuilder = Response.ok().entity(new Select2Response(esamiNonErogati));
        }
        else {
            responseBuilder = Response.status(responseToCheck.getStatus());
        }
        return responseBuilder.build();
    }

    static public String getVisitePazienteURL() {
        return getURL() + "/pazienti/visite?idPaziente=";
    }

    @GET
    @Path("pazienti/visite")
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


    static public String getRefertiURL() {
        return getURL() + "/pazienti/referti?idPaziente=";
    }

    @GET
    @Path("referti")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReferti() {
        Response.ResponseBuilder responseBuilder;
        try {
            MedicoSpecialista medico = (MedicoSpecialista) request.getSession().getAttribute(Attr.USER);
            DatatableResponse dt = new DatatableResponse(request, response);

            Long countReferti = medicoSpecialistaDAO.getCountReferti(medico.getId(), dt.getSearchValue());
            if (dt.getLength()<0) {
                dt.setLength(countReferti - dt.getStart());
            }

            List<Referto> referti = medicoSpecialistaDAO.listaReferti(dt.getSearchValue(), medico.getId(), dt.getStart(), dt.getLength(), dt.getOrderColumn(), dt.getOrderDir());
            dt.setParams(countReferti, countReferti, referti);
            responseBuilder = Response.ok().entity(dt);
        }
        catch(IOException | DAOException ex) {
            LOG(this,ex);
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }
}