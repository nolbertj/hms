/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet;

import it.unitn.disi.wp.project.hms.commons.persistence.Attr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Cookie che renderizza la pagina relativa alle informazioni dei cookie contenente
 * un form per cambiare le impostazioni dei cookie.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 20.01.2020
 */
@WebServlet(
    name = "cookieServlet", urlPatterns = { "/areaPrivata/cookie.html" },
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/restricted/commonPages/cookie/cookie.jsp")}
)
public class CookieServlet extends HttpServlet {

    static public String getURL() {
        return "areaPrivata/cookie.html";
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(req,resp);
    }

}
