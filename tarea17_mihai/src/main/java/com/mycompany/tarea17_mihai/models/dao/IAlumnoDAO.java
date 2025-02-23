/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea17_mihai.models.dao;

import com.mycompany.tarea17_mihai.models.entidades.Alumno;
import com.mycompany.tarea17_mihai.models.entidades.Grupo;
import java.util.List;

/**
 *
 * @author mihai
 */
public interface IAlumnoDAO {
    // CRUD alumnos
    void insertarAlumno(Alumno alumno);
    void insertarAlumnos(List<Alumno> alumnos);
    List<Alumno> obtenerTodosLosAlumnos();
    Alumno buscarAlumnoPorNIA(int nia);
    void actualizarAlumno(Alumno alumno);
    void eliminarAlumnoPorNIA(int nia);
    
    //CRUD para grupos
    void insertarGrupos(List<Grupo> grupos);
    List<Grupo> obtenerGrupos();
    Grupo obtenerGrupoPorId(int idGrupo);
    

    // Metodos adicionales
    void eliminarAlumnosPorCoincidenciaDeApellido(String palabra);
    void eliminarAlumnosPorCurso(String curso);
    List<Alumno> obtenerAlumnosPorGrupo(int idGrupo);
    void cambiarAlumnoDeGrupo(int nia, int nuevoGrupoId);
    
}
