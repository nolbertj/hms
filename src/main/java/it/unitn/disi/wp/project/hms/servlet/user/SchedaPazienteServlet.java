/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Handles HTTP GET and POST for the page defined in the {@link WebServlet#urlPatterns}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 19.01.2020
 */
@WebServlet(
    name = "schedaPazienteServlet", urlPatterns = {"/areaPrivata/documenti/schedaPaziente.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/restricted/commonPages/schedaPaziente/schedaPaziente.jsp")}
)
public class SchedaPazienteServlet extends FactoryServlet {

    PazienteDAO pazienteDAO;

    static public String getURL() {
        return "areaPrivata/documenti/schedaPaziente.html";
    }

    @Override
    public void init() throws ServletException {
        try {
            DAOFactory daoFactory = (DAOFactory)getServletContext().getAttribute(Attr.DAO_FACTORY);
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException ex) {
            LOG(this,ex);
            throw new ServletException("Impossible to init servlet",ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idPaziente = request.getParameter("idPaziente");
            if(Utils.isNullOrEmpty(idPaziente) || idPaziente.equalsIgnoreCase("undefined")) {
                response.sendError(422); // invalid input
            }
            Integer id = Integer.parseInt(idPaziente);
            Paziente paziente = pazienteDAO.getByPrimaryKey(id);
            request.setAttribute("paziente", paziente);
            request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
        }
        catch (NumberFormatException | DAOException ex) {
            LOG(this,ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}