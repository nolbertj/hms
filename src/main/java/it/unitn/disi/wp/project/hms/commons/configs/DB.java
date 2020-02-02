/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.configs;

import it.unitn.disi.wp.project.hms.commons.utils.PropertiesLoader;

import java.io.IOException;
import java.util.Properties;

import static it.unitn.disi.wp.project.hms.commons.utils.Utils.isNullOrEmpty;

/**
 * Database bean class that read its properties file
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 30.10.2019
 */
public class DB {

    static private final String ERROR_MSG  = "DB class must be initialized with init()";

    static private String COMPANY;
    static private String DRIVER;
    static private String HOST;
    static private String PORT;
    static private String DATABASE;
    static private String USERNAME;
    static private String PASSWORD;
    static private String URL;

    private DB(){
        super();
    }

    static private DB instance = null;

    static public void init() throws IOException, InstantiationException {
        if(instance == null) {
            Properties db = PropertiesLoader.get("database.properties");

            COMPANY = db.getProperty("COMPANY");
            DRIVER = db.getProperty("DRIVER");
            HOST = db.getProperty("HOST");
            PORT = db.getProperty("PORT");
            DATABASE = db.getProperty("DATABASE");
            USERNAME = db.getProperty("USERNAME");
            PASSWORD = db.getProperty("PASSWORD");
            URL = db.getProperty("URL");

            instance = new DB();
        }
        else throw new InstantiationException("DB class was already initialized!");
    }

    static public String getCompany() {
        if(instance != null) return COMPANY;
        else throw new NullPointerException(ERROR_MSG);
    }

    static public String getDriver() {
        if(instance != null) return DRIVER;
        else throw new NullPointerException(ERROR_MSG);
    }

    static public String getHost() {
        if(instance != null) return HOST;
        else throw new NullPointerException(ERROR_MSG);
    }

    static public Integer getPort() {
        if(instance != null) return Integer.valueOf(PORT);
        else throw new NullPointerException(ERROR_MSG);
    }

    static public String getDatabaseName() {
        if(instance != null) return DATABASE;
        else throw new NullPointerException(ERROR_MSG);
    }

    static public String getUsername() {
        if(instance != null) return USERNAME;
        else throw new NullPointerException(ERROR_MSG);
    }

    static public String getPassword() {
        if(instance != null) return PASSWORD;
        else throw new NullPointerException(ERROR_MSG);
    }

    static public String getURL() {
        if(instance != null){
            if(!isNullOrEmpty(URL)){
                return URL;
            }
            else {
                if(COMPANY!=null && HOST!=null && PORT!=null && DATABASE!=null)
                    return "jdbc:" + COMPANY.toLowerCase() + "://" + HOST + ":" + PORT + "/" + DATABASE;
                else
                    throw new NullPointerException("PROPERTIES COMPANY, HOST, PORT & DATABASE ARE REQUESTED!");
            }
        }
        else throw new NullPointerException(ERROR_MSG);
    }

}
