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
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryFilter;
import it.unitn.disi.wp.project.hms.persistence.dao.MedicoBaseDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Filter that check if the user is authenticated (and authorized).
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 26.10.19
 */
public class AssetsFilter extends FactoryFilter {

    private String URI;
    private DAOFactory daoFactory;
    private MedicoBaseDAO medicoBaseDAO;

    @Override
    protected void doBeforeProcessing(ServletRequest request, ServletResponse response) throws ServletException, IOException{
        try {
            daoFactory = (DAOFactory)request.getServletContext().getAttribute(Attr.DAO_FACTORY);
            if (daoFactory == null) {
                LOG(this,LEVEL.ERROR, "Impossible to get dao factory for storage system");
                throw new RuntimeException(new ServletException("Impossible to get dao factory for storage system"));
            }
            medicoBaseDAO = daoFactory.getDAO(MedicoBaseDAO.class);
        }
        catch (DAOFactoryException ex) {
            LOG(this,ex);
            throw new RuntimeException(new ServletException(ex));
        }
    }

    protected void doAfterProcessing(ServletRequest request, ServletResponse response) {
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

        HttpSession session = ((HttpServletRequest) request).getSession(false);
        User user = (User)session.getAttribute(Attr.USER);

        String uri = ((HttpServletRequest) request).getRequestURI();

        Throwable problem = null;
        //se l'utente è vuoto pagina 404
        if(user == null){
            log("UTENTE CHIEDE FOTO CHE NON è LA PROPRIA");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        else {
            //se l'utente chiede la sua fotografia va bene
            if (uri.contains(user.getUsername())) {
                log("OK CHAMP");
                continueChain(chain,request,response,problem);
            } else if (user instanceof Paziente) { //questo else if si applica solo ai pazienti
                //serve per permettere a un paziente di vedere la fotografia del proprio medico
                MedicoBase m = ((Paziente) user).getMedicoBase();
                //se l'utente chiede la fotografica del proprio medico (per la card del profilo) va bene
                if (uri.contains(m.getUsername())) {
                    continueChain(chain,request,response,problem);
                } else {
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else if(user instanceof MedicoSpecialista || user instanceof Farmacia){
                continueChain(chain, request, response, problem);

            }else if(user instanceof MedicoBase){
                String[] splits=uri.split("/");
                String userRichiesto=splits[6];
                try {
                   boolean isPaziente = medicoBaseDAO.checkPaziente(((MedicoBase) user).getId(), userRichiesto);
                    if(isPaziente)
                        continueChain(chain, request, response, problem);
                    else{
                        log("MEDICO CHIEDE FOTO DI UN PAZIENTE NON SUO");
                        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
                    }
                } catch (DAOException e) {
                    e.printStackTrace();
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }else {
                //in tutti gli altri casi (per ora) si ha un errore
                log("UTENTE CHIEDE FOTO CHE NON è LA PROPRIA");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }

        doAfterProcessing(request, response);

        throwProblem(problem,response);
    }

    public void destroy(){
    }

}
