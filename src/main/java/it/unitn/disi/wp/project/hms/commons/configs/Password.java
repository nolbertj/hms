/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.configs;

import it.unitn.disi.wp.project.hms.commons.utils.PasswordGenerator;
import it.unitn.disi.wp.project.hms.commons.utils.PropertiesLoader;

import java.io.IOException;
import java.util.Properties;

/**
 * Password bean util
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 29.10.2019
 */
public class Password {

    static private final String ERROR_MSG = "Password class must be initialized with init()";

    static private int minLength;
    static private int maxLength;
    static private boolean atLeastOneNumber;
    static private boolean atLeastOneMaiusc;
    static private boolean atLeastOneMinusc;
    static private boolean atLeastOneSpecialCharacter;
    static private String specialCharacters;
    static private boolean acceptSpecialCharacters;

    private Password() {
        super();
    }

    static private Password instance = null;

    static public void init() throws IOException, InstantiationException {
        if(instance == null) {
            Properties PWD = PropertiesLoader.get("passwordRequirments.properties");
            try {
                maxLength = Integer.valueOf(PWD.getProperty("maxLength")).intValue();
                minLength = Integer.valueOf(PWD.getProperty("minLength")).intValue();
                atLeastOneNumber = Boolean.valueOf(PWD.getProperty("atLeastOneNumber")).booleanValue();
                atLeastOneMaiusc = Boolean.valueOf(PWD.getProperty("atLeastOneMaiusc")).booleanValue();
                atLeastOneMinusc = Boolean.valueOf(PWD.getProperty("atLeastOneMinusc")).booleanValue();
                acceptSpecialCharacters = Boolean.valueOf(PWD.getProperty("acceptSpecialCharacters")).booleanValue();
                specialCharacters = PWD.getProperty("specialCharacters");
                atLeastOneSpecialCharacter = Boolean.valueOf(PWD.getProperty("atLeastOneSpecialCharacter")).booleanValue();
            }
            catch (Exception ex) {
                throw new InstantiationException("Couldn't instantiate properties");
            }
            instance = new Password();
        }
        else throw new InstantiationException("Password class was already initialized!");
    }

    static public int getMinLength() {
        if(instance != null) return minLength;
        else throw new NullPointerException(ERROR_MSG);
    }

    static public int getMaxLength() {
        if(instance != null) return maxLength;
        else throw new NullPointerException(ERROR_MSG);
    }

    public static boolean isAtLeastOneNumber() {
        if(instance != null) return atLeastOneNumber;
        else throw new NullPointerException(ERROR_MSG);
    }

    public static boolean isAtLeastOneMaiusc() {
        if(instance != null) return atLeastOneMaiusc;
        else throw new NullPointerException(ERROR_MSG);
    }

    public static boolean isAtLeastOneMinusc() {
        if(instance != null) return atLeastOneMinusc;
        else throw new NullPointerException(ERROR_MSG);
    }

    public static boolean isAtLeastOneSpecialCharacter() {
        if(instance != null) return atLeastOneSpecialCharacter;
        else throw new NullPointerException(ERROR_MSG);
    }

    static public boolean acceptSpecialCharacters() {
        if(instance != null) return acceptSpecialCharacters;
        else throw new NullPointerException(ERROR_MSG);
    }

    static private boolean atLeastOneNumber(String str) {
        char ch;
        for(int i=0; i < str.length(); i++) {
            ch = str.charAt(i);
            if(Character.isDigit(ch))
                return true;
        }
        return false;
    }

    static private boolean atLeastOneMaiusc(String str) {
        char ch;
        for(int i=0; i < str.length(); i++) {
            ch = str.charAt(i);
            if(Character.isUpperCase(ch))
                return true;
        }
        return false;
    }

    static private boolean atLeastOneMinusc(String str) {
        char ch;
        for(int i=0; i < str.length(); i++) {
            ch = str.charAt(i);
            if(Character.isLowerCase(ch))
                return true;
        }
        return false;
    }

    static private boolean atLeastOneSpecialCharacter(String str) {
        return str.matches("(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])");
    }

