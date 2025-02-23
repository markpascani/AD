/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17_mihai.models.dao;

import com.mycompany.tarea17_mihai.models.entidades.Alumno;
import com.mycompany.tarea17_mihai.models.entidades.Grupo;
import com.mycompany.tarea17_mihai.models.utils.MyDataSource;
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
public class AlumnoJDBC implements IAlumnoDAO {

    @Override
    public void insertarAlumno(Alumno alumno) {
        String sql = "INSERT INTO Alumno (Nombre, Apellidos, Genero, FechaDeNacimiento, Grupo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getApellidos());
            stmt.setString(3, String.valueOf(alumno.getGenero()));
            stmt.setDate(4, Date.valueOf(alumno.getFechaDeNacimiento()));
            if (alumno.getGrupo() != null) {
                stmt.setInt(5, alumno.getGrupo().getId());
            } else {
                stmt.setNull(5, java.sql.Types.INTEGER);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void insertarAlumnos(List<Alumno> alumnos) {
        for (Alumno alumno : alumnos) {
            insertarAlumno(alumno);
        }
    }

    @Override
    public List<Alumno> obtenerTodosLosAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = """
        SELECT a.NIA, a.Nombre, a.Apellidos, a.Genero, a.FechaDeNacimiento, 
               g.Grupo, g.Ciclo, g.Curso
        FROM Alumno a
        LEFT JOIN Grupo g ON a.Grupo = g.Grupo
    """;

        try (Connection conn = MyDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Grupo grupo = null;
                if (rs.getInt("Grupo") != 0) { // Verifica si el grupo no es nulo
                    grupo = new Grupo(
                            rs.getInt("Grupo"),
                            rs.getString("Ciclo"),
                            rs.getString("Curso"),
                            new ArrayList<>()
                    );
                }

                alumnos.add(new Alumno(
                        rs.getInt("NIA"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getString("Genero").charAt(0),
                        rs.getDate("FechaDeNacimiento").toLocalDate(),
                        grupo
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    @Override
    public Alumno buscarAlumnoPorNIA(int nia) {
        String sql = "SELECT * FROM Alumno WHERE NIA = ?";
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nia);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Alumno(
                        rs.getInt("NIA"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getString("Genero").charAt(0),
                        rs.getDate("FechaDeNacimiento").toLocalDate(),
                        new Grupo(rs.getInt("Grupo"), "", "", new ArrayList<>())
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void actualizarAlumno(Alumno alumno) {
        String sql = "UPDATE Alumno SET Nombre=?, Apellidos=?, Genero=?, FechaDeNacimiento=?, Grupo=? WHERE NIA=?";
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getApellidos());
            stmt.setString(3, String.valueOf(alumno.getGenero()));
            stmt.setDate(4, Date.valueOf(alumno.getFechaDeNacimiento()));
            stmt.setInt(5, alumno.getGrupo().getId());
            stmt.setInt(6, alumno.getNIA());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarAlumnoPorNIA(int nia) {
        String sql = "DELETE FROM Alumno WHERE NIA = ?";
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nia);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertarGrupos(List<Grupo> grupos) {
        for (Grupo grupo : grupos) {
            String sql = "INSERT INTO Grupo (Ciclo, Curso) VALUES (?, ?)";
            try (Connection conn = MyDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, grupo.getCiclo());
                stmt.setString(2, grupo.getCurso());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Grupo> obtenerGrupos() {
        List<Grupo> grupos = new ArrayList<>();
        String sql = "SELECT * FROM Grupo";
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                grupos.add(new Grupo(
                        rs.getInt("Grupo"),
                        rs.getString("Ciclo"),
                        rs.getString("Curso"),
                        new ArrayList<>()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grupos;
    }

    @Override
    public Grupo obtenerGrupoPorId(int idGrupo) {
        String sql = "SELECT * FROM Grupo WHERE Grupo = ?";
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idGrupo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Grupo(
                        rs.getInt("Grupo"),
                        rs.getString("Ciclo"),
                        rs.getString("Curso"),
                        new ArrayList<>()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void eliminarAlumnosPorCoincidenciaDeApellido(String palabra) {
        String sql = "DELETE FROM Alumno WHERE Apellidos LIKE ?";
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + palabra + "%");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminarAlumnosPorCurso(String curso) {
        String sql = "DELETE FROM Alumno WHERE Grupo IN (SELECT Grupo FROM Grupo WHERE Curso = ?)";
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Alumno> obtenerAlumnosPorGrupo(int idGrupo) {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM Alumno WHERE Grupo = ?";
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idGrupo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alumnos.add(new Alumno(
                        rs.getInt("NIA"),
                        rs.getString("Nombre"),
                        rs.getString("Apellidos"),
                        rs.getString("Genero").charAt(0),
                        rs.getDate("FechaDeNacimiento").toLocalDate(),
                        new Grupo(idGrupo, "", "", new ArrayList<>())
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }

    @Override
    public void cambiarAlumnoDeGrupo(int nia, int nuevoGrupoId) {
        String sql = "UPDATE Alumno SET Grupo = ? WHERE NIA = ?";
        try (Connection conn = MyDataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nuevoGrupoId);
            stmt.setInt(2, nia);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
