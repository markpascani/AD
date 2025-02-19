/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.controller;

import com.mycompany.tarea17.model.dao.utils.FicheroJson;
import com.mycompany.tarea17.model.dao.AlumnoDAOImplHibernate;
import com.mycompany.tarea17.model.dao.GrupoDAOImplHibernate;
import com.mycompany.tarea17.model.dao.interfaces.IAlumnoDAO;
import com.mycompany.tarea17.model.dao.interfaces.IGrupoDAO;
import com.mycompany.tarea17.model.dao.utils.FicheroBinario;
import com.mycompany.tarea17.model.dao.utils.HibernateUtil;
import com.mycompany.tarea17.view.IVista;
import com.mycompany.tarea17.view.VistaConsola;

/**
 * Ejecutador para la implementacion del DAO con Hibernate JPA
 *
 * @author mihai
 */
public class Ejecutador2 {

    public static void main(String[] args) {
        IAlumnoDAO alumnoDAO = new AlumnoDAOImplHibernate();
        IGrupoDAO grupoDAO = new GrupoDAOImplHibernate();
        IVista vista = new VistaConsola();
        FicheroJson ficheroDAO = new FicheroJson();
        FicheroBinario ficheroBinario = new FicheroBinario();

        Controller ctrl = new Controller();
        try {
            ctrl.ejecutar(alumnoDAO, grupoDAO, vista, ficheroDAO, ficheroBinario);
        } finally {
            System.out.println("Hibernate cerrado correctamente.");
        }

    }
}
