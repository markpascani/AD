/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea15.model.dao;

import com.mycompany.tarea15.model.entidades.Grupo;
import java.util.List;

/**
 *
 * @author mihai
 */
public interface IGrupoDAO {
    boolean insertarGrupo(Grupo grupo);
    List<Grupo> getGrupos();
    boolean buscarGrupoPorId(int id);
}
