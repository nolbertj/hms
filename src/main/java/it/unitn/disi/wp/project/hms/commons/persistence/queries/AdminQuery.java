/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.persistence.queries;

import static it.unitn.disi.wp.project.hms.commons.utils.Utils.getStringFromFile;

/**
 *
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 26.01.2020
 */
public class AdminQuery {

    public final String GET_ADMIN, GET_TABLES, GET_COLUMNS;

    public AdminQuery(){
        GET_ADMIN   = load("get_admin");
        GET_TABLES  = load("get_tables");
        GET_COLUMNS = load("get_columns");
    }

    private String load(String filename){
        return getStringFromFile("/sql/admin/" + filename + ".sql");
    }

}