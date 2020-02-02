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
public class PazienteQuery {

    public final String
            GET_BY_MAIL,
            GET_BY_PRIMARY_KEY,

            COUNT_MEDICI_BASE,
            GET_LISTA_MEDICI_BASE,
            GET_MEDICO_BASE,
            CHANGE_MEDIC,

            COUNT_FOTO,
            GET_LISTA_FOTO,
            UPDATE_FOTO_PAZIENTE,
            UPDATE_ANAGRAFICA,

            COUNT_ESAMI_PRESCRITTI,
            GET_ESAMI_PRESCRITTI,
            GET_ESAMI_PRESCRITTI_EROGABILI_SSP,

            COUNT_REFERTI,
            GET_REFERTI,
            GET_REFERTO_MS,
            GET_REFERTO_SSP,

            GET_RICETTA_1,
            GET_RICETTA_2,
            COUNT_RICETTE,
            GET_RICETTE,
            COUNT_RICETTE_NON_EROGATE,

            GET_ALL_SELECT2,

            COUNT_PAGAMENTI,
            GET_LISTA_PAGAMENTI,
            GET_RICEVUTA_1,
            GET_RICEVUTA_2,
            EFFETTUA_PAGAMENTO,

            GET_VISITE,
            GET_APPUNTAMENTI,

            COUNT_AMBULATORI,
            GET_AMBULATORI;

    public PazienteQuery(){
        GET_BY_MAIL=load("get_paziente_by_mail");
        GET_BY_PRIMARY_KEY=load("get_paziente_by_primary_key");


        COUNT_MEDICI_BASE=load("count_medici_base");
        GET_LISTA_MEDICI_BASE=load("get_lista_medici_base");
        GET_MEDICO_BASE=load("get_medico_base");
        CHANGE_MEDIC = load("change_medic");
        UPDATE_ANAGRAFICA = load("update_anagrafica");


        COUNT_FOTO=load("count_foto");
        GET_LISTA_FOTO=load("get_lista_foto");
        UPDATE_FOTO_PAZIENTE = load("update_foto_paziente");

        COUNT_ESAMI_PRESCRITTI=load("count_esami_prescritti");
        GET_ESAMI_PRESCRITTI = load("get_esami_prescritti");
        GET_ESAMI_PRESCRITTI_EROGABILI_SSP=load("get_esami_prescritti_erogabili_da_ssp");


        COUNT_REFERTI=load("count_referti");
        GET_REFERTI=load("get_referti");
        GET_REFERTO_MS=load("get_referto_by_ms");
        GET_REFERTO_SSP=load("get_referto_by_ssp");

        GET_RICETTA_1=load("get_ricetta_1");
        GET_RICETTA_2=load("get_ricetta_2");
        COUNT_RICETTE=load("count_ricette");
        GET_RICETTE=load("get_ricette");
        COUNT_RICETTE_NON_EROGATE=load("count_ricette_non_erogate");

        GET_ALL_SELECT2=load("get_all_for_select2");

        COUNT_PAGAMENTI=load("count_pagamenti");
        GET_LISTA_PAGAMENTI=load("get_lista_pagamenti");
        GET_RICEVUTA_1=load("get_ricevuta_1");
        GET_RICEVUTA_2=load("get_ricevuta_2");
        EFFETTUA_PAGAMENTO=load("effettua_pagamento");

        GET_VISITE=load("get_visite");
        GET_APPUNTAMENTI=load("get_events");

        COUNT_AMBULATORI=load("count_ambulatori");
        GET_AMBULATORI=load("get_ambulatori");
    }


    private String load(String filename){
        return getStringFromFile("/sql/paziente/" + filename + ".sql");
    }

}