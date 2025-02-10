/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.controller;

import com.mycompany.tarea17.model.dao.AlumnoDAOImpl;
import com.mycompany.tarea17.model.dao.GrupoDAOImpl;
import com.mycompany.tarea17.model.dao.interfaces.IAlumnoDAO;
import com.mycompany.tarea17.model.dao.interfaces.IGrupoDAO;
import com.mycompany.tarea17.view.IVista;
import com.mycompany.tarea17.view.VistaConsola;

/**
 * Ejecutador para la implementacion del DAO con JDBC
 * @author mihai
 */
public class Ejecutador1 {
    public static void main (String[] args){
        IAlumnoDAO alumnoDAO = new AlumnoDAOImpl();  // DAO JDBC
        IGrupoDAO grupoDAO = new GrupoDAOImpl();     // DAO JDBC
        IVista vista = new VistaConsola();
        FicheroJsonImpl ficheroJson = new FicheroJsonImpl();
        
        Controller ctrl = new Controller();
        
        ctrl.ejecutar(alumnoDAO, grupoDAO, vista, ficheroJson);
    }
}
