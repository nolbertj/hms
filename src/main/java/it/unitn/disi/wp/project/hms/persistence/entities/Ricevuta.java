/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.persistence.entities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.sql.Date;

/**
 * Bean ricevuta
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 27.12.2019
 */
public class Ricevuta implements Serializable {

    private final transient PropertyChangeSupport PROPERTY_SUPPORT;

    private Integer idRicevuta;
    private Date dataErogazione;
    private Integer idCausale;
    private Double importo;
    private String metodo;
    private Date dataPagamento;
    private Paziente paziente;
    private Boolean isRicetta;
    private Boolean isEsame;
    private transient String binaryQR;

    public Ricevuta(){
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

        Ricevuta ricevuta = (Ricevuta) o;

        if (!idRicevuta.equals(ricevuta.getIdRicevuta())) return false;
        return idCausale.equals(ricevuta.getIdCausale());
    }

    @Override
    public int hashCode() {
        int result = idRicevuta.hashCode();
        result = 31 * result + idCausale.hashCode();
        return result;
    }

    /**
     * Gets importo.
     *
     * @return Value of importo.
     */
    public Double getImporto() {
        return importo;
    }

    /**
     * Sets new dataPagamento.
     *
     * @param dataPagamento New value of dataPagamento.
     */
    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    /**
     * Gets idCausale.
     *
     * @return Value of idCausale.
     */
    public Integer getIdCausale() {
        return idCausale;
    }

    /**
     * Gets metodo.
     *
     * @return Value of metodo.
     */
    public String getMetodo() {
        return metodo;
    }

    /**
     * Sets new idCausale.
     *
     * @param idCausale New value of idCausale.
     */
    public void setIdCausale(Integer idCausale) {
        this.idCausale = idCausale;
    }

    /**
     * Gets codPagamento.
     *
     * @return Value of codPagamento.
     */
    public Integer getIdRicevuta() {
        return idRicevuta;
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
     * Sets new dataPrescrizione.
     *
     * @param dataErogazione New value of dataPrescrizione.
     */
    public void setDataErogazione(Date dataErogazione) {
        this.dataErogazione = dataErogazione;
    }

    /**
     * Sets new metodo.
     *
     * @param metodo New value of metodo.
     */
    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    /**
     * Gets dataPagamento.
     *
     * @return Value of dataPagamento.
     */
    public Date getDataPagamento() {
        return dataPagamento;
    }

    /**
     * Gets dataPrescrizione.
     *
     * @return Value of dataPrescrizione.
     */
    public Date getDataErogazione() {
        return dataErogazione;
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
     * Sets new importo.
     *
     * @param importo New value of importo.
     */
    public void setImporto(Double importo) {
        this.importo = importo;
    }

    /**
     * Sets new codPagamento.
     *
     * @param idRicevuta New value of idRicevuta.
     */
    public void setIdRicevuta(Integer idRicevuta) {
        this.idRicevuta = idRicevuta;
    }

    /**
     * Sets new isEsame.
     *
     * @param isEsame New value of isEsame.
     */
    public void setIsEsame(Boolean isEsame) {
        this.isEsame = isEsame;
    }

    /**
     * Gets isFarmaco.
     *
     * @return Value of isFarmaco.
     */
    public Boolean getIsRicetta() {
        return isRicetta;
    }

    /**
     * Sets new isFarmaco.
     *
     * @param isRicetta New value of isFarmaco.
     */
    public void setIsRicetta(Boolean isRicetta) {
        this.isRicetta = isRicetta;
    }

    /**
     * Gets isEsame.
     *
     * @return Value of isEsame.
     */
    public Boolean getIsEsame() {
        return isEsame;
    }


    public String giveMeJSON(){
        JSONObject json = new JSONObject();
        json.put("idRicevuta",this.getIdRicevuta());
        json.put("idPaziente",this.paziente.getId());
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

}
