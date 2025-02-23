/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17_mihai.ejecutadores;

import com.mycompany.tarea17_mihai.controlador.Controlador;
import com.mycompany.tarea17_mihai.controlador.IControlador;
import com.mycompany.tarea17_mihai.models.dao.AlumnoJPA;
import com.mycompany.tarea17_mihai.models.dao.IAlumnoDAO;
import com.mycompany.tarea17_mihai.vista.IVista;
import com.mycompany.tarea17_mihai.vista.VistaConsola;

/**
 *
 * @author mihai
 */
public class EjecutadorHIBERNATE {
        public static void main(String[] args){
        IAlumnoDAO alumnoDAO = new AlumnoJPA();
        IVista vista = new VistaConsola();
        IControlador controlador = new Controlador(vista, alumnoDAO);
        controlador.ejecutar();
        
    }
}
