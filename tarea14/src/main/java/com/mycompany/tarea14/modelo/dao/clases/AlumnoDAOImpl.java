/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea14.modelo.dao.clases;

import com.mycompany.tarea14.modelo.dao.interfaces.AlumnoDAO;
import com.mycompany.tarea14.modelo.entidades.Alumno;
import com.mycompany.tarea14.pool.MyDataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihai
 */
public class AlumnoDAOImpl implements AlumnoDAO{
     private static AlumnoDAOImpl instance;

    static {
        instance = new AlumnoDAOImpl();
    }

    private AlumnoDAOImpl() {
    }

    public static AlumnoDAOImpl getInstance() {
        return instance;
    }

    @Override
    public boolean add(Alumno alumno) {
        String sql = """
                INSERT INTO alumno
                (nombre, apellidos, genero, fechaNacimiento, ciclo, curso, grupo)
                VALUES (?, ?, ?, ?, ?, ?, ?);
                """;
        try (Connection conn = MyDataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, alumno.getNombre());
            pstm.setString(2, alumno.getApellidos());
            pstm.setString(3, String.valueOf(alumno.getGenero()));
            pstm.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            pstm.setString(5, alumno.getCiclo());
            pstm.setString(6, alumno.getCurso());
            pstm.setInt(7, alumno.getGrupo());

            return pstm.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al insertar alumno: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public Alumno getById(int nia) {
        Alumno alumno = null;
        String sql = """
                SELECT nia, nombre, apellidos, genero, fechaNacimiento,
                       ciclo, curso, grupo
                FROM alumno
                WHERE nia = ?;
                """;
        try (Connection conn = MyDataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, nia);

            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    alumno = new Alumno();
                    alumno.setNia(rs.getInt("nia"));
                    alumno.setNombre(rs.getString("nombre"));
                    alumno.setApellidos(rs.getString("apellidos"));
                    alumno.setGenero(rs.getString("genero").charAt(0));
                    alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                    alumno.setCiclo(rs.getString("ciclo"));
                    alumno.setCurso(rs.getString("curso"));
                    alumno.setGrupo(rs.getInt("grupo"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener alumno por ID: " + ex.getMessage());
        }
        return alumno;
    }

    @Override
    public List<Alumno> getAll() {
        List<Alumno> lista = new ArrayList<>();
        String sql = """
                SELECT nia, nombre, apellidos, genero, fechaNacimiento,
                       ciclo, curso, grupo
                FROM alumno;
                """;
        try (Connection conn = MyDataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql);
             ResultSet rs = pstm.executeQuery()) {

            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setNia(rs.getInt("nia"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setApellidos(rs.getString("apellidos"));
                alumno.setGenero(rs.getString("genero").charAt(0));
                alumno.setFechaNacimiento(rs.getDate("fechaNacimiento").toLocalDate());
                alumno.setCiclo(rs.getString("ciclo"));
                alumno.setCurso(rs.getString("curso"));
                alumno.setGrupo(rs.getInt("grupo"));

                lista.add(alumno);
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener la lista de alumnos: " + ex.getMessage());
        }
        return lista;
    }

    @Override
    public boolean update(Alumno alumno) {
        String sql = """
                UPDATE alumno
                SET nombre = ?, apellidos = ?, genero = ?, fechaNacimiento = ?,
                    ciclo = ?, curso = ?, grupo = ?
                WHERE nia = ?;
                """;
        try (Connection conn = MyDataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, alumno.getNombre());
            pstm.setString(2, alumno.getApellidos());
            pstm.setString(3, String.valueOf(alumno.getGenero()));
            pstm.setDate(4, Date.valueOf(alumno.getFechaNacimiento()));
            pstm.setString(5, alumno.getCiclo());
            pstm.setString(6, alumno.getCurso());
            pstm.setInt(7, alumno.getGrupo());
            pstm.setInt(8, alumno.getNia());

            return pstm.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al actualizar alumno: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int nia) {
        String sql = "DELETE FROM alumno WHERE nia = ?;";
        try (Connection conn = MyDataSource.getConnection();
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, nia);

            return pstm.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar alumno: " + ex.getMessage());
            return false;
        }
    }
}
