/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Laboratory
 * UniTN
 */

package it.unitn.disi.wp.lab.commons.persistence.dao.factories;

import it.unitn.disi.wp.lab.commons.persistence.dao.DAO;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;

/**
 * Questa intefaccia deve essere implementata da tutte le {@code DAOFactor(y)}ies concrete.
 *
 * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
 * @since 06.04.2019
 */
public interface DAOFactory {

    /**
     * Chiude la connessione col sistema di archiviazione.
     *
     * @throws DAOFactoryException se la chiusura della connessione non va a buon fine.
    */
    public void shutdown() throws DAOFactoryException;
    
    /**
     * Restituisce la {@link DAO dao} concreta il cui tipo è la classe passata 
     * come parametro.
     * 
     * @param <DAO_CLASS> il nome della classe della {@code dao} da ricevere.
     * @param daoInterface l'istanza della classe della {@code dao} da ricevere.
     * @return la {@code dao} concreta il cui tipo è la classe passata come parametro.
     * @throws DAOFactoryException se si è verificato un errore durante l'operazione.
     */
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoInterface) throws DAOFactoryException;
}
