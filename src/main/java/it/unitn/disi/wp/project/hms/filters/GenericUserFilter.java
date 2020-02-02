/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.filters;

import it.unitn.disi.wp.project.hms.commons.configs.RESTConfig;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryFilter;
import it.unitn.disi.wp.project.hms.persistence.entities.User;
import it.unitn.disi.wp.project.hms.services.OpenService;
import it.unitn.disi.wp.project.hms.services.PazienteService;
import it.unitn.disi.wp.project.hms.servlet.CookieServlet;
import it.unitn.disi.wp.project.hms.servlet.PDFServlet;
import it.unitn.disi.wp.project.hms.servlet.authentication.PasswordUpdateServlet;
import it.unitn.disi.wp.project.hms.servlet.user.DashboardServlet;
import it.unitn.disi.wp.project.hms.servlet.user.InfoServlet;
import it.unitn.disi.wp.project.hms.servlet.user.ProfiloServlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Classe di template che servirà per i filtri delle sottoclassi di {@link User}.
 *
 * In particolare, verificherà, prima di processare il filtro sottoclassato,
 * se il tipo di utente in sessione (es. Utente, MedicoBase,...) corrisponde
 * al tipo definito in {@code USER_CLASS}.<br><br>
 * Esempi:<br>
 * <ol>
 *     <li>
 *          Il paziente esegue il login con successo. <br> Il filtro PazienteFilter,
 *          essendo primo nella catena dei filtri del tipo di utenti, eseguirà il suo lavoro
 *          se e solo se la classe definita in USER_SUBCLASS = Paziente.
 *     </li>
 *     <li>
 *          Il medicoBase esegue il login con successo. Il filtro PazienteFilter vedrà che
 *          la classe USER_SUBCLASS = MedicoBase è diversa da quella definita in PazienteFilter(Paziente).
 *          Pertanto, andrà avanti con la catena dei filtri finchè non troverà MedicoBaseFilter.
 *     </li>
 * </ol>
 *
 * @author Nolbert Juarez &lt;nolbert dot juarez at studenti dot unitn dot it&gt;
 * @since 04.01.2020
 */
public abstract class GenericUserFilter<USER_SUBCLASS extends User> extends FactoryFilter {

    protected USER_SUBCLASS user;

    protected boolean filterOfUser;

    protected String requestedPAGE;
    protected String requestedSERVICE;

    protected boolean pageRequested = false;
    protected boolean serviceRequested = false;

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected final String SERVICE_PATH;

    private final List<String> DEFAULT_PAGES = Arrays.asList(
            PasswordUpdateServlet.getURL(),
            DashboardServlet.getURL(),
            ProfiloServlet.getURL(),
            InfoServlet.getCreditsURL(),
            InfoServlet.getCopyrightPrivacyURL(),
            CookieServlet.getURL(),
            PDFServlet.getURL(),
            "areaPrivata/impostazioni.html"
    );

    protected List<String> ALLOWED_PAGES;

    /**
     *
     * @param servicePath {@link Path} defined in Service class (e.g. {@link PazienteService#getURL()})
     * @param allowedPages list of allowed pages that filter will not block
     */
    protected GenericUserFilter(String servicePath, List<String> allowedPages){
        super();
        if (servicePath == null || allowedPages == null) {
            throw new NullPointerException();
        }
        SERVICE_PATH = servicePath;
        ALLOWED_PAGES = new ArrayList<>();
        ALLOWED_PAGES.addAll(DEFAULT_PAGES);
        ALLOWED_PAGES.addAll(allowedPages);
    }

