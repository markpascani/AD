/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.tarea1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author alumno
 */
public class GestionAlumnos {

    private List<Alumno> alumnos = new ArrayList<>();

    public void mostrarMenu(Scanner sc) {
        int opcion = -1;
        while (true) {
            System.out.println("""
                           1. Leer 5 alumnos.
                           2. Mostrar alumnos ordenados por NIA.
                           3. Mostrar alumnos ordenados por criterio.
                           """);
            try {
                opcion = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Tienes que introducir un digito.");
                e.printStackTrace();
            }
            switch (opcion) {
                case 1 ->
                    leerAlumnos(5, sc);
                case 2 ->
                    mostrarAlumnosOrdenados(1);
                case 0 -> {
                    System.out.println("Cerrando programa.");
                    break;
                }
                default -> {
                    System.out.println("Input erróneo.");
                }
            }
        }
    }
    
    private void leerAlumnos(int numero, Scanner sc){
        System.out.println("Introduce nia del alumno:");
        int nia = sc.nextInt();
        
        sc.nextLine(); //Para consumir el salto de línea que deja el nia
        System.out.println("Introduce nombre del alumno:");
        String nombre = sc.nextLine();
        
        System.out.println("Introduce apellidos del alumno: ");
        String apellidos = sc.nextLine();
        
        
        System.out.println("Introduce el genero del alumno:");
        char genero = sc.nextLine().charAt(0);
        
        System.out.println("Introduce la fecha de nacimiento(yyyy-mm-dd): ");
        String fechaString = sc.nextLine();
        LocalDate fechaNacimiento = LocalDate.parse(fechaString);
        
        System.out.println("Introduce el ciclo del alumno: ");
        String ciclo = sc.nextLine();
        
        System.out.println("Introduce el curso de alumno: ");
        String curso = sc.nextLine();
        
        System.out.println("Introduce el grupo del alumno: ");
        String grupo = sc.nextLine();
        
        Alumno alumno = new Alumno(nia, nombre, apellidos, genero, fechaNacimiento, ciclo, curso, grupo);
        System.out.println("Alumno creado en memoria.");
        this.alumnos.add(alumno);
        System.out.println("Alumno guardado en la lista de alumnos en memoria.");
        
    }
    
    private void mostrarAlumnosOrdenados(int criterio){
        
    }

    public static void main(String[] args) {

    }
}
