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
public class FarmaciaQuery {

    public final String
        GET_BY_EMAIL,
        EROGA_RICETTA;

    public FarmaciaQuery(){
        GET_BY_EMAIL=load("get_farmacia_by_email");
        EROGA_RICETTA=load("eroga_ricetta");
    }



    private String load(String filename){
        return getStringFromFile("/sql/farmacia/" + filename + ".sql");
    }

}