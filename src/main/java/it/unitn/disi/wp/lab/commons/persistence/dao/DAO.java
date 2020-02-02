/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Laboratory
 * UniTN
 */

package it.unitn.disi.wp.lab.commons.persistence.dao;

import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOException;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;

import java.util.List;

/**
 * Interfaccia DAO di base che tutte DAOs possono (dovrebbero) implementare.
 * 
 * @param <ENTITY_CLASS> classe dell'entità da gestire
 * @param <PRIMARY_KEY_CLASS> classe della primary key dell'entita gestita nella DAO.
 * 
 * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
 * @since 06.04.2019
 */
public interface DAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> {

    /**
     * Ritorna il numero di tuple {@code ENTITY_CLASS} 
     * conservate nel sistema di archiviazione dell'applicazione.
     * 
     * @return numero di tuple presenti.
     * @throws DAOException se durante la richiesta dei dati incontra un errore.
     */
    public Long getCount() throws DAOException;

    /**
     * Ritorna l'istanza {@code ENTITY_CLASS} localizzata nel 
     * sistema di archiviazione tramite la sua corrispondente 
     * primary key passata come parametro.
     *
     * @param primaryKey classe della primary key utilizzata per ottenere l'istanza dell'entità.
     * @return l'istanza {@code ENTITY_CLASS} tramite la sua corrispondente 
     * primary key passata come parametro o {@code null} se non esistè l'entità 
     * (con tale primary key) nel sistema di archiviazione.
     * 
     * @throws DAOException se durante la richiesta dei dati incontra un errore.
     */
    public ENTITY_CLASS getByPrimaryKey(PRIMARY_KEY_CLASS primaryKey) throws DAOException;

    /**
     * Ritorna una lista di tutte le entità (valide) del tipo {@code ENTITY_CLASS}
     * conservate nel sistema di archiviazione.
     *
     * @return lista di tutte le entità (valide) del tipo {@code ENTITY_CLASS}.
     * @throws DAOException se durante la richiesta dei dati incontra un errore.
     */
    public List<ENTITY_CLASS> getAll() throws DAOException;
    
    /**
     * Se questa DAO interagisce con altre, ritorna la classe DAO passata come parametro.
     *
     * @param <DAO_CLASS> il nome della classe DAO che può interagire con questa DAO.
     * @param daoClass la classe della DAO che può interagire con questa DAO.
     * @return l'istanza della DAO o null se nessuna DAO del tipo passato come parametro 
     * può interagire con questa DAO.
     * 
     * @throws DAOFactoryException se si verifica un errore.
     */
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoClass) throws DAOFactoryException;
}
