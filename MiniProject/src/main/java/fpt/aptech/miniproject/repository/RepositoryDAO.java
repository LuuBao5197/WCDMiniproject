/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fpt.aptech.miniproject.repository;

import java.util.List;

/**
 *
 * @author Admin
 */
public interface RepositoryDAO<T> {

    List<T> findAll();

    T findOne(int id);

    void saveObject(T newObject);

    void updateObject(T editObject);

    void deleteObject(int id);

}
