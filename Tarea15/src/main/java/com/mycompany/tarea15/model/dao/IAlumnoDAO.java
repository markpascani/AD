/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea15.model.dao;

import com.mycompany.tarea15.model.entidades.Alumno;
import java.util.List;

/**
 *
 * @author mihai
 */
public interface IAlumnoDAO {
    
    //Insertar alumno
    boolean insertarAlumno(Alumno alumno);
    
    //Get alumno por NIA
    Alumno getAlumnoPorPK(int pk);
    
    //Get todos los alumnos
    List<Alumno> getAlumnos();
    
    //Actualizar alumno
    boolean actualizarAlumnoPorPK(int pk, String nombre);
    
    //Eliminar alumno por pk
    boolean eliminarAlumnoPorPK(int pk);
    
    //Eliminar alumnos que contengan una palabra en el apellido
    boolean eliminarAlumnosPorCurso(int curso);
    
    //Buscar alumno por pk
    boolean buscarAlumnoPorPk(int pk);
}
