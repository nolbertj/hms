/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao.jdbc;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.commons.persistence.dao.jdbc.JDBCDAOCustom;
import it.unitn.disi.wp.project.hms.commons.persistence.queries.OpenQuery;
import it.unitn.disi.wp.project.hms.persistence.dao.OpenDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.Esame;
import it.unitn.disi.wp.project.hms.persistence.entities.Farmaco;
import it.unitn.disi.wp.project.hms.persistence.utils.PerGenere;
import it.unitn.disi.wp.project.hms.persistence.utils.PerGenere.PER_GENERE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of {@link OpenDAO} interface.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 4.11.2019
 */
public class JDBCOpenDAO extends JDBCDAOCustom<Object, Integer> implements OpenDAO {

    private final OpenQuery QUERY;
    public JDBCOpenDAO(Connection con){
        super(con);
        QUERY=new OpenQuery();
    }

    @Override
    public Long getCountEsamiPrescrivibili(PER_GENERE genere, String searchValue) throws DAOException {
        if(genere == null)
            throw new DAOException(new NullPointerException());

        Long counter = null;

        try(PreparedStatement stm = CON.prepareStatement(QUERY.COUNT_ESAMI_PRESCRIVIBILI)) {
            stm.setString(1, genere.getChar());
            stm.setString(2, PER_GENERE.ALL.getChar());
            stm.setString(3, "%"+searchValue.toLowerCase()+"%");
            stm.setString(4, "%"+searchValue.toLowerCase()+"%");
            stm.setString(5, "%"+searchValue.toLowerCase()+"%");
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                counter = rs.getLong(1);
            }
            rs.close();
            return counter;
        } catch (SQLException e) {
            throw new DAOException("Impossibile contare gli esami", e);
        }
    }

    @Override
    public List<Esame> pageEsamiPrescrivibiliBySearchValue(String searchValue, PER_GENERE genere, Long start, Long length, Integer column, String direction) throws DAOException {

        column+=1; //perchè nelle datatable la prima colonna corrisponde allo 0, in sql a 1.

        String query = String.format( QUERY.GET_ESAMI_PRESCRIVIBILI +
                " ORDER BY %d " + direction + " " +
                "OFFSET %d ROWS FETCH NEXT %d ROWS ONLY ",
                column, start, length);

        try (PreparedStatement stm = CON.prepareStatement(query)) {
            stm.setString(1, genere.getChar());
            stm.setString(2, PER_GENERE.ALL.getChar());
            stm.setString(3, "%"+searchValue.toLowerCase()+"%");
            stm.setString(4, "%"+searchValue.toLowerCase()+"%");
            stm.setString(5, "%"+searchValue.toLowerCase()+"%");

            ResultSet rs = stm.executeQuery();

            List<Esame> esami = new ArrayList<>();
            while (rs.next()) {
               Esame esame = new Esame();

               esame.setCodice(rs.getInt("codice"));
               esame.setNome(rs.getString("nome"));
               esame.setArea(rs.getString("area"));
               esame.setDescrizione(rs.getString("descrizione"));
               esame.setPrezzo(rs.getDouble("prezzo"));

               esami.add(esame);
            }

            rs.close();
            return esami;

        } catch (SQLException ex) {
            throw new DAOException("impossibile ritornare gli esami. Possible causa: prezzo non leggibile o sesso inesistente.");
        }
    }

    @Override
    public List<Esame> searchEsame(String searchValue) throws DAOException {
        if(searchValue==null)
            return null;

        List<Esame> esami;
        try (PreparedStatement pstm = CON.prepareStatement(QUERY.CERCA_ESAME_PRESCRIVIBILE)) {
            for(int i=1; i<=3; i++) {
                pstm.setString(i,'%' + searchValue.toLowerCase() + '%');
            }
            try (ResultSet rs = pstm.executeQuery()) {
                esami = new ArrayList<>();
                while(rs.next()) {
                    Esame e = new Esame();
                    e.setCodice(rs.getInt("codice"));
                    e.setNome(rs.getString("nome"));
                    e.setArea(rs.getString("area"));
                    e.setPrezzo(rs.getDouble("prezzo"));
                    e.setPerGenere(PerGenere.getByCharacter(rs.getString("for_sesso")));
                    esami.add(e);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("impossibile ritornare gli esami.");
        }
        return esami;
    }


    @Override
    public Long getCountFarmaci(String searchValue) throws DAOException {
        Long conteggio = 0L;

        try(PreparedStatement ps=CON.prepareStatement(QUERY.COUNT_FARMACI)){
            for(int i=1; i<=4; i++) {
                ps.setString(i,"%"+searchValue.toLowerCase()+"%");
            }
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    conteggio=rs.getLong("conteggio_farmaci");
                }
            }
        }catch(SQLException e){
            throw new DAOException("Non ci sono faramci", e);
        }

        return conteggio;
    }

    @Override
    public List<Farmaco> pageFarmaciBySearchValue(String searchValue, Long start, Long length, Integer column, String direction) throws DAOException {

        //+2 perché in frontend non c'è id
        String query = String.format(QUERY.GET_FARMACI +" ORDER BY %d "+  direction+ " OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", (column+2),start,length);

        List<Farmaco> farmaci=new ArrayList<>();
        try(PreparedStatement stm=CON.prepareStatement(query)) {
            for(int i=1; i<=4; i++) {
                stm.setString(i,"%"+searchValue.toLowerCase()+"%");
            }
            ResultSet rs=stm.executeQuery();
            while(rs.next()){
                Farmaco f=new Farmaco();
                f.setCodice(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setPrezzo(rs.getDouble("prezzo"));
                f.setDescrizione(rs.getString("descrizione"));

                farmaci.add(f);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return farmaci;
    }

    @Override
    public List<Farmaco> searchFarmaco(String searchValue) throws DAOException {
        if(searchValue==null)
            return null;

        List<Farmaco> farmaci;
        try (PreparedStatement pstm = CON.prepareStatement(QUERY.CERCA_FARMACO)) {
            for(int i=1; i<=2; i++) {
                pstm.setString(i,'%' + searchValue.toLowerCase() + '%');
            }
            try (ResultSet rs = pstm.executeQuery()) {
                farmaci = new ArrayList<>();
                while(rs.next()) {
                    Farmaco f=new Farmaco();
                    f.setCodice(rs.getInt("id"));
                    f.setNome(rs.getString("nome"));
                    f.setDescrizione(rs.getString("descrizione"));
                    farmaci.add(f);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException("impossibile ritornare i farmaci.");
        }
        return farmaci;
    }

}
