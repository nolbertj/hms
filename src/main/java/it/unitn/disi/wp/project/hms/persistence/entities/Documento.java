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

/**
 * Bean documento
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 24.11.2019
 */
public class Documento implements Serializable {

    private final transient PropertyChangeSupport PROPERTY_SUPPORT;

    private String emailProprietario;
    private String titolo;
    private String descrizione;
    private Timestamp dataCaricamento;
    private String filename;

    public Documento() {
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

        Documento documento = (Documento) o;

        if (!emailProprietario.equals(documento.getEmailProprietario())) return false;
        return filename.equals(documento.getFilename());
    }

    @Override
    public int hashCode() {
        int result = emailProprietario.hashCode();
        result = 31 * result + filename.hashCode();
        return result;
    }

    /**
     * Sets new titolo.
     *
     * @param titolo New value of titolo.
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Gets titolo.
     *
     * @return Value of titolo.
     */
    public String getTitolo() {
        return titolo;
    }

    /**
     * Gets filename.
     *
     * @return Value of filename.
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Gets emailProprietario.
     *
     * @return Value of emailProprietario.
     */
    public String getEmailProprietario() {
        return emailProprietario;
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
     * Sets new filename.
     *
     * @param filename New value of filename.
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Timestamp getDataCaricamento() {
        return dataCaricamento;
    }

    public void setDataCaricamento(Timestamp dataCaricamento) {
        this.dataCaricamento = dataCaricamento;
    }

    /**
     * Sets new descrizione.
     *
     * @param descrizione New value of descrizione.
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Sets new emailProprietario.
     *
     * @param emailProprietario New value of emailProprietario.
     */
    public void setEmailProprietario(String emailProprietario) {
        this.emailProprietario = emailProprietario;
    }

}
