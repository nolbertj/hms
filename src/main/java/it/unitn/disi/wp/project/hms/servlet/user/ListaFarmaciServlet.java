/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user;

import it.unitn.disi.wp.project.hms.commons.persistence.Attr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet che gestisce la response della pagina jsp della lista dei farmaci
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 03.01.2020
 */
@WebServlet(
    name = "listaFarmaciServlet", urlPatterns = {"/areaPrivata/documenti/listaFarmaci.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/restricted/commonPages/farmaci/farmaci.jsp")}
)
public class ListaFarmaciServlet extends HttpServlet {

    static public String getURL(){
        return "areaPrivata/documenti/listaFarmaci.html";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
    }

}
