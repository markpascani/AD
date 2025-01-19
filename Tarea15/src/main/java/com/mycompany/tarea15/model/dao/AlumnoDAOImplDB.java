/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea15.model.dao;

import com.mycompany.tarea15.model.entidades.Alumno;
import com.mycompany.tarea15.model.util.MyDataSource;
import java.sql.Connection;
import java.sql.Date;
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
public class AlumnoDAOImplDB implements IAlumnoDAO {

    // Crear un logger estático específico para esta clase
    private static final Logger logger = LogManager.getLogger(AlumnoDAOImplDB.class);

    @Override
    public boolean insertarAlumno(Alumno alumno) {
        String sql = """
                     INSERT INTO Alumno (NIA, Nombre, Apellidos, Genero, FechaDeNacimiento, Ciclo, Curso, Grupo)
                     values (?, ?, ?, ?, ?, ?, ?, ?)
                     """;
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, alumno.getNia());
            pstm.setString(2, alumno.getNombre());
            pstm.setString(3, alumno.getApellidos());
            pstm.setString(4, String.valueOf(alumno.getGenero()));
            pstm.setDate(5, Date.valueOf(alumno.getFechaDeNacimiento()));
            pstm.setString(6, alumno.getCiclo());
            pstm.setString(7, alumno.getCurso());
            pstm.setInt(8, alumno.getGrupo());

            int affectedRows = pstm.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Alumno insertado correctamente" + alumno);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Error al buscar alumno:  " + alumno, e);
        }
        return false;

    }

    @Override
    public Alumno getAlumnoPorPK(int pk) {
        String sql = """
                     SELECT NIA, Nombre, Apellidos, Genero, FechaDeNacimiento, Ciclo, Curso, Grupo
                     FROM Alumno 
                     WHERE NIA = ?
                     """;
        Alumno alumno = new Alumno();
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, pk);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                alumno.setNia(rs.getInt("NIA"));
                alumno.setNombre(rs.getString("Nombre"));
                alumno.setApellidos(rs.getString("Apellidos"));
                alumno.setGenero(rs.getString("Genero").charAt(0));
                alumno.setFechaDeNacimiento(rs.getDate("FechaDeNacimiento").toLocalDate());
                alumno.setCiclo(rs.getString("Ciclo"));
                alumno.setCurso(rs.getString("Curso"));
                alumno.setGrupo(rs.getInt("Grupo"));
            }

        } catch (SQLException e) {
            logger.error("Error al buscar alumno:  " + pk, e);
        }
        return alumno;
    }

    @Override
    public List<Alumno> getAlumnos() {
        String sql = """
                 SELECT NIA, Nombre, Apellidos, Genero, FechaDeNacimiento, Ciclo, Curso, Grupo
                 FROM Alumno
                 """;
        List<Alumno> alumnos = new ArrayList<>();

        try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setNia(rs.getInt("NIA"));
                alumno.setNombre(rs.getString("Nombre"));
                alumno.setApellidos(rs.getString("Apellidos"));
                alumno.setGenero(rs.getString("Genero").charAt(0));
                alumno.setFechaDeNacimiento(rs.getDate("FechaDeNacimiento").toLocalDate());
                alumno.setCiclo(rs.getString("Ciclo"));
                alumno.setCurso(rs.getString("Curso"));
                alumno.setGrupo(rs.getInt("Grupo"));
                alumnos.add(alumno);
            }

        } catch (SQLException e) {
            logger.error("Error al buscar alumnos", e);
        }

        return alumnos;
    }

    @Override
    public boolean actualizarAlumnoPorPK(int pk, String nombre) {
        String sql = """
                 UPDATE Alumno
                 SET Nombre = ?
                 WHERE NIA = ?
                 """;
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, nombre);
            pstm.setInt(2, pk);

            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error al actualizar el alumno con PK: " + pk, e);
        }
        return false;
    }

    @Override
    public boolean eliminarAlumnoPorPK(int pk) {
        String sql = """
                 DELETE FROM Alumno
                 WHERE NIA = ?
                 """;
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, pk);

            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error al eliminar el alumno con PK: " + pk, e);
        }
        return false;
    }

    @Override
    public boolean eliminarAlumnosPorCurso(int grupo) {
        String sql = """
                 DELETE FROM Alumno
                 WHERE Grupo = ?
                 """;
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1,grupo);

            int rowsAffected = pstm.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.error("Error al eliminar alumnos con la palabra en el apellido: " + grupo, e);
        }
        return false;
    }

    @Override
    public boolean buscarAlumnoPorPk(int pk) {
        String sql = """
                     SELECT 1 
                     FROM Alumno
                     WHERE NIA = ?
                     """;
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, pk); 

            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            logger.error("Error al verificar existencia del alumno con NIA: " + pk, e);
        }
        return false;
    }


    
    
}
