/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.view;

import com.mycompany.tarea17.model.entities.Alumno;
import com.mycompany.tarea17.model.entities.Grupo;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author mihai
 */
public class VistaConsola implements IVista{
    
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void mostrarMenu() {
        System.out.println("""
                           1. Insertar alumno nuevo.
                           2. Insertar grupo nuevo.
                           3. Mostrar todos los alumnos.
                           4. Mostrar todos los laumnos de un grupo. 
                           5. Cambiar grupo de un alumno.
                           6. Modificar nombre de un alumno por NIA.
                           7. Eliminar alumno por NIA.
                           8. Eliminar alumno por curso.
                           9. Guardar un grupo con sus alumnos en un fichero Json.
                           10. Mostrar alumno de una lista.
                           11. Guardar alumnos de db en un fichero binario.
                           12. Leer alumnos de fichero binario y guardarlos en db.
                           -> 0. Salir.
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
        return scanner.nextLine().trim();
    }

    @Override
    public void mostrarMensaje(String mensaje
    ) {
        System.out.println(mensaje);
    }

}
