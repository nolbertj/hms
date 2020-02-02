/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao.jdbc;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.commons.persistence.dao.jdbc.JDBCDAOCustom;
import it.unitn.disi.wp.project.hms.persistence.dao.UserDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC implementation of {@link UserDAO} interface.
 * 
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 30.09.2019
 */
public class JDBCUserDAO extends JDBCDAOCustom<User, String> implements UserDAO {

    public JDBCUserDAO(Connection con){
        super(con);
    }


    @Override
    public User getByPrimaryKey(String email) throws DAOException {

        User user = null;

        if (email == null) {
            throw new DAOException("L'email è richiesta!", new NullPointerException("email = null"));
        }

        try (PreparedStatement stm = CON.prepareStatement("SELECT * FROM utente WHERE email = ?")) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if(rs.next()) {
                    user = new User();
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setRuolo(rs.getInt("ruolo"));
                    user.setAvatarFilename(rs.getString("avatar_filename"));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile ritornare l'utente con l'email passata come argomento", ex);
        }

        return user;
    }

    @Override
    public boolean updatePassword(User user) throws DAOException {
        if(user==null){
            throw new DAOException(new NullPointerException("La classe User passata come parametro è null!"));
        }
        try (PreparedStatement stm = CON.prepareStatement("UPDATE utente SET password = ? WHERE email = ?")) {
            stm.setString(1, user.getPassword());
            stm.setString(2, user.getEmail());
            if(stm.executeUpdate() >0) return true;
            else return false;
        }
        catch (SQLException ex) {
            throw new DAOException("Impossibile aggiornare la password dell'utente", ex);
        }
    }

    @Override
    public User getByEmail(String email) throws DAOException {
        return getByPrimaryKey(email);
    }

}
