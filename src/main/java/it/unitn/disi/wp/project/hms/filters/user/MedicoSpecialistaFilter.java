/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.filters.user;

import it.unitn.disi.wp.project.hms.filters.GenericUserFilter;
import it.unitn.disi.wp.project.hms.persistence.entities.MedicoSpecialista;
import it.unitn.disi.wp.project.hms.services.MedicoSpecialistaService;
import it.unitn.disi.wp.project.hms.servlet.user.*;
import it.unitn.disi.wp.project.hms.servlet.user.medicoSpecialista.CercaPazienteServlet;
import it.unitn.disi.wp.project.hms.servlet.user.medicoSpecialista.CompilaRefertoServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Filter for the requests from {@link MedicoSpecialista} (pages and RESTful Web services)
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 13.01.2020
 */
public class MedicoSpecialistaFilter extends GenericUserFilter<MedicoSpecialista> {

    public MedicoSpecialistaFilter() {
        super(
            MedicoSpecialistaService.getURL(),
            Arrays.asList(
                DocumentiServlet.getURL(),
                CercaPazienteServlet.getURL(),
                CompilaRefertoServlet.getURL(),
                RicettaServlet.getURL(),
                RicevutaServlet.getURL(),
                RefertoServlet.getURL(),
                RefertiServlet.getURL(),
                AppuntamentiServlet.getURL(),
                SchedaPazienteServlet.getURL()
            )
        );
    }

    @Override
    protected void checkPage(ServletRequest req, ServletResponse res, Throwable problem) throws ServletException, IOException {
        super.checkAllowedPage(req,res,problem);
        pageRequested = false;
    }

    @Override
    protected void checkService(ServletRequest servletRequest, ServletResponse servletResponse, Throwable problem) throws ServletException, IOException {
        serviceRequested = false;
    }

}
