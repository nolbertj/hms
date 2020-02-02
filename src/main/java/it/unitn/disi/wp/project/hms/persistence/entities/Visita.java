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

/**
 * Bean visite ({@link EsamePrescritto} e {@link Ricetta})
 *
 * @author
 * @since 19.01.2020
 */
public class Visita implements Serializable {

    private final transient PropertyChangeSupport PROPERTY_SUPPORT;

    private String tipo;
    private Integer idPrescrizione;
    private String prescrivente;
    private String dataPrescrizione;
    private String dataErogazione;
    private String descrizione;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Visita visita = (Visita) o;

        if (!tipo.equals(visita.getTipo())) return false;
        if (!idPrescrizione.equals(visita.getIdPrescrizione())) return false;
        if (prescrivente != null ? !prescrivente.equals(visita.getPrescrivente()) : visita.getPrescrivente() != null)
            return false;
        if (dataPrescrizione != null ? !dataPrescrizione.equals(visita.getDataPrescrizione()) : visita.getDataPrescrizione() != null)
            return false;
        return dataErogazione != null ? dataErogazione.equals(visita.getDataErogazione()) : visita.getDataErogazione() == null;
    }

    @Override
    public int hashCode() {
        int result = tipo.hashCode();
        result = 31 * result + idPrescrizione.hashCode();
        result = 31 * result + (prescrivente != null ? prescrivente.hashCode() : 0);
        result = 31 * result + (dataPrescrizione != null ? dataPrescrizione.hashCode() : 0);
        result = 31 * result + (dataErogazione != null ? dataErogazione.hashCode() : 0);
        return result;
    }

    public Visita(){
        super();
        PROPERTY_SUPPORT = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        PROPERTY_SUPPORT.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        PROPERTY_SUPPORT.removePropertyChangeListener(listener);
    }

    /**
     * Sets new idPrescrizione.
     *
     * @param idPrescrizione New value of idPrescrizione.
     */
    public void setIdPrescrizione(Integer idPrescrizione) {
        this.idPrescrizione = idPrescrizione;
    }

    /**
     * Sets new dataErogazione.
     *
     * @param dataErogazione New value of dataErogazione.
     */
    public void setDataErogazione(String dataErogazione) {
        this.dataErogazione = dataErogazione;
    }

    /**
     * Gets dataErogazione.
     *
     * @return Value of dataErogazione.
     */
    public String getDataErogazione() {
        return dataErogazione;
    }

    /**
     * Sets new prescrivente.
     *
     * @param prescrivente New value of prescrivente.
     */
    public void setPrescrivente(String prescrivente) {
        this.prescrivente = prescrivente;
    }

    /**
     * Sets new dataPrescrizione.
     *
     * @param dataPrescrizione New value of dataPrescrizione.
     */
    public void setDataPrescrizione(String dataPrescrizione) {
        this.dataPrescrizione = dataPrescrizione;
    }

    /**
     * Gets idPrescrizione.
     *
     * @return Value of idPrescrizione.
     */
    public Integer getIdPrescrizione() {
        return idPrescrizione;
    }

    /**
     * Gets dataPrescrizione.
     *
     * @return Value of dataPrescrizione.
     */
    public String getDataPrescrizione() {
        return dataPrescrizione;
    }

    /**
     * Sets new tipo.
     *
     * @param tipo New value of tipo.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Gets tipo.
     *
     * @return Value of tipo.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Gets prescrivente.
     *
     * @return Value of prescrivente.
     */
    public String getPrescrivente() {
        return prescrivente;
    }

    /**
     * Gets descrizione.
     *
     * @return Value of descrizione.
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Sets new descrizione.
     *
     * @param descrizione New value of descrizione.
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

}
