/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tarea14.pool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.net.ConnectException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author mihai
 */
public class MyDataSource {

    private static final String DB_URL = "jdbc:mysql://37.187.37.143:3306/interfaces?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&serverTimezone=Europe/Madrid";
    private static final String DB_USER = "markpascani";
    private static final String DB_PASS = "markpascani";

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource = new HikariDataSource();

    static {
        config.setJdbcUrl(DB_URL);
        config.setUsername(DB_USER);
        config.setPassword(DB_PASS);
        config.addDataSourceProperty("maximumPoolSize", 1);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        
        dataSource = new HikariDataSource(config);
    }

    private MyDataSource() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
