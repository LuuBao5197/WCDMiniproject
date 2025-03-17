/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.miniproject.models.dao;

import fpt.aptech.miniproject.models.Books;
import fpt.aptech.miniproject.models.Publishers;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author Luu Bao
 */
public class BookDAO {

    EntityManagerFactory emf;
    EntityManager em;

    public BookDAO() {
        emf = Persistence.createEntityManagerFactory("miniProject");
        em = emf.createEntityManager();
    }

    public int saveBook(Books newBook, Integer userId) {
        try {
            em.getTransaction().begin();

            // Tìm publisher theo userId
            Publishers p = em.createNamedQuery("Publishers.findByUserId", Publishers.class)
                    .setParameter("userId", userId)
                    .getSingleResult();

            if (p == null) {
                System.out.println("Lỗi: Không tìm thấy Publisher với userId " + userId);
                return -1;
            }

            // Gán publisher vào book
            newBook.setPublisherId(p);

            // Lưu book
            em.persist(newBook);

            em.getTransaction().commit();
            return 1;
        } catch (Exception e) {
            System.out.println(e.getMessage());// Hiển thị lỗi cụ thể
            // Hiển thị lỗi cụ thể
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return -1;
        }
        
    }

    public Publishers findOnePublisher(int userId) {
        return em.createNamedQuery("Publishers.findByUserId", Publishers.class).setParameter("userId", userId).getSingleResult();
    }

    public List<Books> getBooks() {
        return em.createNamedQuery("Books.findAll",Books.class).getResultList();
    }

}
