/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.tarea14.modelo.dao.interfaces;

import com.mycompany.tarea14.modelo.entidades.Empleado;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author mihai
 */
public interface EmpleadoDAO {
        
    boolean add(Empleado empleado);
    
    Empleado getById(int id);
    
    List<Empleado> getAll();
    
    boolean update(Empleado emp);
    
    boolean delete(int id);
}
