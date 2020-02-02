/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.persistence;

/**
 * Classe contenente i nomi degli attributi della {@link javax.servlet.ServletContext} e {@link javax.servlet.http.HttpSession}
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 26.10.2019
 */
public final class Attr {

//============================= ATTRIBUTI DELLA SERVLET ================================================================
    /**
     * Rappresenta la context path della webApp.<br>
     * <strong>ATTENZIONE: </strong>Va impostata una sola volta nel WebAppContextListener
     * @see it.unitn.disi.wp.project.hms.listeners.WebAppContextListener
     */
    static public String CP;

    static public final String USER_START_PATH = "/restricted/user/";
    static public final String JSP_PAGE = "jspPage";
    static public final String DAO_FACTORY = "daoFactory";
    static public final String AP = "areaPrivata";

    /**
     * Contiente il nome della cartella degli utenti e il loro percorso principale.
     * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
     * @since 04.10.2019
     */
    public enum USER_FOLDER {
        PATIENT("paziente"),
        BASE_DOCTOR("medicoBase"),
        SPECIAL_DOCTOR("medicoSpecialista"),
        PHARMACY("farmacia"),
        SSP("ssp"),
        ADMIN("admin");

        private String FOLDER_NAME;

        USER_FOLDER(String folderName) {
            this.FOLDER_NAME = folderName;
        }

        /**
         * @return <i>/restricted/user/</i>{@code nome_cartella_utente}
         */
        public String getRootPath() {
            return USER_START_PATH + FOLDER_NAME;
        }

        /**
         * @return nome della cartella dell'utente
         */
        public String getName() {
            return FOLDER_NAME;
        }

        /**
         * @return <i>media/restricted/</i>{@code nome_cartella_utente}
         */
        public String getMediaPath() {
            return "media/restricted/" + FOLDER_NAME;
        }

    }
//======================================================================================================================
//============================ ATTRIBUTI DI REQUEST ====================================================================
    static public final String USER_ROOT_PATH = "FULL_PATH_UNTIL_USER_FOLDERNAME_BEGINS";
    static public final String USER_FOLDERNAME = "USER_FOLDERNAME_WHERE_ARE_STORED_JSP_PAGES";
    static public final String INCORRECT_PWD = "INCORRECT_PASSWORD";
    static public final String TMP_EMAIL = "TEMPORARY_MAIL_FOR_LOGIN_INCORRECT_PASSWORD";
    static public final String TMP_ROLE = "TEMPORARY_STRING_USER_ROLE_FOR_LOGIN_INCORRECT_PASSWORD";
    static public final String PSWD_MODIFICATA = "PASSWORD_CHANGED_CORRECTLY";
    static public final String UPLOAD_STATUS = "UPLOAD_FILE_STATUS";
    static public final String ALERT_MSG = "ERROR_MESSAGE_TO_DISPLAY_IN_ALERT";
    static public final String SUCCESS_MSG = "SUCCESS_MESSAGE_TO_DISPLAY_IN_ALERT";
    static public final String WARNING_MSG = "WARNING_MESSAGE_TO_DISPLAY_IN_ALERT";
    static public final String RICETTA_FARMACEUTICA = "ricetta"; //lasciare così! il nome "ricetta" viene usato dalle jsp,es: ${ricetta.codice}
    static public final String REFERTO="referto";
    static public final String DOCUMENTI="documenti";
    static public final String RICEVUTA = "ricevuta";

    static public final String MED_CAMBIATO="MEDICO_CAMBIATO";
//======================================================================================================================
//============================ ATTRIBUTI DI SESSIONE ===================================================================
    static public final String USER = "USER";
//======================================================================================================================

    private Attr() {
        super();
    } //previene inutili creazioni di istanze del tipo Attr a = new Attr();

}
