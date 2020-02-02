/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.medicoSpecialista;

import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 *
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 28.12.2019
 */
@WebServlet(
    name = "cercaPazienteServlet", urlPatterns = {"/areaPrivata/operazioni/cercaPaziente.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/operazioni/cercaPaziente/cercaPaziente.jsp")}
)
public class CercaPazienteServlet extends FactoryServlet {

    static public String getURL(){
        return "areaPrivata/operazioni/cercaPaziente.html";
    }

}
