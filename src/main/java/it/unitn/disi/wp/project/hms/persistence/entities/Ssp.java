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
 * Bean dell'utente "servizio sanitario provinciale"
 *
 * @author Alessandro Tomazzoli &lt;alessandro dot tomazzoli at studenti dot unitn dot it&gt;
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 09.08.2019
 */
public class Ssp extends User implements Serializable {

    private Integer id;
    private String nome;
    private String provincia;
    private String abbreviazione;
    private String numeroTelefono;
    private Integer numeroPazienti;
    private Integer numeroMediciBase;
    private Integer numeroMediciSpecialisti;


    public Ssp() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ssp)) return false;
        if (!super.equals(o)) return false;
        Ssp ssp = (Ssp) o;
        return getId().equals(ssp.getId()) &&
                getNome().equals(ssp.getNome()) &&
                getProvincia().equals(ssp.getProvincia()) &&
                getNumeroTelefono().equals(ssp.getNumeroTelefono());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getNome(), getProvincia(), getNumeroTelefono());
    }

    public Integer getNumeroPazienti() {
        return numeroPazienti;
    }

    public void setNumeroPazienti(Integer numeroPazienti) {
        this.numeroPazienti = numeroPazienti;
    }

    public Integer getNumeroMediciBase() {
        return numeroMediciBase;
    }

    public void setNumeroMediciBase(Integer numeroMediciBase) {
        this.numeroMediciBase = numeroMediciBase;
    }

    public Integer getNumeroMediciSpecialisti() {
        return numeroMediciSpecialisti;
    }

    public void setNumeroMediciSpecialisti(Integer numeroMediciSpecialisti) {
        this.numeroMediciSpecialisti = numeroMediciSpecialisti;
    }

    public String getAbbreviazione() {
        return abbreviazione;
    }

    public void setAbbreviazione(String abbreviazione) {
        this.abbreviazione = abbreviazione;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

}
