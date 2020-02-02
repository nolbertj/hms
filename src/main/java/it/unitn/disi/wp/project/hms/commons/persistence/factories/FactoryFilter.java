/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.persistence.factories;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Custom Factory Filter class that must be subclassed by all filters
 *
 * @author Nolbert Juarez &lt;nolbert dot juarez at studenti dot unitn dot it&gt;
 * @since 03.01.2020
 */
public abstract class FactoryFilter implements Filter {

    /**
     * The filter configuration object we are associated with.<br>
     * If this value is {@code null}, this filter instance is not currently configured.
     */
    protected FilterConfig filterConfig;

    protected FactoryFilter() {
        super();
    }

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        log("Initializing filter");
    }

    /**
     * Sets the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object.
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Returns the filter configuration object for this filter.
     *
     * @return the {@link FilterConfig} filter configuration object.
     */
    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    protected abstract void doBeforeProcessing(ServletRequest request, ServletResponse response) throws ServletException, IOException;

    protected abstract void doAfterProcessing(ServletRequest request, ServletResponse response) throws ServletException, IOException;

    /**
     *
     * @param throwable
     * @param response
     * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
     */
    protected void sendProcessingError(Throwable throwable, ServletResponse response) throws ServletException, IOException {
        String stackTrace = getStackTrace(throwable);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                try (
                    PrintStream ps = new PrintStream(response.getOutputStream());
                    PrintWriter pw = new PrintWriter(ps)) {

                    pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N
                    // PENDING! Localize this for next official release
                    pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                    pw.print(stackTrace);
                    pw.print("</pre></body>\n</html>"); //NOI18N
                }
                response.getOutputStream().close();
            } catch (IOException | RuntimeException ex) {
            }
        } else {
            try {
                try (PrintStream ps = new PrintStream(response.getOutputStream())) {
                    if(throwable!=null)
                        throwable.printStackTrace(ps);
                }
                response.getOutputStream().close();
            } catch (IOException | RuntimeException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     *
     * @param throwable
     * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
     */
    public String getStackTrace(Throwable throwable) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            if(throwable!=null)
                throwable.printStackTrace(pw);
            pw.close();
            sw.close();
            if(throwable!=null)
                stackTrace = sw.getBuffer().toString();
        } catch (IOException | RuntimeException ex) {
            ex.printStackTrace();
        }
        return stackTrace;
    }

    /**
     *
     * @param msg
     * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
     */
    public void log(String msg) {
        if (filterConfig != null) {
            filterConfig.getServletContext().log(this.filterConfig.getFilterName() + ":" + msg);
        } else {
            Logger.getLogger(msg);
        }
    }

    /**
     * Custom {@code chain.doFilter()} implementation. <br>
     * Checks if an exception is thrown somewhere down the filter chain,
     * we still want to execute our after processing, and then rethrow the problem after that.
     *
     * @param chain
     * @param request
     * @param response
     * @param problem
     *
     * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
     * @author Nolbert Juarez &lt;nolbert dot juarez at studenti at unitn dot it&gt;
     */
    protected void continueChain(FilterChain chain, ServletRequest request, ServletResponse response, Throwable problem) {
        try {
            //System.out.println("chaindo");
            chain.doFilter(request,response);
        } catch (IOException | ServletException | RuntimeException ex) {
            LOG(this,ex);
            if(problem == null)
                problem = ex;
            log("Impossible to propagate to the next filter -> " + ex);
        }
    }

    /**
     *
     * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
     */
    public String toString() {

        if (filterConfig == null) {
            return  "FactoryFilter"+ "()";
        }else{
            String filterName = filterConfig.getFilterName();
            StringBuilder sb = new StringBuilder(filterName + "(");
            sb.append(filterConfig);
            sb.append(")");
            return (sb.toString());
        }

    }

    /**
     * If there was a problem, we want to rethrow it if it is a known type, otherwise log it.
     *
     * @param problem
     * @param response
     * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
     */
    protected void throwProblem(Throwable problem, ServletResponse response) throws ServletException, IOException {
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

}