    /**
     * Metodo che verifica i requisiti impostati in {@code passwordRequirments.properties}
     * @param password stringa della password da controllare
     * @return true se la password accetta i requisiti specificati, false altrimenti
     *
     * @see <a href="/WEB-INF/properties/passwordRequirments.properties">passwordRequirments.properties</a>
     */
    static public boolean checkRequirments(String password) {
        if(instance!=null){
            if(!(password==null) && !password.isEmpty() && !password.equalsIgnoreCase("")){
                if(password.length()>=minLength && password.length()<=maxLength){
                    String p = password;
                    if(acceptSpecialCharacters) {

                        // T F F F
                        if(atLeastOneNumber && !atLeastOneMaiusc && !atLeastOneMinusc && !atLeastOneSpecialCharacter){
                            if(atLeastOneNumber(p)) return true;
                            else return false;
                        }

                        // T T F F
                        else if(atLeastOneNumber && atLeastOneMaiusc && !atLeastOneMinusc && !atLeastOneSpecialCharacter){
                            if(atLeastOneNumber(p) && atLeastOneMaiusc(p)) return true;
                            else return false;
                        }

                        // T T T F
                        else if(atLeastOneNumber && atLeastOneMaiusc && atLeastOneMinusc && !atLeastOneSpecialCharacter){
                            if(atLeastOneNumber(p) && atLeastOneMaiusc(p) && atLeastOneMinusc(p)) return true;
                            else return false;
                        }

                        // T F F T
                        else if(atLeastOneNumber && !atLeastOneMaiusc && !atLeastOneMinusc && atLeastOneSpecialCharacter){
                            if(atLeastOneNumber(p) && atLeastOneSpecialCharacter(p)) return true;
                            else return false;
                        }

                        // T F T F
                        else if(atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc && !atLeastOneSpecialCharacter){
                            if(atLeastOneNumber(p) && atLeastOneMinusc(p)) return true;
                            else return false;
                        }

                        // T T F T
                        if(atLeastOneNumber && atLeastOneMaiusc && !atLeastOneMinusc && atLeastOneSpecialCharacter){
                            if(atLeastOneNumber(p) && atLeastOneMaiusc(p) && atLeastOneSpecialCharacter(p)) return true;
                            else return false;
                        }

                        // T F T T
                        if(atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc && atLeastOneSpecialCharacter){
                            if(atLeastOneNumber(p) && atLeastOneMinusc(p) && atLeastOneSpecialCharacter(p)) return true;
                            else return false;
                        }

                        // T T T T
                        else if(atLeastOneNumber && atLeastOneMaiusc && atLeastOneMinusc && atLeastOneSpecialCharacter){
                            if(atLeastOneNumber(p) && atLeastOneMaiusc(p) && atLeastOneMinusc(p) && atLeastOneSpecialCharacter(p)) return true;
                            else return true;
                        }

                        // F F F F
                        else if(!atLeastOneNumber && !atLeastOneMaiusc && !atLeastOneMinusc && !atLeastOneSpecialCharacter){
                            return true;
                        }

                        // F T F F
                        else if(!atLeastOneNumber && atLeastOneMaiusc && !atLeastOneMinusc && !atLeastOneSpecialCharacter){
                            if(atLeastOneMaiusc(p)) return true;
                            else return false;
                        }

                        // F T T F
                        else if(!atLeastOneNumber && atLeastOneMaiusc && atLeastOneMinusc && !atLeastOneSpecialCharacter){
                            if(atLeastOneMaiusc(p) && atLeastOneMinusc(p)) return true;
                            else return false;
                        }

                        // F F F T
                        else if(!atLeastOneNumber && !atLeastOneMaiusc && !atLeastOneMinusc && atLeastOneSpecialCharacter){
                            if(atLeastOneSpecialCharacter(p)) return true;
                            else return false;
                        }

                        // F F T F
                        else if(!atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc && !atLeastOneSpecialCharacter){
                            if(atLeastOneMinusc(p)) return true;
                            else return false;
                        }

                        // F T F T
                        else if(!atLeastOneNumber && atLeastOneMaiusc && !atLeastOneMinusc && atLeastOneSpecialCharacter){
                            if(atLeastOneMaiusc(p) && atLeastOneSpecialCharacter(p)) return true;
                            else return false;
                        }

                        // F F T T
                        else if(!atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc && atLeastOneSpecialCharacter){
                            if(atLeastOneMinusc(p) && atLeastOneSpecialCharacter(p)) return true;
                            else return false;
                        }

                        // F T T T
                        else if(!atLeastOneNumber && atLeastOneMaiusc && atLeastOneMinusc && atLeastOneSpecialCharacter){
                            if(atLeastOneMaiusc(p) && atLeastOneMinusc(p) && atLeastOneSpecialCharacter(p)) return true;
                            else return false;
                        }

                        else return false;
                    }
                    else {
                        if(!atLeastOneSpecialCharacter(p)) {
                            // T T T
                            if(atLeastOneNumber && atLeastOneMaiusc && atLeastOneMinusc){
                                if(atLeastOneNumber(p) && atLeastOneMaiusc(p) && atLeastOneMinusc(p)) return true;
                                else return false;
                            }

                            // T T F
                            else if(atLeastOneNumber && atLeastOneMaiusc && !atLeastOneMinusc){
                                if(atLeastOneNumber(p) && atLeastOneMaiusc(p)) return true;
                                else return false;
                            }

                            // T F F
                            else if(atLeastOneNumber && !atLeastOneMaiusc && !atLeastOneMinusc){
                                if(atLeastOneNumber(p)) return true;
                                else return false;
                            }

                            // T F T
                            else if(atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc){
                                if(atLeastOneNumber(p) && atLeastOneMinusc(p)) return true;
                                else return false;
                            }

                            // F T T
                            else if(!atLeastOneNumber && atLeastOneMaiusc && atLeastOneMinusc){
                                if(atLeastOneMaiusc(p) && atLeastOneMinusc(p)) return true;
                                else return false;
                            }

                            // F F T
                            else if(!atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc){
                                if(atLeastOneMinusc(p)) return true;
                                else return false;
                            }

                            // F T F
                            else if(!atLeastOneNumber && atLeastOneMaiusc && !atLeastOneMinusc){
                                if(atLeastOneMaiusc(p)) return true;
                                else return false;
                            }

                            // F F F
                            else if(!atLeastOneNumber && !atLeastOneMaiusc && !atLeastOneMinusc){
                                return true;
                            }

                            // F F T
                            else if(!atLeastOneNumber && !atLeastOneMaiusc && atLeastOneMinusc){
                                if(atLeastOneMinusc(p)) return true;
                                else return false;
                            }

                            else return false;
                        }
                        else return false;
                    }
                }
            }
        }
        else throw new NullPointerException(ERROR_MSG);

        return false;
    }

    static public String getRandomPassword() {
        if(instance==null) throw new NullPointerException(ERROR_MSG);

        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(atLeastOneNumber)
                .useLower(atLeastOneMinusc)
                .useUpper(atLeastOneMaiusc)
                .usePunctuation(acceptSpecialCharacters)
                .setPunctuation(specialCharacters)
                .build();

        return passwordGenerator.generate(maxLength);
    }

}