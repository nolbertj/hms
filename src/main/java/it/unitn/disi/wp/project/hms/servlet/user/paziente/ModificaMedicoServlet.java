/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.paziente;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Servlet che gestisce la modifica del medico di base da parte del {@link Paziente}
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 24.12.2019
 */
@MultipartConfig //serve per ricevere file in POST
@WebServlet(
    name = "modificaMedicoServlet", urlPatterns = {"/areaPrivata/profilo/modificaMedico.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/profilo/modificaMedico/modificaMedico.jsp")}
)
public class ModificaMedicoServlet extends FactoryServlet {

    static public String getURL() {
        return "areaPrivata/profilo/modificaMedico.html"; //porre l'URL definito in urlPatterns escluso lo "/" iniziale
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String  idMedico = request.getParameter("idMedico");

        if(idMedico != null) {
            try {
                Integer id=Integer.parseInt(idMedico);
                Paziente paziente = (Paziente)request.getSession().getAttribute(Attr.USER);
                if(paziente!=null){
                    if(pazienteDAO.changeMedic(paziente.getId(), id)) {
                        request.setAttribute(Attr.MED_CAMBIATO,true);
                        //aggiorno l'utente
                        Paziente nPaz = pazienteDAO.getByEmail(paziente.getEmail());
                        request.getSession().setAttribute(Attr.USER, nPaz); //DEVO aggiornare l'utente in sessione con i nuovi dati aggiornati
                    } else {
                        request.setAttribute(Attr.MED_CAMBIATO, false);
                    }
                }
            } catch (DAOException e) {
                request.setAttribute(Attr.MED_CAMBIATO,false);
                LOG(this, e);
            } catch(NumberFormatException e) {
                request.setAttribute(Attr.MED_CAMBIATO,false);
                LOG(this, LEVEL.ERROR,"errore nel parse dell'idMedico");
            }
        }
        else {
            request.setAttribute(Attr.MED_CAMBIATO, false);
            LOG(this, LEVEL.ERROR, "Id del medico di base da cambiare è null");
        }
        //==============================================================================================================
        String root = (String)request.getSession().getAttribute(Attr.USER_ROOT_PATH);
        request.getRequestDispatcher(root + "/pages/profilo/profilo.jsp").forward(request,response);
    }

}
