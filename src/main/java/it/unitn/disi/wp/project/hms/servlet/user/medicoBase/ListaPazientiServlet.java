/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.medicoBase;

import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet che gestisce la response della pagina jsp dei pazienti
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 03.01.2020
 */
@WebServlet(
    name = "listaPazientiServlet", urlPatterns = {"/areaPrivata/documenti/listaPazienti.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/documenti/listaPazienti/listaPazienti.jsp")}
)
public class ListaPazientiServlet extends FactoryServlet {

    static public String getURL(){
        return "areaPrivata/documenti/listaPazienti.html";
    }

}
