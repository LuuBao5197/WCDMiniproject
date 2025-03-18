package fpt.aptech.miniproject.dao;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import fpt.aptech.miniproject.models.Books;
import fpt.aptech.miniproject.repository.RepositoryDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author Admin
 */
public class BookDao implements RepositoryDAO<Books> {
    
    EntityManagerFactory emf;
    EntityManager em;
    
    public BookDao() {
        emf = Persistence.createEntityManagerFactory("miniProject");
        em = emf.createEntityManager();
    }
    
    @Override
    public List<Books> findAll() {
        return em.createNamedQuery("Books.findAll", Books.class).getResultList();
    }
    

    @Override
    public void deleteObject(int id) {
        try {
            em.getTransaction().begin();
            Books book = em.find(Books.class, id);
            em.remove(book);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }
    }

    @Override
    public Books findOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void saveObject(Books newObject) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateObject(Books editObject) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
