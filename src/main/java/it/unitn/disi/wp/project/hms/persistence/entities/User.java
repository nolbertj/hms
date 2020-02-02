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

/**
 * Bean utente generico
 * @author Nolbert Juarez &lt;nolbert dot juarezvera at studenti dot unitn dot it&gt;
 * @since 30.09.2019
 */
public class User implements Serializable {

    private final transient PropertyChangeSupport PROPERTY_SUPPORT;
    private String email;
    private String password;
    private Integer ruolo;
    private String avatarFilename;

    public User(){
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

        User user = (User) o;

        if (!email.equals(user.getEmail())) return false;
        return ruolo != null ? ruolo.equals(user.getRuolo()) : user.ruolo == null;
    }

    @Override
    public int hashCode() {
        int result = email.hashCode();
        result = 31 * result + (ruolo != null ? ruolo.hashCode() : 0);
        return result;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRuolo() {
        return ruolo;
    }

    public void setRuolo(Integer ruolo) {
        this.ruolo = ruolo;
    }

    public String getAvatarFilename() {
        return avatarFilename;
    }

    public void setAvatarFilename(String avatar_path) {
        this.avatarFilename = avatar_path;
    }

    public String getUsername() {
        if(email != null)
            return this.email.split("@")[0];
        else return "";
    }

    @Override
    public String toString() {
        return (getEmail() + ", " + getRuolo());
    }

}
