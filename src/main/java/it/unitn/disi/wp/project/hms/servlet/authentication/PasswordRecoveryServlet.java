/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.authentication;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.configs.Password;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.utils.Cripting;
import it.unitn.disi.wp.project.hms.commons.utils.SendEmail;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.CookieAuthDAO;
import it.unitn.disi.wp.project.hms.persistence.dao.UserDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;
import static it.unitn.disi.wp.project.hms.commons.utils.SendEmail.tipoMail;

/**
 * Servlet for recover {@link it.unitn.disi.wp.project.hms.persistence.entities.User} password.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 13.11.2019
 */
@WebServlet(
        name="passwordRecoveryServlet", urlPatterns = {"/forgotPassword"},
        initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/forgotPassword/forgotPassword.jsp")}
)
public class PasswordRecoveryServlet extends HttpServlet {

    static public String getURL() {
        return "forgotPassword";
    }

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute(Attr.DAO_FACTORY);
        if(daoFactory == null)
            throw new ServletException("Impossible to get dao factory");
        try {
            userDAO = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            LOG(this, ex);
            throw new ServletException(ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        if(Utils.isNullOrEmpty(email)){
            request.setAttribute(Attr.ALERT_MSG,true); //se l'utente ispeziona la pagina html e invia una stringa vuota
            request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
            return;
        }

        User user = null;
        try {
            user = userDAO.getByPrimaryKey(email); //reperisco l'utente dal database
        } catch (DAOException ex) {
            LOG(this, ex);
            throw new ServletException(ex);
        }

        if(user!=null) {
            //====================================================
            // Genero una nuova password
            String newPassword = Password.getRandomPassword();
            LOG(this, "Nuova password generata: " + newPassword);
            //====================================================
            //====================================================

            LOG(this, "Inviata nuova email a " + user.getEmail());
            //====================================================
            // setto questa nuova password all'utente
            user.setPassword(Cripting.hashPwd(newPassword));
            //====================================================
            // aggiorno la nuova password sul database
            try {
                if(userDAO.updatePassword(user)) {
                    request.setAttribute(Attr.TMP_EMAIL,email); //lascio la mail come placeholder
                    // invio una mail all'utente con la nuova password (non hashata)
                    SendEmail.sendMail(user, tipoMail.RESET_PWD, newPassword);
                    request.setAttribute(Attr.SUCCESS_MSG,true); //nella jsp ci sarà il messaggio "email inviata"
                    LOG(this, "la password è stata resettata");
                    Cookie[] cookies = request.getCookies();
                    String selector = "";
                    if (cookies != null) {
                        for (Cookie aCookie : cookies) {
                            if (aCookie.getName().equals("selector")) {
                                selector = aCookie.getValue();
                            }
                        }
                    }
                    if(!selector.equals("")){
                        DAOFactory daoFactory=(DAOFactory) super.getServletContext().getAttribute(Attr.DAO_FACTORY);
                        CookieAuthDAO authDao;
                        if (daoFactory == null)
                            throw new ServletException("Impossible to get dao factory");
                        try {
                            authDao = daoFactory.getDAO(CookieAuthDAO.class);
                        } catch (DAOFactoryException ex) {
                            LOG(this, ex);
                            throw new ServletException(ex);
                        }
                        try {
                            if (authDao.deleteToken(selector)) {
                                Cookie cookieSelector = new Cookie("selector", "");
                                cookieSelector.setPath(getServletContext().getContextPath());
                                cookieSelector.setMaxAge(0);
                                cookieSelector.setHttpOnly(true);

                                Cookie cookieValidator = new Cookie("validator", "");
                                cookieValidator.setPath(getServletContext().getContextPath());
                                cookieValidator.setMaxAge(0);
                                cookieValidator.setHttpOnly(true);
                                response.addCookie(cookieSelector);
                                response.addCookie(cookieValidator);
                            }
                        }catch (DAOException e) {
                            e.printStackTrace();
                        }
                    }

                }
                else {
                    request.setAttribute(Attr.ALERT_MSG, true); //problema interno al server, vedi messaggio nella jsp
                    LOG(this, LEVEL.ERROR, "password NON modificata");
                }
            } catch (DAOException e) {
                LOG(this, e);
                throw new ServletException("Impossibile aggiornare la password con la servlet");
            }
        }
        else { // caso in cui l'email non esiste nel database
            request.setAttribute(Attr.TMP_EMAIL,email); //metto la email inesistente come placeholder nella jsp
            request.setAttribute(Attr.WARNING_MSG,true); // nella jsp ci sarà un messaggio di warning: email inesistente!
        }
        //==============================================================================================================
        request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
    }

}
