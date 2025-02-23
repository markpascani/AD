/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17_mihai.models.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;

/**
 *
 * @author mihai
 */
@Entity
@Table(name = "Alumno") // Tabla 'Alumno' en la BD
public class AlumnoHB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Asume autoincrement en 'NIA'
    @Column(name = "NIA")
    private int NIA;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Apellidos", nullable = false)
    private String apellidos;

    @Column(name = "Genero", nullable = false)
    private char genero;

    @Column(name = "FechaDeNacimiento", nullable = false)
    private LocalDate fechaDeNacimiento;

    // Muchos alumnos pertenecen a un grupo.
    // 'Grupo' es la columna en la tabla 'Alumno' que actúa como FK a la tabla 'Grupo'
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "Grupo", referencedColumnName = "grupo",
            foreignKey = @ForeignKey(name = "fk_alumno_grupo"))
    private GrupoHB grupoHB;

    public AlumnoHB(String nombre, String apellidos, char genero, LocalDate fechaDeNacimiento, GrupoHB grupoHB) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.genero = genero;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.grupoHB = grupoHB;
    }

    public AlumnoHB(int NIA, String nombre, String apellidos, char genero, LocalDate fechaDeNacimiento, GrupoHB grupoHB) {
        this(nombre, apellidos, genero, fechaDeNacimiento, grupoHB);
        this.NIA = NIA;
    }

    public AlumnoHB() {
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

    public GrupoHB getGrupoHB() {
        return grupoHB;
    }

    public void setGrupoHB(GrupoHB grupoHB) {
        this.grupoHB = grupoHB;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AlumnoHB{");
        sb.append("NIA=").append(NIA)
                .append(", nombre=").append(nombre)
                .append(", apellidos=").append(apellidos)
                .append(", genero=").append(genero)
                .append(", fechaDeNacimiento=").append(fechaDeNacimiento);

        if (grupoHB != null) {
            sb.append(", grupoHB=").append(grupoHB);
        }

        sb.append('}');
        return sb.toString();
    }

}
