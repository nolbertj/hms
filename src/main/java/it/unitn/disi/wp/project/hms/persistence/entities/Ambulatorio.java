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
 * Bean ambulatorio
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 12.11.2019
 */
public class Ambulatorio implements Serializable {

    private final transient PropertyChangeSupport PROPERTY_SUPPORT;

    private Integer id;
    private String denominazione;
    private String indirizzo;
    private String citta;
    private String provincia;
    private String contattoTelefonico;

    public Ambulatorio(){
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

        Ambulatorio that = (Ambulatorio) o;

        if (id != null ? !id.equals(that.getId()) : that.getId() != null) return false;
        if (denominazione != null ? !denominazione.equals(that.getDenominazione()) : that.getDenominazione() != null)
            return false;
        if (indirizzo != null ? !indirizzo.equals(that.getIndirizzo()) : that.getIndirizzo() != null) return false;
        if (citta != null ? !citta.equals(that.getCitta()) : that.getCitta() != null) return false;
        if (provincia != null ? !provincia.equals(that.getProvincia()) : that.getProvincia() != null) return false;
        return contattoTelefonico != null ? contattoTelefonico.equals(that.getContattoTelefonico()) : that.getContattoTelefonico() == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (denominazione != null ? denominazione.hashCode() : 0);
        result = 31 * result + (indirizzo != null ? indirizzo.hashCode() : 0);
        result = 31 * result + (citta != null ? citta.hashCode() : 0);
        result = 31 * result + (provincia != null ? provincia.hashCode() : 0);
        result = 31 * result + (contattoTelefonico != null ? contattoTelefonico.hashCode() : 0);
        return result;
    }

    /**
     * Sets new id.
     *
     * @param id New value of id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return Value of id.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets new contattoTelefonico.
     *
     * @param contattoTelefonico New value of contattoTelefonico.
     */
    public void setContattoTelefonico(String contattoTelefonico) {
        this.contattoTelefonico = contattoTelefonico;
    }

    /**
     * Sets new indirizzo.
     *
     * @param indirizzo New value of indirizzo.
     */
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    /**
     * Gets citta.
     *
     * @return Value of citta.
     */
    public String getCitta() {
        return citta;
    }

    /**
     * Sets new denominazione.
     *
     * @param denominazione New value of denominazione.
     */
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    /**
     * Gets denominazione.
     *
     * @return Value of denominazione.
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Gets indirizzo.
     *
     * @return Value of indirizzo.
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Gets contattoTelefonico.
     *
     * @return Value of contattoTelefonico.
     */
    public String getContattoTelefonico() {
        return contattoTelefonico;
    }

    /**
     * Gets provincia.
     *
     * @return Value of provincia.
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Sets new citta.
     *
     * @param citta New value of citta.
     */
    public void setCitta(String citta) {
        this.citta = citta;
    }

    /**
     * Sets new provincia.
     *
     * @param provincia New value of provincia.
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

}
