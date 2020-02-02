/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao.jdbc;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.commons.persistence.entities.Column;
import it.unitn.disi.wp.project.hms.commons.persistence.entities.SimpleTable;
import it.unitn.disi.wp.project.hms.commons.persistence.queries.AdminQuery;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.AdminDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of {@link AdminDAO} interface.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 30.11.2019
 */
public class JDBCAdminDAO extends JDBCUserDAO implements AdminDAO {

    private final AdminQuery QUERY;

    public JDBCAdminDAO(Connection con) {
        super(con);
        QUERY = new AdminQuery();
    }

    @Override
    public Admin getByEmail(String email) throws DAOException {
        if (email == null) throw new DAOException("email is null");
        Admin admin = null;
        try (PreparedStatement stm = CON.prepareStatement(QUERY.GET_ADMIN)) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if(rs.next()){
                    admin = new Admin();
                    admin.setEmail(email);
                    admin.setAvatarFilename(rs.getString("avatar_filename"));
                    admin.setRuolo(rs.getInt("ruolo"));
                }
            }
        }
        catch (SQLException ex) {
            throw new DAOException("Impossibile ricevere il paziente tramite la primary key data", ex);
        }
        return admin;
    }

    @Override
    public List<SimpleTable> getTables() throws DAOException {
        List<SimpleTable> tables = null;
        try(PreparedStatement pstm = CON.prepareStatement(QUERY.GET_TABLES)) {

            try(ResultSet rs = pstm.executeQuery()) {
                tables = new ArrayList<>();
                while(rs.next()) {
                    SimpleTable table = new SimpleTable();
                    table.setName(rs.getString("table_name"));
                    tables.add(table);
                }
            }
        }
        catch(SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(),sqlex);
        }
        return tables;
    }

    @Override
    public List<Column> getColumns(String tableName) throws DAOException  {
        List<Column> columns = null;
        try(PreparedStatement pstm = CON.prepareStatement(QUERY.GET_COLUMNS)) {

            pstm.setString(1,tableName);
            try(ResultSet rs = pstm.executeQuery()) {
                columns = new ArrayList<>();
                while(rs.next()) {
                    Column column = new Column();
                    column.setName(rs.getString("column_name"));
                    columns.add(column);
                }
            }
        }
        catch(SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(),sqlex);
        }
        return columns;
    }

    @Override
    public Object executeQuery(String query) throws DAOException {
        if(Utils.isNullOrEmpty(query))
            return null;
        try {
            Statement stm = CON.createStatement();
            return  stm.executeQuery(query);
        }
        catch(SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(), sqlex);
        }

    }

}
