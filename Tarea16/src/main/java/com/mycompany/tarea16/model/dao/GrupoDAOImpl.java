/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea16.model.dao;


import com.mycompany.tarea16.model.dao.interfaces.IGrupoDAO;
import com.mycompany.tarea16.model.dao.utils.MyDataSource;
import com.mycompany.tarea16.model.entities.Grupo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mihai
 */
public class GrupoDAOImpl implements IGrupoDAO {

    private static final Logger logger = LogManager.getLogger(GrupoDAOImpl.class);

    @Override
    public boolean insertar(Grupo entidad) {
        String sql = """
                     INSERT INTO Grupo ( Ciclo, Curso) 
                     value ( ?, ?)
                     """;

        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, entidad.getCiclo());
            pstm.setString(2, entidad.getCurso());

            int filasAfectadas = pstm.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = pstm.getGeneratedKeys()) {
                    if (rs.next()) {
                        entidad.setGrupo(rs.getInt(1)); // Recuperar el ID generado
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            logger.error("No se ha podido insertar el grupo" + entidad, e);
        }
        return false;
    }

    @Override
    public Grupo obtenerPorId(Integer id) {
        Grupo grupo = new Grupo();
        String sql = """
                     Select Grupo, Ciclo, Curso
                     FROM Grupo
                     WHERE Grupo = ?
                     """;
        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                grupo.setGrupo(rs.getInt("Grupo"));
                grupo.setCiclo(rs.getString("Ciclo"));
                grupo.setCurso(rs.getString("Curso"));
                return grupo;
            }
        } catch (SQLException e) {
            logger.error("No se puede obtener el grupo" + id, e);
        }
        return null;
    }

    @Override
    public List<Grupo> obtenerTodos() {
        List<Grupo> grupos = new ArrayList<>();
        String sql = """
                     SELECT Grupo, Ciclo, Curso
                     FROM Grupo
                     """;

        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Grupo grupo = new Grupo();
                grupo.setGrupo(rs.getInt("Grupo"));
                grupo.setCiclo(rs.getString("Ciclo"));
                grupo.setCurso(rs.getString("Curso"));

                grupos.add(grupo);
            }

        } catch (SQLException ex) {
            logger.error("No se han podido recuperar los grupos" + grupos, ex);
        }

        return grupos;
    }

    @Override
    public boolean actualizar(Grupo entidad) {
        String sql = """
                     UPDATE Grupo
                     SET Ciclo = ?, Curso = ?
                     WHERE Grupo = ?
                     """;

        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, entidad.getCiclo());
            pstm.setString(2, entidad.getCurso());
            pstm.setInt(3, entidad.getGrupo());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("No se ha podido actualizar el grupo" + entidad, e);
            return false;
        }
    }

    @Override
    public boolean eliminarPorId(Integer id) {
        String sql = """
                     DELETE FROM Grupo
                     WHERE Grupo = ?
                     """;
        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setInt(1, id);

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("No se ha podido actualizar el grupo" + id, e);
            return false;
        }
    }

}
