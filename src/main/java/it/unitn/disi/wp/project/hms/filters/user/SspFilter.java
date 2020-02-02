/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.filters.user;

import it.unitn.disi.wp.project.hms.filters.GenericUserFilter;
import it.unitn.disi.wp.project.hms.persistence.entities.Ssp;
import it.unitn.disi.wp.project.hms.services.SspService;
import it.unitn.disi.wp.project.hms.servlet.user.AmbulatoriServlet;
import it.unitn.disi.wp.project.hms.servlet.user.EsamiPrescrivibiliServlet;
import it.unitn.disi.wp.project.hms.servlet.user.ListaFarmaciServlet;
import it.unitn.disi.wp.project.hms.servlet.user.ssp.CompilaRefertoServlet;
import it.unitn.disi.wp.project.hms.servlet.user.ssp.GeneraReportServlet;
import it.unitn.disi.wp.project.hms.servlet.user.ssp.ListaRichiamiServlet;
import it.unitn.disi.wp.project.hms.servlet.user.ssp.PrescriviRichiamoServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Filter for the requests from {@link Ssp} (pages and RESTful Web services)
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 17.01.2020
 */
public class SspFilter extends GenericUserFilter<Ssp> {

    public SspFilter() {
        super(
            SspService.getURL(),
            Arrays.asList(
                GeneraReportServlet.getURL(),
                CompilaRefertoServlet.getURL(),
                EsamiPrescrivibiliServlet.getURL(),
                ListaFarmaciServlet.getURL(),
                ListaRichiamiServlet.getURL(),
                AmbulatoriServlet.getURL(),
                PrescriviRichiamoServlet.getURL()
            )
        );
    }

    @Override
    protected void checkPage(ServletRequest servletRequest, ServletResponse servletResponse, Throwable problem) throws ServletException, IOException {
        super.checkAllowedPage(servletRequest,servletResponse,problem);
        pageRequested = false; // re- init param after page was checked
    }

    @Override
    protected void checkService(ServletRequest servletRequest, ServletResponse servletResponse, Throwable problem) throws ServletException, IOException {
        serviceRequested = false; // re-init param because service works in background when the filter was already initialized for the rendered page
    }

}
