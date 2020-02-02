/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.listeners;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.jdbc.JDBCDAOFactory;
import it.unitn.disi.wp.project.hms.commons.configs.*;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.utils.FilesWriter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LEVEL;
import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Web application life-cycle listener.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 08.10.2019
 */
@WebListener
public class WebAppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce){

        try {
            DB.init();
            QRProps.init();
            Password.init();
            PDFProps.init();
            AvatarProps.init();
            FilesWriter.init(sce.getServletContext().getRealPath("/"), sce.getServletContext().getServletContextName().toLowerCase());

            String contextPath = sce.getServletContext().getContextPath();
            if(!contextPath.endsWith("/")) {
                contextPath += "/";
            }
            Attr.CP = contextPath;

            //==============================================================================
            JDBCDAOFactory.configure(DB.getURL(), DB.getDriver(), DB.getUsername(), DB.getPassword());
            DAOFactory daoFactory = JDBCDAOFactory.getInstance();
            sce.getServletContext().setAttribute(Attr.DAO_FACTORY, daoFactory);
            LOG(this, "Connessiona a " + DB.getCompany() + " stabilita.");
            LOG(this, LEVEL.TRACE, DB.getURL());
        }
        catch (DAOFactoryException | IOException | InstantiationException ex) {
            LOG(this, ex);
            throw new RuntimeException(ex);
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DAOFactory daoFactory = (DAOFactory) sce.getServletContext().getAttribute("daoFactory");
        if (daoFactory != null) {
            try {
                daoFactory.shutdown();
                LOG(this,"Connessione a " + DB.getCompany() + " CHIUSA.");
            } catch (DAOFactoryException ex) {
                LOG(this, ex);
            }
        }
    }
}
