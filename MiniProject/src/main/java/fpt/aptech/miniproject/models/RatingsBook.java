/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.miniproject.models;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author Luu Bao
 */
@Entity
@Table(name = "vw_ratingsBook")
@NamedQueries({
    @NamedQuery(name = "RatingsBook.findAll", query = "SELECT r FROM RatingsBook r"),
    @NamedQuery(name = "RatingsBook.findByBookId", query = "SELECT r FROM RatingsBook r WHERE r.bookId = :bookId"),
    @NamedQuery(name = "RatingsBook.findByTitle", query = "SELECT r FROM RatingsBook r WHERE r.title = :title"),
    @NamedQuery(name = "RatingsBook.findByPhoto", query = "SELECT r FROM RatingsBook r WHERE r.photo = :photo"),
    @NamedQuery(name = "RatingsBook.findByAuthor", query = "SELECT r FROM RatingsBook r WHERE r.author = :author"),
    @NamedQuery(name = "RatingsBook.findByAvgRating", query = "SELECT r FROM RatingsBook r WHERE r.avgRating = :avgRating")})
public class RatingsBook implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "bookId")
    private int bookId;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "photo")
    private String photo;
    @Basic(optional = false)
    @Column(name = "author")
    private String author;
    @Column(name = "avgRating")
    private Float avgRating;

    public RatingsBook() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Float avgRating) {
        this.avgRating = avgRating;
    }
    
}
