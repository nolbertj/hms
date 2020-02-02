/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao.jdbc;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.commons.persistence.dao.jdbc.JDBCDAOCustom;
import it.unitn.disi.wp.project.hms.commons.persistence.queries.DocumentoQuery;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.DocumentoDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Documento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of {@link DocumentoDAO} interface.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 24.11.2019
 */
public class JDBCDocumentoDAO extends JDBCDAOCustom<Documento, Integer> implements DocumentoDAO {

    private final DocumentoQuery QUERY;
    public JDBCDocumentoDAO(Connection con) {
        super(con);
        QUERY=new DocumentoQuery();
    }


    @Override
    public List<Documento> getAll(String emailProprietario) throws DAOException {
        if(emailProprietario==null)
            throw new DAOException(new NullPointerException("emailProprietario è null"));

        List<Documento> documenti = null;

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.GET_BY_EMAIL)) {
            pstm.setString(1,emailProprietario);
            try(ResultSet rs = pstm.executeQuery()) {
                documenti = new ArrayList<>();
                while(rs.next()){
                    Documento doc = new Documento();
                    doc.setEmailProprietario(emailProprietario);
                    doc.setTitolo(rs.getString("titolo"));
                    doc.setDescrizione(rs.getString("descrizione"));
                    doc.setDataCaricamento(rs.getTimestamp("data_caricamento"));
                    doc.setFilename(rs.getString("filename"));
                    documenti.add(doc);
                }
            }
        }
        catch (SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(), sqlex);
        }

        return documenti;
    }

    @Override
    public Boolean delete(String emailProprietario, String filename) throws DAOException {
        if(emailProprietario==null || Utils.isNullOrEmpty(filename))
            throw new DAOException(new NullPointerException("emailProprietario o filename è null"));

        Boolean deleted = null;
        try(PreparedStatement pstm = CON.prepareStatement(QUERY.DELETE_DOCUMENTO)) {
            pstm.setString(1,emailProprietario);
            pstm.setString(2,filename);
            int rowsAffected = pstm.executeUpdate();
            deleted = (rowsAffected>0 ? true:false);
        } catch(SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(),sqlex);
        }
        return deleted;
    }

    @Override
    public Boolean update(Documento documento) throws DAOException {
        if(documento==null || documento.getEmailProprietario()==null || documento.getFilename()==null)
            throw new DAOException(new NullPointerException("uno degli argomenti è null"));

        Boolean updated;
        PreparedStatement pstm = null;
        String startQuery = "UPDATE documento SET ",
               endQuery = " WHERE email_proprietario=? AND filename=?";

        try {
            if(documento.getTitolo()!=null && documento.getDescrizione()==null) {
                pstm = CON.prepareStatement(startQuery + "titolo=?" + endQuery);
                pstm.setString(1,documento.getTitolo());
                pstm.setString(2,documento.getEmailProprietario());
                pstm.setString(3,documento.getFilename());
            }
            else if(documento.getTitolo()!=null && documento.getDescrizione()!=null){
                pstm = CON.prepareStatement(startQuery + "titolo=?,descrizione=?" + endQuery);
                pstm.setString(1,documento.getTitolo());
                pstm.setString(2,documento.getDescrizione());
                pstm.setString(3,documento.getEmailProprietario());
                pstm.setString(4,documento.getFilename());
            }
            else if(documento.getTitolo()==null && documento.getDescrizione()!=null){
                pstm = CON.prepareStatement(startQuery + "descrizione=?" + endQuery);
                pstm.setString(1,documento.getDescrizione());
                pstm.setString(2,documento.getEmailProprietario());
                pstm.setString(3,documento.getFilename());
            }
            else {
              return true;
            }

            int rowsAffected = pstm.executeUpdate();
            updated = (rowsAffected>0 ? true:false);

            pstm.close();
        }
        catch(SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(),sqlex);
        }
        return updated;
    }

    @Override
    public Boolean create(Documento documento) throws DAOException {
        if(documento==null || documento.getEmailProprietario()==null || documento.getFilename()==null)
            throw new DAOException(new NullPointerException("uno degli argomenti è null"));

        Boolean added;

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.CREA_DOCUMENTO)) {
            pstm.setString(1,documento.getEmailProprietario());
            pstm.setString(2,documento.getTitolo());
            pstm.setTimestamp(3, documento.getDataCaricamento());
            pstm.setString(4,documento.getDescrizione());
            pstm.setString(5,documento.getFilename());

            int rowsAffected = pstm.executeUpdate();
            added = (rowsAffected>0 ? true:false);
        }
        catch (SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(),sqlex);
        }
        return added;
    }

    @Override
    public Documento getByPrimaryKeys(String emailProprietario, String filename) throws DAOException {
        if(emailProprietario==null || filename==null)
            throw new DAOException(new NullPointerException("uno degli argomenti è null"));

        Documento documento = null;

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.GET_BY_PRIMARY_KEY)) {
           pstm.setString(1,emailProprietario);
           pstm.setString(2,filename);
           try(ResultSet rs = pstm.executeQuery()) {
               if(rs.next()) {
                   documento = new Documento();
                   documento.setEmailProprietario(emailProprietario);
                   documento.setDataCaricamento(rs.getTimestamp("data_caricamento"));
                   documento.setTitolo(rs.getString("titolo"));
                   documento.setDescrizione(rs.getString("descrizione"));
                   documento.setFilename(filename);
               }
           }
        } catch(SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(),sqlex);
        }

        return documento;
    }
}
