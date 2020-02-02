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
 * Servlet che inoltra la pagina jsp degli esami prescrivibili.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 4.11.2019
 */
@WebServlet(
    name = "esamiPrescrivibiliServlet", urlPatterns = {"/areaPrivata/documenti/esamiPrescrivibili.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/restricted/commonPages/esamiPrescrivibili/esamiPrescrivibili.jsp")}
)
public class EsamiPrescrivibiliServlet extends HttpServlet {

    static public String getURL(){
        return "areaPrivata/documenti/esamiPrescrivibili.html";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
    }

}