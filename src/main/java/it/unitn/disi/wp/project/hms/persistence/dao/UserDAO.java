/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao;

import it.unitn.disi.wp.lab.commons.persistence.dao.DAO;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.persistence.entities.User;

/**
 * DAO interface for {@link User}
 * 
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 30.09.2019
 */
public interface UserDAO extends DAO<User, String> {
    
    boolean updatePassword(User user) throws DAOException;
    <SUB_USER extends User> SUB_USER getByEmail(String email) throws DAOException;

}