/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Laboratory
 * UniTN
 */

package it.unitn.disi.wp.lab.commons.persistence.dao.factories.jdbc;

import it.unitn.disi.wp.lab.commons.persistence.dao.DAO;
import it.unitn.disi.wp.lab.commons.persistence.dao.exceptions.DAOFactoryException;
import it.unitn.disi.wp.lab.commons.persistence.dao.factories.DAOFactory;
import it.unitn.disi.wp.lab.commons.persistence.dao.jdbc.JDBCDAO;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Questa classe JDBC implementa {@code DAOFactory}.
 * Rappresenta una Singleton.
 *
 * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
 * @since 06.04.2019
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 03.06.2019
 */
public class JDBCDAOFactory implements DAOFactory {
    
    private final Connection CON;
    private final HashMap<Class, DAO> DAO_CACHE;

    private static final String DERBY_EMBEDDED = "org.apache.derby.jdbc.EmbeddedDriver";
    private static Boolean isDerby = false;
    
    private static JDBCDAOFactory instance; //riferimento all'istanza
    
    /**
     * Chiama questo metodo prima di usare l'istanza di questa classe.
     * 
     * @param dbUrl URL per accedere al database.
     * @param DRIVER driver del database.
     * @throws DAOFactoryException se si verifica un errore durante la configurazione
     * della dao factory.
     */
    public static void configure(String dbUrl, String DRIVER) throws DAOFactoryException {
   
        if(DRIVER.equals(DERBY_EMBEDDED)) isDerby=true;
        
        if (instance == null) {
            instance = new JDBCDAOFactory(dbUrl, DRIVER);
        } else {
            throw new DAOFactoryException("DAOFactory già configurata. Puoi chiamare la configurazione solo una volta!");
        }
    }
    
     /**
     * Chiama questo metodo prima di usare l'istanza di questa classe.
     * 
     * @param dbUrl URL per accedere al database.
     * @param DRIVER driver del database (es. org.postgresql.Driver)
     * @param USER nome utente per accedere al db (es. postgres)
     * @param PASS password per accedere al db (es. admin)
     * @throws DAOFactoryException se si verifica un errore durante la configurazione
     * della dao factory.
     */
    public static void configure(String dbUrl, String DRIVER, String USER, String PASS) throws DAOFactoryException {
        
        if(DRIVER.equals(DERBY_EMBEDDED)) isDerby=true;

        if (instance == null) {
            instance = new JDBCDAOFactory(dbUrl, DRIVER, USER, PASS);
        } else {
            throw new DAOFactoryException("DAOFactory già configurata. Puoi chiamare la configurazione solo una volta!");
        }
    }
    
    /**
     * Ritorna l'istanza <a href="https://it.wikipedia.org/wiki/Singleton" >singleton</a> della {@link DAOFactory}.
     * 
     * @return l'istanza singleton  della {@code DAOFactory}.
     * @throws DAOFactoryException se questa dao factory non è ancora stata configurata.
     */
    public static JDBCDAOFactory getInstance() throws DAOFactoryException {
        if (instance == null) {
            throw new DAOFactoryException("DAOFactory non ancora configurata. Chiama JDBCDAOFactory.configure(String dbUrl, String DRIVER) prima di usare la classe!");
        }
        return instance;
    }
    
    /**
     * Costruttore privato usato per creare l'istanza singletone di questa {@code DAOFactory}.
     * 
     * @param dbUrl URL per accedere al database.
     * @param DRIVER driver per accedere al database.
     * @throws DAOFactoryException se si verifica un errore durante la creazione della {@code DAOFactory}.
     */
    private JDBCDAOFactory(String dbUrl, String DRIVER) throws DAOFactoryException {
        
        super();

        try {
            Class.forName(DRIVER, true, getClass().getClassLoader());
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe.getMessage(), cnfe.getCause());
        }

        try {
            CON = DriverManager.getConnection(dbUrl);
        } catch (SQLException sqle) {
            throw new DAOFactoryException("Impossibile creare la connessione!", sqle);
        }
        
        DAO_CACHE = new HashMap<>();
    }
        
    /**
     * Costruttore privato usato per creare l'istanza singletone di questa {@code DAOFactory}.
     * 
     * @param dbUrl URL per accedere al database.
     * @param DRIVER driver per accedere al database.
     * @param USER nome utente per accedere al db (es. postgres)
     * @param PASS password per accedere al db (es. admin)
     * @throws DAOFactoryException se si verifica un errore durante la creazione della {@code DAOFactory}.
     */
    private JDBCDAOFactory(String dbUrl, String DRIVER, String USER, String PASS) throws DAOFactoryException {
        
        super();

        try {
            Class.forName(DRIVER, true, getClass().getClassLoader());
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException(cnfe.getMessage(), cnfe.getCause());
        }

        try {
            CON = DriverManager.getConnection(dbUrl, USER, PASS);
        } catch (SQLException sqle) {
            throw new DAOFactoryException("Impossibile creare la connessione", sqle);
        }
        
        DAO_CACHE = new HashMap<>();
    }
    

    /**
     * Chiude la connessione col sistema col database e deregistra il suo driver.
     * @throws DAOFactoryException se la chiusura della connessione non va a buon fine.
     */
    @Override
    public void shutdown() throws DAOFactoryException {
        
        try { 
            if(isDerby==true)
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            else
                CON.close(); //chiusura connessione
            
            //deregistrazione drivers
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while(drivers.hasMoreElements()){
                Driver driver = drivers.nextElement();
                try{
                    DriverManager.deregisterDriver(driver);
                }catch(SQLException e){
                    throw new DAOFactoryException("Errore durante la deregistrazione del driver " + driver, e);
                }
            } 
        } catch (SQLException sqle) {
            throw new DAOFactoryException("Impossibile chiudere la connessione!", sqle);
        }
    }

    /**
     * Ritorna la {@link DAO dao} concreta il cui tipo è la classe passata come parametro.
     *
     * @param <DAO_CLASS> il nome della classe {@code dao} da ricevere.
     * @param daoInterface l'istanza della classe della {@code dao} da ricevere.
     * @return la {@code dao} concreta il cui tipo è la classe passata come parametro.
     * @throws DAOFactoryException se si è verificato un errore durante l'operazione.
     */
    @Override
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoInterface) throws DAOFactoryException {
        
        DAO dao = DAO_CACHE.get(daoInterface);
        
        if (dao != null) {
            return (DAO_CLASS) dao;
        }
        
        Package pkg = daoInterface.getPackage();
        String prefix = pkg.getName() + ".jdbc.JDBC";
        
        try {
            Class daoClass = Class.forName(prefix + daoInterface.getSimpleName());
            
            Constructor<DAO_CLASS> constructor = daoClass.getConstructor(Connection.class);
            
            DAO_CLASS daoInstance = constructor.newInstance(CON);
            if (!(daoInstance instanceof JDBCDAO)) {
                throw new DAOFactoryException("La daoInterface passata come parametro non estende (extend) la classe JDBCDAO");
            }
            
            DAO_CACHE.put(daoInterface, daoInstance);
            
            return daoInstance;
            
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException | SecurityException ex) {
            throw new DAOFactoryException("Impossibile ritornare la DAO!", ex);
        }
    }
}
