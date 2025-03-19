/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.miniproject.models.dao;

import fpt.aptech.miniproject.models.Books;
import fpt.aptech.miniproject.models.Publishers;
import fpt.aptech.miniproject.models.RatingsBook;
import fpt.aptech.miniproject.models.Reviews;
import fpt.aptech.miniproject.repository.RepositoryDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author Luu Bao
 */
public class BookDAO implements RepositoryDAO<Books> {

    EntityManagerFactory emf;
    EntityManager em;

    public BookDAO() {
        emf = Persistence.createEntityManagerFactory("miniProject");
        em = emf.createEntityManager();
    }

    public int saveBook(Books newBook, int userId) {
        Publishers p;
        try {
            em.getTransaction().begin();
            // Tìm publisher theo userId
            p = em.createNamedQuery("Publishers.findByUserId", Publishers.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
            // Gán publisher vào book
            newBook.setPublisherId(p);
            if (p == null) {
                System.out.println("Lỗi: Không tìm thấy Publisher với userId " + userId);
                return -1;
            }

            // Lưu book
            em.persist(newBook);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getCause());// Hiển thị lỗi cụ thể
            e.printStackTrace(); // Hiển thị đầy đủ stack trace
            // Hiển thị lỗi cụ thể
            em.getTransaction().rollback();

            return -1;
        }
        return 1;
    }

    public Publishers findOnePublisher(int userId) {
        return em.createNamedQuery("Publishers.findByUserId", Publishers.class).setParameter("userId", userId).getSingleResult();
    }

    public List<Books> getBooks() {
        return em.createNamedQuery("Books.findAll", Books.class).getResultList();
    }

    @Override
    public List<Books> findAll() {
        return em.createNamedQuery("Books.findAll", Books.class).getResultList();
    }

    @Override
    public Books findOne(int id) {
        try {
            Books b = em.createNamedQuery("Books.findByBookId", Books.class).getSingleResult();
            return b;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void saveObject(Books newObject) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updateObject(Books editObject) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteObject(int id) {
        try {
            em.getTransaction().begin();
            Books book = em.createNamedQuery("Books.findByBookId", Books.class)
                    .setParameter("bookId", id).getSingleResult();
//                 if (book.getReviewsList() != null) {
//                     return;
//                 }
            em.remove(book);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            em.getTransaction().rollback();
        }
    }

    public List<RatingsBook> getRatingBooks() {
        return em.createNamedQuery("RatingsBook.findAll", RatingsBook.class).getResultList();
    }

    public List<Reviews> getReviewBookById(int id) {
        try {
            List<Reviews> list = em.createNamedQuery("Reviews.findByBookId", Reviews.class).setParameter("bookId", id).getResultList();
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
