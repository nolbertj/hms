/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao.jdbc;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.commons.persistence.queries.SspQuery;
import it.unitn.disi.wp.project.hms.persistence.dao.SspDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of {@link SspDAO} interface.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 4.11.2019
 */
public class JDBCSspDAO extends JDBCUserDAO implements SspDAO {

    private final SspQuery QUERY;
    public JDBCSspDAO(Connection con){
        super(con);
        QUERY=new SspQuery();
    }

    @Override
    public Ssp getByEmail(String email) throws DAOException {

        Ssp ssp = null;

        if(email == null){
            throw new DAOException("email is null");
        }

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.GET_BY_EMAIL)){
            pstm.setString(1,email);
            try(ResultSet rs = pstm.executeQuery()) {
                if(rs.next()) {
                    ssp=new Ssp();
                    ssp.setId(rs.getInt("id"));
                    ssp.setNome(rs.getString("nome"));
                    ssp.setEmail(rs.getString("email"));
                    ssp.setNumeroTelefono(rs.getString("numero_telefono"));
                    ssp.setAbbreviazione(rs.getString("abbreviazione") + " " + rs.getString("sigla_provincia"));
                    ssp.setProvincia(rs.getString("nome_provincia"));
                    ssp.setNumeroPazienti(rs.getInt("numero_pazienti"));
                    ssp.setNumeroMediciBase(rs.getInt("numero_mb"));
                    ssp.setNumeroMediciSpecialisti(rs.getInt("numero_ms"));
                    ssp.setRuolo(rs.getInt("ruolo"));
                    ssp.setAvatarFilename(rs.getString("avatar_path"));
                }
                return ssp;
            }
        }
        catch(SQLException sqlex) {
            throw new DAOException("Impossibile ricevere il medico di base tramite l'email data", sqlex);
        }

    }

    @Override
    public Ssp getByPrimaryKey(Integer idSSP) throws DAOException {
        if (idSSP == null) {
            throw new DAOException("primaryKey is null");
        }

        try (PreparedStatement stm = CON.prepareStatement(QUERY.GET_BY_PK)) {
            stm.setInt(1, idSSP);
            try (ResultSet rs = stm.executeQuery()) {
                Ssp ssp = null;
                if(rs.next()) {
                    ssp=new Ssp();
                    ssp.setId(rs.getInt("id"));
                    ssp.setNome(rs.getString("nome"));
                    ssp.setEmail(rs.getString("email"));
                    ssp.setNumeroTelefono(rs.getString("numero_telefono"));
                    ssp.setAbbreviazione(rs.getString("abbreviazione") + " " + rs.getString("sigla_provincia"));
                    ssp.setProvincia(rs.getString("nome_provincia"));
                    ssp.setNumeroPazienti(rs.getInt("numero_pazienti"));
                    ssp.setNumeroMediciBase(rs.getInt("numero_mb"));
                    ssp.setNumeroMediciSpecialisti(rs.getInt("numero_ms"));
                    ssp.setRuolo(rs.getInt("ruolo"));
                    ssp.setAvatarFilename(rs.getString("avatar_path"));
                }
                return ssp;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile ricevere il medico tramite la primary key data", ex);
        }
    }

    @Override
    public List<Ricetta> getRicetteErogateReport(Integer idSSP, Date date) throws DAOException {
        if(idSSP==null || date == null){
            throw new DAOException("id of ssp is null or date is null");
        }
        try(PreparedStatement pstm = CON.prepareStatement(QUERY.GENERA_REPORT_1)) {
            pstm.setInt(1, idSSP);
            pstm.setDate(2, date);

            ResultSet rs = pstm.executeQuery();

            List<Ricetta> ricette = new ArrayList<>();
            while(rs.next()) {
                Ricetta ricetta = new Ricetta();
                ricetta.setCodice(rs.getInt("id_ricetta"));
                ricetta.setDataErogazione(rs.getTimestamp("data_erogazione"));
                ricetta.setMedicoPrescrittore(rs.getString("medico_prescrivente"));
                ricetta.setFarmaciaErogante(rs.getString("nome_farmacia"));
                Paziente p=new Paziente();
                p.setNome(rs.getString("nome_paziente"));
                p.setCognome(rs.getString("cognome_paziente"));
                ricetta.setPaziente(p);
                try(PreparedStatement stm=CON.prepareStatement(QUERY.GENERA_REPORT_2)){
                    stm.setInt(1,ricetta.getCodice());
                    ResultSet rs1=stm.executeQuery();

                    List<FarmacoPrescritto> farmaci=new ArrayList<>();
                    while(rs1.next()){
                        FarmacoPrescritto f=new FarmacoPrescritto();
                        f.setCodice(rs1.getInt("id_farmaco"));
                        f.setNome(rs1.getString("nome"));
                        f.setPrezzo(rs1.getDouble("prezzo"));

                        farmaci.add(f);
                    }
                    rs1.close();

                    ricetta.setFarmaciPrescritti(farmaci);
                }

                ricette.add(ricetta);
            }
            rs.close();
            return ricette;
        }
        catch (SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(), sqlex);
        }
    }

    //restituisce i pazienti affetti da richiamo
    @Override
    public List<Paziente> prescriviRichiamo(Integer idSSP, Integer codiceEsame, Integer etaInizio, Integer etaFine) throws DAOException {
        if(idSSP == null || codiceEsame == null || etaFine == null || etaInizio == null){
            throw new DAOException("some parameters are null");
        }
        String sesso=null;
        List<Paziente> pazienti=null;
        try(PreparedStatement stm=CON.prepareStatement(QUERY.RICHIAMO_1)){
            stm.setInt(1, codiceEsame);
            try(ResultSet rs=stm.executeQuery()){
                if(rs.next()) {
                    sesso = rs.getString(1);
                    rs.close();
                    stm.close();
                    PreparedStatement stm1 = CON.prepareStatement(QUERY.RICHIAMO_4);
                    stm1.setInt(1, idSSP);
                    if (sesso.equals("A")) {
                        stm1.setString(2, "M");
                        stm1.setString(3, "F");
                    } else if (sesso.equals("F")) {
                        stm1.setString(2, "F");
                        stm1.setString(3, "F");
                    } else if (sesso.equals("M")) {
                        stm1.setString(2, "M");
                        stm1.setString(3, "M");
                    }
                    stm1.setInt(4, etaInizio);
                    stm1.setInt(5, etaFine);
                    ResultSet rs1 = stm1.executeQuery();
                    pazienti = new ArrayList<>();
                    while (rs1.next()) {
                        Paziente p=new Paziente();
                        p.setId(rs1.getInt("id"));
                        p.setNome(rs1.getString("nome"));
                        p.setCognome(rs1.getString("cognome"));
                        p.setEmail(rs1.getString("email"));
                        pazienti.add(p);
                    }
                    rs1.close();
                    stm1.close();
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        boolean flag=false;
        if(pazienti !=null && pazienti.size()>0){
            try {
                //mi servono insert in due tabelle e questi sono dipendenti: disattivo autocommit
                CON.setAutoCommit(false);
                try(PreparedStatement stm=CON.prepareStatement(QUERY.RICHIAMO_2)){
                    stm.setInt(1,idSSP);
                    Timestamp t=new Timestamp(new java.util.Date().getTime());
                    stm.setTimestamp(2,t);
                    stm.setInt(3, etaInizio);
                    stm.setInt(4, etaFine);
                    stm.setInt(5, codiceEsame);
                    stm.executeUpdate();
                    stm.close();
                    PreparedStatement stm1=CON.prepareStatement(QUERY.RICHIAMO_3);
                    for(Paziente p: pazienti){
                        stm1.setInt(1, idSSP);
                        stm1.setInt(2, p.getId());
                        stm1.setTimestamp(3, t);
                        stm1.setInt(4, codiceEsame);
                        stm1.executeUpdate();
                    }
                    stm1.close();
                    CON.commit();
                    flag=true;
                }

            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            }
        }
        try {
            CON.setAutoCommit(true);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        }
        if(flag){
            return pazienti;
        }

        return null;
    }

    @Override
    public Long getCountRichiami(Integer idSSP, String searchValue) throws DAOException {
        if(idSSP==null){
            throw new DAOException(new NullPointerException());
        }

        Long counter = null;

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.COUNT_RICHIAMI)) {
            pstm.setInt(1, idSSP);

            for(int i=2; i<=5; i++) {
                pstm.setString(i,"%"+searchValue.toLowerCase()+"%");
            }

            ResultSet rs = pstm.executeQuery();
            if(rs.next()){
                counter = rs.getLong(1);
            }
            rs.close();
            return counter;
        }
        catch (SQLException sqlex){
            throw new DAOException(sqlex.getMessage(), sqlex);
        }
    }

    @Override
    public List<EsameRichiamo> pageRichiamiBySearchValue(String searchValue, Integer idSSP, Long start, Long length, Integer column, String direction) throws DAOException {
        if(idSSP==null){
            throw new DAOException(new NullPointerException());
        }
        String query = String.format(QUERY.GET_RICHIAMI +" ORDER BY %d "+  direction+ " OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", (column+1),start,length);
        List<EsameRichiamo> richiami=new ArrayList<>();
        try(PreparedStatement stm=CON.prepareStatement(query)) {
            stm.setInt(1, idSSP);
            for(int i=2; i<=5; i++) {
                stm.setString(i,"%"+searchValue.toLowerCase()+"%");
            }
            try(ResultSet rs=stm.executeQuery()) {
                while (rs.next()) {
                    EsameRichiamo er = new EsameRichiamo();
                    er.setIdRichiamo(rs.getInt("id_richiamo"));
                    er.setCodice(rs.getInt("codice_esame"));
                    er.setDataPrescrizione(rs.getDate("richiesto_il"));
                    er.setNome(rs.getString("nome_esame"));
                    er.setArea(rs.getString("nome_area"));
                    er.setEtaInizio(rs.getInt("eta_inizio"));
                    er.setEtaFine(rs.getInt("eta_fine"));
                    richiami.add(er);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return richiami;
    }

    @Override
    public boolean generaReferto(Referto referto, Integer idPrescrizione, Integer idPaziente, Integer idSsp, Date dataPagamento, Integer metodo) throws DAOException {
        if(referto==null || idPrescrizione==null || idPaziente==null || idSsp==null)
            throw new DAOException(new NullPointerException());

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.EROGA_ESAME_1)) {
            pstm.setInt(1,idPrescrizione);
            pstm.setInt(2,idSsp);
            pstm.setTimestamp(3,new Timestamp(new java.util.Date().getTime()));
            pstm.setString(4,referto.getAnamnesi());
            pstm.setString(5,referto.getConclusioni());
            pstm.setBoolean(6,false);
            pstm.setBoolean(7,true);

            if (pstm.executeUpdate() >0){
                PreparedStatement stm=CON.prepareStatement(QUERY.EROGA_ESAME_2);
                stm.setInt(1, idPrescrizione);
                stm.setInt(2, idPaziente);
                ResultSet rs=stm.executeQuery();
                if(rs.next()){
                    boolean isSSP=rs.getBoolean(1);
                    rs.close();
                    stm.close();
                    if(!isSSP){
                        PreparedStatement stm1=CON.prepareStatement(QUERY.INSERISCI_PAGAMENTO);
                        stm1.setInt(1, idPrescrizione);

                        if(dataPagamento!=null) {
                            stm1.setTimestamp(2,new Timestamp(dataPagamento.getTime()));
                            stm1.setInt(3,metodo);
                        } else {
                            stm1.setNull(2, Types.TIMESTAMP);
                            stm1.setNull(3,Types.INTEGER);
                        }
                        
                        if(stm1.executeUpdate()>0){
                            return true;
                        }else return false;
                    }else return true;
                }
            }

        }
        catch (SQLException sqlex) {
            throw new DAOException("Impossibile generare il referto",sqlex);
        }

        return false;
    }

    @Override
    public Long getCountAmbulatori(Integer idSsp, String searchValue) throws DAOException {
        Long counter = 0L;
        searchValue = searchValue.toLowerCase();

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.COUNT_AMBULATORI)) {
            pstm.setInt(1, idSsp);
            for(int i=2; i<=5; i++) {
                pstm.setString(i,"%"+searchValue+"%");
            }
            try(ResultSet rs = pstm.executeQuery()) {
                if(rs.next()) {
                    counter = rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return counter;
    }

    @Override
    public List<Ambulatorio> pageAmbulatoriBySearchValue(Integer idSsp, String searchValue, Long start, Long length, Integer column, String direction) throws DAOException {

        searchValue = searchValue.toLowerCase();
        String query = String.format(QUERY.GET_AMBULATORI +" ORDER BY %d "+  direction+ " OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", (column+2),start,length);
        List<Ambulatorio> ambulatori = new ArrayList<>();
        try(PreparedStatement stm=CON.prepareStatement(query)) {
            stm.setInt(1, idSsp);
            for(int i=2; i<=4; i++) {
                stm.setString(i, "%"+searchValue+"%");
            }
            try(ResultSet rs=stm.executeQuery()) {
                while(rs.next()) {
                    Ambulatorio a=new Ambulatorio();
                    a.setId(rs.getInt("id"));
                    a.setDenominazione(rs.getString("nome"));
                    a.setIndirizzo(rs.getString("indirizzo"));
                    a.setCitta(rs.getString("citta"));
                    a.setProvincia(rs.getString("provincia"));
                    a.setContattoTelefonico(rs.getString("contatto_principale"));

                    ambulatori.add(a);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return ambulatori;
    }

}
