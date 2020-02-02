/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.ssp;

import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet che gestisce la response della pagina jsp della lista richiami
 *
 * @author Alessandro Brighenti &lt;alessandro brighenti at studenti dot unitn dot it&gt;
 * @since 18.01.2020
 */
@WebServlet(
    name = "generaRefertoServlet", urlPatterns = {"/areaPrivata/richiami/listaRichiami.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/richiami/listaRichiami/listaRichiami.jsp")}
)
public class ListaRichiamiServlet extends FactoryServlet {

    static public String getURL(){
        return "areaPrivata/richiami/listaRichiami.html";
    }

}
