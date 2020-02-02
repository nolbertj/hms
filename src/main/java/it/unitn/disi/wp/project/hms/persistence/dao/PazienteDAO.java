/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.commons.persistence.entities.Table;
import it.unitn.disi.wp.project.hms.persistence.entities.*;

import java.util.Date;
import java.util.List;

/**
 * DAO interface for {@link Paziente}
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 30.09.2019
 */
public interface PazienteDAO extends UserDAO {

    //nel db il paziente ha un id integer? non sarebbe l'email o il codice fiscale in realtà? (quindi String)
    Paziente getByPrimaryKey(Integer id) throws DAOException;
    //======================================================================================================
    boolean changeMedic(Integer idPaziente, Integer idMedico) throws DAOException;
    boolean updatePhoto(Foto foto) throws DAOException;
    boolean updateAnagrafica(Paziente paziente) throws DAOException;

    Referto getReferto(Integer idEsame, Integer idPaziente) throws DAOException;

    Long getCountReferti(Integer idPaziente, String searchValue) throws DAOException;
    List<Referto> getRefertiOrdered(String searchValue, Integer idPaziente, int idColonna, String direzione, long start, long length) throws DAOException;

    Long getCountRicetteFarmaceutiche(Integer idPaziente, String searchValue) throws DAOException;
    List<Ricetta> pageRicetteFarmaceuticheBySearchValue(String searchValue, Integer idPaziente, Long start, Long length, Integer column, String direction) throws DAOException;
    Ricetta getRicettaFarmaceutica(Integer idRicetta, Integer idPaziente) throws DAOException;
    Long getCountRicetteFarmaceuticheNonErogate(Integer idPaziente) throws DAOException;

    Long getCountFoto(Integer idPaziente) throws DAOException;
    List<Foto> pageListaFoto(Integer idPaziente, Long start, Long length, Integer column, String direction) throws DAOException;

    MedicoBase getMedicoBase(Integer idPaziente) throws DAOException;

    List<Paziente> getAll(String searchValue) throws DAOException;

    Long getCountEsamiPrescritti(Integer idPaziente, String searchValue) throws DAOException;
    List<EsamePrescritto> pageEsPrescrittiBySearchValue(String searchValue, Integer idPaziente, Long start, Long length,Integer column, String direction) throws DAOException;
    List<EsamePrescritto> getEsPrescrittiErogabiliSSP(String searchValue, Integer idPaziente) throws DAOException;

    Long getCountMedici(Integer idPaziente, String searchValue) throws DAOException;
    List<MedicoBase> pageMediciBaseBySearchValue(String searchValue, Integer idPaziente, Long start, Long length,Integer column, String direction) throws DAOException;

    Long getCountPagamenti(Integer idPaziente, String searchValue) throws DAOException;
    List<Ricevuta> pagePagamentiBySearchValue(String searchValue, Integer idPaziente, Long start, Long length, Integer columng, String direction) throws DAOException;
    Ricevuta getRicevuta(Integer idPaziente, Integer idRicevuta) throws DAOException;

    Long getCountVisite(Integer idPaziente, String searchValue) throws DAOException;
    List<Visita> pageVisiteBySearchValue(String searchValue, Integer idPaziente, Long start, Long length, Integer column, String direction) throws DAOException;

    Table getAppuntamenti(Integer idPaziente, Date from, Date to) throws DAOException;

    Long getCountAmbulatori(Integer idPaziente, String searchValue) throws DAOException;
    List<Ambulatorio> pageAmbulatoriBySearchValue(Integer idPaziente, String searchValue, Long start, Long length, Integer column, String direction) throws DAOException;

    boolean effettuaPagamentoEsame(Integer riferimentoPagamento, Integer idPrescrizione, Date dataPagamento, Integer metodo) throws DAOException;

}
