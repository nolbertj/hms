/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.paziente;

import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet per caricare la pagina degli esami prescritti del paziente
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 22.12.2019
 */
@WebServlet(
    name = "esamiPrescrittiServlet", urlPatterns = {"/areaPrivata/prescrizioni/esamiPrescritti.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE,value = "/pages/prescrizioni/esamiPrescritti/esamiPrescritti.jsp")}
)
public class EsamiPrescrittiServlet extends FactoryServlet {

    static public String getURL(){
        return "areaPrivata/prescrizioni/esamiPrescritti.html";
    }

}