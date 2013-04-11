/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Cubo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Lucas Lima
 */
public class CuboDao {

    private Connection conexao = null;

    public CuboDao(Connection conexao) {
        this.conexao = conexao;
    }

    public void cadastrarCubo(CuboVo cuboVo) throws SQLException {
        PreparedStatement ps = null;
        //SQL que será executado no banco de dados
        String sql =
                "		INSERT INTO TB_TIME_LINE "
                + "			(TEMPO, X, Y, Z) "
                + "		VALUES "
                + "			(?, ?, ?, ?)";
        ps = conexao.prepareStatement(sql);
        //Passando os parâmetros para o SQL que será executado

        //ps.setLong(1, cuboVo.getCodigo());
        ps.setLong(1, cuboVo.getTempo());
        ps.setLong(2, cuboVo.getX());
        ps.setLong(3, cuboVo.getY());
        ps.setLong(4, cuboVo.getZ());

        ps.execute();
    }

    private long gerarSequence(String nomeSequence) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        long codigo = 0;
        String sql = "SELECT " + nomeSequence + ".NEXTVAL AS CODIGO FROM DUAL";
        ps = conexao.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next()) {
            codigo = rs.getLong("CODIGO");
        }
        return codigo;

    }
}
