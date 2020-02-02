/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.servlet.user.admin;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.entities.Column;
import it.unitn.disi.wp.project.hms.persistence.dao.AdminDAO;
import it.unitn.disi.wp.project.hms.servlet.user.DashboardServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Servlet that handles the query tool dashboard.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 29.11.2019
 */
@WebServlet(name = "queriesServlet", urlPatterns = {"/areaPrivata/queryServlet"},
    initParams = {@WebInitParam(name = Attr.JSP_PAGE, value = "/pages/dashboard/dashboard.jsp")}
)
public class QueriesServlet extends HttpServlet {

    static public String getURL() {
        return "areaPrivata/queryServlet";
    }

    private AdminDAO adminDAO;

    @Override
    public void init() throws ServletException {
        DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute(Attr.DAO_FACTORY);
        if(daoFactory == null) throw new ServletException("Impossible to get dao factory");
        try {
            adminDAO = daoFactory.getDAO(AdminDAO.class);
        } catch (DAOFactoryException ex) {
            LOG(this, ex);
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendRedirect(res.encodeRedirectURL(Attr.CP + DashboardServlet.getURL()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try {
            String query = req.getParameter("textArea"),
                   queryForHTML = query.replaceAll("\'","\"");

            req.setAttribute("query",queryForHTML);
            Object SQLResult = adminDAO.executeQuery(query);

            ResultSet rs = null;
            if(SQLResult instanceof ResultSet)
                rs = (ResultSet) SQLResult;
            else if(SQLResult instanceof String) {
                req.setAttribute("msg",SQLResult.toString());
            }

            if(rs!=null) {
                ResultSetMetaData rsmd = rs.getMetaData();
                List<Column> columnNames = new ArrayList<>();
                int nrColumns = rsmd.getColumnCount();

                int i = 0;
                while(i < nrColumns) {
                    i++;
                    Column c = new Column();
                    c.setName(rsmd.getColumnName(i));
                    columnNames.add(c);
                }
                req.setAttribute("columnNames",columnNames);

                List<Column> columns = new ArrayList<>();
                while(rs.next()) {
                    for(int j=1; j<=nrColumns; j++) {
                        Column c = new Column();
                        Object obj = rs.getObject(j);
                        if(rs.wasNull() || obj == null)
                             c.setName("null");
                        else c.setName(obj.toString());
                        columns.add(c);
                    }
                }
                req.setAttribute("cols",columns);
                rs.close();
            }
            else if(rs==null && !(SQLResult instanceof String)) {
                req.setAttribute("msg","ERROR");
            }
            req.getRequestDispatcher(Attr.USER_FOLDER.ADMIN.getRootPath() + getInitParameter(Attr.JSP_PAGE)).forward(req,res);
        } catch (DAOException | SQLException e) {
            if(e instanceof SQLException) {
                req.setAttribute("msg",((SQLException) e).getSQLState());
            }
            LOG(this,e);
        }
    }

}
