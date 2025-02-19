/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



/**
 *
 * @author mihai
 */
@Entity(name="Grupo")
public class GrupoHB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Grupo")
    private int grupo;
    @Column(name = "Ciclo", length = 255, nullable = false)
    private String ciclo;
    @Column(name = "Curso", length = 255, nullable = false)
    private String curso;

    public GrupoHB(String ciclo, String curso) {
        this.ciclo = ciclo;
        this.curso = curso;
    }

    public GrupoHB(int grupo, String ciclo, String curso) {
        this(ciclo, curso);
        this.grupo = grupo;
    }

    public GrupoHB() {
    }

    public int getGrupo() {
        return grupo;
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
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

    @Override
    public String toString() {
        return "GrupoHB{" + "grupo=" + grupo + ", ciclo=" + ciclo + ", curso=" + curso + '}';
    }
    
    
    
    

}
