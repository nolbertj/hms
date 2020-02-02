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
public class MedicoBaseQuery {

    public final String
        GET_BY_EMAIL,
        GET_BY_PRIMARY_KEY,

        COUNT_PAZIENTI,
        GET_LISTA_PAZIENTI,
        SEARCH_PAZIENTI_SELECT2,

        PRESCRIVI_ESAME,

        PRESCRIVI_RICETTA_1,
        PRESCRIVI_RICETTA_2,
        DELETE_RICETTA,

        CHECK_PAZIENTE_BY_USER,

        GET_APPUNTAMENTI,
        INSERT_EVENT,
        DELETE_EVENT,
        RESIZE_EVENT,
        UPDATE_EVENT_TEXT,
        UPDATE_EVENT
    ;

    public MedicoBaseQuery(){
        GET_BY_EMAIL=load("get_mb_by_email");
        GET_BY_PRIMARY_KEY=load("get_mb_by_pk");

        COUNT_PAZIENTI=load("count_pazienti");
        GET_LISTA_PAZIENTI=load("get_lista_pazienti");
        SEARCH_PAZIENTI_SELECT2=load("search_pazienti_select2");

        PRESCRIVI_ESAME = load("prescrivi_esame");

        PRESCRIVI_RICETTA_1=load("prescrivi_ricetta_1");
        PRESCRIVI_RICETTA_2=load("prescrivi_ricetta_2");
        DELETE_RICETTA=load("prescrivi_ricetta_1_delete");

        CHECK_PAZIENTE_BY_USER = load("check_paziente_by_username");

        GET_APPUNTAMENTI=load("get_events");
        INSERT_EVENT=load("insert_event");
        DELETE_EVENT=load("delete_event");
        RESIZE_EVENT=load("resize_event");
        UPDATE_EVENT_TEXT=load("update_event_text");
        UPDATE_EVENT=load("update_event");

    }



    private String load(String filename){
        return getStringFromFile("/sql/medicoBase/" + filename + ".sql");
    }

}