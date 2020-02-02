/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The entity that describes a Select2 response entity.
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 23.11.2019
 * @see <a href="https://select2.org/">select2.org</a>
 */
public class Select2Response implements Serializable {

    private List<?> results;

    public Select2Response(List<?> results) {
        if (results == null) {
            results = new ArrayList<>();
        }
        this.results = results;
    }

    public Select2Response() {
        this(null);
    }

    public void setResults(List<?> results) {
        this.results = results;
    }

    public List<?> getResults() {
        return results;
    }

}