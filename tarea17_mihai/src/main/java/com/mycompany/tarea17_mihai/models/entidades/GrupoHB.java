/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17_mihai.models.entidades;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihai
 */
@Entity
@Table(name = "Grupo")  // Tabla 'Grupo' en la BD
public class GrupoHB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement en 'Grupo'
    @Column(name = "Grupo")
    private int id;

    @Column(name = "Ciclo", nullable = false)
    private String ciclo;

    @Column(name = "Curso", nullable = false)
    private String curso;

    // Un grupo tiene muchos alumnos (OneToMany).
    // La columna 'Grupo' en la tabla 'Alumno' es la FK que apunta a esta entidad.
    @OneToMany(mappedBy = "grupoHB", cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<AlumnoHB> alumnosHB = new ArrayList<>();

    public GrupoHB(String ciclo, String curso, List<AlumnoHB> alumnosHB) {
        this.ciclo = ciclo;
        this.curso = curso;
        this.alumnosHB = alumnosHB;
    }

    public GrupoHB(int id, String ciclo, String curso, List<AlumnoHB> alumnosHB) {
        this.id = id;
        this.ciclo = ciclo;
        this.curso = curso;
        this.alumnosHB = alumnosHB;
    }

    public GrupoHB() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<AlumnoHB> getAlumnosHB() {
        return alumnosHB;
    }

    public void setAlumnosHB(List<AlumnoHB> alumnosHB) {
        this.alumnosHB = alumnosHB;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Grupo{")
                .append("id=").append(id)
                .append(", ciclo=").append(ciclo)
                .append(", curso=").append(curso);

        if (alumnosHB != null) {
            sb.append(", alumnos=").append(alumnosHB);
        }

        sb.append('}');
        return sb.toString();
    }

}
