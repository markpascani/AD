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
    
    /**
     * Inserta un alumno en la tabla Alumno
     * @param alumno
     * @return True si se ha insertado correctamente
     */
    boolean insertar(Alumno alumno);
    
    /**
     * Recupera un alumno de la base de datos si existe el id buscado
     * @param id
     * @return Alumno o null
     */
    Alumno obtenerPorId(int id);
    
    /**
     * Recupera de la base de datos todos los alumnos
     * @return Lista con todos los alumnos o null si no hay.
     */
    List<Alumno> obtenerTodos();
    
    /**
     * Actualiza los atributos de un alumno si existe en la tabla Alumno
     * @param alumno 
     * @return True si se ha actualizado correctamente.
     */
    boolean actualizar(Alumno alumno);
    
    /**
     * Busca un alumno por id y lo elimina si existe
     * @param id
     * @return True si se ha eliminado correctamente
     */
    boolean eliminarPorId(int id);
    
    /**
     * Marca el atributo Grupo de los alumnos cuyo Grupo coincida con el Ciclo y el Curso a null (los elimina de ese curso
     * pero no elimina los alumnos)
     * @param ciclo
     * @param curso
     * @return True si la operacion se ha realizado con exito.
     */
    boolean eliminarPorCurso(String ciclo, String curso);
}
