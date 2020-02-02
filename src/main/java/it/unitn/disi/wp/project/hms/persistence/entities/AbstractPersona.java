/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.entities;

import java.sql.Date;

/**
 * Bean astratto per un utente di tipo "persona"
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 30.09.2019
 */
public abstract class AbstractPersona extends User {
    
    private String nome;
    private String cognome;
    private String sesso;
    private Date dataNascita;
    private String luogoNascita;
    
    private String provincia;
    private String codFiscale;
    private String contattoTelefonico; // per i tipi di medici è il contatto dell'ambulatorio

    protected AbstractPersona(){
        super();
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * @param cognome the cognome to set
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * @return the sesso
     */
    public String getSesso() {
        return sesso;
    }

    /**
     * @param sesso the sesso to set
     */
    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    /**
     * @return the luogoNascita
     */
    public String getLuogoNascita() {
        return luogoNascita;
    }

    /**
     * @param luogoNascita the luogoNascita to set
     */
    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    /**
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * @param provincia the provincia to set
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * @return the contattoTelefonico
     */
    public String getContattoTelefonico() {
        return contattoTelefonico;
    }

    /**
     * @param contattoTelefonico the contattoTelefonico to set
     */
    public void setContattoTelefonico(String contattoTelefonico) {
        this.contattoTelefonico = contattoTelefonico;
    }
    
    
    public String getCodFiscale() {
        return codFiscale;
    }

    
    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    @Override
    public String toString() {
        return (
                super.toString() + ", " + getNome() + ", " + getCognome() + ", " + getSesso() + ", "
              + getDataNascita() + ", " + getLuogoNascita() + ", " + getProvincia() + ", " + getCodFiscale() + ", " + getContattoTelefonico()
        );
    }

}
