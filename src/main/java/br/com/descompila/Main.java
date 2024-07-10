package br.com.descompila;

import java.io.Console;

import br.com.descompila.exception.ConexaoFalhouException;
import br.com.descompila.model.dao.ProdutoDAO;
import br.com.descompila.model.dto.Produto;


public class Main {

    static Console console = System.console();
    public static void main(String[] args) {

        int opcao;
        do {
            exibirMenu();
            opcao = Integer.parseInt(console.readLine());
            switch (opcao) {
                case 1 -> salvarProduto();
                case 2 -> buscarProdutoPorId();
                case 3 -> atualizarProduto();
                case 4 -> excluirProduto();
                case 5 -> System.out.println("Programa finalizado.");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        
    }

    private static void exibirMenu() {
        System.out.println("\n### Menu de Operações ###");
        System.out.println("1. Salvar novo produto");
        System.out.println("2. Buscar produto por ID");
        System.out.println("3. Atualizar produto");
        System.out.println("4. Excluir produto");
        System.out.println("5. Sair do programa");
        System.out.print("Escolha uma opção: ");
    }

    private static void salvarProduto() {
        System.out.println("\n### Criar Novo Produto ###");
        System.out.print("Nome do produto: ");
        String nome = console.readLine();

        System.out.print("Quantidade: ");
        int quantidade = Integer.parseInt(console.readLine());
        System.out.print("Valor: ");
        double valor = Double.parseDouble(console.readLine());
        Produto produto = new Produto(nome, quantidade, valor);
        ProdutoDAO produtoDAO = new ProdutoDAO();
        
        try {
            Produto produtoInserido = produtoDAO.criar(produto);
            if (produtoInserido != null) {
                System.out.println("Produto criado com sucesso:");
                System.out.println(produtoInserido);
            } else {
                System.out.println("Falha ao criar o produto.");
            }
        } catch (ConexaoFalhouException e) {
            System.err.println(e.getMessage());
        }
        
    }

    private static void buscarProdutoPorId() {
        System.out.println("\n### Buscar Produto por ID ###");
        System.out.print("Digite o ID do produto: ");
        long id = Long.parseLong(console.readLine());
        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto produto;
        try {
            produto = produtoDAO.buscarPorId(id);
       
            if (produto != null) {
                System.out.println("Produto encontrado:");
                System.out.println(produto);
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (ConexaoFalhouException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void atualizarProduto() {
        System.out.println("\n### Atualizar Produto ###");
        System.out.print("Digite o ID do produto que deseja atualizar: ");
        long id = Long.parseLong(console.readLine());
        ProdutoDAO produtoDAO = new ProdutoDAO();
        Produto produtoExistente;
        try {
            produtoExistente = produtoDAO.buscarPorId(id);
        
            if (produtoExistente != null) {
                System.out.print("Novo nome (atual: " + produtoExistente.nome() + "): ");
                String nome = console.readLine();
                System.out.print("Nova quantidade (atual: " + produtoExistente.quantidade() + "): ");
                int quantidade = Integer.parseInt(console.readLine());
                System.out.print("Novo valor (atual: " + produtoExistente.valor() + "): ");
                double valor = Double.parseDouble(console.readLine());
                Produto produtoAtualizado = new Produto(id, nome, quantidade, valor);
                produtoDAO.atualizar(produtoAtualizado);
                System.out.println("Produto atualizado com sucesso.");
            } else {
                System.out.println("Produto não encontrado.");
            }

        } catch (ConexaoFalhouException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void excluirProduto() {
        System.out.println("\n### Excluir Produto ###");
        System.out.print("Digite o ID do produto que deseja excluir: ");
        long id = Long.parseLong(console.readLine());
        ProdutoDAO produtoDAO = new ProdutoDAO();
        try {
            produtoDAO.excluir(id);
        } catch (ConexaoFalhouException e) {
            System.err.println(e.getMessage());
        }
        System.out.println("Produto excluído com sucesso.");
    }
}