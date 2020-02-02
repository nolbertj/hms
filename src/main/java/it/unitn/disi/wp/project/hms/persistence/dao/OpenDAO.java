/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao;

import it.unitn.disi.wp.lab.commons.persistence.dao.DAO;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.persistence.entities.Esame;
import it.unitn.disi.wp.project.hms.persistence.entities.Farmaco;
import it.unitn.disi.wp.project.hms.persistence.entities.User;

import java.util.List;

import static it.unitn.disi.wp.project.hms.persistence.utils.PerGenere.PER_GENERE;

/**
 * DAO interface thah contains methods available for more than one type of {@link User}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 4.11.2019
 * @see it.unitn.disi.wp.project.hms.services.OpenService
 */
public interface OpenDAO extends DAO<Object, Integer> {

    Long getCountEsamiPrescrivibili(PER_GENERE sesso, String searchValue) throws DAOException;
    List<Esame> pageEsamiPrescrivibiliBySearchValue(String searchValue, PER_GENERE sesso, Long start, Long length, Integer column, String direction) throws DAOException;
    List<Esame> searchEsame(String searchValue) throws DAOException;

    Long getCountFarmaci(String searchValue) throws DAOException;
    List<Farmaco> pageFarmaciBySearchValue(String searchValue, Long start, Long length, Integer column, String direction) throws DAOException;
    List<Farmaco> searchFarmaco(String searchValue) throws DAOException;

}
