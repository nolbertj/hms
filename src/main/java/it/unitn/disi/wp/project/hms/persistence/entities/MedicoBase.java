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
 * Bean dell'utente "medico di base"
 *
 * @author Alessandro Brighenti &lt;alessandro dot brighenti at studenti dot unitn dot it&gt;
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 05.08.2019
 */
public class MedicoBase extends AbstractPersona implements Serializable {

    private Integer id;
    private Ambulatorio ambulatorio;
    private String servizioSanitario;
    private Long numeroPazienti;

    public String getServizioSanitario() {
        return servizioSanitario;
    }

    public void setServizioSanitario(String servizioSanitario) {
        this.servizioSanitario = servizioSanitario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicoBase)) return false;
        if (!super.equals(o)) return false;
        MedicoBase that = (MedicoBase) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getAmbulatorio(), that.getAmbulatorio()) &&
                Objects.equals(getServizioSanitario(), that.getServizioSanitario()) &&
                Objects.equals(getNumeroPazienti(), that.getNumeroPazienti());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getAmbulatorio(), getServizioSanitario(), getNumeroPazienti());
    }

    public Long getNumeroPazienti() {
        return numeroPazienti;
    }

    public void setNumeroPazienti(Long numeroPazienti) {
        this.numeroPazienti = numeroPazienti;
    }

    public MedicoBase() {
        super();
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
     * Sets new id.
     *
     * @param id New value of id.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Sets new ambulatorio.
     *
     * @param ambulatorio New value of ambulatorio.
     */
    public void setAmbulatorio(Ambulatorio ambulatorio) {
        this.ambulatorio = ambulatorio;
    }

    /**
     * Gets ambulatorio.
     *
     * @return Value of ambulatorio.
     */
    public Ambulatorio getAmbulatorio() {
        return ambulatorio;
    }

}
