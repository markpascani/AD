/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea17.model.dao.interfaces;

import com.mycompany.tarea17.model.entities.Alumno;
import java.util.List;

/**
 *
 * @author Mihai Stinga
 */
public interface IAlumnoDAO {

    boolean insertar(Alumno entidad);

    Alumno obtenerPorId(int id);

    List<Alumno> obtenerTodos();

    boolean actualizar(Alumno entidad);

    boolean eliminarPorId(int id);
}
