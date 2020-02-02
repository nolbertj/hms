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
import it.unitn.disi.wp.project.hms.commons.persistence.Ruoli;
import it.unitn.disi.wp.project.hms.commons.utils.Cripting;
import it.unitn.disi.wp.project.hms.persistence.dao.*;
import it.unitn.disi.wp.project.hms.persistence.entities.*;
import it.unitn.disi.wp.project.hms.servlet.user.DashboardServlet;
import org.apache.commons.lang3.RandomStringUtils;

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
import static it.unitn.disi.wp.project.hms.commons.utils.Utils.isNullOrEmpty;

/**
 * Servlet that handles login
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 30.09.2019
 */
@WebServlet(
        name="loginServlet",
        urlPatterns = {"/login"},
        initParams = {@WebInitParam(name=Attr.JSP_PAGE, value="/login/login.jsp")}
)
public class LoginServlet extends HttpServlet {

    static public String getURL() {
        return "login"; //porre l'URL definito in urlPatterns escluso lo "/" iniziale
    }

    private DAOFactory daoFactory;
    private UserDAO userDao;


    @Override
    public void init() throws ServletException {
        daoFactory = (DAOFactory) super.getServletContext().getAttribute(Attr.DAO_FACTORY);
        if (daoFactory == null) 
            throw new ServletException("Impossible to get dao factory");
        try {
            userDao = daoFactory.getDAO(UserDAO.class);
        } catch (DAOFactoryException ex) {
            LOG(this, ex);
            throw new ServletException(ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 

        String email = request.getParameter("username");
        String passwordInserita = request.getParameter("password");
        String ruolo = request.getParameter("ruolo");
        String ricordami = request.getParameter("ricordami");

        if(!isNullOrEmpty(email) || !isNullOrEmpty(passwordInserita) || !isNullOrEmpty(ruolo))
        {
            try {
                //======================================================================================================
                User user = userDao.getByPrimaryKey(email);
                if (user == null) {
                    LOG(this, LEVEL.WARNING, "email inesistente!");
                    request.setAttribute(Attr.ALERT_MSG, "Utente non trovato!");
                    request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
                    //ritorno login.html perchè l'utente può sbagliare ad inserire l'email
                }
                else if(!(user.getRuolo().equals(Ruoli.getValue(ruolo)))){
                    LOG(this, LEVEL.WARNING, "ruolo non corrispondente a quello dell'utente nel db");
                    request.setAttribute(Attr.ALERT_MSG, "L'utente non corrisponde al ruolo selezionato!");
                    request.setAttribute(Attr.TMP_EMAIL, user.getEmail());
                    user=null;
                    request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
                }
                else {
                    LOG(this, "utente " + user.getEmail() + " trovato");
                    request.setAttribute(Attr.TMP_EMAIL, user.getEmail());
                    request.setAttribute(Attr.TMP_ROLE, Ruoli.getKey(user.getRuolo()));

                    if(Cripting.checkPassword(passwordInserita, user.getPassword()))
                    {
                        LOG(this, "password corretta");

                        if(ricordami!=null && ricordami.equalsIgnoreCase("on")){
                            LOG(this, "il checkbox di ricordami è checked");
                            CookieAutenticazione newToken = new CookieAutenticazione();

                            String selector = RandomStringUtils.randomAlphanumeric(15);
                            String rawValidator =  RandomStringUtils.randomAlphanumeric(64);

                            String hashedValidator = Cripting.hashPwd(rawValidator);

                            newToken.setSelector(selector);
                            newToken.setValidator(hashedValidator);
                            newToken.setUserMail(user.getEmail());

                            try{
                                CookieAuthDAO authDAO=daoFactory.getDAO(CookieAuthDAO.class);
                                if(authDAO.insertToken(newToken)){
                                    Cookie cookieSelector = new Cookie("selector", selector);
                                    cookieSelector.setMaxAge(604800); //1 settimana
                                    cookieSelector.setHttpOnly(true);

                                    Cookie cookieValidator = new Cookie("validator", rawValidator);
                                    cookieValidator.setMaxAge(604800); //1 settimana
                                    cookieValidator.setHttpOnly(true);

                                    response.addCookie(cookieSelector);
                                    response.addCookie(cookieValidator);
                                }
                            } catch (DAOFactoryException e) {
                                LOG(this, e);
                            }
                        }

                        switch(Ruoli.getKey(user.getRuolo())){
                            case Ruoli.PAZIENTE:
                                try {
                                    PazienteDAO pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
                                    Paziente paziente = pazienteDAO.getByEmail(user.getEmail());
                                    request.getSession().setAttribute(Attr.USER,paziente);
                                    request.getSession().setAttribute(Attr.USER_FOLDERNAME, Attr.USER_FOLDER.PATIENT.getName());
                                    request.getSession().setAttribute(Attr.USER_ROOT_PATH,Attr.USER_FOLDER.PATIENT.getRootPath());
                                } catch (DAOFactoryException ex) {
                                    LOG(this, ex);
                                }
                                break;
                            case Ruoli.MEDICO_B:
                                try {
                                    MedicoBaseDAO medicoBaseDAO = daoFactory.getDAO(MedicoBaseDAO.class);
                                    MedicoBase medicoBase = medicoBaseDAO.getByEmail(user.getEmail());
                                    request.getSession().setAttribute(Attr.USER, medicoBase);
                                    request.getSession().setAttribute(Attr.USER_FOLDERNAME, Attr.USER_FOLDER.BASE_DOCTOR.getName());
                                    request.getSession().setAttribute(Attr.USER_ROOT_PATH,Attr.USER_FOLDER.BASE_DOCTOR.getRootPath());
                                } catch (DAOFactoryException ex) {
                                    LOG(this, ex);
                                }
                                break;
                            case Ruoli.MEDICO_S:
                                try {
                                    MedicoSpecialistaDAO medicoSpecialistaDAO = daoFactory.getDAO(MedicoSpecialistaDAO.class);
                                    MedicoSpecialista medicoSpecialista = medicoSpecialistaDAO.getByEmail(user.getEmail());
                                    request.getSession().setAttribute(Attr.USER, medicoSpecialista);
                                    request.getSession().setAttribute(Attr.USER_FOLDERNAME, Attr.USER_FOLDER.SPECIAL_DOCTOR.getName());
                                    request.getSession().setAttribute(Attr.USER_ROOT_PATH,Attr.USER_FOLDER.SPECIAL_DOCTOR.getRootPath());
                                } catch (DAOFactoryException ex) {
                                    LOG(this, ex);
                                }
                                break;
                            case Ruoli.FARM:
                                try {
                                    FarmaciaDAO farmaciaDAO = daoFactory.getDAO(FarmaciaDAO.class);
                                    Farmacia farmacia = farmaciaDAO.getByEmail(user.getEmail());
                                    request.getSession().setAttribute(Attr.USER, farmacia);
                                    request.getSession().setAttribute(Attr.USER_FOLDERNAME, Attr.USER_FOLDER.PHARMACY.getName());
                                    request.getSession().setAttribute(Attr.USER_ROOT_PATH,Attr.USER_FOLDER.PHARMACY.getRootPath());
                                } catch (DAOFactoryException ex) {
                                    LOG(this, ex);
                                }
                                break;
                            case Ruoli.SSP:
                                try {
                                    SspDAO sspDAO = daoFactory.getDAO(SspDAO.class);
                                    Ssp ssp = sspDAO.getByEmail(user.getEmail());
                                    request.getSession().setAttribute(Attr.USER, ssp);
                                    request.getSession().setAttribute(Attr.USER_FOLDERNAME, Attr.USER_FOLDER.SSP.getName());
                                    request.getSession().setAttribute(Attr.USER_ROOT_PATH,Attr.USER_FOLDER.SSP.getRootPath());
                                } catch (DAOFactoryException ex) {
                                    LOG(this, ex);
                                }
                                break;
                            case Ruoli.ADMIN:
                                try {
                                    AdminDAO adminDAO = daoFactory.getDAO(AdminDAO.class);
                                    Admin admin = adminDAO.getByEmail(user.getEmail());
                                    request.getSession().setAttribute(Attr.USER, admin);
                                    request.getSession().setAttribute(Attr.USER_FOLDERNAME, Attr.USER_FOLDER.ADMIN.getName());
                                    request.getSession().setAttribute(Attr.USER_ROOT_PATH,Attr.USER_FOLDER.ADMIN.getRootPath());
                                } catch (DAOFactoryException ex) {
                                    LOG(this, ex);
                                }
                                break;
                            default:
                                user = null;
                                break;
                        }

                        if(user!=null){
                            request.getSession().setAttribute("sessionCookie",true); // di default
                            request.getSession().setAttribute("cookieAccepted",false);
                            LOG(this, "Hai effettuato l'accesso con " + user.getEmail());
                            response.sendRedirect(response.encodeRedirectURL(Attr.CP + DashboardServlet.getURL()));
                        }else{
                            LOG(this, "errore interno, nessun utente");
                            request.setAttribute(Attr.ALERT_MSG, "Errore interno!");
                            request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
                        }


                    }
                    else {
                        LOG(this, LEVEL.WARNING, "Hai digitato una password sbagliata!");
                        request.setAttribute(Attr.TMP_ROLE, Ruoli.getKey(user.getRuolo()));
                        request.setAttribute(Attr.TMP_EMAIL, email);
                        user=null;
                        request.setAttribute(Attr.ALERT_MSG, "Password errata!");
                        request.getRequestDispatcher(getInitParameter(Attr.JSP_PAGE)).forward(request,response);
                    }
                }
            }
            catch(DAOException ex) {
                LOG(this, ex);
                throw new ServletException(ex);
            }
        }
        else {
            LOG(this, LEVEL.ERROR, "parametri vuoti!");
            request.setAttribute(Attr.ALERT_MSG, "Sono richiesti tutti i campi!");
        }

    }
}
