/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.model.entities;

import java.util.Objects;

/**
 *
 * @author mihai
 */
public class Grupo {
    private int grupo;
    private String ciclo;
    private String curso;

    public Grupo(String ciclo, String curso) {
        this.ciclo = ciclo;
        this.curso = curso;
    }

    public Grupo(int grupo, String ciclo, String curso) {
        this(ciclo, curso);
        this.grupo = grupo;

    }

    public Grupo() {
    }

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }
    
    
    public int getGrupo() {
        return grupo;
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
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.grupo;
        hash = 97 * hash + Objects.hashCode(this.ciclo);
        hash = 97 * hash + Objects.hashCode(this.curso);
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
        final Grupo other = (Grupo) obj;
        if (this.grupo != other.grupo) {
            return false;
        }
        if (!Objects.equals(this.ciclo, other.ciclo)) {
            return false;
        }
        return Objects.equals(this.curso, other.curso);
    }

    @Override
    public String toString() {
        return "Grupo{" + "grupo=" + grupo + ", ciclo=" + ciclo + ", curso=" + curso + '}';
    }
    
    
    
    
}
