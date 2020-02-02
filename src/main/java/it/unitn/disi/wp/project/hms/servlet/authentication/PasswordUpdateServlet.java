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
import static it.unitn.disi.wp.project.hms.commons.utils.Utils.isNullOrEmpty;

/**
 * Servlet for update {@link it.unitn.disi.wp.project.hms.persistence.entities.User} password.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 29.10.2019
 */
@WebServlet(
    name="passwordUpdateServlet", urlPatterns = {"/areaPrivata/passwordUpdate"},
    initParams = {@WebInitParam(name=Attr.JSP_PAGE, value = "/pages/profilo/profilo.jsp")}
)
public class PasswordUpdateServlet  extends HttpServlet {

    static public String getURL() {
        return "areaPrivata/passwordUpdate"; //porre l'URL definito in urlPatterns escluso lo "/" iniziale
    }

    private UserDAO userDAO;
    private CookieAuthDAO authDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute(Attr.DAO_FACTORY);
        if(daoFactory == null)
            throw new ServletException("Impossible to get dao factory");
        try {
            userDAO = daoFactory.getDAO(UserDAO.class);
            authDAO = daoFactory.getDAO(CookieAuthDAO.class);
        } catch (DAOFactoryException ex) {
            LOG(this, ex);
            throw new ServletException(ex);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String  currentPassword = request.getParameter("currentPwd"),
                newPassword = request.getParameter("newPwd"),
                confirmedPassword = request.getParameter("confirmPwd");

        if(!isNullOrEmpty(currentPassword) && !isNullOrEmpty(newPassword) && !isNullOrEmpty(confirmedPassword)) {
            LOG(this,"i parametri esistono e non sono vuoti");
            if (newPassword.equalsIgnoreCase(confirmedPassword)) {
               LOG(this, "le password nuova e di conferma coincidono");

                if(Password.checkRequirments(newPassword)) {
                    LOG(this, "la password rispetta i requisiti definiti in passwordRequirments.properties");
                    User user = (User) request.getSession().getAttribute(Attr.USER); //non verifico se è null perchè già fatto a monte
                    try {
                        //la classe dell'utente salvato in sessione puo ad esempio essere Paziente, di conseguenza
                        //esso non avrà con sè la password salvata nella sessione lato server.
                        //dovrò quindi reperire la password come segue:
                        user = userDAO.getByPrimaryKey(user.getEmail());
                    } catch (DAOException ex) {
                        throw new ServletException("Impossibile ritornare l'utente");
                    }
                    if (Cripting.checkPassword(currentPassword, user.getPassword())) {
                        LOG(this, "la password corrente è corretta");
                        user.setPassword(Cripting.hashPwd(newPassword));
                        try {
                            if(userDAO.updatePassword(user)) {
                                LOG(this, "la password è stata cambiata");
                                request.setAttribute(Attr.PSWD_MODIFICATA, true);
                                SendEmail.sendMail(request.getSession().getAttribute(Attr.USER), tipoMail.PWD_NUOVA, null );
                                Cookie[] cookies = request.getCookies();
                                String selector = "";
                                if (cookies != null) {
                                    for (Cookie aCookie : cookies) {
                                        if (aCookie.getName().equals("selector")) {
                                            selector = aCookie.getValue();
                                        }
                                    }
                                }
                                if(!Utils.isNullOrEmpty(selector)){
                                    try {
                                        if (authDAO.deleteToken(selector)) {
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
                                        throw new ServletException("Cookie problem!");
                                    }
                                }
                            }
                            else {
                                LOG(this, LEVEL.ERROR, "password NON modificata");
                                request.setAttribute(Attr.PSWD_MODIFICATA, false);
                            }
                        } catch (DAOException e) {
                            throw new ServletException("Impossibile aggiornare la password con la servlet");
                        }
                    }
                    else {
                        LOG(this, LEVEL.WARNING, "La password corrente inserita è errata");
                        request.setAttribute(Attr.PSWD_MODIFICATA, false);
                        request.setAttribute(Attr.INCORRECT_PWD, true);
                    }
                }
                else {
                    LOG(this, LEVEL.ERROR, "la password non rispetta i requisiti per essere tale");
                    request.setAttribute(Attr.PSWD_MODIFICATA, false);
                }

            }
            else {
                LOG(this, LEVEL.ERROR, "la password nuova e di conferma non coincidono");
                request.setAttribute(Attr.INCORRECT_PWD, true);
                request.setAttribute(Attr.PSWD_MODIFICATA, false);
            }
        }
        else {
            LOG(this, LEVEL.ERROR, "Alcuni o tutti i parametri non sono stati trovati o sono vuoti");
            request.setAttribute(Attr.PSWD_MODIFICATA, false);
        }
        //==============================================================================================================
        String root = (String)request.getSession().getAttribute(Attr.USER_ROOT_PATH);
        request.getRequestDispatcher(root + "/pages/profilo/profilo.jsp").forward(request,response);
    }

}
