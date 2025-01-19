/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea15.model.entidades;

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
    private String ciclo;
    private String curso;
    private int idGrupo;

    public Alumno(int nia, String nombre, String apellidos, char genero, LocalDate fechaDeNacimiento, String ciclo, String curso, int idGrupo) {
        this.nia = nia;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.ciclo = ciclo;
        this.curso = curso;
        this.idGrupo = idGrupo;
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
        return idGrupo;
    }

    public void setGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.nia;
        hash = 97 * hash + Objects.hashCode(this.nombre);
        hash = 97 * hash + Objects.hashCode(this.apellidos);
        hash = 97 * hash + Objects.hashCode(this.ciclo);
        hash = 97 * hash + Objects.hashCode(this.curso);
        hash = 97 * hash + Objects.hashCode(this.idGrupo);
        hash = 97 * hash + this.genero;
        hash = 97 * hash + Objects.hashCode(this.fechaDeNacimiento);
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
        if (!Objects.equals(this.ciclo, other.ciclo)) {
            return false;
        }
        if (!Objects.equals(this.curso, other.curso)) {
            return false;
        }
        if (!Objects.equals(this.idGrupo, other.idGrupo)) {
            return false;
        }
        return Objects.equals(this.fechaDeNacimiento, other.fechaDeNacimiento);
    }

    @Override
    public String toString() {
        return "Alumno{" + "nia=" + nia + ", nombre=" + nombre + ", apellidos=" + apellidos + ", ciclo=" + ciclo + ", curso=" + curso + ", idGrupo=" + idGrupo + ", genero=" + genero + ", fechaDeNacimiento=" + fechaDeNacimiento + '}';
    }
    
    
}
