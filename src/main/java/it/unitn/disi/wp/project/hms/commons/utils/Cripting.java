/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 8.10.2019
 */
public class Cripting {

    private static final int LONG_SALT=12;

    public static  boolean checkPassword(String inserita, String hashed){
        return BCrypt.checkpw(inserita, hashed);
    }

    public static String hashPwd(String pass){
        return BCrypt.hashpw(pass, BCrypt.gensalt(LONG_SALT));
    }

}