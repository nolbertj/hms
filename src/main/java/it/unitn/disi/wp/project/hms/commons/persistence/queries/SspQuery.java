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
public class SspQuery {

    public final String
        GET_BY_EMAIL,
        GET_BY_PK,


        GENERA_REPORT_1,
        GENERA_REPORT_2,

        RICHIAMO_1,
        RICHIAMO_2,
        RICHIAMO_3,
        RICHIAMO_4,
        COUNT_RICHIAMI,
        GET_RICHIAMI,

        COUNT_AMBULATORI,
        GET_AMBULATORI,

        EROGA_ESAME_1,
        EROGA_ESAME_2,
        INSERISCI_PAGAMENTO;

    public SspQuery(){

        GET_BY_EMAIL=load("get_ssp_by_mail");
        GET_BY_PK=load("get_ssp_by_pk");
        GENERA_REPORT_1=load("get_farmaci_report_1");
        GENERA_REPORT_2=load("get_farmaci_report_2");

        RICHIAMO_1=load("do_richiamo_1");
        RICHIAMO_2=load("do_richiamo_2");
        RICHIAMO_3=load("do_richiamo_3");
        RICHIAMO_4=load("do_richiamo_4");
        COUNT_RICHIAMI=load("get_count_richiami");
        GET_RICHIAMI=load("get_richiami");

        COUNT_AMBULATORI=load("count_ambulatori");
        GET_AMBULATORI=load("get_ambulatori");

        EROGA_ESAME_1 = MedicoSpecQuery.load("eroga_esame_1");
        EROGA_ESAME_2 = MedicoSpecQuery.load("eroga_esame_2");
        INSERISCI_PAGAMENTO = MedicoSpecQuery.load("insert_pagamento");
    }



    private String load(String filename){
        return getStringFromFile("/sql/ssp/" + filename + ".sql");
    }

}