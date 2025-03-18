/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.miniproject.models;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
/**
 *
 * @author Luu Bao
 */
@Entity
@Table(name = "Publishers")
@NamedQueries({
    @NamedQuery(name = "Publishers.findAll", query = "SELECT p FROM Publishers p"),
    @NamedQuery(name = "Publishers.findByPublisherId", query = "SELECT p FROM Publishers p WHERE p.publisherId = :publisherId"),
    @NamedQuery(name = "Publishers.findByName", query = "SELECT p FROM Publishers p WHERE p.name = :name"),
    @NamedQuery(name = "Publishers.findByEmail", query = "SELECT p FROM Publishers p WHERE p.email = :email"),
    @NamedQuery(name = "Publishers.findByWebsite", query = "SELECT p FROM Publishers p WHERE p.website = :website")})
public class Publishers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "publisherId")
    private Integer publisherId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Column(name = "website")
    private String website;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publisherId")
    private List<Books> booksList;

    public Publishers() {
    }

    public Publishers(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Publishers(Integer publisherId, String name, String email, String password) {
        this.publisherId = publisherId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Books> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Books> booksList) {
        this.booksList = booksList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (publisherId != null ? publisherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Publishers)) {
            return false;
        }
        Publishers other = (Publishers) object;
        if ((this.publisherId == null && other.publisherId != null) || (this.publisherId != null && !this.publisherId.equals(other.publisherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.aptech.miniproject.models.Publishers[ publisherId=" + publisherId + " ]";
    }

}
