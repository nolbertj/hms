/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.medicoSpecialista;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;
import it.unitn.disi.wp.project.hms.commons.utils.SendEmail;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.MedicoSpecialistaDAO;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.MedicoSpecialista;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;
import it.unitn.disi.wp.project.hms.persistence.entities.Referto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Handles HTTP GET and POST for the page defined in the {@link WebServlet#urlPatterns}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 12.01.2020
 */
@WebServlet(
    name = "compilaRefertoServlet", urlPatterns = {"/areaPrivata/operazioni/compilaReferto.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/operazioni/compilaReferto/compilaReferto.jsp")}
)
public class CompilaRefertoServlet extends FactoryServlet {

    static public String getURL(){
        return "areaPrivata/operazioni/compilaReferto.html";
    }

    private MedicoSpecialistaDAO medicoSpecialistaDAO;
    private PazienteDAO pazienteDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute(Attr.DAO_FACTORY);
        if(daoFactory == null)
            throw new ServletException("Impossible to get dao factory");
        try {
            medicoSpecialistaDAO = daoFactory.getDAO(MedicoSpecialistaDAO.class);
            pazienteDAO=daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException ex) {
            LOG(this, ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String  idPrescrizione_ = request.getParameter("idPrescrizione"),
                    idPaziente_     = request.getParameter("idPaziente"),
                    anamnesi        = request.getParameter("anamnesi"),
                    conclusioni     = request.getParameter("conclusioni"),
                    areaEsame       = request.getParameter("area").toLowerCase();

            if (Utils.isNullOrEmpty(idPrescrizione_) || Utils.isNullOrEmpty(idPaziente_) || Utils.isNullOrEmpty(areaEsame)) {
                LOG(this, LEVEL.ERROR, "Mancano alcuni parametri nella request");
                response.sendError(422); // mancano parametri
            }
            else {
                MedicoSpecialista ms = (MedicoSpecialista) request.getSession().getAttribute(Attr.USER);

                if (areaEsame.equalsIgnoreCase(ms.getSpecialita().toLowerCase())) {

                    Integer idPaziente = Integer.parseInt(idPaziente_);
                    Integer idPrescrizione = Integer.parseInt(idPrescrizione_);

                    Referto referto = new Referto();
                    referto.setConclusioni(conclusioni);
                    referto.setAnamnesi(anamnesi);

                    Date dataPagamento = null;
                    if(request.getParameter("includiPagamento").equalsIgnoreCase("on")){
                        dataPagamento = new Date(System.currentTimeMillis());
                    }

                    boolean refertoGenerato = medicoSpecialistaDAO.generaReferto(referto,idPrescrizione,idPaziente,ms.getId(),dataPagamento,1); // 1 è contante

                    if (refertoGenerato) {
                        LOG(this,"Referto generato.");
                        Paziente p=pazienteDAO.getByPrimaryKey(idPaziente);
                        SendEmail.sendMail(p, SendEmail.tipoMail.ESA_EROGATO, null);
                        if(dataPagamento!=null){
                            SendEmail.sendMail(p, SendEmail.tipoMail.PAGAMENTO, null);
                        }
                        request.setAttribute(Attr.SUCCESS_MSG,true);
                    } else {
                        LOG(this,"Referto NON generato.");
                        request.setAttribute(Attr.WARNING_MSG,true);
                    }
                } else {
                    LOG(this,"Il medico specialista loggato non può generare un referto per l'esame in questione");
                    request.setAttribute(Attr.ALERT_MSG,true);
                }
            }
        }
        catch (NumberFormatException | DAOException ex) {
            LOG(this,ex);
            request.setAttribute(Attr.ALERT_MSG,true);
            throw new ServletException(ex);
        }
        finally {
            String ROOT = Attr.USER_FOLDER.SPECIAL_DOCTOR.getRootPath();
            request.getRequestDispatcher(ROOT + getInitParameter(Attr.JSP_PAGE)).forward(request,response);
        }
    }

}
