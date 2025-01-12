/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea14;

import com.mycompany.tarea14.modelo.dao.clases.AlumnoDAOImpl;
import com.mycompany.tarea14.modelo.dao.interfaces.AlumnoDAO;
import com.mycompany.tarea14.modelo.entidades.Alumno;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author mihai
 */
public class Main {

    public static void main(String[] args) {
        menuAlumnos();
    }

    private static void menuAlumnos() {
        AlumnoDAO dao = AlumnoDAOImpl.getInstance();
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- MENU ALUMNOS ---");
            System.out.println("1. Insertar Alumno");
            System.out.println("2. Listar Todos");
            System.out.println("3. Buscar por NIA");
            System.out.println("4. Actualizar Alumno");
            System.out.println("5. Eliminar Alumno");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1 ->
                    insertarAlumno(dao, sc);
                case 2 ->
                    listarAlumnos(dao);
                case 3 ->
                    buscarPorNia(dao, sc);
                case 4 ->
                    actualizarAlumno(dao, sc);
                case 5 ->
                    eliminarAlumno(dao, sc);
                case 6 ->
                    System.out.println("Saliendo...");
                default ->
                    System.out.println("Opción inválida");
            }
        } while (opcion != 6);
    }

    private static void insertarAlumno(AlumnoDAO dao, Scanner sc) {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = sc.nextLine();
        System.out.print("Género (M/F): ");
        char genero = sc.nextLine().charAt(0);

        System.out.println("Fecha de nacimiento:");
        System.out.print("  Año: ");
        int anio = sc.nextInt();
        System.out.print("  Mes: ");
        int mes = sc.nextInt();
        System.out.print("  Día: ");
        int dia = sc.nextInt();
        sc.nextLine(); 

        System.out.print("Ciclo: ");
        String ciclo = sc.nextLine();
        System.out.print("Curso: ");
        String curso = sc.nextLine();
        System.out.print("Grupo (ID): ");
        int grupo = sc.nextInt();
        sc.nextLine();

        Alumno alumno = new Alumno();
        alumno.setNombre(nombre);
        alumno.setApellidos(apellidos);
        alumno.setGenero(genero);
        alumno.setFechaNacimiento(LocalDate.of(anio, mes, dia));
        alumno.setCiclo(ciclo);
        alumno.setCurso(curso);
        alumno.setGrupo(grupo);

        boolean insertado = dao.add(alumno);
        System.out.println(insertado
                ? "Alumno insertado correctamente."
                : "Error al insertar el alumno.");
    }

    private static void listarAlumnos(AlumnoDAO dao) {
        List<Alumno> lista = dao.getAll();
        if (lista.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private static void buscarPorNia(AlumnoDAO dao, Scanner sc) {
        System.out.print("NIA del alumno a buscar: ");
        int nia = sc.nextInt();
        sc.nextLine();
        Alumno alu = dao.getById(nia);
        if (alu != null) {
            System.out.println("Alumno encontrado: " + alu);
        } else {
            System.out.println("No existe un alumno con ese NIA.");
        }
    }

    private static void actualizarAlumno(AlumnoDAO dao, Scanner sc) {
        System.out.print("NIA del alumno a actualizar: ");
        int nia = sc.nextInt();
        sc.nextLine();
        Alumno alu = dao.getById(nia);
        if (alu == null) {
            System.out.println("No existe ese alumno.");
            return;
        }

        System.out.println("Datos actuales: " + alu);

        System.out.print("Nuevo nombre (enter para no cambiar): ");
        String nombre = sc.nextLine();
        if (!nombre.isBlank()) {
            alu.setNombre(nombre);
        }

        System.out.print("Nuevos apellidos (enter para no cambiar): ");
        String apellidos = sc.nextLine();
        if (!apellidos.isBlank()) {
            alu.setApellidos(apellidos);
        }

        System.out.print("Nuevo género (enter para no cambiar): ");
        String genero = sc.nextLine();
        if (!genero.isBlank()) {
            alu.setGenero(genero.charAt(0));
        }

        System.out.println("Nueva fecha de nacimiento (0 para no cambiar):");
        System.out.print("  Año: ");
        int anio = sc.nextInt();
        System.out.print("  Mes: ");
        int mes = sc.nextInt();
        System.out.print("  Día: ");
        int dia = sc.nextInt();
        sc.nextLine();
        if (anio != 0 && mes != 0 && dia != 0) {
            alu.setFechaNacimiento(LocalDate.of(anio, mes, dia));
        }

        System.out.print("Nuevo ciclo (enter para no cambiar): ");
        String ciclo = sc.nextLine();
        if (!ciclo.isBlank()) {
            alu.setCiclo(ciclo);
        }

        System.out.print("Nuevo curso (enter para no cambiar): ");
        String curso = sc.nextLine();
        if (!curso.isBlank()) {
            alu.setCurso(curso);
        }

        System.out.print("Nuevo grupo (0 para no cambiar): ");
        int grupo = sc.nextInt();
        sc.nextLine();
        if (grupo != 0) {
            alu.setGrupo(grupo);
        }

        boolean actualizado = dao.update(alu);
        System.out.println(actualizado
                ? "Alumno actualizado correctamente."
                : "Error al actualizar alumno.");
    }

    private static void eliminarAlumno(AlumnoDAO dao, Scanner sc) {
        System.out.print("NIA del alumno a eliminar: ");
        int nia = sc.nextInt();
        sc.nextLine();
        boolean eliminado = dao.delete(nia);
        System.out.println(eliminado
                ? "Alumno eliminado correctamente."
                : "No se encontró alumno con ese NIA o error al eliminar.");
    }
}
