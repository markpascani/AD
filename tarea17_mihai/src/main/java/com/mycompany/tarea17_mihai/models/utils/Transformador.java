/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17_mihai.models.utils;

import com.mycompany.tarea17_mihai.models.entidades.Alumno;
import com.mycompany.tarea17_mihai.models.entidades.AlumnoHB;
import com.mycompany.tarea17_mihai.models.entidades.Grupo;
import com.mycompany.tarea17_mihai.models.entidades.GrupoHB;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihai
 */
public class Transformador {

    // 1) De Alumno -> AlumnoHB
    public static AlumnoHB toAlumnoHB(Alumno alumno) {
        if (alumno == null) return null;
        AlumnoHB alumnoHB = new AlumnoHB();
        alumnoHB.setNIA(alumno.getNIA());
        alumnoHB.setNombre(alumno.getNombre());
        alumnoHB.setApellidos(alumno.getApellidos());
        alumnoHB.setGenero(alumno.getGenero());
        alumnoHB.setFechaDeNacimiento(alumno.getFechaDeNacimiento());
        
        // Mapeo de grupo: solo ID (sin lista de alumnos)
        if (alumno.getGrupo() != null) {
            GrupoHB gHB = new GrupoHB();
            gHB.setId(alumno.getGrupo().getId());
            gHB.setCiclo(alumno.getGrupo().getCiclo());
            gHB.setCurso(alumno.getGrupo().getCurso());
            alumnoHB.setGrupoHB(gHB);
        }
        return alumnoHB;
    }

    // AlumnoHB -> Alumno (shallow)
    public static Alumno toAlumno(AlumnoHB alumnoHB) {
        if (alumnoHB == null) return null;
        Alumno alumno = new Alumno();
        alumno.setNIA(alumnoHB.getNIA());
        alumno.setNombre(alumnoHB.getNombre());
        alumno.setApellidos(alumnoHB.getApellidos());
        alumno.setGenero(alumnoHB.getGenero());
        alumno.setFechaDeNacimiento(alumnoHB.getFechaDeNacimiento());
        
        // Mapeo de grupo: solo ID, ciclo, curso
        if (alumnoHB.getGrupoHB() != null) {
            Grupo g = new Grupo();
            g.setId(alumnoHB.getGrupoHB().getId());
            g.setCiclo(alumnoHB.getGrupoHB().getCiclo());
            g.setCurso(alumnoHB.getGrupoHB().getCurso());
            alumno.setGrupo(g);
        }
        return alumno;
    }

    // Grupo -> GrupoHB (shallow)
    public static GrupoHB toGrupoHB(Grupo grupo) {
        if (grupo == null) return null;
        GrupoHB grupoHB = new GrupoHB();
        grupoHB.setId(grupo.getId());
        grupoHB.setCiclo(grupo.getCiclo());
        grupoHB.setCurso(grupo.getCurso());
        return grupoHB;
    }

    // GrupoHB -> Grupo (shallow)
    public static Grupo toGrupo(GrupoHB grupoHB) {
        if (grupoHB == null) return null;
        Grupo g = new Grupo();
        g.setId(grupoHB.getId());
        g.setCiclo(grupoHB.getCiclo());
        g.setCurso(grupoHB.getCurso());
        return g;
    }
}


