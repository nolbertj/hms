/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.persistence.entities.Farmacia;

/**
 * DAO interface for {@link Farmacia}
 *
 * @author
 * @since
 */
public interface FarmaciaDAO extends UserDAO {

    boolean erogaRicetta(Integer idRicetta, Integer idFarmacia) throws DAOException;

}
