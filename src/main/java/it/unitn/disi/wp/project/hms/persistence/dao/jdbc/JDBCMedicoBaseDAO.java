/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao.jdbc;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.commons.persistence.entities.Table;
import it.unitn.disi.wp.project.hms.commons.persistence.entities.TableLoader;
import it.unitn.disi.wp.project.hms.commons.persistence.queries.MedicoBaseQuery;
import it.unitn.disi.wp.project.hms.persistence.dao.AppuntamentoDAO;
import it.unitn.disi.wp.project.hms.persistence.dao.MedicoBaseDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.*;
import org.daypilot.date.DateTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * JDBC implementation of {@link MedicoBaseDAO} interface.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 27.12.2019
 */
public class JDBCMedicoBaseDAO extends JDBCUserDAO implements MedicoBaseDAO, AppuntamentoDAO {

    private final MedicoBaseQuery QUERY;
    public JDBCMedicoBaseDAO(Connection con) {
        super(con);
        QUERY=new MedicoBaseQuery();
    }

    @Override
    public MedicoBase getByEmail(String email) throws DAOException {

        MedicoBase medico = null;

        if(email == null){
            throw new DAOException("email is null");
        }

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.GET_BY_EMAIL)){
            pstm.setString(1,email);
            try(ResultSet rs = pstm.executeQuery()) {
                if(rs.next()) {
                    medico=new MedicoBase();
                    medico.setId(rs.getInt("id"));
                    medico.setEmail(rs.getString("email"));
                    medico.setNome(rs.getString("nome"));
                    medico.setCognome(rs.getString("cognome"));
                    medico.setDataNascita(rs.getDate("data_nascita"));
                    medico.setCodFiscale(rs.getString("codice_fiscale"));
                    medico.setLuogoNascita(rs.getString("citta_nascita"));
                    medico.setProvincia(rs.getString("provincia_nascita"));
                    medico.setRuolo(rs.getInt("ruolo"));
                    medico.setSesso(rs.getString("sesso"));
                    medico.setContattoTelefonico(rs.getString("tel_amb"));
                    medico.setAvatarFilename(rs.getString("avatar_path"));
                    medico.setServizioSanitario(rs.getString("servizio_sanitario"));
                    medico.setNumeroPazienti(getCountPazienti(medico.getId(), ""));
                    Ambulatorio a=new Ambulatorio();
                    a.setDenominazione(rs.getString("nome_ambulatorio"));
                    a.setContattoTelefonico(rs.getString("tel_amb"));
                    a.setIndirizzo(rs.getString("indirizzo_amb"));
                    a.setCitta(rs.getString("nome_citta"));
                    a.setProvincia(rs.getString("nome_provincia"));
                    medico.setAmbulatorio(a);
                }
            }
        }
        catch(SQLException sqlex) {
            throw new DAOException("Impossibile ricevere il medico di base tramite l'email data", sqlex);
        }
        return medico;
    }

    @Override
    public MedicoBase getByPrimaryKey(Integer idMedico) throws DAOException {
        if (idMedico == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement(QUERY.GET_BY_PRIMARY_KEY)) {
            stm.setInt(1, idMedico);
            try (ResultSet rs = stm.executeQuery()) {
                MedicoBase medico = null;
                if(rs.next()) {
                    medico=new MedicoBase();
                    medico.setId(rs.getInt("id"));
                    medico.setEmail(rs.getString("email"));
                    medico.setNome(rs.getString("nome"));
                    medico.setCognome(rs.getString("cognome"));
                    medico.setDataNascita(rs.getDate("data_nascita"));
                    medico.setCodFiscale(rs.getString("codice_fiscale"));
                    medico.setLuogoNascita(rs.getString("citta_nascita"));
                    medico.setProvincia(rs.getString("provincia_nascita"));
                    medico.setSesso(rs.getString("sesso"));
                    medico.setContattoTelefonico(rs.getString("tel_amb"));
                    medico.setAvatarFilename(rs.getString("avatar_path"));
                    medico.setServizioSanitario(rs.getString("servizio_sanitario"));
                    medico.setNumeroPazienti(getCountPazienti(idMedico, ""));
                    Ambulatorio a=new Ambulatorio();
                    a.setDenominazione(rs.getString("nome_ambulatorio"));
                    a.setContattoTelefonico(rs.getString("tel_amb"));
                    a.setIndirizzo(rs.getString("indirizzo_amb"));
                    a.setCitta(rs.getString("nome_citta"));
                    a.setProvincia(rs.getString("nome_provincia"));
                    medico.setAmbulatorio(a);
                }
                return medico;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile ricevere il medico tramite la primary key data", ex);
        }
    }

    @Override
    public Long getCountPazienti(Integer idMedico, String searchValue) throws DAOException {
        Long conteggio = 0L;

        try(PreparedStatement ps=CON.prepareStatement(QUERY.COUNT_PAZIENTI)){
            ps.setInt(1,idMedico);
            for(int i=2; i<=8; i++) {
                ps.setString(i,"%"+searchValue.toLowerCase()+"%");
            }
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    conteggio=rs.getLong("numero_pazienti");
                }
            }
        }catch(SQLException e){
            throw new DAOException("Il medico non ha pazienti", e);
        }

        return conteggio;
    }

    @Override
    public List<PazienteForMedic> pagePazientiBySearchValue(String searchValue, Integer idMedico, Long start, Long length, Integer column, String direction) throws DAOException {
        List<PazienteForMedic> pazienti=new ArrayList<>();
        //column+2 perché viene restituito anche l'id
        try(PreparedStatement stm=CON.prepareStatement(String.format(QUERY.GET_LISTA_PAZIENTI + " ORDER BY %d "+  direction+ " OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", (column+2),start,length))) {
            stm.setInt(1, idMedico);
            for(int i=2; i<=8; i++) {
                stm.setString(i,"%"+searchValue.toLowerCase()+"%");
            }
            try(ResultSet rs=stm.executeQuery()){
                while(rs.next()){
                    PazienteForMedic p=new PazienteForMedic();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setCognome(rs.getString("cognome"));
                    p.setCodFiscale(rs.getString("codice_fiscale"));
                    p.setUltimaVisita(rs.getDate("ultimo_esame"));
                    p.setUltimaRicetta(rs.getDate("ultima_ricetta"));

                    pazienti.add(p);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return pazienti;
    }

    public List<Paziente> searchPazientiAttuali(Integer idMedico, String searchValue) throws DAOException {
        if (idMedico == null) {
            throw new DAOException("primaryKey is null");
        }

        List<Paziente> pazienti = null;
        try(PreparedStatement pstm = CON.prepareStatement(QUERY.SEARCH_PAZIENTI_SELECT2)){
            pstm.setInt(1,idMedico);
            for(int i=2; i<=8; i++) {
                pstm.setString(i,'%' + searchValue.toLowerCase() + '%');
            }

            try(ResultSet rs = pstm.executeQuery()) {
                pazienti = new ArrayList<>();
                while(rs.next()) {
                    Paziente p = new Paziente();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setCognome(rs.getString("cognome"));
                    p.setSesso(rs.getString("sesso"));
                    p.setCodFiscale(rs.getString("codice_fiscale"));
                    pazienti.add(p);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return pazienti;
    }

    @Override
    public boolean prescriviEsame(Esame esame, Integer idPaziente, Integer idMedico) throws DAOException {
        if (idPaziente==null || esame==null)
            throw new DAOException(new NullPointerException());

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.PRESCRIVI_ESAME)) {
            pstm.setInt(1,idMedico);
            pstm.setInt(2,idPaziente);
            pstm.setString(3,esame.getDescrizione());
            pstm.setInt(4,esame.getCodice());
            pstm.setBoolean(5,true);
            pstm.setTimestamp(6, new Timestamp(new Date().getTime()));

            if(pstm.executeUpdate() >0) return true;
            else return false;
        }
        catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean prescriviRicetta(List<Integer> codiciFarmaci, Integer idPaziente, Integer idMedico, String descrizione) throws DAOException {
        if (idPaziente==null || codiciFarmaci==null || codiciFarmaci.size()<=0)
            throw new DAOException(new NullPointerException());

        Boolean flag=false;
        Integer idRicetta=null;
        try(PreparedStatement pstm = CON.prepareStatement(QUERY.PRESCRIVI_RICETTA_1)) {
            pstm.setInt(1,idPaziente);
            pstm.setInt(2,idMedico);
            pstm.setString(3,descrizione);
            pstm.setTimestamp(4, new Timestamp(new Date().getTime()));

            //mi serve per capire se cancellare la ricetta -> se true qualcosa è andato storto

            try(ResultSet rs =pstm.executeQuery()){
                if(rs.next()){
                    idRicetta=rs.getInt(1);
                    try(PreparedStatement stm=CON.prepareStatement(QUERY.PRESCRIVI_RICETTA_2)){
                        for(Integer f: codiciFarmaci){
                            if(!flag){
                                stm.setInt(1, idRicetta);
                                stm.setInt(2,f);
                                if(stm.executeUpdate()<=0){
                                    flag=true;
                                }
                            }

                        }
                        if(!flag) return true;
                    }catch(SQLException e){
                        flag=true;
                    }
                }
            }

        }
        catch (SQLException e) {
            throw new DAOException(e);
        }

        if(flag && idRicetta!=null){
            try(PreparedStatement stm=CON.prepareStatement(QUERY.DELETE_RICETTA)){
                stm.setInt(1, idRicetta);
                stm.executeUpdate();
            }catch (SQLException e) {
                throw new DAOException("ricetta non prescritta", e);
            }
        }

        return false;
    }

    //metodo per verificare se un utente è del medico di base. serve nel filtro per le fotografie
    @Override
    public boolean checkPaziente(Integer idMedico, String userPaziente) throws DAOException{
        if(idMedico == null || userPaziente ==null){
            throw new DAOException(new NullPointerException());
        }
        boolean ret=false;
        try(PreparedStatement stm=CON.prepareStatement(QUERY.CHECK_PAZIENTE_BY_USER)){
            stm.setString(1, userPaziente+"@yopmail.com");
            stm.setInt(2, idMedico);
            try(ResultSet rs=stm.executeQuery()) {
                if (rs.next())
                    ret=true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return ret;
    }

    @Override
    public Table getAppuntamenti(Integer idMedico, java.util.Date from, java.util.Date to) throws DAOException {
        Table appuntamenti;
        try (PreparedStatement pstm = CON.prepareStatement(QUERY.GET_APPUNTAMENTI)) {
            pstm.setTimestamp(1,new Timestamp(from.getTime()));
            pstm.setTimestamp(2,new Timestamp(to.getTime()));
            pstm.setInt(3,idMedico);

            try (ResultSet rs = pstm.executeQuery()) {
                appuntamenti = TableLoader.load(rs);
            }
        } catch (SQLException sqlex) {
            throw new DAOException("Impossibile ritornare gli appuntamenti.",sqlex);
        }
        return appuntamenti;
    }

    @Override
    public boolean cancellaAppuntamento(Integer idAppuntamento) throws DAOException {
        try (PreparedStatement pstm = CON.prepareStatement(QUERY.DELETE_EVENT)) {
            pstm.setInt(1,idAppuntamento);

            if(pstm.executeUpdate() > 0) return true;
            else return false;

        } catch (SQLException sqlex) {
            throw new DAOException("Impossibile eliminare l'evento.",sqlex);
        }
    }

    @Override
    public boolean creaAppuntamentoVuoto(Integer idMedico, Date start, Date end) throws DAOException {
        try (PreparedStatement pstm = CON.prepareStatement(QUERY.INSERT_EVENT)) {
            pstm.setInt(1,idMedico);
            pstm.setTimestamp(2, new Timestamp(start.getTime()),Calendar.getInstance(DateTime.UTC));
            pstm.setTimestamp(3, new Timestamp(end.getTime()),Calendar.getInstance(DateTime.UTC));

            if(pstm.executeUpdate() > 0) return true;
            else return false;

        } catch (SQLException sqlex) {
            throw new DAOException("impossibile creare l'appuntamento", sqlex);
        }
    }

    @Override
    public boolean aggiornaOrarioAppuntamento(Integer idMedico, Integer idAppuntamento, Timestamp start, Timestamp end) throws DAOException {
        try (PreparedStatement pstm = CON.prepareStatement(QUERY.RESIZE_EVENT)) {
            pstm.setTimestamp(1, start,Calendar.getInstance(DateTime.UTC));
            pstm.setTimestamp(2, end,Calendar.getInstance(DateTime.UTC));
            pstm.setInt(3, idAppuntamento);
            pstm.setInt(4, idMedico);

            if(pstm.executeUpdate() > 0) return true;
            else return false;

        } catch (SQLException sqlex) {
            throw new DAOException("Impossibile aggiornare l'orario dell'appuntamento.",sqlex);
        }
    }

    @Override
    public boolean aggiornaMotivo(Integer idMedico, Integer idAppuntamento, String motivo) throws DAOException {
        try (PreparedStatement pstm = CON.prepareStatement(QUERY.UPDATE_EVENT_TEXT)) {
            pstm.setString(1, motivo);
            pstm.setInt(2, idAppuntamento);
            pstm.setInt(3,idMedico);

            if(pstm.executeUpdate() > 0) return true;
            else return false;

        } catch (SQLException sqlex) {
            throw new DAOException("Impossibile aggiornare il motivo dell'appuntamento",sqlex);
        }
    }

    @Override
    public boolean aggiornaAppuntamento(Integer idMedico, Integer idAppuntamento, Integer idPaziente, String motivo) throws DAOException {
        try (PreparedStatement pstm = CON.prepareStatement( QUERY.UPDATE_EVENT)) {
            pstm.setString(1, motivo);
            pstm.setInt(2,idPaziente);
            pstm.setInt(3, idAppuntamento);
            pstm.setInt(4, idMedico);

            if(pstm.executeUpdate() > 0) return true;
            else return false;

        } catch (SQLException sqlex) {
            throw new DAOException("Impossibile aggiornare l'appuntamento.",sqlex);
        }
    }

}
