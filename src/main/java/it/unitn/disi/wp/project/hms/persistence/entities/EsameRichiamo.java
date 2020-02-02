/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 * Bean degli esami richiamo da parte del ssp
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 18.01.2020
 */
public class EsameRichiamo extends EsamePrescritto implements Serializable{
    private Integer idRichiamo;
    private Integer etaInizio;
    private Integer etaFine;

    public EsameRichiamo(){
        super();
    }

    public Integer getIdRichiamo() {
        return idRichiamo;
    }

    public void setIdRichiamo(Integer idRichiamo) {
        this.idRichiamo = idRichiamo;
    }

    public Integer getEtaInizio() {
        return etaInizio;
    }

    public void setEtaInizio(Integer etaInizio) {
        this.etaInizio = etaInizio;
    }

    public Integer getEtaFine() {
        return etaFine;
    }

    public void setEtaFine(Integer etaFine) {
        this.etaFine = etaFine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EsameRichiamo)) return false;
        if (!super.equals(o)) return false;
        EsameRichiamo that = (EsameRichiamo) o;
        return Objects.equals(getIdRichiamo(), that.getIdRichiamo()) &&
                Objects.equals(getEtaInizio(), that.getEtaInizio()) &&
                Objects.equals(getEtaFine(), that.getEtaFine());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getIdRichiamo(), getEtaInizio(), getEtaFine());
    }
}
