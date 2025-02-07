/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea16.model.dao.interfaces;

import java.util.List;

/**
 * contrato común para operaciones básicas de persistencia, como insertar, actualizar, eliminar y obtener entidades 
 * @author mihai
 * @param <T> Representa el tipo genérico de la entidad que se manipulará
 * @param <ID> Representa el tipo del identificador único de la entidad. (puede ser long, integer, String o el tipo que sea)
 */
public interface IGenericDAO<T, ID> {
    boolean insertar(T entidad);
    T obtenerPorId(ID id);
    List<T> obtenerTodos();
    boolean actualizar(T entidad);
    boolean eliminarPorId(ID id);
    
}
