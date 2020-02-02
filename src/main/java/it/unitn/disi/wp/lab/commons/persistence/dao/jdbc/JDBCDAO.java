/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Laboratory
 * UniTN
 */

package it.unitn.disi.wp.lab.commons.persistence.dao.jdbc;

import it.unitn.disi.wp.lab.commons.persistence.dao.DAO;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;

import java.sql.Connection;
import java.util.HashMap;

/**
 * Questa è la classe astratta DAO (di base) che tutte le tecnologie JDBC devono estendere (extends).
 * Implementa l'interfaccia DAO.
 *
 * @param <ENTITY_CLASS> classe dell'entità che la DAO gestisce.
 * @param <PRIMARY_KEY_CLASS> classe della primary key dell'entità che la DAO gestisce.
 * 
 * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
 * @since 06.04.2019
 */
public abstract class JDBCDAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> implements DAO<ENTITY_CLASS, PRIMARY_KEY_CLASS> {
    /**
     * La JDBC {@link Connection} usata per accedere il sistema di persistenza
     */
    protected final Connection CON;
    /**
     * Lista di altre DAOs che possono interagire con questa DAO.
     */
    protected final HashMap<Class, DAO> FRIEND_DAOS;
  
    /**
     * Il costruttore di base per tutte le DAOs JDBC.
     * 
     * @param con la {@code Connection} interna.
     */
    protected JDBCDAO(Connection con) {
        super();
        this.CON = con;
        FRIEND_DAOS = new HashMap<>();
    }
    
    /**
     * Se questa DAO interagisce con altre, ritorna la classe DAO passata come parametro.
     * 
     * @param <DAO_CLASS> il nome della classe DAO che può interagire con questa DAO.
     * @param daoClass la classe della DAO che può interagire con questa DAO.
     * 
     * @return l'istanza della DAO o null se nessuna DAO del tipo passato come parametro 
     * può interagire con questa DAO.
     * 
     * @throws DAOFactoryException se si verifica un errore.
     * 
     */
    @Override
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoClass) throws DAOFactoryException {
        return (DAO_CLASS) FRIEND_DAOS.get(daoClass);
    }
}
