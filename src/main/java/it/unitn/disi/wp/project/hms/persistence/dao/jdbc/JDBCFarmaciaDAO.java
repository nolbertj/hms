/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao.jdbc;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.commons.persistence.queries.FarmaciaQuery;
import it.unitn.disi.wp.project.hms.persistence.dao.FarmaciaDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Farmacia;

import java.sql.*;
import java.util.Date;

/**
 * JDBC implementation of {@link FarmaciaDAO} interface.
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 22.01.2020
 */
public class JDBCFarmaciaDAO extends JDBCUserDAO implements FarmaciaDAO {

    private final FarmaciaQuery QUERY;
    public JDBCFarmaciaDAO(Connection con) {
        super(con);
        QUERY=new FarmaciaQuery();
    }

    @Override
    public Farmacia getByEmail(String email) throws DAOException {
        Farmacia farmacia = null;

        if (email == null) {
            throw new DAOException("L'email è richiesta!", new NullPointerException("email = null"));
        }

        try (PreparedStatement stm = CON.prepareStatement(QUERY.GET_BY_EMAIL)) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if(rs.next()) {
                    farmacia = new Farmacia();
                    farmacia.setEmail(rs.getString("email"));
                    farmacia.setRuolo(rs.getInt("ruolo"));
                    farmacia.setAvatarFilename(rs.getString("avatar_filename"));
                    farmacia.setId(rs.getInt("id"));
                    farmacia.setNome(rs.getString("nome"));
                    farmacia.setPartitaIva(rs.getString("partita_iva"));
                    farmacia.setCitta(rs.getString("citta"));
                    farmacia.setProvincia(rs.getString("provincia"));
                    farmacia.setContattoTelefonico(rs.getString("numero_telefono"));
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile ritornare l'utente con l'email passata come argomento", ex);
        }

        return farmacia;
    }

    @Override
    public boolean erogaRicetta(Integer idRicetta, Integer idFarmacia) throws DAOException {
        if(idRicetta==null || idFarmacia==null)
            throw new DAOException(new NullPointerException());

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.EROGA_RICETTA)) {
            pstm.setInt(1,idRicetta);
            pstm.setInt(2,idFarmacia);
            pstm.setTimestamp(3,new Timestamp(new Date().getTime()));
            pstm.setInt(4, idRicetta);
            if (pstm.executeUpdate() >0){
                return true;
            }
        }
        catch (SQLException sqlex) {
            throw new DAOException("Impossibile erogare la ricetta",sqlex);
        }
        return false;
    }
}
