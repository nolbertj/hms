/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.listeners;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.project.hms.commons.persistence.Attr;
import it.unitn.disi.wp.project.hms.commons.persistence.entities.Table;
import it.unitn.disi.wp.project.hms.commons.persistence.factories.FactoryDayPilotListener;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;

import java.util.Date;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Calendario del paziente con sola vista degli appuntamenti. Nessun evento del listener è permesso.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 17.01.2020
 */
public class CalendarioPazienteListener extends FactoryDayPilotListener {

    private PazienteDAO pazienteDAO;

    @Override
    public void onPrepare() throws Exception {
        LOG(this,"init");
        config();
        setDataTextField("motivo");
        setDataTagFields("id, motivo");
        setDataResourceField("nome_medico");

        Table appuntamenti = null;
        try {
            DAOFactory daoFactory = (DAOFactory) getRequest().getServletContext().getAttribute(Attr.DAO_FACTORY);
            pazienteDAO = daoFactory.getDAO(PazienteDAO.class);
            appuntamenti = getAppuntamenti();
        }
        catch (DAOException | DAOFactoryException ex) {
          LOG(this,ex);
        }
        finally {
            if(appuntamenti != null){
                setEvents(appuntamenti);
                update();
            }
        }
    }

    @Override
    public Table getAppuntamenti() throws DAOException {
        Paziente paziente = (Paziente)getRequest().getSession().getAttribute(Attr.USER);

        Date from = getStartDate().toDate();
        Date to = getStartDate().addDays(getDays()).toDate();

        return pazienteDAO.getAppuntamenti(paziente.getId(), from, to);
    }

}