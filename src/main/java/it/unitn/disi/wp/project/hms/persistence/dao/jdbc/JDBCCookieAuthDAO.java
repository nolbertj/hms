package it.unitn.disi.wp.project.hms.persistence.dao.jdbc;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.commons.persistence.dao.jdbc.JDBCDAOCustom;
import it.unitn.disi.wp.project.hms.commons.persistence.queries.CookieQuery;
import it.unitn.disi.wp.project.hms.persistence.dao.CookieAuthDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.CookieAutenticazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC implementation of {@link CookieAuthDAO} interface.
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since
 */
public class JDBCCookieAuthDAO extends JDBCDAOCustom<CookieAutenticazione, Integer> implements CookieAuthDAO {

    private final CookieQuery QUERY;
    public JDBCCookieAuthDAO(Connection con){
        super(con);
        QUERY=new CookieQuery();
    }

    @Override
    public CookieAutenticazione findBySelector(String selector) throws DAOException {
        if(selector==null){
            throw new DAOException(new NullPointerException());
        }

        CookieAutenticazione cookie=null;
        try(PreparedStatement stm=CON.prepareStatement(QUERY.GET_BY_SELECTOR)) {
            stm.setString(1,selector);
            ResultSet rs=stm.executeQuery();
            if(rs.next()){
                cookie=new CookieAutenticazione();
                cookie.setId(rs.getLong("id"));
                cookie.setSelector(rs.getString("selector"));
                cookie.setValidator(rs.getString("validator"));
                cookie.setUserMail(rs.getString("user_mail"));
            }

            return cookie;

        } catch (SQLException e) {
            throw new DAOException("Impossibile ritornare lista dei cookie: ", e);
        }
    }

    @Override
    public boolean updateValidator(String selector, String validator, String userMail) throws DAOException{
        if (selector==null || validator==null){
            throw new DAOException(new NullPointerException());
        }

        try(PreparedStatement stm=CON.prepareStatement(QUERY.UPDATE_VALIDATOR)){
            stm.setString(1, validator);
            stm.setString(2, selector);
            stm.setString(3, userMail);
            if(stm.executeUpdate() >0){
                return true;
            }
            else return false;
        } catch (SQLException e) {
            throw new DAOException("Errore nella creazione del token per i cookie: ", e);
        }
    }

    @Override
    public boolean insertToken(CookieAutenticazione cookie) throws DAOException {
        if (cookie==null){
            throw new DAOException(new NullPointerException());
        }

        try(PreparedStatement stm1=CON.prepareStatement(QUERY.INSERT_TOKEN)) {
            stm1.setString(1, cookie.getSelector());
            stm1.setString(2, cookie.getValidator());
            stm1.setString(3, cookie.getUserMail());
            if(stm1.executeUpdate() >0) return true;
            else return false;
        }catch (SQLException e) {
            throw new DAOException("Errore nella creazione del token per i cookie: ", e);
        }
    }

    @Override
    public boolean deleteToken(String selector) throws DAOException {
        if (selector==null){
            throw new DAOException(new NullPointerException());
        }

        try(PreparedStatement stm1=CON.prepareStatement(QUERY.DELETE_TOKEN)) {
            stm1.setString(1, selector);
            if(stm1.executeUpdate() >0) return true;
            else return false;
        }catch (SQLException e) {
            throw new DAOException("Errore nella creazione del token per i cookie: ", e);
        }
    }

}
