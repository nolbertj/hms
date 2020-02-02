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
import it.unitn.disi.wp.project.hms.persistence.dao.MedicoBaseDAO;
import it.unitn.disi.wp.project.hms.persistence.dao.MedicoSpecialistaDAO;
import it.unitn.disi.wp.project.hms.persistence.dao.jdbc.JDBCMedicoBaseDAO;
import it.unitn.disi.wp.project.hms.persistence.dao.jdbc.JDBCMedicoSpecialistaDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.MedicoBase;
import it.unitn.disi.wp.project.hms.persistence.entities.MedicoSpecialista;
import org.daypilot.ui.args.calendar.CommandArgs;
import org.daypilot.ui.args.calendar.EventMoveArgs;
import org.daypilot.ui.args.calendar.EventResizeArgs;
import org.daypilot.ui.args.calendar.TimeRangeSelectedArgs;
import org.daypilot.ui.enums.UpdateType;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Date;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 *
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 17.01.2020
 */
public class AppuntamentiMedicoListener extends FactoryDayPilotListener {

    private MedicoBase mb;
    private MedicoSpecialista ms;
    private MedicoBaseDAO medicoBaseDAO;
    private MedicoSpecialistaDAO medicoSpecialistaDAO;
    private boolean medicoBase = false;
    private boolean medicoSpecialista = false;

    @Override
    public void onPrepare() throws Exception {
        LOG(this,"prepare");
        config();
        setDataTextField("motivo");
        setDataTagFields("paziente,id_paziente");
        setDataResourceField("codice_fiscale");
        medicoBase=false;
        medicoSpecialista=false;
        mb=null;
        ms=null;
        try {
            DAOFactory daoFactory = (DAOFactory) getRequest().getServletContext().getAttribute(Attr.DAO_FACTORY);
            if (getRequest().getSession().getAttribute(Attr.USER) instanceof MedicoBase) {
                mb = (MedicoBase)getRequest().getSession().getAttribute(Attr.USER);
                medicoBaseDAO = daoFactory.getDAO(MedicoBaseDAO.class);
                medicoBase = true;
            }
            else if (getRequest().getSession().getAttribute(Attr.USER) instanceof MedicoSpecialista) {
                ms = (MedicoSpecialista)getRequest().getSession().getAttribute(Attr.USER);
                medicoSpecialistaDAO = daoFactory.getDAO(MedicoSpecialistaDAO.class);
                medicoSpecialista = true;
            }
            else {
                getResponse().sendError(HttpServletResponse.SC_NOT_FOUND); // page not allowed
            }
        } catch (DAOFactoryException ex) {
            LOG(this,ex);
        }
        finally {
            if(medicoBase || medicoSpecialista) {
                setEvents(getAppuntamenti());
                update(UpdateType.FULL);
            }
        }
    }

    @Override
    public void onFinish() throws Exception {
        super.onFinish();
    }

    @Override
    public Table getAppuntamenti() throws Exception {
        Table table = null;
        Date from = getStartDate().toDate();
        Date to = getStartDate().addDays(getDays()).toDate();

        if(medicoBase) {
            table = ((JDBCMedicoBaseDAO)medicoBaseDAO).getAppuntamenti(mb.getId(),from,to);
        }
        else if(medicoSpecialista) {
            table = ((JDBCMedicoSpecialistaDAO)medicoSpecialistaDAO).getAppuntamenti(ms.getId(),from,to);
        }

        return table;
    }

