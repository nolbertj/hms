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
public class MedicoSpecQuery {

    public final String
        GET_BY_EMAIL,

        EROGA_ESAME_1,
        EROGA_ESAME_2,
        INSERISCI_PAGAMENTO,

        GET_APPUNTAMENTI,
        INSERT_EVENT,
        DELETE_EVENT,
        RESIZE_EVENT,
        UPDATE_EVENT_TEXT,
        UPDATE_EVENT,

        COUNT_REFERTI,
        GET_LISTA_REFERTI;

    public MedicoSpecQuery(){
        GET_BY_EMAIL=load("get_ms_by_email");

        EROGA_ESAME_1 = load("eroga_esame_1");
        EROGA_ESAME_2 = load("eroga_esame_2");
        INSERISCI_PAGAMENTO = load("insert_pagamento");

        GET_APPUNTAMENTI=load("get_events");
        INSERT_EVENT=load("insert_event");
        DELETE_EVENT=load("delete_event");
        RESIZE_EVENT=load("resize_event");
        UPDATE_EVENT_TEXT=load("update_event_text");
        UPDATE_EVENT=load("update_event");

        COUNT_REFERTI=load("count_referti");
        GET_LISTA_REFERTI=load("get_lista_referti");

    }



    static public String load(String filename){
        return getStringFromFile("/sql/medicoSpecialista/" + filename + ".sql");
    }

}