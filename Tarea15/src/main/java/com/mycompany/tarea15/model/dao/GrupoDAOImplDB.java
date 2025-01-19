/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea15.model.dao;

import com.mycompany.tarea15.model.entidades.Grupo;
import com.mycompany.tarea15.model.util.MyDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mihai
 */

public class GrupoDAOImplDB implements IGrupoDAO {
    // Crear un logger estático específico para esta clase
    private static final Logger logger = LogManager.getLogger(AlumnoDAOImplDB.class);

    @Override
    public boolean insertarGrupo(Grupo grupo) {
        String sql = """
                     INSERT INTO Grupo (Id, Nombre)
                     values (?, ?)
                     """;

        try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, grupo.getId());
            pstm.setString(2, grupo.getNombre());
            
            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("No se ha podido insertar el grupo. "+grupo,e);
        }
        return false;
    }

    @Override
    public List<Grupo> getGrupos() {
        String sql = """
                     SELECT Id, Nombre 
                     FROM Grupo
                     """;
        List<Grupo> grupos = new ArrayList<>();
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
                Grupo grupo = new Grupo();
                grupo.setId(rs.getInt("Id"));
                grupo.setNombre(rs.getString("Nombre"));
                
                grupos.add(grupo);
            }
        } catch (SQLException e) {
            logger.error("No se ha podido insertar el grupo. "+grupos,e);
        }
        return grupos;
    
    }

    @Override
    public boolean buscarGrupoPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
