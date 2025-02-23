/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17_mihai.models.entidades;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author mihai
 */
public class Alumno implements Serializable{
    private static final long serialVersionUID = 1L;
    private int NIA;
    private String nombre;
    private String apellidos;
    private char genero;
    private LocalDate fechaDeNacimiento;
    private Grupo grupo;

    public Alumno(String nombre, String apellidos, char genero, LocalDate fechaDeNacimiento, Grupo grupo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.grupo = grupo;
    }

    public Alumno(int NIA, String nombre, String apellidos, char genero, LocalDate fechaDeNacimiento, Grupo grupo) {
        this(nombre, apellidos, genero, fechaDeNacimiento, grupo);
        this.NIA = NIA;
    }

    public Alumno() {
    }
    
    

    public int getNIA() {
        return NIA;
    }

    public void setNIA(int NIA) {
        this.NIA = NIA;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return "Alumno{" + "NIA=" + NIA + ", nombre=" + nombre + ", apellidos=" + apellidos + ", genero=" + genero + ", fechaDeNacimiento=" + fechaDeNacimiento + ", grupo=" + grupo + '}';
    }
    
    
    
    
    
    
    
    
}
