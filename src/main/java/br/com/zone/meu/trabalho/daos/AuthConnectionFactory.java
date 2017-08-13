package br.com.zone.meu.trabalho.daos;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author daniel
 */
public class AuthConnectionFactory {

    private static AuthConnectionFactory connectionFactory;
    private final ComboPooledDataSource cpds;
    
    public static final String DRIVER = "org.postgresql.Driver";
    public static final String USUARIO = "postgres";
    public static final String URL = "jdbc:postgresql://localhost:5432/works";
    public static final String SENHA = "123456";
    
    private AuthConnectionFactory() throws IOException, SQLException, PropertyVetoException {
        
        cpds = new ComboPooledDataSource();
        cpds.setDriverClass(DRIVER);
        cpds.setJdbcUrl(URL);
        cpds.setUser(USUARIO);
        cpds.setPassword(SENHA);

        cpds.setMinPoolSize(3);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(5);
        cpds.setMaxStatements(50);

    }
    
    public static AuthConnectionFactory getInstance() throws IOException, SQLException, PropertyVetoException {
        if (connectionFactory == null) {
            connectionFactory = new AuthConnectionFactory();
            return connectionFactory;
        } else {
            return connectionFactory;
        }
    }

    public Connection getConnection() throws SQLException {
        return this.cpds.getConnection();
    }

}
