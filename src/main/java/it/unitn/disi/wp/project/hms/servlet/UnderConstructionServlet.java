/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet that response with error 503 (page under construction)
 * when one of the URL defined in the {@link WebServlet#urlPatterns()} is called
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 03.01.2020
 */
@WebServlet(
    name = "underConstructionServlet",
    urlPatterns = {
        "/areaPrivata/prescrizioni/prenotaPrelievo.html",
        "/areaPrivata/impostazioni.html"
    }
)
public class UnderConstructionServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        processRequest(req,res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        processRequest(req,res);
    }

}
