/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea14.modelo.entidades;

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
    private LocalDate fechaNacimiento;
    private String ciclo;
    private String curso;
    private int grupo;
    private String nombreGrupo;

    public Alumno(String nombre, String apellidos, char genero, LocalDate fechaNacimiento, String ciclo, String curso, int grupo) {

        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.ciclo = ciclo;
        this.curso = curso;
        this.grupo = grupo;
    }

    public Alumno(int nia, String nombre, String apellidos, char genero, LocalDate fechaNacimiento, String ciclo, String curso, int grupo) {
        this(nombre, apellidos, genero, fechaNacimiento, ciclo, curso, grupo);
    }



    public Alumno() {
        // Constructor vacío requerido por Jackson
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.nia;
        hash = 37 * hash + Objects.hashCode(this.nombre);
        hash = 37 * hash + Objects.hashCode(this.apellidos);
        hash = 37 * hash + this.genero;
        hash = 37 * hash + Objects.hashCode(this.fechaNacimiento);
        hash = 37 * hash + Objects.hashCode(this.ciclo);
        hash = 37 * hash + Objects.hashCode(this.curso);
        hash = 37 * hash + this.grupo;
        hash = 37 * hash + Objects.hashCode(this.nombreGrupo);
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
        if (this.grupo != other.grupo) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellidos, other.apellidos)) {
            return false;
        }
        if (!Objects.equals(this.ciclo, other.ciclo)) {
            return false;
        }
        if (!Objects.equals(this.curso, other.curso)) {
            return false;
        }
        if (!Objects.equals(this.nombreGrupo, other.nombreGrupo)) {
            return false;
        }
        return Objects.equals(this.fechaNacimiento, other.fechaNacimiento);
    }

    @Override
    public String toString() {
        return "Alumno{" + "nia=" + nia + ", nombre=" + nombre + ", apellidos=" + apellidos + ", genero=" + genero + ", fechaNacimiento=" + fechaNacimiento + ", ciclo=" + ciclo + ", curso=" + curso + ", grupo=" + grupo + ", nombreGrupo=" + nombreGrupo + '}';
    }
    
    
    
}
