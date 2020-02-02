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
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;
import it.unitn.disi.wp.project.hms.persistence.entities.Referto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Servlet che inoltra la pagina jsp del {@link Referto} del {@link Paziente}
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 14.11.2019
 */
@WebServlet(
    name = "refertoServlet", urlPatterns = {"/areaPrivata/documenti/listaReferti.html/referto"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/restricted/commonPages/referto/referto.jsp")}
)
public class RefertoServlet extends HttpServlet {

    static public String getURL() {
        return "areaPrivata/documenti/listaReferti.html/referto";
    }

    private PazienteDAO pazienteDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute(Attr.DAO_FACTORY);
        if(daoFactory == null)
            throw new ServletException("Impossible to get dao factory");
        try {
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException ex) {
            LOG(this, ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idReferto = null, idPaziente = null;
        try {
            if(request.getParameter("id")!=null || request.getParameter("idPaziente")!=null) {
                idReferto = Integer.parseInt(request.getParameter("id"));
                idPaziente = Integer.parseInt(request.getParameter("idPaziente"));
            }
            else {
                LOG(this, LEVEL.WARNING, "manca il il parametro 'id'");
                response.sendError(404); //id non trovato, esempio url senza id: listaReferti.html/referto
            }
        } catch(NumberFormatException ex){
            LOG(this, LEVEL.ERROR, "input invalido! Devi scrivere un numero nell'url (sia id referto che id paziente");
            response.sendError(422); //cioè input invalido, es: listaReferti.html/referto?id=doge
        }

        if(idReferto!=null && idPaziente!=null) {
            Referto referto = null;
            try {
                referto = pazienteDAO.getReferto(idReferto, idPaziente);

                if(referto!=null) {
                    LOG(this, "referto " + idReferto + " trovato");
                    request.setAttribute(Attr.REFERTO, referto);

                    request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
                }
                else {
                    LOG(this, LEVEL.WARNING, "referto " + idReferto + " non trovato per il paziente di id=" + idPaziente);
                    response.sendError(404);
                }
            } catch (DAOException ex) {
                LOG(this, LEVEL.ERROR, "errore JDBC");
                response.sendError(500);
                throw new ServletException();
            }
        }
        else response.sendError(404);
    }
}
