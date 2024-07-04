package br.com.descompila.model.dao;

import java.sql.*;

import br.com.descompila.connection.Conexao;
import br.com.descompila.exception.ConexaoFalhouException;
import br.com.descompila.model.dto.Produto;

public class ProdutoDAO {

    // Método para criar um novo produto
    public Produto criar(Produto produto) throws ConexaoFalhouException {
        var sql = "INSERT INTO produto (nome, quantidade, valor) VALUES (?, ?, ?)";
        try (var conn = Conexao.obterConexao();
            var stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, produto.nome());
                stmt.setInt(2, produto.quantidade());
                stmt.setDouble(3, produto.valor());
                int linhasAfetadas = stmt.executeUpdate();
        
            if (linhasAfetadas > 0) {
                try (var rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        var id = rs.getLong(1); // Retorna o ID gerado
                        var produtoInserido = new Produto(id, produto.nome(), produto.quantidade(), produto.valor());
                        return produtoInserido;
                    }
                } 
            }
        
            return null;
        } catch (SQLException e) {
            throw new ConexaoFalhouException(e);
        }
    }

    // Método para buscar um produto pelo ID
    public Produto buscarPorId(Long id) throws ConexaoFalhouException {
        Produto produto = null;
        String sql = "SELECT * FROM produto WHERE id = ?";
        try (Connection conn = Conexao.obterConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    produto = new Produto(rs.getLong("id"), rs.getString("nome"),
                            rs.getInt("quantidade"), rs.getDouble("valor"));
                }
            }
        } catch (SQLException e) {
            throw new ConexaoFalhouException(e);
        }
        return produto;
    }

    // Método para atualizar um produto
    public void atualizar(Produto produto) throws ConexaoFalhouException {
        try (Connection conn = Conexao.obterConexao()) {
            String sql = "UPDATE produto SET nome = ?, quantidade = ?, valor = ? WHERE id = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, produto.nome());
                stmt.setInt(2, produto.quantidade());
                stmt.setDouble(3, produto.valor());
                stmt.setLong(4, produto.id());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ConexaoFalhouException(e);
        }
    }

    // Método para excluir um produto
    public void excluir(Long id) throws ConexaoFalhouException {
        try (Connection conn = Conexao.obterConexao()) {
            String sql = "DELETE FROM produto WHERE id = ?";
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new ConexaoFalhouException(e);
        }
    }
}
