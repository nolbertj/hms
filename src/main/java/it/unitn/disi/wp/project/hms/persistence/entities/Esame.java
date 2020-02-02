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
import java.util.Objects;

import static it.unitn.disi.wp.project.hms.persistence.utils.PerGenere.PER_GENERE;

/**
 * Bean esame
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 04.11.2019
 */
public class Esame implements Serializable {

    private final transient PropertyChangeSupport PROPERTY_SUPPORT;

    private Integer codice;
    private String nome;
    private String area;
    private String descrizione;
    private Double prezzo;
    private PER_GENERE perGenere;

    public Esame() {
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
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Esame that = (Esame) other;
        return codice.equals(that.getCodice()) &&
                nome.equals(that.getNome()) &&
                area.equals(that.getArea()) &&
                perGenere.equals(that.getPerGenere()) &&
                Objects.equals(descrizione, that.getDescrizione()) &&
                Objects.equals(prezzo, that.getPrezzo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(codice, nome, area, descrizione);
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
     * Gets codice.
     *
     * @return Value of codice.
     */
    public Integer getCodice() {
        return codice;
    }

    /**
     * Gets area.
     *
     * @return Value of area.
     */
    public String getArea() {
        return area;
    }

    /**
     * Gets nome.
     *
     * @return Value of nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets new nome.
     *
     * @param nome New value of nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Sets new area.
     *
     * @param area New value of area.
     */
    public void setArea(String area) {
        this.area = area;
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
     * Sets new codice.
     *
     * @param codice New value of codice.
     */
    public void setCodice(Integer codice) {
        this.codice = codice;
    }

    /**
     * Gets importo.
     *
     * @return Value of importo.
     */
    public Double getPrezzo() {
        return prezzo;
    }

    /**
     * Sets new price.
     *
     * @param prezzo New value for price.
     */
    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }


    /**
     * Gets perGenere.
     *
     * @return Value of perGenere.
     */
    public PER_GENERE getPerGenere() {
        return perGenere;
    }

    /**
     * Sets new perGenere.
     *
     * @param perGenere New value of perGenere.
     */
    public void setPerGenere(PER_GENERE perGenere) {
        this.perGenere = perGenere;
    }
}
