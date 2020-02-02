/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.filters;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.Ruoli;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryFilter;
import it.unitn.disi.wp.project.hms.commons.utils.Cripting;
import it.unitn.disi.wp.project.hms.persistence.dao.*;
import it.unitn.disi.wp.project.hms.persistence.entities.*;
import it.unitn.disi.wp.project.hms.servlet.authentication.LoginServlet;
import it.unitn.disi.wp.project.hms.servlet.authentication.LogoutServlet;
import it.unitn.disi.wp.project.hms.servlet.user.DashboardServlet;
import org.apache.commons.lang3.RandomStringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;

/**
 * Filter that check if the user is authenticated (and authorized).
 *
 * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 26.10.19
 */
public class AuthenticationFilter extends FactoryFilter {

    private String URI;

    @Override
    protected void doBeforeProcessing(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        log("DoBeforeProcessing");

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        if (request instanceof HttpServletRequest) {

            ServletContext servletContext = request.getServletContext();

            URI = ((HttpServletRequest) request).getRequestURI().replaceAll(servletContext.getContextPath() , "");
            log("URI: "+ URI);

            HttpSession session = ((HttpServletRequest) request).getSession(false);
        }
    }

    @Override
    protected void doAfterProcessing(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        log("DoAfterProcessing");
        //Nothing to do
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     *
     * @author Stefano Chirico
     * @author Alessandro Brighenti
     * @since 1.0.0.190519
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log("doFilter()");

        doBeforeProcessing(request, response);

        ServletContext servletContext = request.getServletContext();
        HttpSession session = ((HttpServletRequest) request).getSession(false);
        boolean isLoggedIn=(session!=null && session.getAttribute(Attr.USER)!=null);
        String loginURI=((HttpServletRequest) request).getContextPath() + "/" + LoginServlet.getURL();

        Cookie[] cookies=((HttpServletRequest) request).getCookies();
        boolean isLoginPage = ((HttpServletRequest) request).getRequestURI().contains(loginURI);


        Throwable problem = null;
        if(!isLoggedIn && cookies!=null){
            boolean isRedirected=false;
            //l'utente non è loggato ma ci sono dei cookie
            String selector="";
            String rawValidator="";
            //cookie da aggiornare
            Cookie toChange=null;
            for(Cookie c: cookies){
                if(c.getName().equals("selector"))
                    selector=c.getValue();
                else if(c.getName().equals("validator")){
                    rawValidator=c.getValue();
                    toChange=c;
                }
            }
            if(!selector.equals("") && !rawValidator.equals("")){
                DAOFactory daoFactory = (DAOFactory) request.getServletContext().getAttribute(Attr.DAO_FACTORY);
                CookieAuthDAO cookieDao=null;
                if (daoFactory == null)
                    throw new ServletException("Impossible to get dao factory");
                try {
                    cookieDao = daoFactory.getDAO(CookieAuthDAO.class);
                } catch (DAOFactoryException ex) {
                    LOG(this, ex);
                    throw new ServletException(ex);
                }
                CookieAutenticazione token=null;
                try {
                    token=cookieDao.findBySelector(selector);
                } catch (DAOException e) {
                    throw new ServletException(e);
                }
                if(token!=null) {
                    if (Cripting.checkPassword(rawValidator, token.getValidator())) {
                        UserDAO userDao = null;
                        User user = null;
                        try {
                            userDao = daoFactory.getDAO(UserDAO.class);
                            user = userDao.getByPrimaryKey(token.getUserMail());
                            if (user != null) {
                                if(session==null){
                                    session=((HttpServletRequest) request).getSession(true); // WTF ?!?!
                                }
                                switch (Ruoli.getKey(user.getRuolo())) {
                                    case Ruoli.PAZIENTE:
                                        try {
                                            PazienteDAO pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
                                            Paziente paziente = pazienteDAO.getByEmail(user.getEmail());
                                            paziente.setRuolo(user.getRuolo());
                                            session.setAttribute(Attr.USER, paziente);
                                            session.setAttribute(Attr.USER_FOLDERNAME, Attr.USER_FOLDER.PATIENT.getName());
                                            session.setAttribute(Attr.USER_ROOT_PATH,Attr.USER_FOLDER.PATIENT.getRootPath());
                                        } catch (DAOFactoryException ex) {
                                            LOG(this, ex);
                                        }
                                        break;
                                    case Ruoli.MEDICO_B:
                                        try {
                                            MedicoBaseDAO medicoBaseDAO = daoFactory.getDAO(MedicoBaseDAO.class);
                                            MedicoBase medicoBase = medicoBaseDAO.getByEmail(user.getEmail());
                                            medicoBase.setRuolo(user.getRuolo());
                                            session.setAttribute(Attr.USER, medicoBase);
                                            session.setAttribute(Attr.USER_FOLDERNAME, Attr.USER_FOLDER.BASE_DOCTOR.getName());
                                            session.setAttribute(Attr.USER_ROOT_PATH,Attr.USER_FOLDER.BASE_DOCTOR.getRootPath());
                                        } catch (DAOFactoryException ex) {
                                            LOG(this, ex);
                                        }
                                        break;
                                    case Ruoli.MEDICO_S:
                                        try {
                                            MedicoSpecialistaDAO medicoSpecialistaDAO = daoFactory.getDAO(MedicoSpecialistaDAO.class);
                                            MedicoSpecialista medicoSpecialista = medicoSpecialistaDAO.getByEmail(user.getEmail());
                                            medicoSpecialista.setRuolo(user.getRuolo());
                                            session.setAttribute(Attr.USER, medicoSpecialista);
                                            session.setAttribute(Attr.USER_FOLDERNAME, Attr.USER_FOLDER.SPECIAL_DOCTOR.getName());
                                            session.setAttribute(Attr.USER_ROOT_PATH,Attr.USER_FOLDER.SPECIAL_DOCTOR.getRootPath());
                                        } catch (DAOFactoryException ex) {
                                            LOG(this, ex);
                                        }
                                        break;
                                    case Ruoli.FARM:
                                        try {
                                            FarmaciaDAO farmaciaDAO = daoFactory.getDAO(FarmaciaDAO.class);
                                            Farmacia farmacia = farmaciaDAO.getByEmail(user.getEmail());
                                            farmacia.setRuolo(user.getRuolo());
                                            session.setAttribute(Attr.USER, farmacia);
                                            session.setAttribute(Attr.USER_FOLDERNAME, Attr.USER_FOLDER.PHARMACY.getName());
                                            session.setAttribute(Attr.USER_ROOT_PATH,Attr.USER_FOLDER.PHARMACY.getRootPath());
                                        } catch (DAOFactoryException ex) {
                                            LOG(this, ex);
                                        }
                                        break;
                                    case Ruoli.SSP:
                                        try {
                                            SspDAO sspDAO = daoFactory.getDAO(SspDAO.class);
                                            Ssp ssp = sspDAO.getByEmail(user.getEmail());
                                            ssp.setRuolo(user.getRuolo());
                                            session.setAttribute(Attr.USER, ssp);
                                            session.setAttribute(Attr.USER_FOLDERNAME, Attr.USER_FOLDER.SSP.getName());
                                            session.setAttribute(Attr.USER_ROOT_PATH,Attr.USER_FOLDER.SSP.getRootPath());
                                        } catch (DAOFactoryException ex) {
                                            LOG(this, ex);
                                        }
                                        break;
                                    case Ruoli.ADMIN:
                                        try {
                                            AdminDAO adminDAO = daoFactory.getDAO(AdminDAO.class);
                                            Admin admin = adminDAO.getByEmail(user.getEmail());
                                            admin.setRuolo(user.getRuolo());
                                            session.setAttribute(Attr.USER, admin);
                                            session.setAttribute(Attr.USER_FOLDERNAME, Attr.USER_FOLDER.ADMIN.getName());
                                            session.setAttribute(Attr.USER_ROOT_PATH,Attr.USER_FOLDER.ADMIN.getRootPath());
                                        } catch (DAOFactoryException ex) {
                                            LOG(this, ex);
                                        }
                                        break;
                                    default:
                                        user = null;
                                        break;
                                }
                                String newValidator=RandomStringUtils.randomAlphanumeric(64);
                                String newHashedValidator=Cripting.hashPwd(newValidator);
                                if(user!=null) {
                                    if (cookieDao.updateValidator(selector, newHashedValidator, user.getEmail())) {
                                        if(toChange!=null){
                                            toChange.setValue(newValidator);
                                            toChange.setMaxAge(604800);
                                            ((HttpServletResponse) response).addCookie(toChange);
                                        }
                                        LOG(this, "Hai effettuato l'accesso con " + user.getEmail());
                                    }
                                }else{
                                    LOG(this, "Non aggiornato il cookie di validazione perché user null");
                                }
                                if (isLoginPage) {
                                    LOG(this,LEVEL.TRACE,"logged");
                                    ((HttpServletResponse) response).sendRedirect(((HttpServletResponse)
                                            response).encodeRedirectURL(Attr.CP + DashboardServlet.getURL()));
                                    isRedirected=true;
                                } else {
                                    chain.doFilter(request,response);
                                }


                            }
                        } catch (DAOException | DAOFactoryException ex) {
                            LOG(this, ex);
                        }
                    }
                }
            }
            if(!isRedirected){
                if(!isLoginPage){
                    LOG(this,LEVEL.TRACE,"no ok cookie");
                    ((HttpServletResponse) response).sendRedirect(((HttpServletResponse)
                            response).encodeRedirectURL(Attr.CP + LoginServlet.getURL()));
                }else{
                   chain.doFilter(request,response);
                }
            }

        } else if(isLoggedIn && isLoginPage) {
            LOG(this,LEVEL.TRACE,"logged");
            ((HttpServletResponse) response).sendRedirect(((HttpServletResponse)
                    response).encodeRedirectURL(Attr.CP + DashboardServlet.getURL()));

        } else if(URI.equals("/areaPrivata/" + LogoutServlet.getURL())) {
            //in pratica questo modo è alquanto forzato
            //dato che non vogliamo vedere /areaPrivata/logout ma solo /logout come URL
            ((HttpServletResponse)response).sendRedirect(servletContext.getContextPath() + "/" + LogoutServlet.getURL());
            log("/" + LogoutServlet.getURL());
        } else {
            continueChain(chain,request,response,problem);
        }

        doAfterProcessing(request,response);

        throwProblem(problem,response);
    }

    public void destroy(){
    }

}