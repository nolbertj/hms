/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Laboratory
 * UniTN
 */

package it.unitn.disi.wp.lab.commons.persistence.dao.exceptions;

/**
 * L'eccezione generata quando qualcosa va storto durante il recupero di dati.
 *
 * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
 * @since 06.04.2019
 */
public class DAOException extends Exception {

    /**
     * Crea una nuova eccezione con {@code null} come messaggio specifico.
     * La causa non è inizializzata, può dunque essere inizializzata 
     * successivamente da una chiamata verso {@link #initCause}.
     */
    public DAOException() {
        super();
    }
    
    /**
     * Crea una nuova eccezione con uno specifico messaggio.
     * La causa non è inizializzata, può dunque essere inizializzata 
     * successivamente da una chiamata verso {@link #initCause}.
     *
     * @param message il messaggio desiderato. Questo viene salvato per
     * essere recuperato successivamente dal metodo {@link #getMessage()}.
     */
    public DAOException(String message) {
        super(message);
    }
    
    /**
     * Crea una nuova eccezzione con la causa specificata (come parametro) 
     * e un messaggio dettagliato <tt>(cause==null ? null : cause.toString())</tt> 
     * (solitamente contiene la classe e il messaggio dettagliato della <tt>cause</tt>).
     * Questo costruttore è utile per le eccezioni che sono poco più di wrappers 
     * per altri oggetti throwables (esempio, {@link java.security.PrivilegedActionException}).
     *
     * @param  cause la causa (che viene salvata per il recupero successivo dal metodo {@link #getCause()}).
     * (Il valore <tt>null</tt> è conestito e indica che la causa è inesistente o sconosciuta.)
     */
    public DAOException(Throwable cause) {
        super(cause);
    }

    /**
     * Crea una nuova eccezzione con la causa e messaggio specifici passati come parametro.
     * <p>Si noti che il messaggio associato alla {@code cause} <i>non</i> è automaticamente 
     * incorporato in quest'eccezzione.
     *
     * @param  message il messaggio desiderato. Questo viene salvato per 
     * essere recuperato successivamente dal metodo {@link #getMessage()}.
     * @param  cause la causa (che viene salvata per il recupero successivo dal metodo {@link #getCause()}).
     * (Il valore <tt>null</tt> è conestito e indica che la causa è inesistente o sconosciuta.)
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
