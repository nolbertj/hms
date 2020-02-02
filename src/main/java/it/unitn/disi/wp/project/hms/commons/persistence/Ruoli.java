/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 */
public final class Ruoli {

    static public final String PAZIENTE = "paziente";
    static public final String MEDICO_B = "medicoBase";
    static public final String MEDICO_S = "medicoSpecialista";
    static public final String FARM = "farmacia";
    static public final String SSP = "ssp";
    static public final String ADMIN = "admin";

    static private final HashMap<String, Integer> r = new HashMap<String, Integer>() {{
        put(PAZIENTE, 0);
        put(MEDICO_B, 1);
        put(MEDICO_S, 2);
        put(FARM, 3);
        put(SSP, 4);
        put(ADMIN, 5);
    }};

    static public Integer getValue(String key){
        return r.get(key);
    }

    /**
     * Ritorna la stringa del nome della key del ruolo in base al suo valore
     * @param value valore corrispondente ad un ruolo
     * @return nome della key contenente la value
     * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
     * @since 01.11.2019
     */
    static public String getKey(Integer value){
        Optional<String> opt= r.entrySet()
                .stream()
                .filter(e -> Objects.equals(e.getValue(), value))
                .map(Map.Entry::getKey)
                .findAny();
        if(opt.isPresent()){
            return opt.get();
        }else{
            return "-1";
        }

    }

}
