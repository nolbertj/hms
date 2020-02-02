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
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 26.01.2020
 */
public class CookieQuery {

    public final String
            GET_BY_SELECTOR,
            UPDATE_VALIDATOR,
            INSERT_TOKEN,
            DELETE_TOKEN;

    public CookieQuery(){
        GET_BY_SELECTOR=load("get_by_selector");
        UPDATE_VALIDATOR=load("update_validator");
        INSERT_TOKEN=load("insert_token");
        DELETE_TOKEN=load("delete_token");
    }



    private String load(String filename){
        return getStringFromFile("/sql/cookie/" + filename + ".sql");
    }

}