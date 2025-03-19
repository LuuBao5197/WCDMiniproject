/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fpt.aptech.miniproject.models.dao;

import fpt.aptech.miniproject.models.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Valid;

/**
 *
 * @author Luu Bao
 */
public class UsesDAO {
     EntityManagerFactory emf;
    EntityManager em;

    public UsesDAO() {
        emf = Persistence.createEntityManagerFactory("miniProject");
        em = emf.createEntityManager();
    }
    public Users checkLogin(String uname, String uPass){
        try {
            Users u =  em.createNamedQuery("Users.checkLogin",Users.class).setParameter("username", uname)
                    .setParameter("password", uPass).getSingleResult();
            return u;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
