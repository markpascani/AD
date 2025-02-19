/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.model.dao;

import com.mycompany.tarea17.model.dao.interfaces.IAlumnoDAO;
import com.mycompany.tarea17.model.dao.utils.MyDataSource;
import com.mycompany.tarea17.model.entities.Alumno;
import com.mycompany.tarea17.model.entities.Grupo;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author mihai
 */
public class AlumnoDAOImpl implements IAlumnoDAO {

    private static final Logger logger = LogManager.getLogger(AlumnoDAOImpl.class);

    @Override
    public boolean insertar(Alumno entidad) {
        String sql = """
                     INSERT INTO Alumno (Nombre, Apellidos, Genero, FechaDeNacimiento, Grupo)
                     values (?, ?, ?, ?, ?);
                     """;

        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, entidad.getNombre());
            pstm.setString(2, entidad.getApellidos());
            pstm.setString(3, String.valueOf(entidad.getGenero()));
            pstm.setDate(4, Date.valueOf(entidad.getFechaDeNacimiento()));
            if (entidad.getGrupo() != null) {
                pstm.setInt(5, entidad.getGrupo().getGrupo());
            } else {
                pstm.setNull(5, java.sql.Types.INTEGER);
            }

            int filasAfectadas = pstm.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = pstm.getGeneratedKeys()) {
                    if (rs.next()) {
                        entidad.setNia(rs.getInt(1)); // Recuperar el ID generado
                    }
                }
                return true;
            }
        } catch (SQLException ex) {
            logger.error("No se ha podido insertar el alumno" + entidad, ex);
        }
        return false;

    }

    @Override
    public Alumno obtenerPorId(int id) {
        Alumno alumno = new Alumno();
        Grupo grupo = new Grupo();
        String sqlAlumno = """
                     SELECT a.Nombre AS Nombre, a.Apellidos as Apellidos, a.Genero as Genero, a.FechaDeNacimiento as FechaDeNacimiento,
                           g.Grupo as Grupo, g.Ciclo as Ciclo, g.Curso as Curso
                     FROM Alumno a
                     LEFT JOIN Grupo g ON a.Grupo = g.Grupo
                     WHERE a.NIA = ?
                     """;

        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sqlAlumno)) {
            pstm.setInt(1, id);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                alumno.setNia(id);
                alumno.setNombre(rs.getString("Nombre"));
                alumno.setApellidos(rs.getString("Apellidos"));
                alumno.setGenero(rs.getString("Genero").charAt(0));
                alumno.setFechaDeNacimiento(rs.getDate("FechaDeNacimiento").toLocalDate());
                grupo.setGrupo(rs.getInt("Grupo"));
                grupo.setCiclo(rs.getString("Ciclo"));
                grupo.setCurso(rs.getString("Curso"));
                alumno.setGrupo(grupo);

                return alumno;
            }
        } catch (SQLException e) {
            logger.error("No se ha podido recuperar el alumno." + id, alumno, grupo, e);
        }

        return null;
    }

    @Override
    public List<Alumno> obtenerTodos() {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = """
                     SELECT a.NIA as NIA, a.Nombre AS Nombre, a.Apellidos as Apellidos, a.Genero as Genero, a.FechaDeNacimiento as FechaDeNacimiento,
                            g.Grupo as Grupo, g.Ciclo as Ciclo, g.Curso as Curso
                     FROM Alumno a
                     LEFT JOIN Grupo g ON g.Grupo = a.Grupo
                     """;
        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Alumno alumno = new Alumno();
                Grupo grupo = new Grupo();

                alumno.setNia(rs.getInt("NIA"));
                alumno.setNombre(rs.getString("Nombre"));
                alumno.setApellidos(rs.getString("Apellidos"));
                alumno.setGenero(rs.getString("Genero").charAt(0));
                alumno.setFechaDeNacimiento(rs.getDate("FechaDeNacimiento").toLocalDate());
                int idGrupo = rs.getInt("Grupo");
                if (idGrupo > 0) {
                    grupo.setGrupo(idGrupo);
                    grupo.setCiclo(rs.getString("Ciclo"));
                    grupo.setCurso(rs.getString("Curso"));
                    alumno.setGrupo(grupo);
                }

                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            logger.error("No se han podido recuperar los alumnos." + alumnos, e);
        }
        return alumnos;
    }

    @Override
    public boolean actualizar(Alumno entidad) {
        String sql = """
                     UPDATE Alumno
                     SET Nombre = ?, Apellidos = ?, Genero = ?, FechaDeNacimiento = ?, Grupo = ?
                     WHERE NIA = ?
                     """;

        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, entidad.getNombre());
            pstm.setString(2, entidad.getApellidos());
            pstm.setString(3, String.valueOf(entidad.getGenero()));
            pstm.setDate(4, Date.valueOf(entidad.getFechaDeNacimiento()));
            if (entidad.getGrupo() != null) {
                pstm.setInt(5, entidad.getGrupo().getGrupo());
            } else {
                pstm.setNull(5, java.sql.Types.INTEGER);
            }
            pstm.setInt(6, entidad.getNia());

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("No se ha podido actualizar." + entidad, e);
            return false;
        }
    }

    @Override
    public boolean eliminarPorId(int id) {
        String sql = """
                     DELETE FROM Alumno
                     WHERE NIA = ?
                     """;
        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setInt(1, id);

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("No se ha podido actualizar." + id, e);
            return false;
        }

    }

    @Override
    public boolean eliminarPorCurso(String ciclo, String curso) {
        String sql = """
                     UPDATE Alumno a SET a.Grupo = NULL
                     WHERE a.Grupo IN (SELECT g.Grupo FROM Grupo g 
                     WHERE g.Ciclo = ? AND g.Curso = ?)
                     """;

        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {

            pstm.setString(1, ciclo);
            pstm.setString(2, curso);

            return pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("No se ha podido realizar la operacion" + ciclo, curso, e);
            return false;
        }

    }

    @Override
    public boolean eliminarAlumnosCuyoApellidoContengaUnaPalabra(String palabra) {
        String sql = """
                     DELETE FROM Alumno WHERE Apellidos like "%?%"
                     """;
        
        try(Connection connection = MyDataSource.getConnection();
                PreparedStatement pstm = connection.prepareStatement(sql)){
            
            pstm.setString(1, palabra);            
            return pstm.executeUpdate() >= 0;
        }catch(SQLException e){
            logger.error("No se ha podido realizar la operación -> "+palabra, e);
            return false;
        }
    }

}
