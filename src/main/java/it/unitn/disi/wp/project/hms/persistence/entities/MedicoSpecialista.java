/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.entities;

import java.io.Serializable;

/**
 * Bean dell'utente "medico specialista"
 *
 * @author Alessandro Brighenti
 * @since 05.08.2019
 */
public class MedicoSpecialista extends AbstractPersona implements Serializable {
    
    private Integer id;
    private String specialita;
    private Ambulatorio ambulatorio;

    public MedicoSpecialista() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        MedicoSpecialista that = (MedicoSpecialista) o;

        if (!id.equals(that.getId())) return false;
        return specialita != null ? specialita.equals(that.getSpecialita()) : that.specialita == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + (specialita != null ? specialita.hashCode() : 0);
        return result;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the specialita
     */
    public String getSpecialita() {
        return specialita;
    }

    /**
     * @param specialita the specialita to set
     */
    public void setSpecialita(String specialita) {
        this.specialita = specialita;
    }

    public Ambulatorio getAmbulatorio() {
        return ambulatorio;
    }

    public void setAmbulatorio(Ambulatorio ambulatorio) {
        this.ambulatorio = ambulatorio;
    }

}
