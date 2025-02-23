/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea17_mihai.models.utils;

import com.mycompany.tarea17_mihai.models.entidades.Alumno;
import com.mycompany.tarea17_mihai.models.entidades.Grupo;
import java.util.List;

/**
 *
 * @author mihai
 */
public interface IFichero {
    
    void escribirEnUnFicheroTodosLosAlumnos(List<Alumno> alumnos);
    
    List<Alumno> leerAlumnosDeUnFichero();
    
    void escribirGruposEnFichero(List<Grupo> grupos);
    
    List<Grupo> leerFicheroParaDevolverGrupos();
}
