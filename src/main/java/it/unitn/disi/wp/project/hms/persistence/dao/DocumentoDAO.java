/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.dao;

import it.unitn.disi.wp.lab.commons.persistence.dao.DAO;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.project.hms.persistence.entities.Documento;

import java.util.List;

/**
 * DAO interface for {@link Documento}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 24.11.2019
 */
public interface DocumentoDAO extends DAO<Documento,Integer> {
    Documento getByPrimaryKeys(String emailProprietario, String filename) throws DAOException;
    List<Documento> getAll(String emailProprietario) throws DAOException;
    Boolean delete(String emailProprietario, String filename) throws DAOException;
    Boolean update(Documento documento) throws DAOException;
    Boolean create(Documento documento) throws DAOException;
}
