/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conv.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joel
 */
@Entity
@Table(name = "GNOME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gnome.findAll", query = "SELECT g FROM Gnome g"),
    @NamedQuery(name = "Gnome.findByAmount", query = "SELECT g FROM Gnome g WHERE g.amount = :amount"),
    @NamedQuery(name = "Gnome.findByName", query = "SELECT g FROM Gnome g WHERE g.name = :name")})
public class Gnome implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    private int amount;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NAME")
    private String name;

    public Gnome() {
    }

    public Gnome(String name) {
        this.name = name;
    }

    public Gnome(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gnome)) {
            return false;
        }
        Gnome other = (Gnome) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "conv.model.Gnome[ name=" + name + " ]";
    }
    
}
