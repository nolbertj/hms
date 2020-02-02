/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.filters.user;

import it.unitn.disi.wp.project.hms.filters.GenericUserFilter;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;
import it.unitn.disi.wp.project.hms.services.PazienteService;
import it.unitn.disi.wp.project.hms.servlet.user.*;
import it.unitn.disi.wp.project.hms.servlet.user.paziente.*;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Filter for the requests from {@link Paziente} (pages and RESTful Web services)
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 04.01.2020
 */
public class PazienteFilter extends GenericUserFilter<Paziente> {

    public PazienteFilter() {
        super(
            PazienteService.getURL(),
            Arrays.asList(
                DocumentiServlet.getURL(),
                EsamiPrescrivibiliServlet.getURL(),
                PagamentiServlet.getURL(),
                RefertoServlet.getURL(),
                RefertiServlet.getURL(),
                RicettaServlet.getURL(),
                RicevutaServlet.getURL(),
                EsamiPrescrittiServlet.getURL(),
                ModificaMedicoServlet.getURL(),
                ModificaProfiloServlet.getURL(),
                RicetteServlet.getURL(),
                AmbulatoriServlet.getURL(),
                CalendarioServlet.getURL(),
                "areaPrivata/prescrizioni/prenotaPrelievo.html"
            )
        );
    }

    @Override
    protected void checkPage(ServletRequest req, ServletResponse res, Throwable problem) throws ServletException, IOException {
        boolean pageAllowed = super.checkAllowedPage(req,res,problem);
        if (pageAllowed) {
            Integer idPaziente = getPatientIDFromQueryParam(this.request);
            if (idPaziente!=null) {
                if(idPaziente.intValue() != user.getId().intValue()) {
                    this.response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    LOG(this, LEVEL.WARNING,"service forbidden");
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

    static public Integer getPatientIDFromQueryParam(HttpServletRequest request) {
        Integer idPaziente = null;
        try {
            String possibleID = request.getParameter("idPaziente");
            if (possibleID!=null && !possibleID.equals(""))
                idPaziente = Integer.parseInt(possibleID);
        } catch(NumberFormatException | NoSuchElementException ex) {
            idPaziente = null;
        }
        return idPaziente;
    }
}
