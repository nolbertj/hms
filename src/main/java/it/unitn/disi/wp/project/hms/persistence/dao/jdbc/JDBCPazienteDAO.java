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
import it.unitn.disi.wp.project.hms.commons.persistence.queries.PazienteQuery;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;
import it.unitn.disi.wp.project.hms.persistence.dao.PazienteDAO;
import it.unitn.disi.wp.project.hms.persistence.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static it.unitn.disi.wp.project.hms.persistence.utils.PerGenere.PER_GENERE;

/**
 * JDBC implementation of {@link PazienteDAO} interface.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @author Alessandro Brighenti &lt; alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 30.09.2019
 */
public class JDBCPazienteDAO extends JDBCUserDAO implements PazienteDAO {

    private final PazienteQuery QUERY;
    public JDBCPazienteDAO(Connection con) {
        super(con);
        QUERY=new PazienteQuery();
    }
    
    @Override
    public Paziente getByEmail(String email) throws DAOException {

        Paziente paziente = null;

        if (email == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement(QUERY.GET_BY_MAIL)) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if(rs.next()){
                    paziente = new Paziente();
                    paziente.setId(rs.getInt("id"));
                    paziente.setRuolo(rs.getInt("ruolo"));
                    paziente.setEmail(rs.getString("email"));
                    paziente.setNome(rs.getString("nome"));
                    paziente.setCognome(rs.getString("cognome"));
                    paziente.setDataNascita(rs.getDate("data_nascita"));
                    paziente.setLuogoNascita(rs.getString("citta_l"));
                    paziente.setSesso(rs.getString("sesso"));
                    paziente.setProvincia(rs.getString("prov"));
                    paziente.setCodFiscale(rs.getString("codice_fiscale"));
                    paziente.setContattoTelefonico(rs.getString("contatto_riferimento"));
                    paziente.setContattoEmergenza(rs.getString("contatto_emergenza"));
                    paziente.setCittaResidenza(rs.getString("citta_res"));
                    paziente.setAvatarFilename(rs.getString("avatar_path"));
                    paziente.setMedicoBase(getMedicoBase(paziente.getId()));
                }
            }

        }
        catch (SQLException ex) {
            throw new DAOException("Impossibile ricevere il paziente tramite la primary key data", ex);
        }
        return paziente;
    }

    @Override
    public Paziente getByPrimaryKey(Integer id) throws DAOException {
        if (id == null) {
            throw new DAOException("primaryKey is null");
        }
        try (PreparedStatement stm = CON.prepareStatement(QUERY.GET_BY_PRIMARY_KEY)) {
            stm.setInt(1, id);
            try (ResultSet rs = stm.executeQuery()) {
                Paziente paziente = null;
                if(rs.next()) {
                    paziente = new Paziente();
                    paziente.setId(rs.getInt("id"));
                    paziente.setEmail(rs.getString("email"));
                    paziente.setNome(rs.getString("nome"));
                    paziente.setCognome(rs.getString("cognome"));
                    paziente.setDataNascita(rs.getDate("data_nascita"));
                    paziente.setLuogoNascita(rs.getString("citta_l"));
                    paziente.setSesso(rs.getString("sesso"));
                    paziente.setProvincia(rs.getString("prov"));
                    paziente.setCodFiscale(rs.getString("codice_fiscale"));
                    paziente.setContattoTelefonico(rs.getString("contatto_riferimento"));
                    paziente.setContattoEmergenza(rs.getString("contatto_emergenza"));
                    paziente.setCittaResidenza(rs.getString("citta_res"));
                    paziente.setAvatarFilename(rs.getString("avatar_path"));
                    paziente.setMedicoBase(getMedicoBase(paziente.getId()));
                }
                return paziente;
            }
        } catch (SQLException ex) {
            throw new DAOException("Impossibile ricevere il paziente tramite la primary key data", ex);
        }
    }

    @Override
    public boolean changeMedic(Integer idPaziente, Integer idMedico) throws DAOException {
        if(idPaziente == null || idMedico==null){
            throw new DAOException(new NullPointerException());
        }
        try(PreparedStatement stm = CON.prepareStatement(QUERY.CHANGE_MEDIC)) {
            stm.setDate(1, new Date(System.currentTimeMillis()));
            stm.setInt(2, idPaziente);
            stm.setInt(3, idPaziente);
            stm.setInt(4, idMedico);
            stm.setDate(5, new Date(System.currentTimeMillis()));

            if(stm.executeUpdate() >0) return true;
            else return false;

        }catch (SQLException ex){
            throw new DAOException("Impossibile cambiare il medico di base", ex);
        }
    }

    @Override
    public boolean updatePhoto(Foto foto) throws DAOException {
        if(foto == null){
            throw new DAOException(new NullPointerException());
        }
        try(PreparedStatement stm = CON.prepareStatement(QUERY.UPDATE_FOTO_PAZIENTE)) {
            stm.setInt(1, foto.getIdOwner());
            stm.setString(2, foto.getFilename());
            stm.setTimestamp(3, foto.getTimeStamp());
            stm.setString(4, foto.getFilename());
            stm.setInt(5, foto.getIdOwner());

            if(stm.executeUpdate() >0) return true;
            else return false;

        }catch (SQLException ex){
            throw new DAOException("Impossibile caricare la foto paziente", ex);
        }
    }

    @Override
    public Referto getReferto(Integer idEsame, Integer idPaziente) throws DAOException {
        Referto r=null;
        try(PreparedStatement stm=CON.prepareStatement(QUERY.GET_REFERTO_MS)){
            stm.setInt(1,idEsame);
            stm.setInt(2, idPaziente);
            stm.setInt(3,idEsame);
            stm.setInt(4, idPaziente);
            try(ResultSet rs=stm.executeQuery()){
                if(rs.next()){
                    r=new Referto();
                    r.setNomeEsame(rs.getString("nome_esame"));
                    r.setMedicoEsecutore(rs.getString("medico_esecutore"));
                    r.setMedicoPrescrivente(rs.getString("medico_prescrivente"));
                    r.setIdEsame(rs.getInt("id"));
                    r.setDataErogazione(rs.getTimestamp("data_erogazione"));
                    r.setAnamnesi(rs.getString("anamnesi"));
                    r.setConclusioni(rs.getString("conclusioni"));
                    r.setPaziente(rs.getString("n_paziente"));
                }else{
                    try(PreparedStatement stm1=CON.prepareStatement(QUERY.GET_REFERTO_SSP)){
                        stm1.setInt(1, idEsame);
                        stm1.setInt(2, idPaziente);
                        stm1.setInt(3, idEsame);
                        stm1.setInt(4, idPaziente);
                        try(ResultSet rs1=stm1.executeQuery()){
                            if(rs1.next()){
                                r=new Referto();
                                r.setNomeEsame(rs1.getString("nome_esame"));
                                r.setMedicoEsecutore(rs1.getString("medico_esecutore"));
                                r.setMedicoPrescrivente(rs1.getString("medico_prescrivente"));
                                r.setIdEsame(rs1.getInt("id"));
                                r.setDataErogazione(rs1.getTimestamp("data_erogazione"));
                                r.setAnamnesi(rs1.getString("anamnesi"));
                                r.setConclusioni(rs1.getString("conclusioni"));
                                r.setPaziente(rs.getString("n_paziente"));
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Il paziente non ha un esame con l'id dato", e);
        }
        return r;
    }

    @Override
    public List<Referto> getRefertiOrdered(String searchValue, Integer idPaziente, int idColonna, String direzione, long start, long length) throws DAOException {
        List<Referto> referti=new ArrayList<>();
        try(PreparedStatement stm=CON.prepareStatement(String.format(QUERY.GET_REFERTI + " ORDER BY %d "+  direzione+ " OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", (idColonna+1),start,length))) {
            stm.setInt(1, idPaziente);
            stm.setInt(2, idPaziente);
            stm.setInt(3, idPaziente);
            stm.setInt(4, idPaziente);
            for(int i=5; i<=9; i++) {
                stm.setString(i,"%"+searchValue.toLowerCase()+"%");
            }
            try(ResultSet rs=stm.executeQuery()){
                while(rs.next()){
                    Referto r=new Referto();
                    r.setIdEsame(rs.getInt("id"));
                    r.setDataErogazione(rs.getTimestamp("data_erogazione"));
                    r.setMedicoPrescrivente(rs.getString("medico_prescrivente"));
                    r.setMedicoEsecutore(rs.getString("medico_esecutore"));
                    r.setNomeEsame(rs.getString("nome_esame"));

                    referti.add(r);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return referti;
    }

    @Override
    public Long getCountReferti(Integer idPaziente, String searchValue) throws DAOException {
        Long conteggio = 0L;;
        try(PreparedStatement stm=CON.prepareStatement(QUERY.COUNT_REFERTI)){
            stm.setInt(1, idPaziente);
            stm.setInt(2, idPaziente);
            for(int i=3; i<=7; i++) {
                stm.setString(i,"%"+searchValue.toLowerCase()+"%");
            }
            try(ResultSet rs=stm.executeQuery()){
                if(rs.next()){
                    conteggio=rs.getLong("conteggio_referti");
                }
            }
        }catch(SQLException e){
            throw new DAOException("Il paziente dato non ha referti", e);
        }

        return conteggio;
    }

    @Override
    public Ricetta getRicettaFarmaceutica(Integer idRicetta, Integer idPaziente) throws DAOException {
        if(idRicetta == null || idPaziente == null){
            return null;
        }

        Ricetta ricetta = null;

        try(PreparedStatement pstm2 = CON.prepareStatement(QUERY.GET_RICETTA_1)) {
            pstm2.setInt(1,idRicetta);
            pstm2.setInt(2,idPaziente);
            ResultSet rs = pstm2.executeQuery();

            if(rs.next()) {
                ricetta=new Ricetta();
                Paziente paziente=new Paziente();
                paziente.setId(rs.getInt("id_paziente"));
                paziente.setNome(rs.getString("nome_paziente"));
                paziente.setCognome(rs.getString("cognome_paziente"));
                paziente.setCodFiscale(rs.getString("codFiscalePaziente"));
                ricetta.setPaziente(paziente);

                ricetta.setCodice(rs.getInt("id_ricetta"));
                ricetta.setDataPrescrizione(rs.getTimestamp("data_prescrizione"));
                ricetta.setCodiceMedico(rs.getInt("codice_medico"));
                String medico = rs.getString("medico_prescrivente");
                ricetta.setMedicoPrescrittore(medico);
                ricetta.setDescrizione(rs.getString("descrizione"));

                if (rs.getTimestamp("data_erogazione")!=null) {
                    ricetta.setDataErogazione(rs.getTimestamp("data_erogazione"));
                    ricetta.setFarmaciaErogante(rs.getString("nome_farmacia"));
                }

                // ora cerco tutti i farmacia prescritti in base all'id del paziente e ricetta"
                try (PreparedStatement pstm3 = CON.prepareStatement(QUERY.GET_RICETTA_2)) {
                    pstm3.setInt(1, idRicetta);
                    pstm3.setInt(2, idPaziente);

                    rs = pstm3.executeQuery();

                    List<FarmacoPrescritto> farmaci = new ArrayList<>();
                    while (rs.next()) {
                        FarmacoPrescritto farmaco = new FarmacoPrescritto();

                        //devo stare attento a settare il prezzo solo se il farmaco è stato erogato!
                        //può capitare che una farmacia abbia un altro prezzo
                        //e logicamente non ha senso mosrtare il prezzo se la ricetta non è stata erogata

                        farmaco.setCodice(rs.getInt("codice"));
                        farmaco.setNome(rs.getString("nome"));
                        farmaco.setDescrizione(rs.getString("descrizione"));
                        farmaco.setQuantita(rs.getInt("quantita"));
                        farmaco.setNote(rs.getString("note"));

                        if (ricetta.getDataErogazione() != null) {
                            farmaco.setPrezzo(Double.valueOf(rs.getInt("prezzo")));
                            farmaco.setTotale(Double.valueOf(rs.getString("totale")));
                        }

                        farmaci.add(farmaco);
                    }
                    //aggiungo il ticket se la ricetta non è stata erogata
                    if (ricetta.getDataErogazione() != null) {
                        FarmacoPrescritto farmaco = new FarmacoPrescritto();
                        farmaco.setCodice(0);
                        farmaco.setNome("TICKET RICETTA");
                        farmaco.setQuantita(1);
                        farmaco.setPrezzo(3.00);
                        farmaco.setTotale(3.00);
                        farmaci.add(farmaco);
                    }
                    ricetta.setFarmaciPrescritti(farmaci);

                    //infine preparo il totale speso dalla lista dei farmaci prescritti
                    //MA SOLO SE LA RICETTA è STATA EROGATa
                    if (ricetta.getDataErogazione() != null) {
                        Double totale = 0.0;
                        for (FarmacoPrescritto f : farmaci) {
                            totale += f.getTotale();
                        }
                        ricetta.setTotale(totale);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }




        return ricetta;
    }

    @Override
    public Long getCountRicetteFarmaceutiche(Integer idPaziente, String searchValue) throws DAOException {
        if(idPaziente == null)
            throw new DAOException(new NullPointerException());

        Long counter = null;
        try(PreparedStatement pstm = CON.prepareStatement(QUERY.COUNT_RICETTE)) {
            pstm.setInt(1, idPaziente);
            pstm.setString(2, "%"+searchValue.toLowerCase()+"%");
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

    public List<Ricetta> pageRicetteFarmaceuticheBySearchValue(String searchValue, Integer idPaziente, Long start, Long length, Integer column, String direction) throws DAOException {

        column+=1; //perchè nelle datatable la prima colonna corrisponde allo 0, in sql a 1.

        String query = String.format(
                QUERY.GET_RICETTE +
                " ORDER BY %d " + direction + " " +
                "OFFSET %d ROWS FETCH NEXT %d ROWS ONLY ", column, start, length);

        try(PreparedStatement pstm = CON.prepareStatement(query)) {
            pstm.setInt(1, idPaziente);
            pstm.setString(2, "%"+searchValue.toLowerCase()+"%");

            ResultSet rs = pstm.executeQuery();

            List<Ricetta> ricette = new ArrayList<>();
            while(rs.next()) {
                Ricetta ricetta = new Ricetta();

                ricetta.setCodice(rs.getInt("id_ricetta"));
                ricetta.setDataPrescrizione(rs.getTimestamp("data_prescrizione"));
                String medico="Dott.";
                if(rs.getString("sesso_medico").equals(PER_GENERE.FEMALE.getChar()))
                    medico+="ssa";
                medico += " " + rs.getString("medico_prescrivente");
                ricetta.setMedicoPrescrittore(medico);
                ricetta.setTotale((double) ((Integer)rs.getInt("nr_farmaci")).intValue());

                if(rs.getDate("data_erogazione")!=null)
                    ricetta.setDataErogazione(rs.getTimestamp("data_erogazione"));


                ricette.add(ricetta);
            }
            rs.close();
            return ricette;
        }
        catch (SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(), sqlex);
        }

    }

    @Override
    public Long getCountFoto(Integer idPaziente) throws DAOException {
        if(idPaziente == null)
            throw new DAOException(new NullPointerException());

        Long counter = null;

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.COUNT_FOTO)) {
            pstm.setInt(1, idPaziente);

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
    public List<Foto> pageListaFoto(Integer idPaziente, Long start, Long length, Integer column, String direction) throws DAOException {

        column +=1;

        String query = String.format(
                QUERY.GET_LISTA_FOTO +
                " ORDER BY %d " + direction + " " +
                "OFFSET %d ROWS FETCH NEXT %d ROWS ONLY ",
                column, start, length);

        try(PreparedStatement pstm = CON.prepareStatement(query)) {

            pstm.setInt(1, idPaziente);

            ResultSet rs = pstm.executeQuery();

            List<Foto> lista = new ArrayList<>();
            while(rs.next()) {
                Foto foto = new Foto();

                foto.setFilename(rs.getString("filename"));
                foto.setTimeStamp(rs.getTimestamp("data"));

                lista.add(foto);
            }

            rs.close();
            return lista;
        }
        catch (SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(), sqlex);
        }
    }

    @Override
    public MedicoBase getMedicoBase(Integer idPaziente) throws DAOException {
        if (idPaziente == null) {
            throw new DAOException("idPaziente is null");
        }

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.GET_MEDICO_BASE)){
            pstm.setInt(1,idPaziente);
            ResultSet rs = pstm.executeQuery();

            MedicoBase medicoBase = null;
            if(rs.next()){
                medicoBase = new MedicoBase();
                String prefisso = "Dott.";
                if(rs.getString("sesso").toLowerCase().equals(PER_GENERE.FEMALE.getChar().toLowerCase()))
                    prefisso+="ssa";
                medicoBase.setNome(prefisso + " " + rs.getString("nome"));
                medicoBase.setCognome(rs.getString("cognome"));
                medicoBase.setAvatarFilename(rs.getString("avatar_filename"));
                medicoBase.setEmail(rs.getString("email"));

                Ambulatorio ambulatorio = new Ambulatorio();
                ambulatorio.setDenominazione(rs.getString("ambulatorio"));
                ambulatorio.setCitta(rs.getString("citta"));
                ambulatorio.setProvincia(rs.getString("provincia"));
                ambulatorio.setIndirizzo(rs.getString("indirizzo"));
                ambulatorio.setContattoTelefonico(rs.getString("contatto_telefonico"));

                medicoBase.setAmbulatorio(ambulatorio);
            }
            rs.close();
            return medicoBase;
        }
        catch (SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(), sqlex);
        }
    }

    @Override
    public List<Paziente> getAll(String searchValue) throws DAOException {

        List<Paziente> pazienti = null;

        if (Utils.isNullOrEmpty(searchValue)) {
            throw new DAOException("L'argomento non può essere nullo o avere una lunghezza pari a 0");
        } else {
            pazienti = new ArrayList<>();
        }

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.GET_ALL_SELECT2)) {

            for(int i=1; i<=5; i++) {
                pstm.setString(i,"%"+searchValue.toLowerCase()+"%");
            }

            try(ResultSet rs = pstm.executeQuery()) {
                while(rs.next()) {
                    Paziente p = new Paziente();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setCognome(rs.getString("cognome"));
                    p.setCodFiscale(rs.getString("codice_fiscale"));
                    pazienti.add(p);
                }
            }
        }
        catch (SQLException sqlex) {
            throw new DAOException(sqlex.getMessage(), sqlex);
        }

        return pazienti;
    }

    @Override
    public Long getCountRicetteFarmaceuticheNonErogate(Integer idPaziente) throws DAOException {
        if(idPaziente == null)
            throw new DAOException(new NullPointerException());

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.COUNT_RICETTE_NON_EROGATE)) {
            pstm.setInt(1, idPaziente);

            Long counter = null;
            try(ResultSet rs = pstm.executeQuery()){
                if(rs.next())
                    counter = rs.getLong(1);
            }
            return counter;
        }
        catch (SQLException sqlex){
            throw new DAOException(sqlex.getMessage(), sqlex);
        }
    }

    @Override
    public Long getCountEsamiPrescritti(Integer idPaziente, String searchValue) throws DAOException {
        if(idPaziente == null)
            throw new DAOException(new NullPointerException());

        Long counter = null;

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.COUNT_ESAMI_PRESCRITTI)) {
            pstm.setInt(1, idPaziente);
            pstm.setInt(1, idPaziente);
            pstm.setInt(2, idPaziente);
            for(int i=3; i<=7; i++) {
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
    public List<EsamePrescritto> pageEsPrescrittiBySearchValue(String searchValue, Integer idPaziente, Long start, Long length, Integer column, String direction) throws DAOException {
        if(column!=7)
            column+=1;
        String query = String.format(
            QUERY.GET_ESAMI_PRESCRITTI + " ORDER BY %d " +  direction + " OFFSET %d ROWS FETCH NEXT %d ROWS ONLY",
            (column),start,length
        );

        List<EsamePrescritto> esamiPresc = new ArrayList<>();
        try(PreparedStatement stm = CON.prepareStatement(query)) {
            stm.setInt(1, idPaziente);
            stm.setInt(2, idPaziente);
            for(int i=3; i<=9; i++) {
                stm.setString(i,"%"+searchValue.toLowerCase()+"%");
            }
            try(ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    EsamePrescritto ep = new EsamePrescritto();
                    ep.setIdPrescrizione(rs.getInt("id"));
                    ep.setCodice(rs.getInt("codice_esame"));
                    ep.setPrescrivente(rs.getString("nome_medico_prescrittore"));
                    ep.setNome(rs.getString("nome_esame"));
                    ep.setArea(rs.getString("area"));
                    ep.setPrescrittoDaSSP(rs.getBoolean("is_ssp"));
                    ep.setDataPrescrizione(rs.getDate("data_prescrizione"));
                    ep.setDataErogazione(rs.getDate("data_erogazione"));
                    if (rs.wasNull()) {
                        ep.setDataErogazione(null);
                    }
                    esamiPresc.add(ep);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return esamiPresc;
    }

    @Override
    public List<EsamePrescritto> getEsPrescrittiErogabiliSSP(String searchValue, Integer idPaziente) throws DAOException {
        List<EsamePrescritto> esamiPresc = new ArrayList<>();
        try(PreparedStatement stm = CON.prepareStatement(QUERY.GET_ESAMI_PRESCRITTI_EROGABILI_SSP)) {
            stm.setInt(1, idPaziente);
            stm.setInt(2, idPaziente);
            for(int i=3; i<=9; i++) {
                stm.setString(i,"%"+searchValue.toLowerCase()+"%");
            }
            try(ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    EsamePrescritto ep = new EsamePrescritto();
                    ep.setIdPrescrizione(rs.getInt("id"));
                    ep.setCodice(rs.getInt("codice_esame"));
                    ep.setPrescrivente(rs.getString("nome_medico_prescrittore"));
                    ep.setNome(rs.getString("nome_esame"));
                    ep.setArea(rs.getString("area"));
                    ep.setDataPrescrizione(rs.getDate("data_prescrizione"));
                    ep.setDataErogazione(rs.getDate("data_erogazione"));
                    if (rs.wasNull()) {
                        ep.setDataErogazione(null);
                    }
                    esamiPresc.add(ep);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return esamiPresc;
    }

    @Override
    public Long getCountMedici(Integer idPaziente, String searchValue) throws DAOException {
        if(idPaziente==null){
            throw new DAOException(new NullPointerException());
        }

        Long counter = null;

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.COUNT_MEDICI_BASE)) {
            pstm.setInt(1, idPaziente);
            pstm.setInt(2, idPaziente);
            for(int i=3; i<=8; i++) {
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
    public List<MedicoBase> pageMediciBaseBySearchValue(String searchValue, Integer idPaziente, Long start, Long length, Integer column, String direction) throws DAOException {
        String query = String.format(QUERY.GET_LISTA_MEDICI_BASE +" ORDER BY %d "+  direction+ " OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", (column+1),start,length);

        List<MedicoBase> mediciBase=new ArrayList<>();
        try(PreparedStatement stm=CON.prepareStatement(query)) {
            stm.setInt(1, idPaziente);
            stm.setInt(2, idPaziente);
            for(int i=3; i<=8; i++) {
                stm.setString(i,"%"+searchValue.toLowerCase()+"%");
            }
            try(ResultSet rs=stm.executeQuery()){
                while(rs.next()){
                    MedicoBase mb=new MedicoBase();
                    Ambulatorio a=new Ambulatorio();
                    mb.setId(rs.getInt("id"));
                    mb.setNome(rs.getString("nome"));
                    mb.setCognome(rs.getString("cognome"));
                    String sesso=rs.getString("sesso");
                    if(sesso.equals("F")){
                        mb.setSesso("Femmina");
                    }else if(sesso.equals("M")){
                        mb.setSesso("Maschio");
                    }else{
                        mb.setSesso("");
                    }
                    mb.setDataNascita(rs.getDate("data_nascita"));
                    a.setDenominazione(rs.getString("nome_ambulatorio"));
                    a.setIndirizzo(rs.getString("indirizzo_medico"));
                    a.setContattoTelefonico(rs.getString("contatto_telefonico"));
                    mb.setAmbulatorio(a);

                    mediciBase.add(mb);
                }

            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return mediciBase;
    }

    @Override
    public Long getCountPagamenti(Integer idPaziente, String searchValue) throws DAOException {
        Long counter = null;

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.COUNT_PAGAMENTI)) {
            pstm.setInt(1, idPaziente);
            pstm.setInt(2, idPaziente);
            for(int i=3; i<=5; i++) {
                pstm.setString(i,"%"+searchValue.toLowerCase()+"%");
            }
            try(ResultSet rs = pstm.executeQuery()) {
                if(rs.next()) {
                    counter = rs.getLong(1);
                }
            }
        }
        catch (SQLException sqlex) {
            throw new DAOException(sqlex);
        }

        return counter;
    }

    @Override
    public List<Ricevuta> pagePagamentiBySearchValue(String searchValue, Integer idPaziente, Long start, Long length, Integer column, String direction) throws DAOException {
        if(column!=2)
            column+=1;

        String query = String.format(QUERY.GET_LISTA_PAGAMENTI +" ORDER BY %d "+  direction+ " OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", column,start,length);
        List<Ricevuta> pagamenti=new ArrayList<>();
        try(PreparedStatement stm=CON.prepareStatement(query)) {
            stm.setInt(1, idPaziente);
            stm.setInt(2, idPaziente);
            for(int i=3; i<=5; i++) {
                stm.setString(i,"%"+searchValue.toLowerCase()+"%");
            }
            try(ResultSet rs=stm.executeQuery()){
                while(rs.next()){
                    Ricevuta p=new Ricevuta();
                    p.setIdRicevuta(rs.getInt("id_pagamento"));
                    p.setDataPagamento(rs.getDate("data_pagamento"));
                    p.setIsEsame(rs.getBoolean("is_esame"));
                    p.setIsRicetta(rs.getBoolean("is_ricetta"));
                    p.setIdCausale(rs.getInt("id"));
                    p.setDataErogazione(rs.getDate("data_erogazione"));
                    p.setImporto(rs.getDouble("importo"));

                    pagamenti.add(p);
                }

            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return pagamenti;
    }

    @Override
    public Ricevuta getRicevuta(Integer idPaziente, Integer idRicevuta) throws DAOException {
        Ricevuta ricevuta=null;

        try(PreparedStatement stm=CON.prepareStatement(QUERY.GET_RICEVUTA_1)){
            stm.setInt(1,idRicevuta);
            stm.setInt(2, idPaziente);
            Paziente tmp = null;
            try(ResultSet rs=stm.executeQuery()){
                if(rs.next()){
                    ricevuta=new Ricevuta();
                    ricevuta.setIdRicevuta(rs.getInt("id_pagamento"));
                    ricevuta.setImporto(rs.getDouble("importo"));
                    ricevuta.setDataErogazione(rs.getDate("data_erogazione"));
                    ricevuta.setIsRicetta(rs.getBoolean("is_ricetta"));
                    ricevuta.setIsEsame(rs.getBoolean("is_esame"));
                    ricevuta.setIdCausale(rs.getInt("id_causale"));
                    ricevuta.setDataPagamento(rs.getDate("data_pagamento"));
                    ricevuta.setMetodo(rs.getString("metodo"));
                    tmp = this.getByPrimaryKey(rs.getInt("paziente"));
                    Paziente paziente = new Paziente();
                    paziente.setNome(tmp.getNome());
                    paziente.setCognome(tmp.getCognome());
                    paziente.setCodFiscale(tmp.getCodFiscale());
                    ricevuta.setPaziente(paziente);
                }else{
                    try(PreparedStatement stm1=CON.prepareStatement(QUERY.GET_RICEVUTA_2)){
                        stm1.setInt(1, idRicevuta);
                        stm1.setInt(2, idPaziente);
                        try(ResultSet rs1=stm1.executeQuery()){
                            if(rs1.next()){
                                ricevuta=new Ricevuta();
                                ricevuta.setIdRicevuta(rs1.getInt("id_pagamento"));
                                ricevuta.setImporto(rs1.getDouble("importo"));
                                ricevuta.setDataErogazione(rs1.getDate("data_erogazione"));
                                ricevuta.setIsRicetta(rs1.getBoolean("is_ricetta"));
                                ricevuta.setIsEsame(rs1.getBoolean("is_esame"));
                                ricevuta.setIdCausale(rs1.getInt("id_causale"));
                                ricevuta.setDataPagamento(rs1.getDate("data_pagamento"));
                                ricevuta.setMetodo(rs1.getString("metodo"));
                                tmp = this.getByPrimaryKey(rs1.getInt("paziente"));
                                Paziente paziente = new Paziente();
                                paziente.setNome(tmp.getNome());
                                paziente.setCognome(tmp.getCognome());
                                paziente.setCodFiscale(tmp.getCodFiscale());
                                ricevuta.setPaziente(paziente);
                            }
                        }
                    }
                }

            }
        } catch (SQLException e) {
            throw new DAOException("Il paziente non ha un pagamento con l'id dato", e);
        }
        return ricevuta;
    }

    public boolean updateAnagrafica(Paziente paziente) throws DAOException {
        if(paziente == null){
            throw new DAOException(new NullPointerException());
        }
        try(PreparedStatement pstm = CON.prepareStatement(QUERY.UPDATE_ANAGRAFICA)){
            pstm.setString(1,paziente.getContattoEmergenza());
            pstm.setString(2,paziente.getContattoTelefonico());
            pstm.setInt(3,paziente.getId());

            if(pstm.executeUpdate() >0) return true;
            else return false;

        }catch (SQLException ex){
            throw new DAOException("Impossibile aggiornare l'anagrafica del paziente.", ex);
        }
    }

    public Long getCountVisite(Integer idPaziente, String searchValue) throws DAOException {
        Long counter = 0L;
        searchValue = searchValue.toLowerCase();

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.GET_VISITE + " LIMIT 1")){
            for(int i=1; i<=3; i++) {
                pstm.setInt(i, idPaziente);
            }
            for(int i=4; i<=6; i++) {
                pstm.setString(i,'%' + searchValue + '%');
            }
            try(ResultSet rs = pstm.executeQuery()) {
                if(rs.next()) {
                    counter = rs.getLong("counter");
                }
            }
        }
        catch (SQLException sqlex) {
            throw new DAOException(sqlex);
        }

        return counter;
    }

    @Override
    public List<Visita> pageVisiteBySearchValue(String searchValue, Integer idPaziente, Long start, Long length, Integer column, String direction) throws DAOException {
        List<Visita> visite;
        String query = String.format(QUERY.GET_VISITE +" ORDER BY %d "+  direction+ " OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", (column+1),start,length);
        try (PreparedStatement pstm = CON.prepareStatement(query)) {
            for(int i=1; i<=3; i++) {
                pstm.setInt(i, idPaziente);
            }
            for(int i=4; i<=6; i++) {
                pstm.setString(i,'%' + searchValue + '%');
            }
            try (ResultSet rs = pstm.executeQuery()) {
                visite = new ArrayList<>();
                while(rs.next()) {
                    Visita v = new Visita();
                    v.setIdPrescrizione(rs.getInt("idPrescrizione"));
                    v.setTipo(rs.getString("tipo"));
                    v.setDescrizione(rs.getString("descrizione"));
                    v.setPrescrivente(rs.getString("prescrivente"));
                    v.setDataPrescrizione(rs.getTimestamp("data_prescrizione").toString());
                    if(rs.getTimestamp("data_erogazione")!=null)
                        v.setDataErogazione(rs.getTimestamp("data_erogazione").toString());
                    else v.setDataErogazione("");
                    visite.add(v);
                }
            }
        } catch (SQLException sqlex){
            throw new DAOException(sqlex.getMessage(), sqlex);
        }
        return visite;
    }

    @Override
    public Table getAppuntamenti(Integer idPaziente, java.util.Date from, java.util.Date to) throws DAOException {
        Table appuntamenti;
        try (PreparedStatement pstm = CON.prepareStatement(QUERY.GET_APPUNTAMENTI)) {
            pstm.setTimestamp(1,new Timestamp(from.getTime()));
            pstm.setTimestamp(2,new Timestamp(to.getTime()));
            pstm.setInt(3,idPaziente);
            pstm.setTimestamp(4,new Timestamp(from.getTime()));
            pstm.setTimestamp(5,new Timestamp(to.getTime()));
            pstm.setInt(6,idPaziente);

            try (ResultSet rs = pstm.executeQuery()) {
                appuntamenti = TableLoader.load(rs);
            }
        } catch (SQLException sqlex) {
            throw new DAOException("Impossibile ritornare gli appuntamenti.",sqlex);
        }
        return appuntamenti;
    }

    @Override
    public Long getCountAmbulatori(Integer idPaziente, String searchValue) throws DAOException {
        Long counter = 0L;
        searchValue = searchValue.toLowerCase();

        try(PreparedStatement pstm = CON.prepareStatement(QUERY.COUNT_AMBULATORI)) {
            pstm.setInt(1, idPaziente);
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
    public List<Ambulatorio> pageAmbulatoriBySearchValue(Integer idPaziente, String searchValue, Long start, Long length, Integer column, String direction) throws DAOException {

        searchValue = searchValue.toLowerCase();
        String query = String.format(QUERY.GET_AMBULATORI +" ORDER BY %d "+  direction+ " OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", (column+2),start,length);
        List<Ambulatorio> ambulatori = new ArrayList<>();
        try(PreparedStatement stm=CON.prepareStatement(query)) {
            stm.setInt(1, idPaziente);
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

    public boolean effettuaPagamentoEsame(Integer riferimentoPagamento, Integer idPrescrizione, java.util.Date dataPagamento, Integer metodo) throws DAOException {
        try (PreparedStatement pstm = CON.prepareStatement(QUERY.EFFETTUA_PAGAMENTO)) {
            pstm.setTimestamp(1, new Timestamp(dataPagamento.getTime()));
            pstm.setInt(2,metodo);
            pstm.setInt(3,idPrescrizione);
            pstm.setInt(4,riferimentoPagamento);

            if(pstm.executeUpdate() > 0) return true;
            else return false;

        } catch (SQLException sqlex) {
            throw new DAOException("Impossibile effettuare il pagamento",sqlex);
        }
    }

}
