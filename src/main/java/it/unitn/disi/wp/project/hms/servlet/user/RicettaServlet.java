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
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;
import it.unitn.disi.wp.project.hms.persistence.entities.Ricetta;
import it.unitn.disi.wp.project.hms.servlet.user.paziente.RicetteServlet;

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
 * Servlet che inoltra la pagina jsp della ricetta farmaceutica del {@link Paziente}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 4.11.2019
 */
@WebServlet(
    name = "ricettaServlet", urlPatterns = {"/areaPrivata/prescrizioni/ricetteFarmaceutiche.html/ricetta"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/restricted/commonPages/ricetta/ricetta.jsp")}
)
public class RicettaServlet extends HttpServlet {

    static public String getURL() {
        return RicetteServlet.getURL() + "/ricetta";
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

        Integer idRicetta = null, idPaziente = null;
        try {
            if(request.getParameter("id")!=null && request.getParameter("idPaziente")!=null) {
                idRicetta = Integer.parseInt(request.getParameter("id"));
                idPaziente = Integer.parseInt(request.getParameter("idPaziente"));
            }
            else {
                LOG(this, LEVEL.WARNING, "mancano alcuni parametri");
                response.sendError(HttpServletResponse.SC_NOT_FOUND); //id non trovato, esempio url senza id: ricetteFarmaceutiche.html/ricetta
            }
        }catch(NumberFormatException ex){
            LOG(this, LEVEL.ERROR, "input invalido! Devi scrivere un numero nell'url");
            response.sendError(422); //cioè input invalido, es: ricetteFarmaceutiche.html/ricetta?id=doge
        }

        if(idRicetta!=null && idPaziente!=null) {
            try {
                Ricetta ricettaFarmaceutica = pazienteDAO.getRicettaFarmaceutica(idRicetta, idPaziente);

                if(ricettaFarmaceutica!=null) {
                    String jsonRicetta = ricettaFarmaceutica.giveMeJSON();
                    ricettaFarmaceutica.setBinaryQR(QRGen.getBase64Binary(jsonRicetta));
                    request.setAttribute(Attr.RICETTA_FARMACEUTICA, ricettaFarmaceutica);

                    request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
                }
                else {
                    LOG(this, LEVEL.WARNING, "ricetta " + idRicetta + " non trovata per il paziente di id=" + idPaziente);
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (WriterException | DAOException ex) {
                LOG(this, LEVEL.ERROR, "errore JDBC");
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                throw new ServletException();
            }
        }
        else response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

}