    @Override
    protected void doBeforeProcessing(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        log("DoBeforeProcessing");
        request = (HttpServletRequest)servletRequest;
        response = (HttpServletResponse)servletResponse;

        // This filter doesn't check authentication so there's no problem if user doesn't exists in session
        User userLogged = (User)request.getSession(false).getAttribute(Attr.USER);

        String genericClassName = (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getTypeName();

        // check if the given Class in USER_SUBCLASS is the same of the user logged
        filterOfUser = genericClassName.equals(userLogged.getClass().getName());

        if (filterOfUser) {
            user = (USER_SUBCLASS)userLogged;

            String requestURI = this.request.getRequestURI();

            if (requestURI.contains(OpenService.getURL())) {
                serviceRequested = false; // beacuse it's a public service and not a service of the user logged
            }
            else if (requestURI.contains(RESTConfig.getURL())) {
                serviceRequested = true;
                requestedSERVICE = requestURI.replaceAll(Attr.CP + SERVICE_PATH, "");
                LOG(this,LEVEL.TRACE,"SERVICE REQUESTED: " + requestedSERVICE);
                log("SERVICE REQUESTED: " + requestedSERVICE);
            }
            else {
                pageRequested = true;
                requestedPAGE = requestURI.replaceAll(Attr.CP, "");
                LOG(this,LEVEL.TRACE,"PAGE REQUSETED: " + requestedPAGE);
                log("PAGE REQUESTED: " + requestedPAGE);
            }
        }
        log((filterOfUser ? "":" doesn't") + " corresponds with class of user logged");
    }

    @Override
    protected void doAfterProcessing(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        log("DoAfterProcessing");
    }

    protected abstract void checkPage(ServletRequest servletRequest, ServletResponse servletResponse, Throwable problem) throws ServletException, IOException;

    protected abstract void checkService(ServletRequest servletRequest, ServletResponse servletResponse, Throwable problem) throws ServletException, IOException;

    protected final boolean checkAllowedPage(ServletRequest req, ServletResponse res, Throwable problem) {
        boolean pageAllowed;
        try {
            pageAllowed = ALLOWED_PAGES.stream().anyMatch(page-> requestedPAGE.contains(page) || requestedPAGE.equals(page));
            /*
             * Se la pagina non è permessa all'utente non li dico che non ha permessi per accedere
             * perchè altrimenti verrebbe a conoscenza dell'esistenza di tale pagina.
             * Pertanto rispondo con "pagina non trovata" (error 404).
             */
            if (pageAllowed == false)
                response.sendError(HttpServletResponse.SC_NOT_FOUND);

            String msg = " Page " + (pageAllowed ? "":" not ") + "allowed" + (pageAllowed?".":" or not found.");
            log(msg);
            LOG(this, (pageAllowed ? LEVEL.TRACE:LEVEL.WARNING), msg);
        } catch(IOException ex) {
            LOG(this,ex);
            problem = ex;
            pageAllowed = false;
        }
        return pageAllowed;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log("doFilter()");
        doBeforeProcessing(request,response);

        Throwable problem = null;
        try {
            if (filterOfUser) {
                if (pageRequested)
                    checkPage(request, response, problem);
                if(serviceRequested)
                    checkService(request, response, problem);

                if(problem!=null)
                    LOG(this,LEVEL.ERROR,problem.getMessage());
            }
            if(!response.isCommitted())
                chain.doFilter(request,response);
        }
        catch (IOException ex) {
            LOG(this,ex);
            problem = ex;
        }

        doAfterProcessing(request,response);

        if(problem!=null)
            LOG(this,LEVEL.ERROR,problem.getMessage());
        throwProblem(problem,response);
    }

    /**
     * Checks if the :id present in the requested URI equals the id of the logged user.
     * <br>
     * Example:
     * URI for get resources: /api/:id/resources <br>
     * ID of user logged = 1 <br>
     * User logged request resources with :id=1 => return true <br>
     * User logged request resources with :id=5 => return false <br>
     *
     * @param idUserLogged id of user logged in session
     * @return {@code true} if id of user logged equals the :id in {@link PathParam}, {@code false} otherwise.
     * @return {@code null} if uri doesn't contains any id
     * @author Nolbert Juarez &lt;nolbert dot juarez at studenti dot unitn dot it&gt;
     */
    protected final Boolean checkUserIDFromPath(Integer idUserLogged) throws IOException {
        String[] paths = requestedSERVICE.split("/");
        Boolean idExists;
        try {
            Integer possibleID = Integer.parseInt(paths[1]);
            idExists = possibleID.intValue() == idUserLogged.intValue();
            String msg = "service" + (idExists ? " ":" not ") + "allowed";
            LOG(this,(idExists ? LEVEL.TRACE:LEVEL.WARNING),msg);
            log(msg);
        } catch(NumberFormatException | NullPointerException ex) {
            LOG(this,ex);
            idExists = null;
        }
        return idExists;
    }

    @Override
    public void destroy(){
    }

}
