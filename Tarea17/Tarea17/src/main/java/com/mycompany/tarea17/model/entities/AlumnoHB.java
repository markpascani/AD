/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.model.entities;


import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author mihai
 */
@Entity(name = "Alumno")
public class AlumnoHB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NIA")
    private int nia;
    @Column(name = "Nombre", length = 255, nullable = false)
    private String nombre;
    @Column(name = "Apellidos", length = 255, nullable = false)
    private String apellidos;
    @Column(name = "Genero", length = 1, nullable = false)
    private char genero;
    @Column(name = "FechaDeNacimiento", nullable = false)
    private LocalDate fechaDeNacimiento;
    @ManyToOne
    @JoinColumn(name = "Grupo", nullable = true)
    private GrupoHB grupo;

    public AlumnoHB(String nombre, String apellidos, char genero, LocalDate fechaDeNacimiento, GrupoHB grupo) {

        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.grupo = grupo;
    }

    public AlumnoHB(int nia, String nombre, String apellidos, char genero, LocalDate fechaDeNacimiento, GrupoHB grupo) {
        this(nombre, apellidos, genero, fechaDeNacimiento, grupo);
        this.nia = nia;

    }

    public AlumnoHB() {
    }

    public int getNia() {
        return nia;
    }

    public void setNia(int nia) {
        this.nia = nia;
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

    public GrupoHB getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoHB grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        return "AlumnoHB{" + "nia=" + nia + ", nombre=" + nombre + ", apellidos=" + apellidos + ", genero=" + genero + ", fechaDeNacimiento=" + fechaDeNacimiento + (grupo != null ? grupo.toString() : "SIN GRUPO") +'}';
    }
    
    
    
}
