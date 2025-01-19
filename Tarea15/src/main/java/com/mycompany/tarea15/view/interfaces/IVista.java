/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea15.view.interfaces;

import com.mycompany.tarea15.model.entidades.Alumno;
import com.mycompany.tarea15.model.entidades.Grupo;
import java.util.List;

/**
 *
 * @author mihai
 */
public interface IVista {
    
    // Mostrar el menú inicial
    void mostrarMenu();

    // Mostrar todos los alumnos
    void mostrarAlumnos(List<Alumno> alumnos);

    // Mostrar un alumno en específico
    void mostrarAlumno(Alumno alumno);

    // Mostrar el formulario para agregar un alumno nuevo
    void mostrarFormularioAlumnoNuevo();

    // Mostrar el formulario para actualizar un alumno por su PK
    void mostrarFormularioActualizacionPorPk();

    // Mostrar el formulario para eliminar un alumno por su PK
    void mostrarFormularioEliminacionPorPk();

    // Mostrar el formulario para eliminar alumnos cuyos apellidos contengan una palabra dada
    void mostrarFormularioEliminacionPorApellido();

    // Método genérico para solicitar entradas del usuario
    String solicitarEntrada(String mensaje);

    // Método para mostrar un mensaje de error o información
    void mostrarMensaje(String mensaje);
    
    void mostrarGrupos(List<Grupo> grupos);
    
}
