package com.example.bibliotheque.bean;

import java.sql.*;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDataSource {
    
    private static BasicDataSource ds = new BasicDataSource();
    
    static {
        // ds.setUrl("jdbc:postgresql://localhost:5432/bibliotheque");
        // ds.setUsername("postgres");
        // ds.setPassword("root");
        // ds.setMinIdle(5);
        // ds.setMaxIdle(10);
        // ds.setMaxOpenPreparedStatements(100);
        ds.setUrl("postgresql://postgres:VnyKHnoptbwhOCQbbtNieKEynFbhQnOn@viaduct.proxy.rlwy.net:27630/railway");
        ds.setUsername("postgres");
        ds.setPassword("VnyKHnoptbwhOCQbbtNieKEynFbhQnOn");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }
    
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
    
    private DBCPDataSource(){ }
}
