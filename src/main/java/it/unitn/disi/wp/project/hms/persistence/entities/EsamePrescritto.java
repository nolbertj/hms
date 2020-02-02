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
 * Bean degli esami prescritti di un paziente
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 22.12.2019
 */
public class EsamePrescritto extends Esame implements Serializable {

    private Integer idPrescrizione; //che non è il codice dell'esame ma l'id della prescrizione!
    private String prescrivente;
    private Date dataPrescrizione;
    private Date dataErogazione;
    private Boolean isPrescrittoDaSSP;

    public EsamePrescritto() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EsamePrescritto)) return false;
        if (!super.equals(o)) return false;
        EsamePrescritto that = (EsamePrescritto) o;
        return isPrescrittoDaSSP() == that.isPrescrittoDaSSP() &&
                Objects.equals(getIdPrescrizione(), that.getIdPrescrizione()) &&
                Objects.equals(getPrescrivente(), that.getPrescrivente()) &&
                Objects.equals(getDataPrescrizione(), that.getDataPrescrizione()) &&
                Objects.equals(getDataErogazione(), that.getDataErogazione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getIdPrescrizione(), getPrescrivente(), getDataPrescrizione(), getDataErogazione(), isPrescrittoDaSSP());
    }

    public Boolean isPrescrittoDaSSP() {
        return isPrescrittoDaSSP;
    }

    public void setPrescrittoDaSSP(boolean prescrittoDaSSP) {
        isPrescrittoDaSSP = prescrittoDaSSP;
    }

    public Integer getIdPrescrizione() {
        return idPrescrizione;
    }

    public void setIdPrescrizione(Integer idPrescrizione) {
        this.idPrescrizione = idPrescrizione;
    }

    public String getPrescrivente() {
        return prescrivente;
    }

    public void setPrescrivente(String prescrivente) {
        this.prescrivente = prescrivente;
    }

    public Date getDataPrescrizione() {
        return dataPrescrizione;
    }

    public void setDataPrescrizione(Date dataPrescrizione) {
        this.dataPrescrizione = dataPrescrizione;
    }

    public Date getDataErogazione() {
        return dataErogazione;
    }

    public void setDataErogazione(Date dataErogazione) {
        this.dataErogazione = dataErogazione;
    }
}
