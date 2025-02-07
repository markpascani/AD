/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.model.entities;

import com.mycompany.tarea17.model.entities.Grupo;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author mihai
 */
public class Alumno {
    private int nia;
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
    
    public Alumno(int nia, String nombre, String apellidos, char genero, LocalDate fechaDeNacimiento, Grupo grupo){
        this(nombre, apellidos, genero, fechaDeNacimiento, grupo);
        this.nia = nia;
    }

    public Alumno() {
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

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.nia;
        hash = 79 * hash + Objects.hashCode(this.nombre);
        hash = 79 * hash + Objects.hashCode(this.apellidos);
        hash = 79 * hash + this.genero;
        hash = 79 * hash + Objects.hashCode(this.fechaDeNacimiento);
        hash = 79 * hash + Objects.hashCode(this.grupo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Alumno other = (Alumno) obj;
        if (this.nia != other.nia) {
            return false;
        }
        if (this.genero != other.genero) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellidos, other.apellidos)) {
            return false;
        }
        if (!Objects.equals(this.fechaDeNacimiento, other.fechaDeNacimiento)) {
            return false;
        }
        return Objects.equals(this.grupo, other.grupo);
    }

    @Override
    public String toString() {
        return "Alumno{" + "nia=" + nia + ", nombre=" + nombre + ", apellidos=" + apellidos + ", genero=" + genero + ", fechaDeNacimiento=" + fechaDeNacimiento + ", "+grupo.toString() + '}';
    }
    
    
    
}
