/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.filters;

import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter that check if the user hits a JSP page
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 22.11.19
 */
public class PagesFilter extends FactoryFilter {

    protected void doBeforeProcessing(ServletRequest request, ServletResponse response) {
    }

    protected void doAfterProcessing(ServletRequest request, ServletResponse response) {
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

        String uri=((HttpServletRequest) request).getRequestURI();

        Throwable problem = null;
        if(uri.contains("/restricted/") && uri.contains(".jsp")) {
            log("UTENTE CHIEDE JSP RESTRICTED");
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        else {
            continueChain(chain,request,response,problem);
        }

        doAfterProcessing(request, response);

        throwProblem(problem,response);
    }

    public void destroy(){
    }

}
