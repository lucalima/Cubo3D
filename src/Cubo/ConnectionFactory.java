/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cubo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Luiz Brandão
 */
public class ConnectionFactory {

    // Fabrica de Conexoes com o Banco de dados
    public Connection getConnection() {
        try {
            // Driver do Banco Escolhido
            Class.forName("com.mysql.jdbc.Driver");
            // String de conexao com o banco e scolhido
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/cuboproject", "root", "");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
