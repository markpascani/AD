/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea14.modelo.dao.interfaces;

import com.mycompany.tarea14.modelo.entidades.Alumno;
import java.util.List;

/**
 *
 * @author mihai
 */
public interface AlumnoDAO {

    boolean add(Alumno alumno);

    Alumno getById(int nia);

    List<Alumno> getAll();

    boolean update(Alumno alumno);

    boolean delete(int nia);
}
