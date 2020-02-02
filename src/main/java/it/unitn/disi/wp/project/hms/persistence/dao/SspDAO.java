/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.persistence.entities.*;

import java.sql.Date;
import java.util.List;

/**
 * DAO interface for {@link Ssp}
 *
 * @author Alessandro Brighenti
 * @since 08.01.2020
 */
public interface SspDAO extends UserDAO {

    Ssp getByPrimaryKey(Integer idSSP) throws DAOException;
    List<Ricetta> getRicetteErogateReport(Integer idSSP, Date date) throws DAOException;
    List<Paziente> prescriviRichiamo(Integer idSSP, Integer codiceEsame, Integer etaInizio, Integer etaFine) throws DAOException;
    Long getCountRichiami(Integer idSSP, String searchValue) throws DAOException;
    List<EsameRichiamo> pageRichiamiBySearchValue(String searchValue, Integer idSSP, Long start, Long length, Integer column, String direction) throws DAOException;
    boolean generaReferto(Referto referto, Integer idPrescrizione, Integer idPaziente, Integer idSsp, Date dataPagamento, Integer metodo) throws DAOException;

    Long getCountAmbulatori(Integer idSsp, String searchValue) throws DAOException;
    List<Ambulatorio> pageAmbulatoriBySearchValue(Integer idSsp, String searchValue, Long start, Long length, Integer column, String direction) throws DAOException;
}
