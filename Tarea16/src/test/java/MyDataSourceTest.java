/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.tarea16.model.dao.utils.MyDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author mihai
 */
public class MyDataSourceTest {
     private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(MyDataSourceTest.class);
    
    public MyDataSourceTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
        System.out.println("Ejecutando configuracion inicial...");
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
        System.out.println("Finalizando pruebas...");
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
        System.out.println("Iniciando prueba");
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
        System.out.println("prueba completada");
    }


    @Test
    public void testConnection(){
        System.out.println("Probando conexion...");
        
        try(Connection connection = MyDataSource.getConnection()){
            assertNotNull(connection, "La conexion no debe ser nula.");
        } catch (SQLException ex) {
            logger.error("No se ha esteblecido conexion"+ ex);
        }

    }
}
