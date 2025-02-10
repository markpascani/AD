/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea17.model.dao.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Al ser dataSource estática y única, no se crean múltiples instancias del pool
 * de conexiones, lo cual es una de las principales ventajas del patrón
 * Singleton.
 *
 * @author mihai
 */
public class MyDataSource {

    /*
    Se declara como static para que exista solo una instancia de la conexión compartida entre todas las partes de la aplicación.
     */
    private static HikariDataSource dataSource;

    /*
        Crea un logger estático específico para esta clase
     */
    private static final Logger logger = LogManager.getLogger(MyDataSource.class);

    /*
    Bloque estático para inicializar dataSource
     */
    static {
        try (InputStream is = MyDataSource.class.getResourceAsStream("/db.properties")) {
            Properties props = new Properties();
            props.load(is);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.user"));
            config.setPassword(props.getProperty("db.pass"));
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("db.poolSize", "20")));
            config.setConnectionTimeout(Long.parseLong(props.getProperty("db.connectionTimeout")));
            config.setIdleTimeout(Long.parseLong(props.getProperty("db.idleTimeout")));
            config.setMaxLifetime(Long.parseLong(props.getProperty("db.maxLifetime")));
            

            dataSource = new HikariDataSource(config);
        } catch (IOException ex) {
            logger.error("Error al establecer el datasource"+ ex);
        }   
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
