/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.persistence.factories;

import it.unitn.disi.wp.project.hms.commons.persistence.Attr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provides an abstract class to be subclassed to create
 * an HTTP servlet suitable for this Web site.<br>
 * This abstract class has particularly the {@code doGet()}
 * method implemented that returns the JSP page of the subclassed class.<br>
 * This means that the subclass must have a {@link WebInitParam} with the path
 * of a jsp page. See the example:
 * <pre>{@code
 * @WebServlet(
 *     name = "sampleServlet", urlPatterns = {"/restricted/sample.html"},
 *     initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/sample/sample.jsp")}
 * )
 * }</pre>
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 31.12.2019
 */
public abstract class FactoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String root = (String)request.getSession().getAttribute(Attr.USER_ROOT_PATH),
               jspPagePath = getInitParameter(Attr.JSP_PAGE);
        request.getRequestDispatcher(root + jspPagePath).forward(request,response);
    }

}