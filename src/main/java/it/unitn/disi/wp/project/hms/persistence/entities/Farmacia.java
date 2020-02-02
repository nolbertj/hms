/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.entities;

import java.io.Serializable;

/**
 * Bean dell'utente "farmacia"
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 05.08.2019
 */
public class Farmacia extends User implements Serializable {

    private Integer id;
    private String nome;
    private String partitaIva;
    private String citta;
    private String provincia;
    private String contattoTelefonico;

    public Farmacia() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Farmacia farmacia = (Farmacia) o;

        if (!id.equals(farmacia.id)) return false;
        if (nome != null ? !nome.equals(farmacia.getNome()) : farmacia.getNome() != null) return false;
        if (partitaIva != null ? !partitaIva.equals(farmacia.getPartitaIva()) : farmacia.getPartitaIva() != null) return false;
        if (citta != null ? !citta.equals(farmacia.getCitta()) : farmacia.getCitta() != null) return false;
        if (provincia != null ? !provincia.equals(farmacia.getProvincia()) : farmacia.getProvincia() != null) return false;
        return contattoTelefonico != null ? contattoTelefonico.equals(farmacia.getContattoTelefonico()) : farmacia.getContattoTelefonico() == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (partitaIva != null ? partitaIva.hashCode() : 0);
        result = 31 * result + (citta != null ? citta.hashCode() : 0);
        result = 31 * result + (provincia != null ? provincia.hashCode() : 0);
        result = 31 * result + (contattoTelefonico != null ? contattoTelefonico.hashCode() : 0);
        return result;
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
     * Sets new provincia.
     *
     * @param provincia New value of provincia.
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
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
     * Sets new nome.
     *
     * @param nome New value of nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
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
     * Sets new id.
     *
     * @param id New value of id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Sets new partitaIva.
     *
     * @param partitaIva New value of partitaIva.
     */
    public void setPartitaIva(String partitaIva) {
        this.partitaIva = partitaIva;
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
     * Gets nome.
     *
     * @return Value of nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Gets partitaIva.
     *
     * @return Value of partitaIva.
     */

    public String getPartitaIva() {
        return partitaIva;
    }

    /**
     * Gets provincia.
     *
     * @return Value of provincia.
     */
    public String getProvincia() {
        return provincia;
    }

}
