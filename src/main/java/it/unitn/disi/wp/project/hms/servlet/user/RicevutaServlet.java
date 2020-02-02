/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user;

import com.google.zxing.WriterException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.utils.QRGen;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Ricevuta;

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
 *
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 28.12.2019
 */
@WebServlet(
    name = "ricevutaServlet", urlPatterns = {"/areaPrivata/pagamenti/listaPagamenti.html/ricevuta"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/restricted/commonPages/ricevuta/ricevuta.jsp")}
)
public class RicevutaServlet extends HttpServlet {

    static public String getURL() {
        return "areaPrivata/pagamenti/listaPagamenti.html/ricevuta";
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
        Integer idRicevuta = null, idPaziente = null;
        try {
            if(request.getParameter("id")!=null || request.getParameter("idPaziente")!=null) {
                idRicevuta = Integer.parseInt(request.getParameter("id"));
                idPaziente = Integer.parseInt(request.getParameter("idPaziente"));
            }
            else {
                LOG(this,LEVEL.WARNING, "mancano alcuni parametri");
                response.sendError(404);
            }
        } catch (NumberFormatException nfex) {
            LOG(this,nfex);
            response.sendError(422);
        }

        if(idRicevuta!=null && idPaziente!=null) {
            Ricevuta ricevuta = null;
            try {
                ricevuta = pazienteDAO.getRicevuta(idPaziente, idRicevuta);

                if(ricevuta!=null) {
                    LOG(this, "ricevuta " + idRicevuta + " trovata.");
                    String jsonRicevuta = ricevuta.giveMeJSON();
                    ricevuta.setBinaryQR(QRGen.getBase64Binary(jsonRicevuta));
                    request.setAttribute(Attr.RICEVUTA, ricevuta);
                    //===========================================================================
                    request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request, response);
                    //===========================================================================
                }
                else {
                    LOG(this,LEVEL.WARNING, "ricevuta " + idRicevuta + " NON trovata.");
                    response.sendError(404);
                }
            } catch (WriterException | DAOException ex) {
                LOG(this,LEVEL.ERROR, "errore JDBC");
                response.sendError(500);
                throw new ServletException();
            }
        }
        else response.sendError(404);
    }
}
