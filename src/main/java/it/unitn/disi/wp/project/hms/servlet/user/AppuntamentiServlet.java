/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user;

import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryDayPilotServlet;
import it.unitn.disi.wp.project.hms.listeners.AppuntamentiMedicoListener;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 18.01.2020
 */
@WebServlet(
    name = "appuntamentiServlet", urlPatterns = {"/areaPrivata/appuntamenti.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/restricted/commonPages/appuntamenti/appuntamenti.jsp")}
)
public class AppuntamentiServlet extends FactoryDayPilotServlet {

    static public String getURL() {
        return "areaPrivata/appuntamenti.html";
    }

    @Override
    public void init() throws ServletException {
        super.init();
        listener = new AppuntamentiMedicoListener();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
    }

}