    @Override
    public void onTimeRangeSelected(TimeRangeSelectedArgs ea) throws Exception {
        LOG(this,"time range selected from " + ea.getStart() + " to " + ea.getEnd());
        try {
            boolean response;
            Date start = ea.getStart().toDate();
            Date end = ea.getEnd().toDate();
            if(medicoBase){
                response = ((JDBCMedicoBaseDAO)medicoBaseDAO).creaAppuntamentoVuoto(mb.getId(),start,end);
            }
            else if(medicoSpecialista){
                response = ((JDBCMedicoSpecialistaDAO)medicoSpecialistaDAO).creaAppuntamentoVuoto(ms.getId(),start,end);
            }
            else {
                response = false;
                getResponse().sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
            }
            if(!response){
                updateWithMessage("Impossibile creare l'appuntamento!",UpdateType.NONE);
            } else {
                updateWithMessage("Nuovo appuntamento creato con successo", UpdateType.FULL);
            }
            LOG(this,"appuntamento creato: " + response);
        }
        catch (DAOException | NumberFormatException ex) {
            LOG(this,ex);
            if(ex instanceof NumberFormatException)
                getResponse().sendError(422); // invalid input
            else
                getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void onEventMove(EventMoveArgs ea) throws Exception {
        LOG(this,"event move from " + ea.getStart() + " to " + ea.getEnd());
        try {
            boolean response;
            Timestamp start = ea.getNewStart().toTimeStamp();
            Timestamp end = ea.getNewEnd().toTimeStamp();
            Integer idAppuntamento = Integer.parseInt(ea.getId());
            if(medicoBase){
                response = ((JDBCMedicoBaseDAO)medicoBaseDAO).aggiornaOrarioAppuntamento(mb.getId(),idAppuntamento,start,end);
            }
            else if(medicoSpecialista){
                response = ((JDBCMedicoSpecialistaDAO)medicoSpecialistaDAO).aggiornaOrarioAppuntamento(ms.getId(),idAppuntamento,start,end);
            }
            else {
                response = false;
                getResponse().sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
            }
            if(!response){
                updateWithMessage("Impossibile aggiornare l'orario!",UpdateType.NONE);
            } else {
                updateWithMessage("Orario aggiornato.",UpdateType.FULL);
            }
            LOG(this,"moved: " + response);
        } catch (DAOException | NumberFormatException ex) {
            LOG(this,ex);
            if(ex instanceof NumberFormatException)
                getResponse().sendError(422); // invalid input
            else
                getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void onEventResize(EventResizeArgs ea) throws Exception {
        LOG(this,"event resize");
        try {
            boolean response;
            Timestamp start = ea.getNewStart().toTimeStamp();
            Timestamp end = ea.getNewEnd().toTimeStamp();
            Integer idAppuntamento = Integer.parseInt(ea.getId());
            if(medicoBase){
                response = ((JDBCMedicoBaseDAO)medicoBaseDAO).aggiornaOrarioAppuntamento(mb.getId(),idAppuntamento,start,end);
            }
            else if(medicoSpecialista){
                response = ((JDBCMedicoSpecialistaDAO)medicoSpecialistaDAO).aggiornaOrarioAppuntamento(ms.getId(),idAppuntamento,start,end);
            }
            else {
                response = false;
                getResponse().sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
            }
            if(!response){
                updateWithMessage("Impossibile aggiornare l'orario!",UpdateType.NONE);
            } else {
                updateWithMessage("Orario aggiornato.", UpdateType.FULL);
            }
            LOG(this,"resized: " + response);
        } catch (DAOException | NumberFormatException ex) {
            LOG(this,ex);
            if(ex instanceof NumberFormatException)
                getResponse().sendError(422); // invalid input
            else
                getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        update(UpdateType.FULL);
    }

    @Override
    public void onCommand(CommandArgs ea) throws Exception {
        super.onCommand(ea); // per controllare il date picker
        try {
            boolean response;
            if (ea.getCommand().equals("delete")) {
                Integer event_id = Integer.parseInt(ea.getData().getString("idAppuntamento"));
                if (medicoBase){
                    response = ((JDBCMedicoBaseDAO)medicoBaseDAO).cancellaAppuntamento(event_id);
                }
                else if (medicoSpecialista){
                    response = ((JDBCMedicoSpecialistaDAO)medicoSpecialistaDAO).cancellaAppuntamento(event_id);
                }
                else {
                    response = false;
                    getResponse().sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                }
                if(!response){
                    updateWithMessage("Impossibile cancellare l'appuntamento!",UpdateType.NONE);
                } else {
                    updateWithMessage("Appuntamento cancellato.", UpdateType.FULL);
                }
                LOG(this,"deleted: " + response);
            }
            else if (ea.getCommand().equals("update")) {
                Integer idAppuntamento = Integer.parseInt(ea.getData().getString("idAppuntamento"));
                Integer idPaziente = Integer.parseInt(ea.getData().getString("idPaziente"));
                if(medicoBase){
                    String motivo = ea.getData().getString("motivo");
                    response = ((JDBCMedicoBaseDAO)medicoBaseDAO).aggiornaAppuntamento(
                       mb.getId(),idAppuntamento,idPaziente,motivo
                   );
                }
                else if(medicoSpecialista){
                    String esame = ea.getData().getString("esame");
                    response = ((JDBCMedicoSpecialistaDAO)medicoSpecialistaDAO).aggiornaAppuntamento(
                        ms.getId(),idAppuntamento,idPaziente,esame
                    );
                }
                else {
                    response = false;
                    getResponse().sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                }
                if(!response){
                    updateWithMessage("Impossibile aggiornare l'appuntamento!",UpdateType.NONE);
                } else {
                    updateWithMessage("Appuntamento aggiornato.", UpdateType.FULL);
                }
                LOG(this,"updated: " + response);
            }
            else if (ea.getCommand().equals("edit")) {
                Integer idAppuntamento = Integer.parseInt(ea.getData().getString("idAppuntamento"));
                String motivo = ea.getData().getString("motivo");
                if(medicoBase){
                    response = ((JDBCMedicoBaseDAO)medicoBaseDAO).aggiornaMotivo(mb.getId(),idAppuntamento,motivo);
                }
                else {
                    response = false;
                    getResponse().sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                }
                if(!response){
                    updateWithMessage("Impossibile modificare l'appuntamento!",UpdateType.NONE);
                } else {
                    updateWithMessage("Appuntamento modificato.", UpdateType.FULL);
                }
                LOG(this,"edited: " + response);
            }
        }
        catch (DAOException | NumberFormatException ex) {
            LOG(this,ex);
            if(ex instanceof NumberFormatException)
                getResponse().sendError(422); // invalid input
            else
                getResponse().sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
