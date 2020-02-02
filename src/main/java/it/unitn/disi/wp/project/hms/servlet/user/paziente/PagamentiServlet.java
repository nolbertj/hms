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
import it.unitn.disi.wp.project.hms.commons.utils.SendEmail;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;
import static it.unitn.disi.wp.project.hms.commons.utils.SendEmail.tipoMail;

/**
 *
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 27.12.2019
 */
@WebServlet(
    name = "pagamentiServlet", urlPatterns = {"/areaPrivata/pagamenti/listaPagamenti.html"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/pagamenti/listaPagamenti/listaPagamenti.jsp")}
)
public class PagamentiServlet extends FactoryServlet {

    static public String getURL() {
        return "areaPrivata/pagamenti/listaPagamenti.html";
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idPrestazione_ = request.getParameter("idPrestazione"),
               riferimentoPagamento_ = request.getParameter("cod"),
               payment = request.getParameter("payment");

        if(!Utils.isNullOrEmpty(payment)) {
            try {
                Integer idPrestazione = Integer.parseInt(idPrestazione_);
                Integer riferimentoPagamento = Integer.parseInt(riferimentoPagamento_);
                Date dataPagamento = new Date();
                boolean res = false;
                if(payment.equalsIgnoreCase("paypal")) {
                    res = pazienteDAO.effettuaPagamentoEsame(riferimentoPagamento, idPrestazione,dataPagamento,4);
                }
                else if(payment.equalsIgnoreCase("mastercard")) {
                    res = pazienteDAO.effettuaPagamentoEsame(riferimentoPagamento,idPrestazione,dataPagamento,3);
                }
                else if(payment.equalsIgnoreCase("visa")) {
                    res = pazienteDAO.effettuaPagamentoEsame(riferimentoPagamento,idPrestazione,dataPagamento,2);
                }

                if(res){
                    Paziente p=(Paziente)request.getSession().getAttribute(Attr.USER);
                    request.setAttribute(Attr.SUCCESS_MSG,true);
                    Paziente paziente = (Paziente)request.getSession().getAttribute(Attr.USER);
                    SendEmail.sendMail(paziente, tipoMail.PAGAMENTO, null);
                }
                else {
                    request.setAttribute(Attr.ALERT_MSG,true);
                }
            }
            catch (DAOException | NumberFormatException ex) {
                LOG(this,ex);
                request.setAttribute(Attr.ALERT_MSG,true);
                /*if(ex instanceof NumberFormatException)
                    response.sendError(422); // invalid input
                else response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);*/
            }
            finally{
                String ROOT = (String)request.getSession().getAttribute(Attr.USER_ROOT_PATH);
                request.getRequestDispatcher(ROOT + getInitParameter(Attr.JSP_PAGE)).forward(request,response);
            }
        }
    }

}