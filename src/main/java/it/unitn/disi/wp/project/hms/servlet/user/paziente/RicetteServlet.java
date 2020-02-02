/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.paziente;

import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet che inoltra la pagina delle ricette farmaceutiche del {@link Paziente}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 11.11.2019
 */
@WebServlet(
    name="ricetteServlet",urlPatterns={"/areaPrivata/prescrizioni/ricetteFarmaceutiche.html"},
    initParams={@WebInitParam(name=Attr.JSP_PAGE,value="/pages/prescrizioni/ricetteFarmaceutiche/ricetteFarmaceutiche.jsp")}
)
public class RicetteServlet extends FactoryServlet {

    static public String getURL(){
        return "areaPrivata/prescrizioni/ricetteFarmaceutiche.html";
    }

}