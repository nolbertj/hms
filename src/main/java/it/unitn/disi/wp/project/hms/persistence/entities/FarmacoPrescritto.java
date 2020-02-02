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
 * Bean farmaco prescritto
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 11.11.2019
 */
public class FarmacoPrescritto extends Farmaco implements Serializable {

    private final transient PropertyChangeSupport PROPERTY_SUPPORT;

    private Integer quantita;
    private Double totale;
    private String note;

    public FarmacoPrescritto(){
        super();
        PROPERTY_SUPPORT = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        PROPERTY_SUPPORT.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        PROPERTY_SUPPORT.removePropertyChangeListener(listener);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        FarmacoPrescritto that = (FarmacoPrescritto) o;

        if (quantita != null ? !quantita.equals(that.getQuantita()) : that.getQuantita() != null) return false;
        if (totale != null ? !totale.equals(that.getTotale()) : that.getTotale() != null) return false;
        return note != null ? note.equals(that.getNote()) : that.getNote() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (quantita != null ? quantita.hashCode() : 0);
        result = 31 * result + (totale != null ? totale.hashCode() : 0);
        result = 31 * result + (note != null ? note.hashCode() : 0);
        return result;
    }

    /**
     * Sets new note.
     *
     * @param note New value of note.
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Sets new quantita.
     *
     * @param quantita New value of quantita.
     */
    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }

    /**
     * Sets new totale.
     *
     * @param totale New value of totale.
     */
    public void setTotale(Double totale) {
        this.totale = totale;
    }

    /**
     * Gets note.
     *
     * @return Value of note.
     */
    public String getNote() {
        return note;
    }


    /**
     * Gets totale.
     *
     * @return Value of totale.
     */
    public Double getTotale() {
        return totale;
    }


    /**
     * Gets quantita.
     *
     * @return Value of quantita.
     */
    public Integer getQuantita() {
        return quantita;
    }

}
