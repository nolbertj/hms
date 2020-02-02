/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user;

import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet che gestisce la response della dashboard
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 26.10.2019
 */
@WebServlet(
    name = "dashboardServlet", urlPatterns = {"/areaPrivata/dashboard.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/dashboard/dashboard.jsp")}
)
public class DashboardServlet extends FactoryServlet {

    static public String getURL() {
        return "areaPrivata/dashboard.html"; //porre l'URL definito in urlPatterns escluso lo "/" iniziale
    }

}