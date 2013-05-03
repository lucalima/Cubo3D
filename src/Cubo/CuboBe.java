/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cubo;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Lucas Lima
 */
public class CuboBe {

    private Connection conexao = null;

    public CuboBe() {
        try {
            conexao = new ConnectionFactory().getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    public void fechaConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void rollback() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarCubo(CuboVo cuboVo) {
        try {
            CuboDao dao = new CuboDao(conexao);
            dao.cadastrarCubo(cuboVo);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}