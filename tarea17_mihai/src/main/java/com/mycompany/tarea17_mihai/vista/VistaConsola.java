/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17_mihai.vista;

import com.mycompany.tarea17_mihai.models.entidades.Alumno;
import com.mycompany.tarea17_mihai.models.entidades.Grupo;
import java.util.List;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mihai
 */
public class VistaConsola implements IVista {

    private static final Logger logger = LogManager.getLogger(VistaConsola.class);

    @Override
    public void mostrarMenu() {
        mostrarMensaje("""
        =====================================
                MENÚ PRINCIPAL
        =====================================
        1. Insertar un nuevo alumno
        2. Insertar grupos
        3. Mostrar todos los alumnos
        4. Guardar todos los alumnos en un fichero
        5. Leer alumnos de un fichero y guardarlos en la BD
        6. Modificar el nombre de un alumno por NIA
        7. Eliminar un alumno por NIA
        8. Eliminar alumnos que contengan una palabra en su apellido
        9. Eliminar alumnos de un curso indicado
        10. Guardar todos los grupos en un fichero
        11. Leer fichero de grupos y guardarlos en la BD
        12. Mostrar todos los alumnos de un grupo
        13. Mostrar un alumno a partir de un NIA elegido
        14. Cambiar un alumno de grupo
        15. Guardar un grupo en un fichero
        0. Salir
        =====================================
        """);
    }

    @Override
    public void mostrarAlumno(Alumno alumno) {
        mostrarMensaje("""
        =====================================
                 DATOS DEL ALUMNO
        =====================================
        """ + alumno.toString() + "\n====================================="
        );

    }

    @Override
    public void mostrarGrupo(Grupo grupo) {
        mostrarMensaje("""
        =====================================
                 DATOS DEL GRUPO
        =====================================
        """ + grupo.toString() + "\n====================================="
        );
    }

    @Override
    public void mostrarAlumnos(List<Alumno> alumnos) {
        if (alumnos.isEmpty()) {
            mostrarMensaje("No hay alumnos para mostrar.");
            return;
        }
        StringBuilder sb = new StringBuilder("""
        =====================================
                 LISTA DE ALUMNOS
        =====================================
        """);
        for (Alumno alumno : alumnos) {
            sb.append(alumno.toString()).append("\n");
        }
        sb.append("=====================================");
        mostrarMensaje(sb.toString());
    }
    
    @Override
    public void mostrarAlumnosPorNia(List<Alumno> alumnos) {
        if (alumnos.isEmpty()) {
            mostrarMensaje("No hay alumnos para mostrar.");
            return;
        }
        StringBuilder sb = new StringBuilder("""
        =====================================
               LISTA DE ALUMNOS POR NIA
        =====================================
        """);
        for (Alumno alumno : alumnos) {
            sb.append(alumno.getNIA()).append("\n");
        }
        sb.append("=====================================");
        mostrarMensaje(sb.toString());
    }

    @Override
    public void mostrarGrupos(List<Grupo> grupos) {
        if (grupos.isEmpty()) {
            mostrarMensaje("No hay grupos para mostrar.");
            return;
        }
        StringBuilder sb = new StringBuilder("""
        =====================================
                 LISTA DE GRUPOS
        =====================================
        """);
        for (Grupo grupo : grupos) {
            sb.append(grupo.toString()).append("\n");
        }
        sb.append("=====================================");
        mostrarMensaje(sb.toString());
    }

    @Override
    public void mostrarFomularioNuevoAlumno() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mostrarFomularioNuevoGrupo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje + ":");
    }

    @Override
    public String pedirEntrada(String mensaje) {
        System.out.print(mensaje + " ");
        Scanner sc = new Scanner(System.in);
        return sc.nextLine().trim();
    }

}
