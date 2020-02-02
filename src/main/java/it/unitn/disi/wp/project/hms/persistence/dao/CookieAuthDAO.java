/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao;

import it.unitn.disi.wp.lab.commons.persistence.dao.DAO;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.persistence.entities.CookieAutenticazione;

/**
 * DAO interface for {@link CookieAutenticazione}
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since
 */
public interface CookieAuthDAO extends DAO<CookieAutenticazione, Integer> {
    CookieAutenticazione findBySelector(String selector) throws DAOException;
    boolean updateValidator(String selector, String newValidator, String userMail) throws DAOException;
    boolean insertToken(CookieAutenticazione cookie) throws DAOException;
    boolean deleteToken(String selector) throws DAOException;
}
