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
public class DocumentoQuery {

    public final String
        GET_BY_EMAIL,
        DELETE_DOCUMENTO,
        CREA_DOCUMENTO,
        GET_BY_PRIMARY_KEY;

    public DocumentoQuery(){
        GET_BY_EMAIL=load("get_by_email");
        DELETE_DOCUMENTO=load("delete_documento");
        CREA_DOCUMENTO=load("crea_documento");
        GET_BY_PRIMARY_KEY=load("get_documento_by_pk");
    }



    private String load(String filename){
        return getStringFromFile("/sql/documento/" + filename + ".sql");
    }

}