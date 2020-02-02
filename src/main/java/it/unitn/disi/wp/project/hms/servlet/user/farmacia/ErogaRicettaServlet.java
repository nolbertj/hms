/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.farmacia;

import com.google.zxing.WriterException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryServlet;
import it.unitn.disi.wp.project.hms.commons.utils.CustomLogger;
import it.unitn.disi.wp.project.hms.commons.utils.QRGen;
import it.unitn.disi.wp.project.hms.commons.utils.SendEmail;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.FarmaciaDAO;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Farmacia;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;
import it.unitn.disi.wp.project.hms.persistence.entities.Ricetta;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Servlet che inoltra la pagina jsp {@code erogaRicetta.jsp}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 23.11.2019
 */
@WebServlet(
    name = "erogaRicettaServlet", urlPatterns = {"/areaPrivata/erogaRicetta.html"},
    initParams = {
        @WebInitParam(name = Attr.JSP_PAGE, value = "/pages/erogaRicetta/erogaRicetta.jsp"),
        @WebInitParam(name = "ricettaPAGE", value = "/restricted/commonPages/ricetta/ricetta.jsp")
    }
)
public class ErogaRicettaServlet extends FactoryServlet {

    static public String getURL(){
        return "areaPrivata/erogaRicetta.html";
    }

    private FarmaciaDAO farmaciaDAO;
    private PazienteDAO pazienteDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute(Attr.DAO_FACTORY);
        if(daoFactory == null)
            throw new ServletException("Impossible to get dao factory");
        try {
            farmaciaDAO = daoFactory.getDAO(FarmaciaDAO.class);
            pazienteDAO=daoFactory.getDAO(PazienteDAO.class);
        } catch (DAOFactoryException ex) {
            LOG(this, ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String  idRicetta_ = request.getParameter("id"),
                    idPaziente_     = request.getParameter("idPaziente");


            if (Utils.isNullOrEmpty(idRicetta_) || Utils.isNullOrEmpty(idPaziente_)) {
                LOG(this, CustomLogger.LEVEL.ERROR, "Mancano alcuni parametri nella request");
                response.sendError(422); // mancano parametri
            }
            else {
                Farmacia farmacia = (Farmacia) request.getSession().getAttribute(Attr.USER);

                Integer idPaziente = Integer.parseInt(idPaziente_);
                Integer idRicetta = Integer.parseInt(idRicetta_);

                boolean isErogata = farmaciaDAO.erogaRicetta(idRicetta, farmacia.getId());

                Ricetta ricetta = pazienteDAO.getRicettaFarmaceutica(idRicetta,idPaziente);
                String jsonRicetta = ricetta.giveMeJSON();
                ricetta.setBinaryQR(QRGen.getBase64Binary(jsonRicetta));
                request.setAttribute(Attr.RICETTA_FARMACEUTICA, ricetta);

                if (isErogata) {
                    LOG(this,"Ricetta con id "+ idRicetta + " erogata con successo.");
                    Paziente paziente = pazienteDAO.getByPrimaryKey(idPaziente);
                    SendEmail.sendMail(paziente, SendEmail.tipoMail.RIC_EROGATA, null);
                    request.setAttribute(Attr.SUCCESS_MSG, true);
                } else {
                    LOG(this,"Ricetta con id "+ idRicetta + " non erogata");
                    request.setAttribute(Attr.WARNING_MSG,true);
                }
            }
        }
        catch (NumberFormatException | DAOException | WriterException ex) {
            LOG(this,ex);
            request.setAttribute(Attr.ALERT_MSG,true);
            throw new ServletException(ex);
        }
        finally {
            request.getRequestDispatcher(getInitParameter("ricettaPAGE")).forward(request,response);
        }
    }
}
