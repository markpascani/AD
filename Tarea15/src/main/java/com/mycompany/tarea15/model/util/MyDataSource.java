/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea15.model.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author mihai
 */
public class MyDataSource {

    private static HikariDataSource dataSource;

    static {
        try (InputStream input = MyDataSource.class.getResourceAsStream("/db.properties")) {
            Properties props = new Properties();
            props.load(input);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.user"));
            config.setPassword(props.getProperty("db.pass"));
            config.setMaximumPoolSize(Integer.parseInt(props.getProperty("db.poolSize", "10")));

            dataSource = new HikariDataSource(config);
        } catch (IOException e) {
            throw new RuntimeException("Error cargando db.properties", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
