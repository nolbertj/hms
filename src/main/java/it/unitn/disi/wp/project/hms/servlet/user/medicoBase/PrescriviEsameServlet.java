/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.medicoBase;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;
import it.unitn.disi.wp.project.hms.commons.utils.SendEmail;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.MedicoBaseDAO;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Esame;
import it.unitn.disi.wp.project.hms.persistence.entities.MedicoBase;
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
 * @since 10.01.2020
 */
@WebServlet(
    name = "prescriviEsameServlet", urlPatterns = {"/areaPrivata/operazioni/prescriviEsame.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/operazioni/prescriviEsame/prescriviEsame.jsp")}
)
public class PrescriviEsameServlet extends FactoryServlet {

    static public String getURL(){
        return "areaPrivata/operazioni/prescriviEsame.html";
    }

    private MedicoBaseDAO medicoBaseDAO;
    private PazienteDAO pazienteDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute(Attr.DAO_FACTORY);
        if(daoFactory == null)
            throw new ServletException("Impossible to get dao factory");
        try {
            medicoBaseDAO = daoFactory.getDAO(MedicoBaseDAO.class);
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
                    idPaziente_ = request.getParameter("idPaziente"),
                    annotazioni = request.getParameter("annotazioni");

            if (Utils.isNullOrEmpty(codEsame_) || Utils.isNullOrEmpty(idPaziente_)) {
                request.setAttribute(Attr.WARNING_MSG, true);
            }
            else {
                MedicoBase medicoBase = (MedicoBase)request.getSession().getAttribute(Attr.USER);

                Integer idPaziente = Integer.parseInt(idPaziente_);
                Integer codEsame = Integer.parseInt(codEsame_);

                Esame esame = new Esame();
                esame.setCodice(codEsame);
                esame.setDescrizione(annotazioni);

                boolean prescrizioneEffettuata = medicoBaseDAO.prescriviEsame(esame,idPaziente, medicoBase.getId());

                if (prescrizioneEffettuata) {
                    LOG(this,"Prescrizione effettuata");
                    request.setAttribute(Attr.SUCCESS_MSG,true);

                    Paziente paziente = pazienteDAO.getByPrimaryKey(idPaziente);
                    SendEmail.sendMail(paziente, SendEmail.tipoMail.ESA_PRESCRITTO,null);
                    LOG(this,"email di conferma invaia al paziente " + paziente.getEmail());
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
            String ROOT = Attr.USER_FOLDER.BASE_DOCTOR.getRootPath();
            request.getRequestDispatcher(ROOT + getInitParameter(Attr.JSP_PAGE)).forward(request,response);
        }
    }

}
