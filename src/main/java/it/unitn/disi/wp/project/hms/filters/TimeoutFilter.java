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
import java.io.IOException;

/**
 * Filter that check if the session is timeout
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 22.12.19
 * @see <a href="https://stackoverflow.com/questions/2070179/how-to-check-session-has-been-expired-in-java">
 *     How to check session has been expired in java
 *     </a>
 */
public class TimeoutFilter extends FactoryFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log("doFilter()");

        doBeforeProcessing(request,response);

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;

        if(req.getRequestedSessionId() !=null && !req.isRequestedSessionIdValid())
            res.sendError(HttpServletResponse.SC_REQUEST_TIMEOUT); //sessione scaduta
        else
            chain.doFilter(request,response);

        doAfterProcessing(request,response);
    }

    @Override
    protected void doBeforeProcessing(ServletRequest request, ServletResponse response) {
    }

    @Override
    protected void doAfterProcessing(ServletRequest request, ServletResponse response) {
    }

    @Override
    public void destroy(){
    }

}
