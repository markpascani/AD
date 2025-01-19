/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea15.view;

import com.mycompany.tarea15.model.entidades.Alumno;
import com.mycompany.tarea15.model.entidades.Grupo;
import com.mycompany.tarea15.view.interfaces.IVista;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author mihai
 */
public class VistaConsola implements IVista {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void mostrarMenu() {
        System.out.println("""
                           1. Insertar alumno nuevo.
                           2. Insertar grupo nuevo.
                           3. Mostrar todos los alumnos.
                           4. Guardar alumnos en fichero. 
                           5. Leer alumnos de un fichero y guardarlo en BD.
                           6. Modificar nombre de un alumno por NIA.
                           7. Eliminar alumno por NIA.
                           8. Eliminar alumno por curso.
                           9. Guardar todos los grupos en un fichero Json con todos los alumnos(listado).
                           10. Leer fichero Json de grupos y guardarlos en BD.
                           """);
    }

    @Override
    public void mostrarAlumnos(List<Alumno> alumnos) {
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos para mostrar.");
        } else {
            alumnos.forEach(alumno -> System.out.println(alumno.toString()));
        }
    }

    @Override
    public void mostrarAlumno(Alumno alumno) {
        if (alumno == null) {
            System.out.println("Alumno no encontrado.");
        } else {
            System.out.println(alumno.toString());
        }
    }

    @Override
    public void mostrarGrupos(List<Grupo> grupos) {
        if (grupos.isEmpty()) {
            System.out.println("No hay alumnos para mostrar.");
        } else {
            grupos.forEach(grupo -> System.out.println(grupo.toString()));
        }
    }

    @Override
    public void mostrarFormularioAlumnoNuevo() {
        System.out.println("Formulario para insertar un nuevo alumno:");
    }

    @Override
    public void mostrarFormularioActualizacionPorPk() {
        System.out.println("Formulario para actualizar un alumno por NIA:");
    }

    @Override
    public void mostrarFormularioEliminacionPorPk() {
        System.out.println("Formulario para eliminar un alumno por NIA:");
    }

    @Override
    public void mostrarFormularioEliminacionPorApellido() {
        System.out.println("Formulario para eliminar alumnos por palabra contenida en el apellido:");
    }

    @Override
    public String solicitarEntrada(String mensaje
    ) {
        System.out.print(mensaje + ": ");
        return scanner.nextLine();
    }

    @Override
    public void mostrarMensaje(String mensaje
    ) {
        System.out.println(mensaje);
    }

}
