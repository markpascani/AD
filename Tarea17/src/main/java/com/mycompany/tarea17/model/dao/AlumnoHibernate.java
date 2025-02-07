/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.model.dao;

import com.mycompany.tarea17.model.entities.AlumnoHB;
import java.time.LocalDate;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;




/**
 *
 * @author mihai
 */
public class AlumnoHibernate {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AlumnosHibernateJPA");
        
        EntityManager em = emf.createEntityManager();
        
        AlumnoHB alumno = new AlumnoHB("Mihai", "Stinga", 'H', LocalDate.of(1995, 07, 17), null);
        
        em.getTransaction().begin();
        
        em.persist(alumno);
        
        
        em.getTransaction().commit();
        em.close();
        emf.close();
        
        
    }
  
}
