/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.ssp;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;
import it.unitn.disi.wp.project.hms.commons.utils.SendEmail;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.dao.SspDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;
import it.unitn.disi.wp.project.hms.persistence.entities.Ssp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Handles HTTP GET and POST for the page defined in the {@link WebServlet#urlPatterns}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 10.01.2020
 */
@WebServlet(
    name = "prescriviRichiamoServlet", urlPatterns = {"/areaPrivata/richiami/prescriviRichiamo.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/richiami/prescriviRichiamo/prescriviRichiamo.jsp")}
)
public class PrescriviRichiamoServlet extends FactoryServlet {

    static public String getURL(){
        return "areaPrivata/richiami/prescriviRichiamo.html";
    }

    private SspDAO sspDAO;
    private PazienteDAO pazienteDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute(Attr.DAO_FACTORY);
        if(daoFactory == null)
            throw new ServletException("Impossible to get dao factory");
        try {
            sspDAO = daoFactory.getDAO(SspDAO.class);
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException ex) {
            LOG(this, ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String  codEsame_ = request.getParameter("codEsame"),
                    etaInizio_ = request.getParameter("etaInizio"),
                    etaFine_ = request.getParameter("etaFine");

            if (Utils.isNullOrEmpty(codEsame_) || Utils.isNullOrEmpty(etaInizio_) || Utils.isNullOrEmpty(etaFine_)) {
                request.setAttribute(Attr.WARNING_MSG, true);
            }
            else {
                Ssp ssp=(Ssp)request.getSession().getAttribute(Attr.USER);

                Integer codEsame = Integer.parseInt(codEsame_);
                Integer etaInizio=Integer.parseInt(etaInizio_);
                Integer etaFine=Integer.parseInt(etaFine_);

                List<Paziente> richiamoEffettuato=sspDAO.prescriviRichiamo(ssp.getId(), codEsame, etaInizio, etaFine);



                if (richiamoEffettuato.size()>0) {
                    LOG(this,"Prescrizione effettuata");
                    request.setAttribute(Attr.SUCCESS_MSG,true);

                    for(Paziente paziente: richiamoEffettuato){
                        SendEmail.sendMail(paziente, SendEmail.tipoMail.ESA_PRESCRITTO,null);
                        LOG(this,"email di conferma inviata al paziente " + paziente.getEmail());
                    }
                } 
                else {
                    LOG(this,"prescrizione NON effettuata.");
                    request.setAttribute(Attr.ALERT_MSG,true);
                }
            }
        }
        catch(NumberFormatException | DAOException ex) {
            request.setAttribute(Attr.ALERT_MSG,true);
            LOG(this,ex);
            throw new ServletException(ex);
        }
        finally {
            String ROOT = Attr.USER_FOLDER.SSP.getRootPath();
            request.getRequestDispatcher(ROOT + getInitParameter(Attr.JSP_PAGE)).forward(request,response);
        }
    }

}
