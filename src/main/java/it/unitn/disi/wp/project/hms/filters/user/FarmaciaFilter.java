/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.filters.user;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.filters.GenericUserFilter;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Farmacia;
import it.unitn.disi.wp.project.hms.persistence.entities.Ricetta;
import it.unitn.disi.wp.project.hms.services.FarmaciaService;
import it.unitn.disi.wp.project.hms.servlet.user.RicettaServlet;
import it.unitn.disi.wp.project.hms.servlet.user.RicevutaServlet;
import it.unitn.disi.wp.project.hms.servlet.user.farmacia.ErogaRicettaServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Filter for the requests from {@link Farmacia} (pages and RESTful Web services)
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 09.01.2020
 */
public class FarmaciaFilter extends GenericUserFilter<Farmacia> {

    private DAOFactory daoFactory;
    private PazienteDAO pazienteDAO;

    public FarmaciaFilter(){
        super(
            FarmaciaService.getURL(),
            Arrays.asList(
                ErogaRicettaServlet.getURL(),
                RicettaServlet.getURL(),
                RicevutaServlet.getURL(),
                "areaPrivata/impostazioni.html"
            )
        );
    }

   @Override
   protected void doBeforeProcessing(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
       super.doBeforeProcessing(servletRequest,servletResponse);
       try {
           daoFactory = (DAOFactory)request.getServletContext().getAttribute(Attr.DAO_FACTORY);
           if (daoFactory == null) {
               LOG(this,LEVEL.ERROR, "Impossible to get dao factory for storage system");
               throw new RuntimeException(new ServletException("Impossible to get dao factory for storage system"));
           }
           pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
       }
       catch (DAOFactoryException ex) {
           LOG(this,ex);
           throw new RuntimeException(new ServletException(ex));
       }
   }

   @Override
    protected void checkPage(ServletRequest servletRequest, ServletResponse servletResponse, Throwable problem) throws ServletException, IOException {
        boolean pageAllowed = super.checkAllowedPage(request,response,problem);
        if (pageAllowed) {
            //checkRicetta(); // la farmacia può vedere una ricetta anche se già erogata ????
        }
        pageRequested = false;
    }

    /**
     * Verifica se la ricetta che viene richiesta dalla farmacia è stata erogata o meno.
     * Se lo è, non può essere visualizzata. La farmacia inoltre non potrà sapre se tale ricetta esiste,
     * pertanto se la ricetta è già stata erogata, risponderemo con {@link javax.servlet.http.HttpServletResponse#SC_FORBIDDEN}
     */
    private void checkRicetta() throws IOException {
        try {
            if (requestedPAGE.contains(RicettaServlet.getURL())) {
                Integer idPaziente = PazienteFilter.getPatientIDFromQueryParam(request);
                Integer idRicetta = Integer.parseInt(request.getParameter("id"));

                if(idPaziente!=null && idRicetta!= null) {
                    Ricetta ricetta = pazienteDAO.getRicettaFarmaceutica(idRicetta,idPaziente);
                    if (ricetta!=null) {
                        if (ricetta.getDataErogazione()!=null) {
                            response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        } else {
                            LOG(this,"ricetta già erogata. La farmacia non puo visualizzarla!");
                        }
                    }
                }
            }
        } catch (DAOException | NumberFormatException ex) {
            LOG(this,ex);
        }
    }

    @Override
    protected void checkService(ServletRequest servletRequest, ServletResponse servletResponse, Throwable problem) throws ServletException, IOException {
        serviceRequested = false; // if a service is requested and user it's already in the allowed page, this attribute must be resetted
    }

}
