package br.com.descompila;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.descompila.connection.Conexao;
import br.com.descompila.model.dto.Produto;

public class ProdutoDAOJava6 {
    
    public Produto findById(int id) {
        String sql = "SELECT * FROM produtos WHERE id = ?";
        Produto produto = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexao.obterConexao();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                produto = new Produto(
                    rs.getLong("id"),
                    rs.getString("nome"),
                    rs.getInt("quantidade"),
                    rs.getDouble("preco")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {

            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return produto;
    }
}
