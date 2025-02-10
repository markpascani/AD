/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea17.model.dao.interfaces;

import com.mycompany.tarea17.model.entities.Grupo;
import java.util.List;

/**
 *
 * @author Mihai Stinga
 */
public interface IGrupoDAO {

    boolean insertar(Grupo grupo);

    Grupo obtenerPorId(int id);

    List<Grupo> obtenerTodos();

    boolean actualizar(Grupo grupo);

    boolean eliminarPorId(int id);
}
