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
public class OpenQuery {

    public final String
            COUNT_ESAMI_PRESCRIVIBILI,
            GET_ESAMI_PRESCRIVIBILI,
            CERCA_ESAME_PRESCRIVIBILE,

            COUNT_FARMACI,
            GET_FARMACI,
            CERCA_FARMACO;

    public OpenQuery(){
        COUNT_ESAMI_PRESCRIVIBILI=load("count_esami_prescrivibili");
        GET_ESAMI_PRESCRIVIBILI=load("get_esami_prescrivibili");
        CERCA_ESAME_PRESCRIVIBILE = load("cerca_esame_prescrivibile");

        COUNT_FARMACI=load("count_farmaci");
        GET_FARMACI=load("get_farmaci");
        CERCA_FARMACO=load("cerca_farmaco");
    }



    private String load(String filename){
        return getStringFromFile("/sql/open/" + filename + ".sql");
    }

}