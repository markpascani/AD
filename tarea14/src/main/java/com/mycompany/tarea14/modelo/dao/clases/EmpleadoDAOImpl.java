/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea14.modelo.dao.clases;

import com.mycompany.tarea14.modelo.dao.interfaces.EmpleadoDAO;
import com.mycompany.tarea14.modelo.entidades.Empleado;
import com.mycompany.tarea14.pool.MyDataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mihai
 */
public class EmpleadoDAOImpl implements EmpleadoDAO {

    private static EmpleadoDAOImpl instance;

    static {
        instance = new EmpleadoDAOImpl();
    }

    private EmpleadoDAOImpl() {
    }

    ;
    
    public static EmpleadoDAOImpl getInstance() {
        return instance;
    }

    @Override
    public boolean add(Empleado empleado) {
        String sql = """
                     INSERT INTO Empleado(nombre, apellidos, fecha_nacimiento, puesto, email)
                     VALUES (?, ?, ?, ?, ?);
                     """;

        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, empleado.getNombre());
            pstm.setString(2, empleado.getApellidos());
            pstm.setDate(3, Date.valueOf(empleado.getFecha_nacimiento()));
            pstm.setString(4, empleado.getPuesto());
            pstm.setString(5, empleado.getEmail());

            return pstm.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("No se ha podido ejecutar la consulta. -> " + ex.getMessage());
            return false;
        }

    }

    @Override
    public Empleado getById(int id) {
        Empleado result = null;

        String sql = """
                     SELECT id_empleado, nombre, apellidos, fecha_nacimiento, puesto, email
                     FROM Empleado WHERE id_empleado = ?;
                     """;

        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);

            try (ResultSet rs = pstm.executeQuery()) {
                while (rs.next()) {
                    result = new Empleado();
                    result.setId_empleado(rs.getInt("id_empleado"));
                    result.setNombre(rs.getString("nombre"));
                    result.setApellidos(rs.getString("apellidos"));
                    result.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                    result.setPuesto(rs.getString("puesto"));
                    result.setEmail(rs.getString("email"));

                }
            }

        } catch (SQLException ex) {
            System.err.println("No se ha podido realizar la consulta -> " + ex.getMessage());
        }

        return result;
    }

    @Override
    public List<Empleado> getAll() {
        String sql = """
                     SELECT id_empleado, nombre, apellidos, fecha_nacimiento, puesto, email
                     FROM Empleado;
                     """;

        List<Empleado> result = new ArrayList<>();

        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
            Empleado emp;

            while (rs.next()) {
                emp = new Empleado();
                emp.setId_empleado(rs.getInt("id_empleado"));
                emp.setNombre(rs.getNString("nombre"));
                emp.setApellidos(rs.getNString("apellidos"));
                emp.setFecha_nacimiento((rs.getDate("fecha_nacimiento")).toLocalDate());
                emp.setPuesto(rs.getNString("puesto"));
                emp.setEmail(rs.getNString("email"));

                result.add(emp);
            }
        } catch (SQLException ex) {
            System.err.println("No se ha podido realizar la consulta ->" + ex.getMessage());
        }

        return result;
    }

    @Override
    public boolean update(Empleado emp) {
        String sql = """
                    UPDATE Empleado SET
                        nombre = ?, apellidos = ?,
                        fecha_nacimiento = ?,
                        puesto = ?, email = ?
                    WHERE id_empleado = ?
                    """;

        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setString(1, emp.getNombre());
            pstm.setString(2, emp.getApellidos());
            pstm.setDate(3, Date.valueOf(emp.getFecha_nacimiento()));
            pstm.setString(4, emp.getPuesto());
            pstm.setString(5, emp.getEmail());
            pstm.setInt(6, emp.getId_empleado());

            return pstm.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("No se ha podido realizar la consulta ->" + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Empleado WHERE id_empleado = ?";

        try (Connection connection = MyDataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)) {
            pstm.setInt(1, id);

            return pstm.executeUpdate() > 0;

        } catch (SQLException ex) {
            System.err.println("No se ha podido realizar la consulta ->" + ex.getMessage());
            return false;
        }
    }

}
