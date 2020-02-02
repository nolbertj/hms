/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.persistence.dao.jdbc;

import it.unitn.disi.wp.lab.commons.persistence.dao.DAO;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.jdbc.JDBCDAO;

import java.sql.Connection;
import java.util.List;

/**
 * Custom JDBC abstract class that overrides some methods in {@link JDBCDAO}.
 * In this way the developer can choose if implement that methods (overriding it another time) or not.
 *
 * @param <ENTITY_CLASS> the class of the entity the {@link DAO} handle.
 * @param <PRIMARY_KEY_CLASS> the class of the primary key of the entity the {@link DAO} handle.
 *
 * @see JDBCDAO
 * @see DAO
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 28.11.2019
 */
public abstract class JDBCDAOCustom<ENTITY_CLASS, PRIMARY_KEY_CLASS> extends JDBCDAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> {

    protected JDBCDAOCustom(Connection con) {
        super(con);
    }

    @Override
    public Long getCount() throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<ENTITY_CLASS> getAll() throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ENTITY_CLASS getByPrimaryKey(PRIMARY_KEY_CLASS primaryKey) throws DAOException {
        throw new UnsupportedOperationException();
    }

}
