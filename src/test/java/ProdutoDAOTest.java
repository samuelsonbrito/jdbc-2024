import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.descompila.exception.ConexaoFalhouException;
import br.com.descompila.model.dao.ProdutoDAO;
import br.com.descompila.model.dto.Produto;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoDAOTest {
    private ProdutoDAO produtoDAO;

    @BeforeEach
    public void setUp() {
        produtoDAO = new ProdutoDAO();
    }

    @AfterEach
    public void tearDown() {

    }

    @Test
    @Order(1)
    public void testCriarProduto() throws ConexaoFalhouException {

        Produto produto = new Produto("Arroz", 1, 5.0);
        Produto produtoCriado = produtoDAO.salvar(produto);
        assertNotNull(produtoCriado);
        assertNotNull(produtoCriado.id());

    }

    @Test
    @Order(2)
    public void testBuscarProduto() throws ConexaoFalhouException {
        // Verificar se o produto foi criado no teste anterior
        Produto produto = new Produto("Arroz", 1, 5.0);
        Produto produtoCriado = produtoDAO.salvar(produto);

        // Buscar produto
        Produto produtoEncontrado = produtoDAO.buscarPorId(produtoCriado.id());
        
        // Verificar se o produto foi encontrado corretamente
        assertNotNull(produtoEncontrado);
        assertEquals(produtoCriado.id(), produtoEncontrado.id());
        assertEquals(produtoCriado.nome(), produtoEncontrado.nome());
        assertEquals(produtoCriado.quantidade(), produtoEncontrado.quantidade());
        assertEquals(produtoCriado.valor(), produtoEncontrado.valor(), 0.01);
    }

    @Test
    @Order(3)
    public void testAtualizarProduto() throws ConexaoFalhouException {
        // Verificar se o produto foi criado no teste anterior
        Produto produto = new Produto("Arroz", 1, 5.0);
        Produto produtoCriado = produtoDAO.salvar(produto);

        // Atualizar produto
        produtoCriado = new Produto(produtoCriado.id(), "Novo Nome", 20, 200.0);
        produtoDAO.atualizar(produtoCriado);

        // Verificar se o produto foi atualizado corretamente
        Produto produtoAtualizado = produtoDAO.buscarPorId(produtoCriado.id());
        assertNotNull(produtoAtualizado);
        assertEquals("Novo Nome", produtoAtualizado.nome());
        assertEquals(20, produtoAtualizado.quantidade());
        assertEquals(200.0, produtoAtualizado.valor(), 0.01);
    }

    @Test
    @Order(4)
    public void testExcluirProduto() throws ConexaoFalhouException {
        // Verificar se o produto foi criado no teste anterior
        Produto produto = new Produto("Arroz", 1, 5.0);
        Produto produtoCriado = produtoDAO.salvar(produto);

        // Excluir produto
        produtoDAO.excluir(produtoCriado.id());

        // Verificar se o produto foi excluído
        Produto produtoExcluido = produtoDAO.buscarPorId(produtoCriado.id());
        assertNull(produtoExcluido);
    }
}