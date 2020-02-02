/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.persistence.factories;

import it.unitn.disi.wp.project.hms.commons.persistence.entities.Table;
import org.daypilot.date.DateTime;
import org.daypilot.date.Week;
import org.daypilot.ui.DayPilotCalendar;
import org.daypilot.ui.args.calendar.CommandArgs;
import org.daypilot.ui.enums.UpdateType;
import org.daypilot.ui.enums.ViewType;

import static it.unitn.disi.wp.project.hms.commons.utils.CustomLogger.LOG;

/**
 * Custom Factory Listener class that must be subclassed by listeners
 * that would process {@link DayPilotCalendar} events.
 * This Factory class works only with table 'Appuntamenti'.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarez at studenti dot unitn dot it&gt;
 * @since 17.01.2020
 */
public abstract class FactoryDayPilotListener extends DayPilotCalendar {

    protected void config() {
        setHeaderDateFormat("d MMMM yyyy");
        setDataValueField("id");
        setDataStartField("timestamp_start");
        setDataEndField("timestamp_end");
    }

    @Override
    public void onFinish() throws Exception {
        LOG(this,"finish");
        if (getUpdateType() != UpdateType.NONE)
            setEvents(getAppuntamenti());
    }

    public abstract Table getAppuntamenti() throws Exception;

    @Override
    public void onCommand(CommandArgs ea) throws Exception {
        LOG(this,"event command: " + ea.getCommand());

        if ("navigate".equalsIgnoreCase(ea.getCommand())) {
            LOG(this,"navigate");
            DateTime start = DateTime.parseString(ea.getData().getString("start"));

            start = Week.firstDayOfWeek(start, getFirstDayOfWeek());

            setStartDate(start);
            update(UpdateType.FULL);
        }
        else if ("day".equalsIgnoreCase(ea.getCommand())) {
            LOG(this,"day");
            setViewType(ViewType.DAY);
            update(UpdateType.FULL);
        }
        else if ("week".equalsIgnoreCase(ea.getCommand())) {
            LOG(this,"week");
            setViewType(ViewType.WEEK);
            update(UpdateType.FULL);
        }
        else if ("previous".equalsIgnoreCase(ea.getCommand())) {
            LOG(this,getViewType().toString());
            switch (getViewType()) {
                case DAY:
                    setStartDate(getStartDate().addDays(-1));
                    break;
                case WEEK:
                    setStartDate(getStartDate().addDays(-7));
                    break;
            }
            update(UpdateType.FULL);
        }
        else if ("next".equalsIgnoreCase(ea.getCommand())) {
            LOG(this,getViewType().toString());
            switch (getViewType()) {
                case DAY:
                    setStartDate(getStartDate().addDays(1));
                    break;
                case WEEK:
                    setStartDate(getStartDate().addDays(7));
                    break;
            }
            update(UpdateType.FULL);

        }
        else if ("today".equalsIgnoreCase(ea.getCommand())) {
            LOG(this,"today");
            setStartDate(DateTime.getToday());
            update(UpdateType.FULL);
        }

    }

}