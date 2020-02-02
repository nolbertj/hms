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
import it.unitn.disi.wp.project.hms.commons.persistence.queries.MedicoSpecQuery;
import it.unitn.disi.wp.project.hms.persistence.dao.AppuntamentoDAO;
import it.unitn.disi.wp.project.hms.persistence.dao.MedicoSpecialistaDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.MedicoSpecialista;
import it.unitn.disi.wp.project.hms.persistence.entities.Referto;
import org.daypilot.date.DateTime;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * JDBC implementation of {@link MedicoSpecialistaDAO} interface.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 27.12.2019
 */
public class JDBCMedicoSpecialistaDAO extends JDBCUserDAO implements MedicoSpecialistaDAO, AppuntamentoDAO {
    private final MedicoSpecQuery QUERY;
    public JDBCMedicoSpecialistaDAO(Connection con) {
        super(con);
        QUERY=new MedicoSpecQuery();
    }

    @Override
    public MedicoSpecialista getByEmail(String email) throws DAOException {


        MedicoSpecialista medicoSpecialista = null;

        if(email == null) throw new DAOException("email is null");

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.GET_BY_EMAIL)){
            pstm.setString(1,email);
            try(ResultSet rs = pstm.executeQuery()) {
                if(rs.next()) {
                    medicoSpecialista = new MedicoSpecialista();
                    medicoSpecialista.setId(rs.getInt("id"));
                    medicoSpecialista.setEmail(rs.getString("email"));
                    medicoSpecialista.setNome(rs.getString("nome"));
                    medicoSpecialista.setCognome(rs.getString("cognome"));
                    medicoSpecialista.setDataNascita(rs.getDate("data_nascita"));
                    medicoSpecialista.setCodFiscale(rs.getString("codice_fiscale"));
                    medicoSpecialista.setSesso(rs.getString("sesso"));
                    medicoSpecialista.setRuolo(rs.getInt("ruolo"));
                    medicoSpecialista.setAvatarFilename(rs.getString("avatar_filename"));
                    medicoSpecialista.setLuogoNascita(rs.getString("luogo_nascita"));
                    medicoSpecialista.setProvincia(rs.getString("provincia"));
                    medicoSpecialista.setSpecialita(rs.getString("specialita"));
                }
            }
        }
        catch(SQLException sqlex) {
            throw new DAOException("Impossibile ricevere il medico specialista tramite l'email data", sqlex);
        }

        return medicoSpecialista;
    }

    @Override
    public boolean generaReferto(Referto referto, Integer idPrescrizione, Integer idPaziente, Integer idMedico, java.sql.Date dataPagamento, Integer metodo) throws DAOException {
        if(referto==null || idPrescrizione==null || idPaziente==null || idMedico==null)
            throw new DAOException(new NullPointerException());

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.EROGA_ESAME_1)) {
            pstm.setInt(1,idPrescrizione);
            pstm.setInt(2,idMedico);
            pstm.setTimestamp(3,new Timestamp(new Date().getTime()));
            pstm.setString(4,referto.getAnamnesi());
            pstm.setString(5,referto.getConclusioni());
            pstm.setBoolean(6,true);
            pstm.setBoolean(7,false);

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
            throw new DAOException("Impossibile aggiornare l'evento.",sqlex);
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
        try (PreparedStatement pstm = CON.prepareStatement(QUERY.UPDATE_EVENT)) {
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


    @Override
    public List<Referto> listaReferti(String searchValue, Integer idMedico, Long start, Long length, Integer column, String direction) throws DAOException {
        List<Referto> referti;
        try (PreparedStatement pstm = CON.prepareStatement(String.format(QUERY.GET_LISTA_REFERTI + " ORDER BY %d "+direction+ " OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", (column+1),start,length))) {
            pstm.setInt(1, idMedico);
            for(int i=2;i<=6;i++){
                pstm.setString(i, "%"+searchValue.toLowerCase()+"%");
            }
            referti = new ArrayList<>();

            try (ResultSet rs = pstm.executeQuery()) {
                while(rs.next()){
                    Referto r=new Referto();
                    r.setIdEsame(rs.getInt("id_esame"));
                    r.setDataErogazione(rs.getTimestamp("data"));
                    r.setMedicoPrescrivente(rs.getString("medico_prescrittore"));
                    r.setNomeEsame(rs.getString("nome_esame"));
                    r.setAnamnesi(rs.getString("anamnesi"));
                    r.setConclusioni(rs.getString("conclusioni"));
                    r.setPaziente(rs.getString("paziente"));
                    r.setIdPaziente(rs.getInt("id_paziente"));
                    referti.add(r);
                }
            }
        } catch (SQLException sqlex) {
            throw new DAOException("Impossibile ritornare i referti.",sqlex);
        }
        return referti;
    }


    @Override
    public Long getCountReferti(Integer idMedico, String searchValue) throws DAOException {
        Long conteggio = 0L;

        try(PreparedStatement ps=CON.prepareStatement(QUERY.COUNT_REFERTI)){
            ps.setInt(1,idMedico);
            for(int i=2;i<=6;i++){
                ps.setString(i, "%"+searchValue.toLowerCase()+"%");
            }
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    conteggio=rs.getLong(1);
                }
            }
        }catch(SQLException e){
            throw new DAOException("Il medico non ha referti", e);
        }

        return conteggio;
    }

}