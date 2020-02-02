/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.paziente;

import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryDayPilotServlet;
import it.unitn.disi.wp.project.hms.listeners.CalendarioPazienteListener;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet that handles the {@link CalendarioPazienteListener}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 17.01.2020
 */
@WebServlet(
    name = "calendarioServlet", urlPatterns = {"/areaPrivata/calendario.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/calendario/calendario.jsp")}
)
public class CalendarioServlet extends FactoryDayPilotServlet {

    static public String getURL() {
        return "areaPrivata/calendario.html";
    }

    @Override
    public void init() throws ServletException {
        super.init();
        listener = new CalendarioPazienteListener();
    }

}
