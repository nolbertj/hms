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
 * Bean dell'utente "paziente"
 * 
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 30.09.2019
 */
public class Paziente extends AbstractPersona implements Serializable {

    private Integer id;
    private String contattoEmergenza;
    private String cittaResidenza;
    private MedicoBase medicoBase;

    public Paziente() {
        super();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.id;
        hash = 23 * hash + Objects.hashCode(this.contattoEmergenza);
        hash = 23 * hash + Objects.hashCode(this.cittaResidenza);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Paziente other = (Paziente) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.contattoEmergenza, other.contattoEmergenza)) {
            return false;
        }
        if (!Objects.equals(this.cittaResidenza, other.cittaResidenza)) {
            return false;
        }
        return true;
    }


    public String getCittaResidenza() {
        return cittaResidenza;
    }

    public void setCittaResidenza(String cittaResidenza) {
        this.cittaResidenza = cittaResidenza;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getContattoEmergenza() {
        return contattoEmergenza;
    }

    public void setContattoEmergenza(String contattoEmergenza) {
        this.contattoEmergenza = contattoEmergenza;
    }

    /**
     * Sets new medicoBase.
     *
     * @param medicoBase New value of medicoBase.
     */
    public void setMedicoBase(MedicoBase medicoBase) {
        this.medicoBase = medicoBase;
    }

    /**
     * Gets medicoBase.
     *
     * @return Value of medicoBase.
     */
    public MedicoBase getMedicoBase() {
        return medicoBase;
    }

    @Override
    public String toString(){
        return (
                "Paziente[ " + getId() + ", " + super.toString() + ", " + getContattoEmergenza() + ", "
                + getCittaResidenza() + ", " + getAvatarFilename() + " ]"
        );
    }

}
