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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Luu Bao
 */
@Entity
@Table(name = "Users")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.checkLogin", query = "SELECT u FROM Users u WHERE u.username = :username AND u.password =:password"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByEmail", query = "SELECT u FROM Users u WHERE u.email = :email")})
public class Users implements Serializable {

    @Basic(optional = false)
    @NotEmpty(message = "Password can't be empty!")
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Publishers> publishersList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userId")
    private Integer userId;
    @Basic(optional = false)
    @NotEmpty(message = "Username can't be empty!")
    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Reviews> reviewsList;
    @JoinColumn(name = "roleId", referencedColumnName = "roleId")
    @ManyToOne(optional = false)
    private Roles roleId;

    public Users() {
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Users(Integer userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<Reviews> getReviewsList() {
        return reviewsList;
    }

    public void setReviewsList(List<Reviews> reviewsList) {
        this.reviewsList = reviewsList;
    }

    public Roles getRoleId() {
        return roleId;
    }

    public void setRoleId(Roles roleId) {
        this.roleId = roleId;
    }


    public List<Publishers> getPublishersList() {
        return publishersList;
    }

    public void setPublishersList(List<Publishers> publishersList) {
        this.publishersList = publishersList;
    }

}
