/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.persistence.entities.Esame;
import it.unitn.disi.wp.project.hms.persistence.entities.MedicoBase;
import it.unitn.disi.wp.project.hms.persistence.entities.Paziente;
import it.unitn.disi.wp.project.hms.persistence.entities.PazienteForMedic;

import java.util.List;

/**
 * DAO interface for {@link MedicoBase}
 * @author Alessandro Brighenti &lt; alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 03.01.2020
 */
public interface MedicoBaseDAO extends UserDAO {

    MedicoBase getByPrimaryKey(Integer idMedico) throws DAOException;
    Long getCountPazienti(Integer idMedico, String searchValue) throws DAOException;
    List<PazienteForMedic> pagePazientiBySearchValue(String searchValue, Integer idMedico, Long start, Long length, Integer column, String direction) throws DAOException;
    List<Paziente> searchPazientiAttuali(Integer idMedico, String searchValue) throws DAOException;
    boolean prescriviEsame(Esame esame, Integer idPaziente, Integer idMedico) throws DAOException;
    boolean prescriviRicetta(List<Integer> codiciFarmaci, Integer idPaziente, Integer idMedico, String descrizione) throws DAOException;

    boolean checkPaziente(Integer idMedico, String userPaziente) throws DAOException;
}
