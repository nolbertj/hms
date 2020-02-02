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
import it.unitn.disi.wp.project.hms.persistence.dao.MedicoBaseDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.MedicoBase;
import it.unitn.disi.wp.project.hms.persistence.entities.PazienteForMedic;
import it.unitn.disi.wp.project.hms.services.MedicoBaseService;
import it.unitn.disi.wp.project.hms.servlet.user.*;
import it.unitn.disi.wp.project.hms.servlet.user.medicoBase.ListaPazientiServlet;
import it.unitn.disi.wp.project.hms.servlet.user.medicoBase.PrescriviEsameServlet;
import it.unitn.disi.wp.project.hms.servlet.user.medicoBase.PrescriviRicettaServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Filter for the requests from {@link MedicoBase} (pages and RESTful Web services)
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 08.01.2020
 */
public class MedicoBaseFilter extends GenericUserFilter<MedicoBase> {

    private DAOFactory daoFactory;
    private MedicoBaseDAO medicoBaseDAO;

    public MedicoBaseFilter(){
        super(
            MedicoBaseService.getURL(),
            Arrays.asList(
                DocumentiServlet.getURL(),
                EsamiPrescrivibiliServlet.getURL(),
                ListaFarmaciServlet.getURL(),
                ListaPazientiServlet.getURL(),
                RicettaServlet.getURL(),
                RicevutaServlet.getURL(),
                RefertoServlet.getURL(),
                PrescriviEsameServlet.getURL(),
                PrescriviRicettaServlet.getURL(),
                SchedaPazienteServlet.getURL(),
                AppuntamentiServlet.getURL()
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
            medicoBaseDAO = daoFactory.getDAO(MedicoBaseDAO.class);
        }
        catch (DAOFactoryException ex) {
            LOG(this,ex);
            throw new RuntimeException(new ServletException(ex));
        }
    }

    @Override
    protected void checkPage(ServletRequest req, ServletResponse res, Throwable problem) throws ServletException, IOException {
        boolean pageAllowed = super.checkAllowedPage(req,res,problem);
        if (pageAllowed) {
            Integer idPaziente = PazienteFilter.getPatientIDFromQueryParam(this.request);
            if (idPaziente!=null) {
                try {
                    // =====================================================================================
                    Long totPazienti = medicoBaseDAO.getCountPazienti(user.getId(),"");
                    List<PazienteForMedic> pazienti = medicoBaseDAO.pagePazientiBySearchValue(
                    "",user.getId(),1L,totPazienti,0,"asc"
                    ); // mi prendo tutta la lista dei pazienti del medico di base
                    boolean pazientePermesso = pazienti.stream().anyMatch(p -> p.getId().intValue() == idPaziente.intValue());
                    // e controllo se l'id del paziente nella queryParam appartiene alla lista pazienti del
                    // medico di base loggato.
                    // =====================================================================================
                    if(pazientePermesso == false) {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        LOG(this, LEVEL.WARNING,"page forbidden");
                    }
                } catch (DAOException ex) {
                    LOG(this,ex);
                }
            }
        }
        pageRequested = false; //reset to default value because the service operates in background when the page is already defined
    }

    @Override
    protected void checkService(ServletRequest servletRequest, ServletResponse servletResponse, Throwable problem) throws ServletException, IOException {
        Boolean idExists = super.checkUserIDFromPath(user.getId());
        if (idExists == false) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        serviceRequested = false;
    }

}
