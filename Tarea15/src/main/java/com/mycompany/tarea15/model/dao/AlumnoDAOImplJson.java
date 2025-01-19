/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea15.model.dao;

import com.mycompany.tarea15.model.entidades.Alumno;
import java.util.List;

/**
 *
 * @author mihai
 */
public class AlumnoDAOImplJson implements IAlumnoDAO {

    @Override
    public boolean insertarAlumno(Alumno alumno) {
        //TODO
        return true;
    }

    @Override
    public Alumno getAlumnoPorPK(int pk) {
        //TODO
        return null;
    }

    @Override
    public List<Alumno> getAlumnos() {
        //TODO
        return null;
    }

    @Override
    public boolean actualizarAlumnoPorPK(int pk, String nombre) {
        //TODO
        return true;
    }

    @Override
    public boolean eliminarAlumnoPorPK(int pk) {
        //TODO
        return true;
    }

    @Override
    public boolean buscarAlumnoPorPk(int pk) {
        //TODO
        return true;
    }

    @Override
    public boolean eliminarAlumnosPorCurso(int curso) {
        //TODO
        return true;
    }

}
