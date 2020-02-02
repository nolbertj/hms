/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Bean referto
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @since 08.11.2019
 */
public class Referto implements Serializable {

    private final transient PropertyChangeSupport PROPERTY_SUPPORT;

    private Integer idEsame;
    private String medicoPrescrivente;
    private String nomeEsame;
    private String medicoEsecutore;
    private Timestamp dataErogazione;
    private String anamnesi;
    private String conclusioni;
    private String paziente;
    private Integer idPaziente;

    public Integer getIdPaziente() {
        return idPaziente;
    }

    public void setIdPaziente(Integer idPaziente) {
        this.idPaziente = idPaziente;
    }

    public Referto() {
        super();
        PROPERTY_SUPPORT = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        PROPERTY_SUPPORT.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        PROPERTY_SUPPORT.removePropertyChangeListener(listener);
    }

    public String getMedicoPrescrivente() {
        return medicoPrescrivente;
    }

    public void setMedicoPrescrivente(String medicoPrescrivente) {
        this.medicoPrescrivente = medicoPrescrivente;
    }

    public String getNomeEsame() {
        return nomeEsame;
    }

    public void setNomeEsame(String nomeEsame) {
        this.nomeEsame = nomeEsame;
    }

    public String getMedicoEsecutore() {
        return medicoEsecutore;
    }

    public void setMedicoEsecutore(String medicoEsecutore) {
        this.medicoEsecutore = medicoEsecutore;
    }

    public String getPaziente() {
        return paziente;
    }

    public void setPaziente(String paziente) {
        this.paziente = paziente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Referto)) return false;
        Referto referto = (Referto) o;
        return Objects.equals(getIdEsame(), referto.getIdEsame()) &&
                Objects.equals(getMedicoPrescrivente(), referto.getMedicoPrescrivente()) &&
                Objects.equals(getNomeEsame(), referto.getNomeEsame()) &&
                Objects.equals(getMedicoEsecutore(), referto.getMedicoEsecutore()) &&
                Objects.equals(getDataErogazione(), referto.getDataErogazione()) &&
                Objects.equals(getAnamnesi(), referto.getAnamnesi()) &&
                Objects.equals(getConclusioni(), referto.getConclusioni()) &&
                Objects.equals(paziente, referto.paziente);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdEsame(), getMedicoPrescrivente(), getNomeEsame(), getMedicoEsecutore(), getDataErogazione(), getAnamnesi(), getConclusioni(), paziente);
    }

    public Integer getIdEsame() {
        return idEsame;
    }

    public void setIdEsame(Integer idEsame) {
        this.idEsame = idEsame;
    }

    public Timestamp getDataErogazione() {
        return dataErogazione;
    }

    public void setDataErogazione(Timestamp dataErogazione) {
        this.dataErogazione = dataErogazione;
    }

    public String getAnamnesi() {
        return anamnesi;
    }

    public void setAnamnesi(String anamnesi) {
        this.anamnesi = anamnesi;
    }

    public String getConclusioni() {
        return conclusioni;
    }

    public void setConclusioni(String conclusioni) {
        this.conclusioni = conclusioni;
    }

}
