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
 * Servlet che gestisce la response della pagina jsp dei referti
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 4.11.2019
 */
@WebServlet(
    name = "refertiServlet", urlPatterns = {"/areaPrivata/documenti/listaReferti.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/documenti/referti/referti.jsp")}
)
public class RefertiServlet extends FactoryServlet {

    static public String getURL(){
        return "areaPrivata/documenti/listaReferti.html";
    }

}
