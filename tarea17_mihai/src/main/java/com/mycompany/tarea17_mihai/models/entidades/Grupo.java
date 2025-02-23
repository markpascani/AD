/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17_mihai.models.entidades;

import com.mycompany.tarea17_mihai.models.entidades.Alumno;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author mihai
 */
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String ciclo;
    private String curso;
    private List<Alumno> alumnos;

    public Grupo(String ciclo, String curso, List<Alumno> alumnos) {
        this.ciclo = ciclo;
        this.curso = curso;
        this.alumnos = alumnos;
    }

    public Grupo(int id, String ciclo, String curso, List<Alumno> alumnos) {
        this(ciclo, curso, alumnos);
        this.id = id;
    }

    public Grupo() {
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

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Grupo{");
        sb.append("id=").append(id)
                .append(", ciclo=").append(ciclo)
                .append(", curso=").append(curso);

        if (alumnos != null) {
            sb.append(", alumnos=[");
            for (int i = 0; i < alumnos.size(); i++) {
                Alumno a = alumnos.get(i);
                sb.append(a.getNIA());
                if (i < alumnos.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("]");
        }

        sb.append('}');
        return sb.toString();
    }

}
