/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.commons.persistence.entities.Column;
import it.unitn.disi.wp.project.hms.commons.persistence.entities.SimpleTable;
import it.unitn.disi.wp.project.hms.persistence.entities.Admin;

import java.util.List;

/**
 * DAO interface for {@link Admin}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 29.1.2019
 */
public interface AdminDAO extends UserDAO {
    List<SimpleTable> getTables() throws DAOException;
    List<Column> getColumns(String tableName) throws DAOException;
    Object executeQuery(String query) throws DAOException;
}
