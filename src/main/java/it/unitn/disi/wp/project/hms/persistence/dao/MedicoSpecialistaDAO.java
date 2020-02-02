/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.persistence.entities.MedicoSpecialista;
import it.unitn.disi.wp.project.hms.persistence.entities.Referto;

import java.sql.Date;
import java.util.List;

/**
 * DAO interface for {@link MedicoSpecialista}
 *
 * @author Alessandro Tomazzolli &lt;a dot tomazzolli at studenti dot unitn dot it&gt;
 * @since 30.12.2019
 */
public interface MedicoSpecialistaDAO extends UserDAO {
    String getReferti = "SELECT ep.codice_esame as idEsame, mb.nome ||' '|| mb.cognome as medicoPrescrittore, es.nome as nomeEsame, ms.nome ||' '|| ms.cognome as medicoEsecutore, ee.data_erogazione as data, ee.anamnesi, ee.conclusioni,p.nome ||' '|| p.cognome as paziente" +
    "FROM esame_erogato ee" +
    "JOIN esame_prescritto ep ON ep.id=ee.id" +
    "JOIN esame_specialistico es ON es.codice=ep.codice_esame" +
    "JOIN medico_specialista ms ON ms.id=ee.id_erogatore" +
    "JOIN medico_base mb ON ep.id_prescrivente=mb.id" +
    "JOIN paziente p ON ep.id_paziente=p.id" +
    "WHERE ep.is_medico";
    boolean generaReferto(Referto referto, Integer idPrescrizione, Integer idPaziente, Integer idMedico, Date dataPagamento, Integer metodo) throws DAOException;
    List<Referto> listaReferti(String searchValue, Integer idMedico, Long start, Long length, Integer column, String direction) throws DAOException;
    Long getCountReferti(Integer idMedico, String searchValue) throws DAOException;
}
