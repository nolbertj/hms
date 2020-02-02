/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet che gestisce la response delle seguenti pagine di informazione comuni a tutti gli utenti.
 * <ul>
 *     <li>Credits</li>
 *     <li>Copyright & Privacy</li>
 *     <li>FAQ</li>
 *     <li>Contatti</li>
 * </ul>
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 26.12.2019
 */
@WebServlet(
    name = "infoServlet",
    urlPatterns = {
        "/areaPrivata/credits.html",
        "/areaPrivata/copyrightPrivacy.html"
    }
)
public class InfoServlet extends HttpServlet {

    static public String getCreditsURL() {
        return "areaPrivata/credits.html";
    }
    static public String getCopyrightPrivacyURL() {
        return "areaPrivata/copyrightPrivacy.html";
    }

    private final String ROOT = "/restricted/commonPages/",
            CREDITS = ROOT + "credits/credits.jsp",
            COPYRIGHT = ROOT + "copyrightPrivacy/copyrightPrivacy.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String PAGE_ABSOLUTE_PATH = null;
        String requestedPage = request.getRequestURI().replaceAll(request.getServletContext().getContextPath(),"");

        if(requestedPage.equals("/" + getCreditsURL()))
            PAGE_ABSOLUTE_PATH = CREDITS;
        else if(requestedPage.equals("/" + getCopyrightPrivacyURL()))
            PAGE_ABSOLUTE_PATH = COPYRIGHT;
        else
            response.sendError(404);

        request.getRequestDispatcher(PAGE_ABSOLUTE_PATH).forward(request,response);
    }

}
