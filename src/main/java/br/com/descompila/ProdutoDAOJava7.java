package br.com.descompila;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.descompila.connection.Conexao;
import br.com.descompila.model.dto.Produto;

public class ProdutoDAOJava7 {
    
    public Produto findById(int id) {

        String sql = "SELECT * FROM produtos WHERE id = ?";
        Produto produto = null;

        try (Connection conn = Conexao.obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getInt("quantidade"),
                        rs.getDouble("preco")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }
}
