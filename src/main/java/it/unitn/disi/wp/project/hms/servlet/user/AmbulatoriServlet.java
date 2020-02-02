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
 * Servlet per caricare la pagina con la lista degli ambulatori disponibili
 *
 * @author Alessandro Tomazzolli &lt;a dot tomazzolli at studenti dot unitn dot it&gt;
 * @since 04.01.2019
 */
 
@WebServlet(
    name = "ambulatoriServlet", urlPatterns = {"/areaPrivata/ambulatori/listaAmbulatori.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE,value = "/pages/ambulatori/listaAmbulatori/listaAmbulatori.jsp")}
)

public class AmbulatoriServlet extends FactoryServlet {

    static public String getURL(){
        return "areaPrivata/ambulatori/listaAmbulatori.html";
    }

}
