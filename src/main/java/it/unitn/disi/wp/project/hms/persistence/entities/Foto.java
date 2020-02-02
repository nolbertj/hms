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
 * Bean foto generica
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 02.11.2019
 */
public class Foto implements Serializable {

    private final transient PropertyChangeSupport PROPERTY_SUPPORT;

    private Integer idPhoto;
    private Integer idOwner;
    private String filename;
    private Timestamp timeStamp;

    public Foto(){
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
        return filename.equals(((Foto)o).getFilename());
    }

    @Override
    public int hashCode() {
        return 31 + filename.hashCode();
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Sets new idOwner.
     *
     * @param idOwner New value of idOwner.
     */
    public void setIdOwner(Integer idOwner) {
        this.idOwner = idOwner;
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
     * Gets idOwner.
     *
     * @return Value of idOwner.
     */
    public Integer getIdOwner() {
        return idOwner;
    }

    /**
     * Sets new idPhoto.
     *
     * @param idPhoto New value of idPhoto.
     */
    public void setIdPhoto(Integer idPhoto) {
        this.idPhoto = idPhoto;
    }

    /**
     * Gets idPhoto.
     *
     * @return Value of idPhoto.
     */
    public Integer getIdPhoto() {
        return idPhoto;
    }


    /**
     * Sets new filename.
     *
     * @param filename New value of filename.
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

}
