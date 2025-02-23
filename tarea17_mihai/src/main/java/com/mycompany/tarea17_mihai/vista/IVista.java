/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea17_mihai.vista;

import com.mycompany.tarea17_mihai.models.entidades.Alumno;
import com.mycompany.tarea17_mihai.models.entidades.Grupo;
import java.util.List;

/**
 *
 * @author mihai
 */
public interface IVista {
        
    
    void mostrarMenu();
    void mostrarAlumno(Alumno alumno);
    void mostrarGrupo(Grupo grupo);
    void mostrarAlumnos(List<Alumno> alumnos);
    void mostrarGrupos(List<Grupo> grupos);
    
    void mostrarFomularioNuevoAlumno();
    void mostrarFomularioNuevoGrupo();
    void mostrarMensaje(String mensaje);
    String pedirEntrada(String mensaje);
    void mostrarAlumnosPorNia(List<Alumno> alumnos);
}
