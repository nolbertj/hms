/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user;

import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.persistence.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet che inoltra la pagina del profilo in base al ruolo dell'{@link User}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 28.10.2019
 */
@WebServlet(
    name = "profiloServlet", urlPatterns = {"/areaPrivata/profilo"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/profilo/profilo.jsp")}
)
public class ProfiloServlet extends HttpServlet {

    static public String getURL() {
        return "areaPrivata/profilo"; //porre l'URL definito in urlPatterns escluso lo "/" iniziale
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String root = (String)request.getSession().getAttribute(Attr.USER_ROOT_PATH);
        request.getRequestDispatcher(root + getInitParameter(Attr.JSP_PAGE)).forward(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}
