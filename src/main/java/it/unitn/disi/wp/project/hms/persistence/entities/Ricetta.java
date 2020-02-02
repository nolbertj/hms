/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.entities;

import com.alibaba.fastjson.JSON;
import it.unitn.disi.wp.project.hms.commons.utils.Utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Bean ricetta farmaceutica
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 11.11.2019
 */
public class Ricetta implements Serializable {

    private final transient PropertyChangeSupport PROPERTY_SUPPORT;

    private Integer codice;
    private Timestamp dataPrescrizione;
    private Integer codiceMedico;
    private Paziente paziente;
    private String medicoPrescrittore;
    private Timestamp dataErogazione;
    private String farmaciaErogante;
    private String descrizione;
    private Double totale;
    private List<FarmacoPrescritto> farmaciPrescritti;
    private transient String binaryQR;

    public Ricetta(){
        super();
        PROPERTY_SUPPORT = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        PROPERTY_SUPPORT.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        PROPERTY_SUPPORT.removePropertyChangeListener(listener);
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
     * Gets farmaciPrescritti.
     *
     * @return Value of farmaciPrescritti.
     */
    public List<FarmacoPrescritto> getFarmaciPrescritti() {
        return farmaciPrescritti;
    }

    /**
     * Sets new dataErogazione.
     *
     * @param dataErogazione New value of dataErogazione.
     */
    public void setDataErogazione(Timestamp dataErogazione) {
        this.dataErogazione = dataErogazione;
    }

    /**
     * Gets dataErogazione.
     *
     * @return Value of dataErogazione.
     */
    public Timestamp getDataErogazione() {
        return dataErogazione;
    }

    /**
     * Sets new paziente.
     *
     * @param paziente New value of paziente.
     */
    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    /**
     * Gets paziente.
     *
     * @return Value of paziente.
     */
    public Paziente getPaziente() {
        return paziente;
    }

    /**
     * Sets new medicoPrescrittore.
     *
     * @param medicoPrescrittore New value of medicoPrescrittore.
     */
    public void setMedicoPrescrittore(String medicoPrescrittore) {
        this.medicoPrescrittore = medicoPrescrittore;
    }

    /**
     * Gets dataPrescrizione.
     *
     * @return Value of dataPrescrizione.
     */
    public Timestamp getDataPrescrizione() {
        return dataPrescrizione;
    }

    /**
     * Sets new farmaciaErogante.
     *
     * @param farmaciaErogante New value of farmaciaErogante.
     */
    public void setFarmaciaErogante(String farmaciaErogante) {
        this.farmaciaErogante = farmaciaErogante;
    }

    /**
     * Gets medicoPrescrittore.
     *
     * @return Value of medicoPrescrittore.
     */
    public String getMedicoPrescrittore() {
        return medicoPrescrittore;
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
     * Sets new farmaciPrescritti.
     *
     * @param farmaciPrescritti New value of farmaciPrescritti.
     */
    public void setFarmaciPrescritti(List<FarmacoPrescritto> farmaciPrescritti) {
        this.farmaciPrescritti = farmaciPrescritti;
    }

    /**
     * Sets new dataPrescrizione.
     *
     * @param dataPrescrizione New value of dataPrescrizione.
     */
    public void setDataPrescrizione(Timestamp dataPrescrizione) {
        this.dataPrescrizione = dataPrescrizione;
    }

    /**
     * Gets farmaciaErogante.
     *
     * @return Value of farmaciaErogante.
     */
    public String getFarmaciaErogante() {
        return farmaciaErogante;
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
     * Gets descrizione.
     *
     * @return Value of descrizione.
     */
    public String getDescrizione() {
        return descrizione;
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
     * Sets new totale.
     *
     * @param totale New value of totale.
     */
    public void setTotale(Double totale) {
        this.totale = totale;
    }

    public String giveMeJSON(){
        JSONObject json = new JSONObject();
        json.put("idRicetta",this.getCodice());
        json.put("codFiscale",this.paziente.getCodFiscale());
        json.put("dataPrescrizione", Utils.getTimestampAsString(this.dataPrescrizione));
        json.put("medico", this.getCodiceMedico());
        for(int i=0;i<farmaciPrescritti.size();i++){
            if(farmaciPrescritti.get(i).getCodice() !=0){
                json.put("farmaco "+i, farmaciPrescritti.get(i).getNome());
            }
        }

        return JSON.toJSONString(json);
    }


    /**
     * Sets new binaryQR.
     *
     * @param binaryQR New value of binaryQR.
     */
    public void setBinaryQR(String binaryQR) {
        this.binaryQR = binaryQR;
    }

    /**
     * Gets binaryQR.
     *
     * @return Value of binaryQR.
     */
    public String getBinaryQR() {
        return binaryQR;
    }

    public Integer getCodiceMedico() {
        return codiceMedico;
    }

    public void setCodiceMedico(Integer codiceMedico) {
        this.codiceMedico = codiceMedico;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ricetta)) return false;
        Ricetta ricetta = (Ricetta) o;
        return Objects.equals(getCodice(), ricetta.getCodice()) &&
                Objects.equals(getDataPrescrizione(), ricetta.getDataPrescrizione()) &&
                Objects.equals(getCodiceMedico(), ricetta.getCodiceMedico()) &&
                Objects.equals(getPaziente(), ricetta.getPaziente()) &&
                Objects.equals(getMedicoPrescrittore(), ricetta.getMedicoPrescrittore()) &&
                Objects.equals(getDataErogazione(), ricetta.getDataErogazione()) &&
                Objects.equals(getFarmaciaErogante(), ricetta.getFarmaciaErogante()) &&
                Objects.equals(getDescrizione(), ricetta.getDescrizione()) &&
                Objects.equals(getTotale(), ricetta.getTotale()) &&
                Objects.equals(getFarmaciPrescritti(), ricetta.getFarmaciPrescritti());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodice(), getDataPrescrizione(), codiceMedico, getPaziente(), getMedicoPrescrittore(), getDataErogazione(), getFarmaciaErogante(), getDescrizione(), getTotale(), getFarmaciPrescritti());
    }
}
