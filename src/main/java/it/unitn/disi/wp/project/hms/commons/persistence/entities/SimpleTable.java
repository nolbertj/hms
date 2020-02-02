/*
 * AA 2018-2019
 * Introduction to Web Programming
 * Healthcare Management System
 * UniTN
 */
package it.unitn.disi.wp.project.hms.commons.persistence.entities;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.List;

/**
 * Bean tabella
 *
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 30.11.2019
 */
public class SimpleTable implements Serializable {

    private final transient PropertyChangeSupport PROPERTY_SUPPORT;

    private String name;
    private List<Column> columns;

    public SimpleTable() {
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

        SimpleTable table = (SimpleTable) o;

        return name.equals(table.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Sets new columns.
     *
     * @param columns New value of columns.
     */
    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    /**
     * Sets new name.
     *
     * @param name New value of name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets columns.
     *
     * @return Value of columns.
     */
    public List<Column> getColumns() {
        return columns;
    }

    /**
     * Gets name.
     *
     * @return Value of name.
     */
    public String getName() {
        return name;
    }

}
