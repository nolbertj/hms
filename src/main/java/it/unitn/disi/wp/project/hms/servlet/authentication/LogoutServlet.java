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
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.persistence.dao.CookieAuthDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Enumeration;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Servlet that handles logout
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 02.11.2019
 */
@WebServlet(
        name="logoutServlet", urlPatterns = {"/logout"},
        initParams = {@WebInitParam(name=Attr.JSP_PAGE, value="/logout/logout.jsp")}
)
public class LogoutServlet extends HttpServlet {

    static public String getURL(){
        return "logout"; //porre l'URL definito in urlPatterns escluso lo "/" iniziale
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null) {
            Enumeration<String> sessionAttr = session.getAttributeNames();
            while(sessionAttr.hasMoreElements()){
                String attrNome=sessionAttr.nextElement();
                if((!(attrNome.equals(Attr.USER))) && session.getAttribute(attrNome)!=null){
                    session.removeAttribute(attrNome);
                }
            }
            LOG(this, "tutti gli attributi di sessione sono stati rimossi con successo");

            User user = (User) session.getAttribute(Attr.USER);
            if (user != null) {
                Cookie[] cookies = request.getCookies();
                String selector = "";
                if (cookies != null) {
                    for (Cookie aCookie : cookies) {
                        if (aCookie.getName().equals("selector")) {
                            selector = aCookie.getValue();
                        }
                    }
                }
                if(!(selector.equals(""))){
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

                    try{
                        if(authDao.deleteToken(selector)){
                            Cookie cookieSelector = new Cookie("selector", "");
                            cookieSelector.setMaxAge(0);
                            cookieSelector.setHttpOnly(true);

                            Cookie cookieValidator = new Cookie("validator", "");
                            cookieValidator.setMaxAge(0);
                            cookieValidator.setHttpOnly(true);
                            response.addCookie(cookieSelector);
                            response.addCookie(cookieValidator);
                        }
                    } catch (DAOException e) {
                        e.printStackTrace();
                    }
                }


                session.removeAttribute(Attr.USER);
                session.invalidate();
                user = null;
                session = null;
                LOG(this, "session invalidata!");
            }
            else LOG(this, LEVEL.WARNING, "Nessun utente presente in sessione");
        }
        else LOG(this, LEVEL.ERROR, "NESSUNA SESSIONE ESISTENTE!");

       // if (!response.isCommitted()) {  // questo if crea problemi perchè non esegue la getRequestDispatcher()
            request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
       // }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

}
