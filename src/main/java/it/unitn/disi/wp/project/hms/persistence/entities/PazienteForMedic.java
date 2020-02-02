/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * 
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 02.01.2019
 */
public class PazienteForMedic extends Paziente implements Serializable {

    private Date ultimaVisita;
    private Date ultimaRicetta;

    public PazienteForMedic() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PazienteForMedic)) return false;
        if (!super.equals(o)) return false;
        PazienteForMedic that = (PazienteForMedic) o;
        return ultimaVisita.equals(that.getUltimaVisita()) &&
                ultimaRicetta.equals(that.getUltimaRicetta());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ultimaVisita, ultimaRicetta);
    }

    public Date getUltimaVisita() {
        return ultimaVisita;
    }

    public void setUltimaVisita(Date ultimaVisita) {
        this.ultimaVisita = ultimaVisita;
    }

    public Date getUltimaRicetta() {
        return ultimaRicetta;
    }

    public void setUltimaRicetta(Date ultimaRicetta) {
        this.ultimaRicetta = ultimaRicetta;
    }

}
