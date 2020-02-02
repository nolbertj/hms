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
import it.unitn.disi.wp.project.hms.persistence.dao.OpenDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.*;
import it.unitn.disi.wp.project.hms.persistence.utils.DatatableResponse;
import it.unitn.disi.wp.project.hms.persistence.utils.PerGenere;
import it.unitn.disi.wp.project.hms.persistence.utils.RESTService;
import it.unitn.disi.wp.project.hms.persistence.utils.Select2Response;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;
import static it.unitn.disi.wp.project.hms.persistence.utils.PerGenere.PER_GENERE;

/**
 * REST Web Service per servizi di pubblica utilità
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 4.11.2019
 */
@Path("open")
public class OpenService extends RESTService {

    static public String getURL() {
        return RESTConfig.getURL() + "/open";
    }

    private OpenDAO openDAO;

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
                openDAO = daoFactory.getDAO(OpenDAO.class);
            } catch (DAOFactoryException ex) {
                throw new RuntimeException(new ServletException(ex));
            }
        }
    }

    /**
     * @return {@link RESTConfig#getURL()}/open/esamiPrescrivibili
     */
    static public String getEsamiPrescrivibiliURL(){
        return getURL() + "/esamiPrescrivibili";
    }

    @GET
    @Path("esamiPrescrivibili")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEsamiPrescrivibili() {
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt = new DatatableResponse(request,response);

            Long conteggioEsami = null;
            List<Esame> esami = null;

            Paziente paziente = null;
            MedicoBase medicoBase = null;
            Ssp ssp=null;

            if (request.getSession().getAttribute(Attr.USER) instanceof Paziente){
                paziente = (Paziente) request.getSession().getAttribute(Attr.USER);
                conteggioEsami = openDAO.getCountEsamiPrescrivibili(PerGenere.getByCharacter(paziente.getSesso()), dt.getSearchValue());
                if (dt.getLength() < 0) {
                    dt.setLength(conteggioEsami - dt.getStart());
                }
                esami = openDAO.pageEsamiPrescrivibiliBySearchValue(
                    dt.getSearchValue(), PerGenere.getByCharacter(paziente.getSesso()),
                    dt.getStart(), dt.getLength(), dt.getOrderColumn(), dt.getOrderDir()
                );
            }
            else if (request.getSession().getAttribute(Attr.USER) instanceof MedicoBase){
                medicoBase = (MedicoBase) request.getSession().getAttribute(Attr.USER);
                conteggioEsami = openDAO.getCountEsamiPrescrivibili(PER_GENERE.ALL, dt.getSearchValue());
                if (dt.getLength() < 0) {
                    dt.setLength(conteggioEsami - dt.getStart());
                }
                esami = openDAO.pageEsamiPrescrivibiliBySearchValue(
                    dt.getSearchValue(), PER_GENERE.ALL, dt.getStart(),
                    dt.getLength(), dt.getOrderColumn(), dt.getOrderDir()
                );
            }
            else if (request.getSession().getAttribute(Attr.USER) instanceof Ssp){
                ssp = (Ssp) request.getSession().getAttribute(Attr.USER);
                conteggioEsami = openDAO.getCountEsamiPrescrivibili(PER_GENERE.ALL, dt.getSearchValue());
                if (dt.getLength() < 0) {
                    dt.setLength(conteggioEsami - dt.getStart());
                }
                esami = openDAO.pageEsamiPrescrivibiliBySearchValue(
                        dt.getSearchValue(), PER_GENERE.ALL, dt.getStart(),
                        dt.getLength(), dt.getOrderColumn(), dt.getOrderDir()
                );
            }
            else {
                return Response.status(Response.Status.NOT_IMPLEMENTED).build();
            }

            if(paziente != null || medicoBase != null || ssp!=null) {
                dt.setParams(conteggioEsami,conteggioEsami,esami);

                responseBuilder = Response.ok().entity(dt);
            } else {
                responseBuilder = Response.status(Response.Status.NOT_IMPLEMENTED);
            }
        } catch (DAOException | IOException ex) {
            LOG(this,"Impossible to access the persistence layer: " + ex.getMessage());
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    @GET
    @Path("esamiPrescrivibili/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchEsame(@PathParam("term") String term) {
        Response.ResponseBuilder responseBuilder;
        if (Utils.isNullOrEmpty(term) || term.equalsIgnoreCase("undefined" ) || term.length() < 1)
            return Response.noContent().entity(new Select2Response()).build();
        try {
            List<Esame> esami = openDAO.searchEsame(term);
            if(esami==null)
                responseBuilder = Response.noContent().entity(new Select2Response());
            else
                responseBuilder = Response.ok().entity(new Select2Response(esami));
        } catch (DAOException ex) {
            LOG(this,ex);
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    /**
     * @return {@link RESTConfig#getURL()}/open/farmaci
     */
    static public String getFarmaciURL(){
        return getURL() + "/farmaci";
    }

    @GET
    @Path("farmaci")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFarmaci(){
        Response.ResponseBuilder responseBuilder;
        try {
            DatatableResponse dt = new DatatableResponse(request,response);

            Long counter = openDAO.getCountFarmaci(dt.getSearchValue());
            if (dt.getLength() < 0) {
                dt.setLength(counter - dt.getStart());
            }

            List<Farmaco> farmaci = openDAO.pageFarmaciBySearchValue(
                dt.getSearchValue(),dt.getStart(),dt.getLength(), dt.getOrderColumn(),dt.getOrderDir()
            );

            dt.setParams(counter,counter,farmaci);
            responseBuilder = Response.ok().entity(dt);
        }
        catch(IOException | DAOException ex) {
            LOG(this,ex);
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

    @GET
    @Path("farmaci/{term}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchFarmaco(@PathParam("term") String term) {
        Response.ResponseBuilder responseBuilder;
        if (Utils.isNullOrEmpty(term) || term.equalsIgnoreCase("undefined" ) || term.length() < 1)
            return Response.noContent().entity(new Select2Response()).build();
        try {
            List<Farmaco> farmaci = openDAO.searchFarmaco(term);
            if(farmaci==null)
                responseBuilder = Response.noContent().entity(new Select2Response());
            else
                responseBuilder = Response.ok().entity(new Select2Response(farmaci));
        } catch (DAOException ex) {
            LOG(this,ex);
            responseBuilder = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return responseBuilder.build();
    }

}
