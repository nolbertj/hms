/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.commons.persistence.entities.Table;

import java.sql.Timestamp;
import java.util.Date;

/**
 * DAO interface that handle 'appuntamento' table
 *
 * @author Nolbert Juarez &lt; nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 22.01.2020
 */
public interface AppuntamentoDAO {
    Table getAppuntamenti(Integer idMedico, Date from, Date to) throws DAOException;
    boolean cancellaAppuntamento(Integer idAppuntamento) throws DAOException;
    boolean creaAppuntamentoVuoto(Integer idMedico, Date start, Date end) throws DAOException;
    boolean aggiornaOrarioAppuntamento(Integer idMedico, Integer idAppuntamento, Timestamp start, Timestamp end) throws DAOException;
    boolean aggiornaMotivo(Integer idMedico, Integer idAppuntamento, String motivo) throws DAOException;
    boolean aggiornaAppuntamento(Integer idMedico, Integer idAppuntamento, Integer idPaziente, String motivo) throws DAOException;
}
